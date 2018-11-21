package com.woqu.business.price.server.web;

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
public class PriceWebRouter {
    @Bean
    public RouterFunction<ServerResponse> route(PriceWebHandler priceWebHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/ready")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        priceWebHandler::ready)
                .andRoute(RequestPredicates.GET("/price/{merchandiseId}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        priceWebHandler::get)
                .andRoute(RequestPredicates.GET("/price/{merchandiseId}/{userId}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        priceWebHandler::getByUserId);
    }
}
