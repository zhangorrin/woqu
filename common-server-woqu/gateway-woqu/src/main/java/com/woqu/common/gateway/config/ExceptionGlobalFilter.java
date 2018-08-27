package com.woqu.common.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woqu.framework.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
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

        Mono<Void> result = chain.filter(exchange)
                .onErrorResume(ex -> {
                    ex.printStackTrace();
                    LOGGER.error("ExceptionGlobalFilter error = {}", ex.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

                    ObjectMapper mapper = new ObjectMapper();
                    byte[] bytes = null;
                    try {
                        bytes = mapper.writeValueAsBytes(ApiResponse.error(ex.getMessage()));
                    } catch (JsonProcessingException e) {
                        bytes = ("{\"code\":500,\"data\":null,\"message\":\"" + ex.getMessage() + "\"}").getBytes();
                        e.printStackTrace();
                    }

                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    return exchange.getResponse().writeWith(Flux.just(buffer));
                });

        return result;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
