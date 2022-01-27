package com.noah.dubbo.alibaba.service.impl;

import com.noah.dubbo.alibaba.service.NoahService;
import org.springframework.stereotype.Service;

@Service(value = "noahService")
public class NoahServiceImpl implements NoahService {
    @Override
    public String hello(String name) {

        return "hello " + name+",我是200权重的服务器";
        //return "hello " + name+",我是300权重的服务器";
        //return "hello " + name+",我是默认权重的服务器";
    }
}
