package com.noah.dubbo.client.controller;

import com.noah.dubbo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/dubblo/client/hello")
@Slf4j
public class HelloClientController {

    @DubboReference(timeout = 1000)
    HelloService helloService;

    @GetMapping("")
    public String helloNoah() throws ExecutionException, InterruptedException {

        String greeting = helloService.greeting("noah");
        //helloService.replyGreeting("noah v2");
        //CompletableFuture<String> noah_v2 = helloService.greeting("noah v2", Byte.MAX_VALUE);
        //log.info(noah_v2.get());
        return "hello noah";
    }
}
