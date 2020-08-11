package com.francis.cors.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.lang.reflect.Field;

/**
 * @author: francis
 * @description:
 * @date: 2020/8/11 21:26
 */
@Configuration
public class CorsConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${cors.allowedOrigins:*}")
    private String allowedOrigins;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        for (String origin : allowedOrigins.split(",")) {
            if (StringUtils.isNotBlank(origin)) {
                corsConfiguration.addAllowedOrigin(origin);
            }
        }
        return corsConfiguration;
    }

    private UrlBasedCorsConfigurationSource buildSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(buildSource());
    }


    private final String interestedKey = "cors.allowedOrigins";

    @ApolloConfigChangeListener(interestedKeys = { interestedKey })
    private void onChange(ConfigChangeEvent changeEvent) {
        allowedOrigins = changeEvent.getChange(interestedKey).getNewValue();
        CorsFilter corsFilter = applicationContext.getBean("corsFilter", CorsFilter.class);
        try {
            final Field configSource = corsFilter.getClass().getDeclaredField("configSource");
            configSource.setAccessible(true);
            configSource.set(corsFilter, buildSource());
        } catch (Exception e) {}
    }
}
