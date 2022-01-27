package com.noah.dubbo.alibaba.service;

import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumeService {

    @Reference
    NoahService noahService;

    public String hello() {
        return noahService.hello("noah");
    }
}
