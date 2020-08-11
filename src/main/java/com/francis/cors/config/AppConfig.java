package com.francis.cors.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: francis
 * @description:
 * @date: 2020/8/11 21:27
 */
//@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Value("${cors.allowedOrigins:*}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins.split(","))
                .allowCredentials(true);
    }
}
