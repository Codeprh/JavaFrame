package com.noah.practice.queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockQueueTest {

    public static void main(String[] args) throws Exception {

        linkedBlockingQueueTest();
        //
        //endlessLoopFromGithub();
    }

    public static void linkedBlockingQueueTest() {

        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);

        System.out.println("begin scan,begin,begin,begin");
        queue.stream().anyMatch(Objects::isNull);
        System.out.println("finish scan,finish,finish,finish");
    }

    private static void endlessLoopFromGithub() throws InterruptedException {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(1000);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                while (true) {
                    queue.offer(new Integer(finalI));
                    queue.remove();
                }
            }, "noahThread" + finalI).start();
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
