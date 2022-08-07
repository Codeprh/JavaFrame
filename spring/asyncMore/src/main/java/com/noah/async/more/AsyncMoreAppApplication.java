package com.noah.async.more;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncMoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncMoreAppApplication.class, args);
    }
}
