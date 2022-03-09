package com.noah.lock.transaction.domain.service;

import com.noah.lock.transaction.LockTransactionApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootTest(classes = LockTransactionApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ProductDomainServiceTest {

    @Resource
    ProductDomainService productDomainService;

    @Test
    public void test() {

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        IntStream.rangeClosed(1, 100).forEach(i -> {
            executorService.submit(() -> {
                productDomainService.sellProductError(1L);
            });
        });

        try {
            TimeUnit.SECONDS.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testPlus() {

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch cdl = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                try {
                    cdl.await();
                    //必现：超卖1件
                    //productDomainService.sellProduct(1L);

                    //必现：超卖20件
                    productDomainService.sellProductError(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
            cdl.countDown();
        }

        try {
            TimeUnit.SECONDS.sleep(120L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}