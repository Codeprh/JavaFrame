package com.noah.spring.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@Configuration
@EnableRetry
public class SpringRetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRetryApplication.class, args);
    }
}
