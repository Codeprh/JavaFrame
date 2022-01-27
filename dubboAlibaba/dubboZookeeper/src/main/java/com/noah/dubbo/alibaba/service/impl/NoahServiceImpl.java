package com.noah.dubbo.alibaba.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.noah.dubbo.alibaba.service.NoahService;
import lombok.extern.slf4j.Slf4j;

@Service(loadbalance = "leastactive",weight = 200)
@Slf4j
public class NoahServiceImpl implements NoahService {

    @Override
    public String hello(String name) {
        log.info("======================");
        log.info("======================");
        log.info("======================");
        //return "hello " + name + ",我是200权重的服务器";
        return "hello " + name;
        //return "hello " + name + ",我是默认权重的服务器";
    }
}
