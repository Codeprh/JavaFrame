package com.noah.practice.jvm;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IssueExectorJava {

    static void print(ThreadPoolExecutor pool) {
        System.out.printf("poolSize=%d core=%d max=%d%n",
                pool.getPoolSize(),
                pool.getCorePoolSize(),
                pool.getMaximumPoolSize());
    }

    public static void main(String[] args) {
        final int CORE = 2,
                MAX = 10,
                KEEP_ALIVE_TIME = 30,
                QUEUE_MAX_SIZE = 25;
        final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(QUEUE_MAX_SIZE);
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE, MAX, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue);
        print(executor);
        executor.setCorePoolSize(MAX + 1);
        print(executor);
    }
}
