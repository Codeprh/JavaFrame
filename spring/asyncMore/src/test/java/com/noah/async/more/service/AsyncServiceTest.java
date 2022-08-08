package com.noah.async.more.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class AsyncServiceTest {

    @Resource
    AsyncService asyncService;

    @Test
    void doAsync() {
        String doAsync = asyncService.doAsync();
        log.info("doAsync:{}", doAsync);
        log.info("hello world");
    }
}