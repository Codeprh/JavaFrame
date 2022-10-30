package com.noah.practice.queue;

import com.noah.practice.queue.clq.NoahConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {

    public static void main(String[] args) {

        jdkNew();

        System.out.println("-------old-------");

        jdkOld();
    }

    private static void jdkOld() {
        NoahConcurrentLinkedQueue<Object> queue = new NoahConcurrentLinkedQueue<>();
        queue.offer(new Object());

        Object item = new Object();

        Integer iterations = 0;
        try {
            while (true && iterations < 5) {
                ++iterations;
                queue.offer(item);

                queue.remove_jdk8_40(item, iterations);
                System.out.println();
            }
        } catch (OutOfMemoryError e) {
            queue = null;
            System.err.println("iterations: " + iterations);
            throw e;
        }
    }


    private static void jdkNew() {
        NoahConcurrentLinkedQueue<Object> queue = new NoahConcurrentLinkedQueue<>();
        queue.offer(new Object());

        Object item = new Object();

        Integer iterations = 0;
        try {
            while (true && iterations < 5) {
                ++iterations;
                queue.offer(item);

                queue.remove_jdk8_312(item, iterations);
                System.out.println();
            }
        } catch (OutOfMemoryError e) {
            queue = null;
            System.err.println("iterations: " + iterations);
            throw e;
        }
    }

}
