package com.noah.dubbo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.TimeUnit;

@DubboService(timeout = 1000, weight = 300)
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        log.info("log info for greeting");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name;
    }
}
