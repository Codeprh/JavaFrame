package com.noah.practice.map;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HashMapEndlessLoop {
    public static void main(String[] args) throws InterruptedException {

        final HashMap<String, String> map = new HashMap<String, String>(2);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");

        t.start();

        t.join();
        System.out.println("hello world");
        TimeUnit.SECONDS.sleep(100);
    }
}
