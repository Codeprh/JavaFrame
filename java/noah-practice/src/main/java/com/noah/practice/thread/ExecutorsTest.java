package com.noah.practice.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ExecutorsTest {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = buildThreadPool();
        executor.execute(() -> sayHi("execute"));
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.submit(() -> sayHi("submit"));

    }

    public static void sayHi(String way) {

        String log = "my thread name is %s , and way is %s";
        System.out.println(String.format(log, Thread.currentThread().getName(), way));
        throw new RuntimeException("i am say hi and error " + way);
    }

    public static ThreadPoolExecutor buildThreadPool() {
        int corePoolSize = 5;
        int maximumPoolSize = 10;
        long keepAliveTime = 30;
        TimeUnit unit = TimeUnit.SECONDS;

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000);

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("noah-pool").build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
