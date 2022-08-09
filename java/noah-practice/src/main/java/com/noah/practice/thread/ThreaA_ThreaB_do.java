package com.noah.practice.thread;

import lombok.SneakyThrows;
import org.omg.CORBA.TIMEOUT;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreaA_ThreaB_do {

    public static int var = 1;

    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(1);
        Thread a = new Thread(() -> {
            ThreaA_ThreaB_do.var++;
            cdl.countDown();
        });

        Thread b = new Thread(() -> {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ThreaA_ThreaB_do.var == 2) {
                System.out.println("hello world");
            }
        });

        a.start();
        b.start();

        cdl.await();
    }
}
