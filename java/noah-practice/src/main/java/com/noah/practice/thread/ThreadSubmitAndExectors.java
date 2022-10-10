package com.noah.practice.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadSubmitAndExectors {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> submit = executorService.submit(() -> {
            System.out.println("11");
            return "1231";
        });

        executorService.execute(()->{

        });
    }
}
