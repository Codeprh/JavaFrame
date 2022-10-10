package com.noah.practice.thread;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GuavaThreadPoolExecutor {
    public static void main(String[] args) throws InterruptedException {
        ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> listenableFuture = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "-女神：我开始化妆了，好了我叫你。");
            TimeUnit.SECONDS.sleep(5);
            return "化妆完毕了。";
        });

        listenableFuture.addListener(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "-future的内容:" + listenableFuture.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);
        System.out.println(Thread.currentThread().getName() + "-等女神化妆的时候可以干点自己的事情。");
        Thread.currentThread().join();
    }
}
