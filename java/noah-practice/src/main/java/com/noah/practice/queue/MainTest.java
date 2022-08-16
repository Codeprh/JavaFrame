package com.noah.practice.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class MainTest {

    public static void main(String[] args) throws InterruptedException {

        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        queue.put(1);
        queue.put(2);

        queue.remove();
    }
}
