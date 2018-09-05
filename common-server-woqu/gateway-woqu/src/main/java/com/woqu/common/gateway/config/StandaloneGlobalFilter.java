package com.woqu.common.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woqu.model.ApiResponse;
import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author orrin on 2018/7/3
 * 自定义全局过滤器
 */
@Configuration
@RefreshScope
@EnableConfigurationProperties(StandaloneBucketProperties.class)
public class StandaloneGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneGlobalFilter.class);


    @Autowired
    private StandaloneBucketProperties standaloneBucketProperties;

    private static final TimeUnit refillUnit = TimeUnit.SECONDS;
    private TokenBucket tokenBucket;

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    /**
     * 当 CPU 使用率高于某个阈值就开启限流，否则不开启限流
     */
    private static final String METRIC_NAME = "system.cpu.usage";
    private static final double MAX_USAGE = 0.90D;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        /**
         * 基于系统负载的动态限流
         * 当 CPU 使用率高于某个阈值就开启限流，否则不开启限流
         */
        Double systemCpuUsage = metricsEndpoint.metric(METRIC_NAME, null)
                .getMeasurements()
                .stream().filter(Objects::nonNull)
                .findFirst()
                .map(MetricsEndpoint.Sample::getValue)
                .filter(Double::isFinite)
                .orElse(0.1);

        LOGGER.info("systemCpuUsage={}", systemCpuUsage);
        boolean cpuOk = systemCpuUsage < MAX_USAGE;
        LOGGER.info("cpuOk={}", cpuOk);

        boolean consumed = true;

        if (standaloneBucketProperties != null && standaloneBucketProperties.shouldWork()) {
            if (tokenBucket == null) {
                tokenBucket = TokenBuckets.builder()
                        .withCapacity(standaloneBucketProperties.getCapacity())
                        .withFixedIntervalRefillStrategy(standaloneBucketProperties.getRefillTokens(), standaloneBucketProperties.getRefillPeriod(), refillUnit)
                        .build();
            }
            LOGGER.info("TokenBucket capacity: " + tokenBucket.getCapacity());
            consumed = tokenBucket.tryConsume();
            LOGGER.info("TokenBucket consumed: " + consumed);
        }

        if (!consumed || !cpuOk) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = null;
            try {
                bytes = mapper.writeValueAsBytes(ApiResponse.error("请求过于频繁，请稍后重试"));
            } catch (JsonProcessingException e) {
                bytes = ("{\"code\":500,\"data\":null,\"message\":\"请求过于频繁，请稍后重试\"}").getBytes();
                e.printStackTrace();
            }

            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }


    /**
     * 自定义限流标志的key，多个维度可以从这里入手
     * exchange对象中获取服务ID、请求信息，用户信息等
     */
    @Bean
    KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
