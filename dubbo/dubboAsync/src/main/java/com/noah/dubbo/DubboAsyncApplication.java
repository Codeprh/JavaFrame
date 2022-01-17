package com.noah.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboAsyncApplication.class, args);
    }
}
