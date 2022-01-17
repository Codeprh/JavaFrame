package com.noah.dubbo.service;

@org.apache.dubbo.config.annotation.DubboService
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        return "hello " + name;
    }
}
