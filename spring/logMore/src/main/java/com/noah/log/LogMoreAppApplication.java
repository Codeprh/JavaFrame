package com.noah.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LogMoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogMoreAppApplication.class, args);
    }
}
