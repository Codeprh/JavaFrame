package com.noah.dubbo.alibaba.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/noah")
@Slf4j
public class NoahController {

    @Resource
    ConsumeService consumeService;

    @GetMapping("/hello")
    public String noah() {
        for (int i = 0; i < 20; i++) {
            log.info("i=========" + i);
            new Thread(() -> consumeService.hello()).start();
        }

        consumeService.hello();
        return "123";
    }

    @GetMapping("/hello1")
    public String noah1() {
        return consumeService.hello();
    }
}
