package com.noah.dubbo.alibaba.service.impl;

import com.noah.dubbo.alibaba.service.NoahService;

public class NoahServiceImpl implements NoahService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
