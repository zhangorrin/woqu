package com.woqu.business.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author orrin on 2018/9/12
 */
@Configuration
public class ReadinessRouter {
    @Bean
    public RouterFunction<ServerResponse> routeCity(ReadinessHandler readinessHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/v2/ready")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        readinessHandler::ready);
    }
}
