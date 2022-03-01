package com.noah.redisson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class RedisApplication {
    public static void main(String[] args) {
        log.info("=======启动默认=======");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RedisApplication.class, args);
    }
}
