package ru.kudrovo.simpledataquery.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/** КОНФИГУРАЦИЯ SPRING-MVC
 * 
 * @author shirokiy
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.kudrovo.simpledataquery.core.web")
@PropertySources({     
        @PropertySource("classpath:application.properties")  
})
public class WebApplicationConfiguration extends WebMvcConfigurerAdapter{
    /** РЕСУРСЫ ПРОКИНУТЫЕ В ИНТЕРФЕЙС БЕЗ ОБРАБОТКИ
     * 
     * @param registry 
     */
    @Override        
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
    
    /** НАСТРОЙКА РАСПОЛОЖЕНИЯ ОТОБРАЖЕНИЙ
     * 
     * @return 
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
                
    }
}
