package com.woqu.business.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author orrin on 2018/9/12
 */
@SpringBootApplication
@ComponentScan(value = "com.woqu")
public class DemoBusinessApp {
    public static void main(String[] args) {
        SpringApplication.run(DemoBusinessApp.class, args);
    }
}
