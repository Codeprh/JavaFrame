package com.noah.spring.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@Configuration
@EnableRetry
public class SpringCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCodeApplication.class, args);
    }
}
