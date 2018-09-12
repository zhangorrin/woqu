package com.woqu.core.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author orrin on 2018/9/5
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        //corsConfiguration.addExposedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        PathPatternParser pathPatternParser = new PathPatternParser();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(pathPatternParser);
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsWebFilter(source);
    }

}
