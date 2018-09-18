/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kudrovo.simpledataquery.config;

import javax.servlet.Filter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/** Инициализация веб приложения.
 *
 * @author shirokiy
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return  null;
    
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {        
        return  new Class<?>[]{ApplicationConfiguration.class,WebApplicationConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    
    /** Устанавливем кодировку UTF8
     *  
     * @return 
     */
    @Override
    protected Filter[] getServletFilters() {
        final CharacterEncodingFilter encFilter = new CharacterEncodingFilter();
        encFilter.setEncoding("UTF-8");
        encFilter.setForceEncoding(true);
        Filter [] singleton = { encFilter };
        return singleton;
    }

    
}
