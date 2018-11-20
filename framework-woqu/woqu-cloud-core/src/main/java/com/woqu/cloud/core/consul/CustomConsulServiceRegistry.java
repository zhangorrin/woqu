package com.woqu.cloud.core.consul;

import com.ecwid.consul.v1.ConsulClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;

/**
 * @author orrin on 2018/11/20
 */
public class CustomConsulServiceRegistry extends ConsulServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomConsulServiceRegistry.class);

    public CustomConsulServiceRegistry(ConsulClient client, ConsulDiscoveryProperties properties, TtlScheduler ttlScheduler, HeartbeatProperties heartbeatProperties) {
        super(client, properties, ttlScheduler, heartbeatProperties);
    }

    @Override
    public void register(ConsulRegistration reg) {
        LOGGER.info("new service id = {}", reg.getService().getName() + "-" + reg.getService().getAddress() + "-" + reg.getService().getPort());
        reg.getService().setId(reg.getService().getName() + "-" + reg.getService().getAddress() + "-" + reg.getService().getPort());
        super.register(reg);
    }

}