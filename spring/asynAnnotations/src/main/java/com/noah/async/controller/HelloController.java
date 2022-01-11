package com.noah.async.controller;

import com.noah.async.service.AsyncHelloService;
import com.noah.async.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/noah")
@Slf4j
public class HelloController {

    @Resource
    HelloService helloService;

    @Resource
    AsyncHelloService asyncHelloService;

    @Resource
    ApplicationContext applicationContext;

    @GetMapping("/hello")
    public String helloAsync(int num) {

        log.info("thread name:{}", Thread.currentThread().getName());

        for (int i = 0; i < num; i++) {
            helloService.asyncHello();
            asyncHelloService.asyncHello();
        }

        return "hello noah";
    }

    @GetMapping("/noah")
    public String noahAsync() {

        log.info("thread name:{}", Thread.currentThread().getName());

        Future<Integer> integerFuture = helloService.noahThreadAsync();

        try {
            Integer r = integerFuture.get();
            log.info("r:{}", r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "hello noah";
    }


    @GetMapping("/asyncReturn")
    public String returnAsync(int num) {

        Integer aReturn = helloService.asyncReturn();
        log.info("r:{}", aReturn);
        return "hello noah";
    }

    @GetMapping("/asyncReturnMore")
    public String returnAsyncMore() {

        Future<Integer> v2 = helloService.asyncReturnV2();
        Future<Integer> v3 = helloService.asyncReturnV3();
        log.info("v2:{},v3:{}", v2, v3);
        return "hello noah";
    }

    @GetMapping("springP")
    public void printSpringPool() {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String bn : definitionNames) {

            Object bean = applicationContext.getBean(bn);

            if (bean instanceof Executor) {
                log.info(bean + "");
            }
        }
    }

}
