package com.noah.practice.twoPc;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwoPcTest {

    public static Boolean doItFlag = true;

    @SneakyThrows
    public static void main(String[] args) {

        CountDownLatch mainMonitoring = new CountDownLatch(1);

        CountDownLatch childMonitoring = new CountDownLatch(3);

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Boolean> childResponseList = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {

            int finalI = i;
            executorService.execute(() -> {

                try {

                    System.out.println("i am i = " + finalI + ",我准备好了数据");
                    childResponseList.add(false);

                    childMonitoring.countDown();

                    mainMonitoring.await();
                    if (doItFlag) {
                        System.out.println("i am i = " + finalI + ",我成功提交了数据");
                    } else {
                        System.out.println("i am i = " + finalI + ",我回滚了数据");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }

        childMonitoring.await();

        for (Boolean cResponse : childResponseList) {
            if (!cResponse) {
                doItFlag = false;
            }
        }
        mainMonitoring.countDown();
        Thread.currentThread().join();


    }

}
