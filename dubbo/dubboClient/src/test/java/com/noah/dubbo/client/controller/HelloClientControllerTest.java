package com.noah.dubbo.client.controller;

import com.noah.dubbo.apache.api.HelloService;
import junit.framework.TestCase;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloClientControllerTest extends TestCase {

    @DubboReference(timeout = 1000 * 60 * 10, filter = "activelimit")
    //@DubboReference(timeout = 1000 * 60 * 10)
    HelloService helloService;

    @Test
    public void contextLoads() {

        for (int i = 0; i < 20; i++) {
            int fi = i;
            new Thread(() -> {
                helloService.greeting("noahTest-" + fi);
            }).start();
        }

        helloService.greeting("noahTest-" + 100);
    }

}