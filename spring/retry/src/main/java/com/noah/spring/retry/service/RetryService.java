package com.noah.spring.retry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RetryService {

    @Retryable(RemoteAccessException.class)
    public void service() {
        log.info("do retry service");
        dosomething();
    }

    private void dosomething() {
        throw new RemoteAccessException("remote access exception");
    }

    @Recover
    public void recover(RemoteAccessException e) {
        log.info("do retry recover");
    }
}
