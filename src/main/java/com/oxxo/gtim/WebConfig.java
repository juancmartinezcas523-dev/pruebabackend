package com.oxxo.gtim;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${web.assets.pattern}")
    private String assetsPattern;

    @Value("${web.assets.directory}")
    private String assetsDirectory;

    @Value("${web.jsp.filePrefix}")
    private String jspFilePrefix;

    @Value("${web.jsp.fileSuffix}")
    private String jspFileSuffix;
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Add the client URL here
                //.allowedOriginPatterns("https://*.femcom.net*","http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                //.allowedHeaders("Content-Type", "X-Requested-With","accept","Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                //.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Headers")
                .allowCredentials(true)
                .maxAge(3600);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(assetsPattern).addResourceLocations(assetsDirectory);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/test").setViewName("test");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(jspFilePrefix);
        viewResolver.setSuffix(jspFileSuffix);
        return viewResolver;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonBuilder() {
        return b -> b.serializerByType(LocalDateTime.class, new LocalDateTimeConverter());
    }
}
