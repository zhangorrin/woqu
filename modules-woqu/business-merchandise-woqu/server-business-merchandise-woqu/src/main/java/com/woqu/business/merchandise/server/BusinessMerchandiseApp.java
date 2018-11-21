package com.woqu.business.merchandise.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author orrin on 2018/11/21
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@ComponentScan(value = "com.woqu")
public class BusinessMerchandiseApp {
    public static void main(String[] args) {
        SpringApplication.run(BusinessMerchandiseApp.class, args);
    }
}
