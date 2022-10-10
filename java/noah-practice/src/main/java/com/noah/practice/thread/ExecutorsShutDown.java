package com.noah.practice.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsShutDown {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(6);
                System.out.println("i am finish 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(6);
                System.out.println("i am finish 2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {

            //TimeUnit.SECONDS.sleep(6);
            System.out.println("i am finish 3");

        });

        //executorService.shutdown();
        executorService.shutdownNow();
        System.out.println("over");
    }
}
