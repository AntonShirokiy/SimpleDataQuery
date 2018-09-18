package ru.kudrovo.simpledataquery.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
/** 
 *  
 * @author shirokiy
 */

@Configuration
@PropertySource("classpath:application.properties")        
public class ApplicationConfiguration {

    @Autowired    
    private Environment environment;
        
    @Bean     
    public DataSource dataSourceProduction() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(environment.getProperty("db.pool"));
    }

        
}
