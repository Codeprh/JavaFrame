package com.noah.async.more.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncService {

    @Async
    public String doAsync() {

        String threadName = Thread.currentThread().getName();
        log.info(threadName);

        return threadName;
    }
}
