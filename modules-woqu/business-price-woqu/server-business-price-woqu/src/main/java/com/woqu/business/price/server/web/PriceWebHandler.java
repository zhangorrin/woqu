package com.woqu.business.price.server.web;

import com.woqu.business.price.client.Price;
import com.woqu.business.price.server.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author orrin on 2018/11/21
 */
@Component
public class PriceWebHandler {

    @Autowired
    private PriceService priceService;

    public Mono<ServerResponse> ready(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(true));
    }

    public Mono<ServerResponse> getByUserId(ServerRequest request) {
        String merchandiseId = request.pathVariable("merchandiseId");
        String userId = request.pathVariable("userId");
        Mono<Price> result = priceService.getByUserId(merchandiseId, userId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result, Price.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        String merchandiseId = request.pathVariable("merchandiseId");
        Mono<Price> result = priceService.get(merchandiseId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result, Price.class);
    }
}
