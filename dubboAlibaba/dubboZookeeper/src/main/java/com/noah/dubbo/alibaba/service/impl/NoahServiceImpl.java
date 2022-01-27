package com.noah.dubbo.alibaba.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.noah.dubbo.alibaba.NoahService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service(loadbalance = "leastactive",timeout = 1000*60*20)
@Slf4j
public class NoahServiceImpl implements NoahService {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public String hello(String name) {
        log.info("======================" + name + ",num=" + atomicInteger.addAndGet(1));
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name;
    }
}
