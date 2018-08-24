package com.woqu.common.gateway.config;

import com.woqu.framework.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author orrin on 2018/7/3
 * 自定义全局过滤器
 */
@Configuration
public class ExceptionGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionGlobalFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



        Mono<Void> result = null;
        try {
            result = chain.filter(exchange);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("ExceptionGlobalFilter error = {}", ex.getMessage());

            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            exchange.getAttributes().put(ServerWebExchangeUtils.CLIENT_RESPONSE_ATTR, ApiResponse.error(ex.getMessage()));
            exchange.getResponse().setComplete();
        }

        return result;
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}
