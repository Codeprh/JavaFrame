package com.noah.practice.jvm;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class PutError {

    static Integer poolSize = 2;
    static AtomicInteger atomicInteger = new AtomicInteger();

    static ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(poolSize, poolSize, 1, MILLISECONDS, queue, new RejectedExecutionHandler() {
        @Override
        @SneakyThrows
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("i am reject");
            queue.put(r);
        }
    });

    static {

    }

    public static void getJob() throws InterruptedException {
        new Thread(() -> {
            while (true) {

                try {
                    for (int i = 0; i < 20; i++) {
                        System.out.println(" i am into queue" + atomicInteger.getAndIncrement());
                        threadPoolExecutor.execute(() -> {
                            System.out.println("i am " + atomicInteger.get());
                            try {
                                SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        getJob();
        System.out.println("hello world");
        SECONDS.sleep(1000);
    }


}
