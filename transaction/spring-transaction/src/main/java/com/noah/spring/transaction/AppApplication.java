package com.noah.spring.transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述:
 * 启动类1
 *
 * @author Noah
 * @create 2021-11-15 4:45 下午
 */
@SpringBootApplication
@MapperScan("com.noah.spring.transaction.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
