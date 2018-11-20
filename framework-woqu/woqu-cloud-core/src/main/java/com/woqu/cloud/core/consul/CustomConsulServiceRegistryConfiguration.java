package com.woqu.cloud.core.consul;

import com.ecwid.consul.v1.ConsulClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration;
import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author orrin on 2018/11/20
 */
@Configuration
@ConditionalOnConsulEnabled
@ConditionalOnProperty(value = "spring.cloud.service-registry.enabled", matchIfMissing = true)
@AutoConfigureBefore(ServiceRegistryAutoConfiguration.class)
public class CustomConsulServiceRegistryConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomConsulServiceRegistryConfiguration.class);

    @Autowired(required = false)
    private TtlScheduler ttlScheduler;

    @Bean
    public ConsulServiceRegistry consulServiceRegistry(ConsulClient consulClient, ConsulDiscoveryProperties properties,
                                                        HeartbeatProperties heartbeatProperties) {
        return new CustomConsulServiceRegistry(consulClient, properties, ttlScheduler, heartbeatProperties);
    }

}