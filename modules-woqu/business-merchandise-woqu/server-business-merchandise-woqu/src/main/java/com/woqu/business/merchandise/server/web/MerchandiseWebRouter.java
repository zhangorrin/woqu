package com.woqu.business.merchandise.server.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author orrin on 2018/11/21
 */
@Configuration
public class MerchandiseWebRouter {
    @Bean
    public RouterFunction<ServerResponse> route(MerchandiseWebHandler merchandiseWebHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/ready")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        merchandiseWebHandler::ready)
                .andRoute(RequestPredicates.GET("/merchandise/{merchandiseId}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        merchandiseWebHandler::get)
                .andRoute(RequestPredicates.GET("/merchandise/{merchandiseId}/price")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        merchandiseWebHandler::price);
    }
}
