package com.woqu.business.b.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author orrin on 2018/7/4
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.woqu")
@EnableHystrix
@ComponentScan(value = "com.woqu")
public class BusinessBApp {
    public static void main(String[] args) {
        SpringApplication.run(BusinessBApp.class, args);
    }
}
