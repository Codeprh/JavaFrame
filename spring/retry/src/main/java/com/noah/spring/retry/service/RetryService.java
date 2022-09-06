package com.noah.spring.retry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RetryService {

    @Retryable(include = RemoteAccessException.class,
            recover = "doRecover",
            backoff = @Backoff(value = 5000),
            maxAttempts = 4)
    public String service() {
        log.info("do retry service");
        dosomething();

        return "service";
    }

    @Recover
    public String doRecover(Exception remoteAccessException) {
        log.info("i am do recover");
        return "recover";
    }

    private void dosomething() {
        throw new RemoteAccessException("remote access exception");
    }
}
