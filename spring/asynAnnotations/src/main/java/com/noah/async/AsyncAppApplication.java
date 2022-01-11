package com.noah.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncAppApplication.class, args);
    }
}
