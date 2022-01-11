package com.noah.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Async
@Slf4j
@Service
public class AsyncHelloService {

    public void asyncHello() {
        log.info("" + Thread.currentThread().getName());
    }
}
