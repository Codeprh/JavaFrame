package com.noah.dubbo.alibaba.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/noah")
public class NoahController {

    @Resource
    ConsumeService consumeService;

    @GetMapping("/hello")
    public String noah() {
        return consumeService.hello();
    }
}
