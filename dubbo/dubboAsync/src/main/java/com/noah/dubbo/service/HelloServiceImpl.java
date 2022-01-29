package com.noah.dubbo.service;

import com.noah.dubbo.apache.api.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.TimeUnit;

@DubboService(timeout = 5000, loadbalance = "noahconsistenthash", parameters = {"hash.nodes", "4"})
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        log.info("log info for greeting");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name;
    }
}
