package com.noah.lock.transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.noah.lock.transaction.mapper")
public class LockTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockTransactionApplication.class, args);
    }
}
