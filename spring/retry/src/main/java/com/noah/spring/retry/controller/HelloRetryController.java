package com.noah.spring.retry.controller;

import com.noah.spring.retry.service.RetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/retry")
@Slf4j
public class HelloRetryController {

    @Resource
    RetryService retryService;

    @GetMapping("hello")
    public String helloRetry() {
        log.info("hello retry");
        retryService.service();
        return "hello retry";
    }
}
