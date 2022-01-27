package com.noah.dubbo.alibaba;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDubbo
@SpringBootApplication
public class ConsumeBootstrap {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(ConsumeBootstrap.class, args);

    }
}
