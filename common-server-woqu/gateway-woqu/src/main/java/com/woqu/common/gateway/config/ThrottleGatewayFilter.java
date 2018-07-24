package com.woqu.common.gateway.config;

import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @author migu-orrin on 2018/6/27 027.
 */
public class ThrottleGatewayFilter implements GatewayFilter {

	private int capacity;
	private int refillTokens;
	private int refillPeriod;
	private TimeUnit refillUnit;
	private TokenBucket tokenBucket;

	public int getCapacity() {
		return capacity;
	}

	public ThrottleGatewayFilter setCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public int getRefillTokens() {
		return refillTokens;
	}

	public ThrottleGatewayFilter setRefillTokens(int refillTokens) {
		this.refillTokens = refillTokens;
		return this;
	}

	public int getRefillPeriod() {
		return refillPeriod;
	}

	public ThrottleGatewayFilter setRefillPeriod(int refillPeriod) {
		this.refillPeriod = refillPeriod;
		return this;
	}

	public TimeUnit getRefillUnit() {
		return refillUnit;
	}

	public ThrottleGatewayFilter setRefillUnit(TimeUnit refillUnit) {
		this.refillUnit = refillUnit;
		return this;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		if(tokenBucket == null) {
			tokenBucket = TokenBuckets.builder()
					.withCapacity(capacity)
					.withFixedIntervalRefillStrategy(refillTokens, refillPeriod, refillUnit)
					.build();
		}

		//TODO: get a token bucket for a key
		System.out.println("TokenBucket capacity: " + tokenBucket.getCapacity());
		boolean consumed = tokenBucket.tryConsume();
		System.out.println("TokenBucket consumed: " + consumed);
		if (consumed) {
			return chain.filter(exchange);
		}
		exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
		return exchange.getResponse().setComplete();
	}


}
