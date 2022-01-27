package com.noah.dubbo.alibaba;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@Slf4j
public class ProviderBootstrap {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProviderBootstrap.class, args);
    }
}
