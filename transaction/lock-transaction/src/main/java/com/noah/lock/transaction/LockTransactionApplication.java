package com.noah.lock.transaction;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableAsync
@MapperScan("com.noah.lock.transaction.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class LockTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockTransactionApplication.class, args);
    }

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RestTemplate restTemplate = restTemplateBuilder.setReadTimeout(Duration.ofSeconds(2)).setConnectTimeout(Duration.ofSeconds(2)).build();
        //RestTemplate restTemplate =httpClientBuilder.build();
        return restTemplate;
    }
}
