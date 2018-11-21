package com.woqu.business.merchandise.server.web;

import com.woqu.business.merchandise.client.Merchandise;
import com.woqu.business.merchandise.server.service.MerchandiseService;
import com.woqu.business.price.client.Price;
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
public class MerchandiseWebHandler {

    @Autowired
    private MerchandiseService merchandiseService;

    public Mono<ServerResponse> ready(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(true));
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        String merchandiseId = request.pathVariable("merchandiseId");
        Mono<Merchandise> result = merchandiseService.get(merchandiseId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result, Merchandise.class);
    }

    public Mono<ServerResponse> price(ServerRequest request) {
        String merchandiseId = request.pathVariable("merchandiseId");
        Mono<Price> result = merchandiseService.price(merchandiseId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result, Price.class);
    }
}
