package com.woqu.common.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author migu-orrin on 2018/6/27 027.
 */
@Configuration
public class RouteConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneGlobalFilter.class);

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		LOGGER.debug(" start customRouteLocator ");
		return builder.routes()
				.build();
	}

}
