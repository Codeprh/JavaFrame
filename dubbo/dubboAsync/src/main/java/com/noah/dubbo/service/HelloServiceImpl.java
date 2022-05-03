package com.noah.dubbo.service;

import com.noah.dubbo.apache.api.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.TimeUnit;

//@DubboService(timeout = 5000, loadbalance = "noahconsistenthash", parameters = {"hash.nodes", "4"})

//最少链接负载均衡策略

//8081端口配置
//@DubboService(timeout = 10 * 60 * 1000, loadbalance = "noahleastactiveloadbalance",filter = "activelimit")

//8082端口配置
//@DubboService(timeout = 10 * 60 * 1000, loadbalance = "noahleastactiveloadbalance", weight = 200,filter = "activelimit")

//8083端口配置
@DubboService(timeout = 10 * 60 * 1000, loadbalance = "noahleastactiveloadbalance", weight = 300,filter = "activelimit")
@Slf4j
public class HelloServiceImpl implements HelloService {

    /**
     * 服务提供者：sleep 10min，保持链接
     *
     * @param name
     * @return
     */
    @Override
    public String greeting(String name) {
        log.info("log info for greeting" + name);
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name;
    }
}
