package com.noah.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubbo
@Slf4j
public class DubboAsyncApplication {

    public static void main(String[] args) {
        log.info("=======启动默认=======");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DubboAsyncApplication.class, args);
    }
}
