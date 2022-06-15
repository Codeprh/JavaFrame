package com.noah.practice.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockQueueTest {

    public static void main(String[] args) throws Exception {

        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(1000);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                while (true) {
                    queue.offer(new Integer(finalI));
                    queue.remove();
                }
            }).start();
        }

        while (true) {
            System.out.println("begin scan, i still alive");
            queue.stream()
                    .filter(o -> o == null)
                    .findFirst()
                    .isPresent();
            Thread.sleep(100);
            System.out.println("finish scan, i still alive");
        }
    }

}
