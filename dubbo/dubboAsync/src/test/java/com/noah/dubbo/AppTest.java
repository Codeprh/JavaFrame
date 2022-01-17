package com.noah.dubbo;

import com.noah.dubbo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = DubboAsyncApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class AppTest {

    @DubboReference
    HelloService helloService;

    @Test
    public void testApp(){
        helloService.greeting("11");
        log.info("hello wordl");
    }
}
