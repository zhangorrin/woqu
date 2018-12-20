package com.woqu.business.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author orrin on 2018/9/12
 */
@Component
public class ReadinessHandler {

   @Value("version")
   private String version;

    public Mono<ServerResponse> ready(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(true + " " + version));
    }
}
