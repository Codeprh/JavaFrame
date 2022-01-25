package com.noah.dubbo.alibaba;

import com.noah.dubbo.alibaba.service.NoahService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerBootstrap {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});

        context.start();
        NoahService noahService = (NoahService) context.getBean("noahService");
        String hello = noahService.hello("noah");
        System.out.println(hello);

//        System.out.println("start void test...");
//        demoService.testVoid();
    }
}
