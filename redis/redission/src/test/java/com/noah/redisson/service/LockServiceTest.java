package com.noah.redisson.service;

import com.noah.redisson.RedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootTest(classes = RedisApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class LockServiceTest {
    @Autowired
    RedissonClient redissonClient;

    public static Integer cc = 0;

    @Test
    public void testLock() {

        Long trainId = -9999L;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        IntStream.rangeClosed(1, 100).forEach(i -> {
            //executorService.execute(() -> doSome(trainId));
        });

        try {
            TimeUnit.SECONDS.sleep(100000);
        } catch (InterruptedException e) {
            log.error("执行失败");
        }

    }

    @Test
    public void doSome() {
        Long trainId = 100081L;

        RLock lock = redissonClient.getLock("TRAIN" + trainId);
        lock.lock();

        try {

            System.out.println("获取到lock了，开始执行逻辑");
            if (cc == 0) {
                TimeUnit.SECONDS.sleep(32);
                cc = 1;
                System.out.println("更改为cc=1");
            }

            System.out.println("我业务执行完了,cc=" + cc);

        } catch (Exception e) {
            log.error("", e);
        } finally {
            lock.unlock();
        }

    }

}