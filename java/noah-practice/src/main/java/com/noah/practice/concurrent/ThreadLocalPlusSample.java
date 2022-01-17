package com.noah.practice.concurrent;

import java.util.concurrent.CountDownLatch;

public class ThreadLocalPlusSample {

    public static void main(String[] args) throws InterruptedException {

        int threads = 3;

        CountDownLatch countDownLatch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {

            new Thread(() -> {

                for (int j = 0; j < 4; j++) {
                    Content.threadLocal1.set(Content.threadLocal1.get() + String.valueOf(j));
                    Content.threadLocal2.set(Content.threadLocal2.get() + String.valueOf(j));
                }

                countDownLatch.countDown();
                System.out.println(Content.threadLocal1.get() + "---" + Content.threadLocal2.get());

            }, "thread - " + i).start();
        }

        countDownLatch.await();
    }

    public static class Content {

        public static ThreadLocal<String> threadLocal1 = ThreadLocal.withInitial(()->"");
        public static ThreadLocal<String> threadLocal2 =ThreadLocal.withInitial(()->"");
    }
}
