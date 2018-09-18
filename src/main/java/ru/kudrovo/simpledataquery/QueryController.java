package ru.kudrovo.simpledataquery.core.web;

import java.io.IOException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.servlet.ServletOutputStream;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/** Контроллер реализуется потоковый вывод из бд в виде html и xlsx.
 *  Попытка поддержки единоразовой выгрузки больших объемов данных.  
 *
 * @author shirokiy
 */
@Controller
public class QueryController {

    @Autowired
    DataSource dataSource;

    @RequestMapping(path = {"/"}, method = {RequestMethod.GET})
    public String setAnswer() {
        return "index";
    }

    @RequestMapping(path = {"/"}, method = {RequestMethod.POST})
    public void getResult(@RequestParam(name = "query") String query, @RequestParam(name = "type") String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (type) {
            case "xlsx":
                getXLSX(query, request, response);
                break;
            default:
                getHtml(query, request, response);

        }

    }

    private void getHtml(String query, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (PrintWriter out = response.getWriter();) {

            out.print("<html><head> <link type=\"text/css\" rel=\"stylesheet\" href=\"" + request.getServletContext().getContextPath() + "/static/bootstrap/css/bootstrap.min.css\"/>"
                    + "</head><body><div class=\"container\"><table class=\"table table-striped\">");

            try (
                    Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);) {
                out.print("<thead><tr><th>№</th>");
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    out.print("<th>");
                    out.print(resultSet.getMetaData().getColumnName(i));
                    out.print("</th>");
                }
                out.print("</tr></thead><tbody>");
                while (resultSet.next()) {
                    out.print("<tr>");
                    out.print("<td>");
                    out.print(resultSet.getRow());
                    out.print("</td>");
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        out.print("<td>");
                        out.print(resultSet.getString(i));
                        out.println("</td>");
                    }
                    out.println("</tr>");
                }
                out.println("</tbody><table>");

            } catch (Exception ex) {
                out.print("</tbody><table>");
                out.print("<pre>");
                ex.printStackTrace(out);
                out.print("</pre>");
            }

            out.print("</div></body>");
            out.print("</html>");
        }
    }

    private void getXLSX(String query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=\"repopt_" + java.time.LocalDateTime.now().toString() + ".xlsx\"");
        try (ServletOutputStream out = response.getOutputStream();) {

            try (
                    Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);) {
                SXSSFWorkbook wb = new SXSSFWorkbook(-1);
                Sheet sh = wb.createSheet();
                Row head = sh.createRow(0);
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    Cell cell = head.createCell(i - 1);
                    cell.setCellValue(resultSet.getMetaData().getColumnName(i));
                }

                while (resultSet.next()) {
                    Row row = sh.createRow(resultSet.getRow());
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        Cell cell = row.createCell(i - 1);
                        cell.setCellValue(resultSet.getString(i));
                    }
                    if (resultSet.getRow() % 100 == 0) {
                        ((SXSSFSheet) sh).flushRows(100);
                    }
                }

                wb.write(out);
                wb.dispose();

            } catch (Exception ex) {
                out.print(ex.getMessage());
            }
        }

    }

}
