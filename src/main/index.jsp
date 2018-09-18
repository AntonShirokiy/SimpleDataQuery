<%-- 
    Document   : queryIndex
    Created on : 30.07.2018, 16:49:21
    Author     : shirokiy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${survey.name}</title>
        <%@include file="sources.jsp" %>
    </head>
    <body style="position: relative;">
        <div class="container">
            <div><h4>Запрос</h4> <div class="alert alert-danger">
                    Внимание! Пожалейте браузер,ограничте выборку вменяемым количеством выводимых записей.
                </div>
                <form method="POST" target="result">                    
                    <textarea class="form-control" name="query"></textarea>
                    <br>
                    <button type="submit" name="type" class=" btn btn-primary" value="html" onclick="window.frames['result'].document.open();window.frames['result'].document.write('Данные загружаются');window.frames['result'].document.close();">Таблица</button>
                    <button type="submit" name="type" class="btn btn-success" value="xlsx" onclick="window.frames['result'].document.open();window.frames['result'].document.write('Файл формируется');window.frames['result'].document.close();">Excel</button>
                </form>
               
            </div>
            <div style="position:fixed; top:300px; bottom:0px; right:0px; left:0px;">
                <iframe name="result" id="result" style="position:absolute; right:0px; bottom:0px; left:0px; top:0px; width: 100%; height:100%; background-color: transparent; overflow: auto;  border: none;">
                </iframe>
            </div>
        </div>
    </body>
</html>
