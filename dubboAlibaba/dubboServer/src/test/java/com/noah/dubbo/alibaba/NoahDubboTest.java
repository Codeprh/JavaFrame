package com.noah.dubbo.alibaba;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsumerBootstrap.class)
public class NoahDubboTest {

    @Test
    public void testHello() {
        System.out.println("noah");
    }
}
