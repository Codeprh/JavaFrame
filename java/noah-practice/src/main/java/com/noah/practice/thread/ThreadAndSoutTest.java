package com.noah.practice.thread;

import java.util.concurrent.TimeUnit;

public class ThreadAndSoutTest {

    public static boolean flag = false;
    public static long i = 0;

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                flag = true;
                System.out.println("i am change to true");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (!flag) {
            i++;
            System.out.println("i am flag=" + flag);
        }
        System.out.println("i am finish i=" + i);
    }

}
