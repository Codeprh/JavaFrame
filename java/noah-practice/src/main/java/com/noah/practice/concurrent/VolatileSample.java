package com.noah.practice.concurrent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolatileSample {

    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    public static final Integer THREAD_COUNT = 20;

    public static void main(String[] args) {
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("race=" + race);
        //printThread();
        //tryMethod();
        //System.out.println(Thread.activeCount());
        //Thread.currentThread().getThreadGroup().list();
    }

    private static void printThread() {

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (threadGroup.getParent() != null) {
            threadGroup = threadGroup.getParent();
        }

        int activeCount = threadGroup.activeCount();
        Thread[] threads = new Thread[activeCount];

        //把系统创建的线程，引用赋值给threads变量
        threadGroup.enumerate(threads);

        for (Thread t : threads) {
            log.info("tcount:{},tid:{},tname:{},tstatus:{}", activeCount,
                    t.getId(), t.getName(), t.getState());
        }
    }

    private static void tryMethod() {
        for (int i = 0; i < THREAD_COUNT; i++) {

            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
            }).start();

        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("race=" + race);
        System.out.println("==========over=======");
    }
}
