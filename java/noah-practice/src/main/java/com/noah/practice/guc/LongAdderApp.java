package com.noah.practice.guc;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderApp {

    public static void main(String[] args) {

        LongAdder longAdder = new LongAdder();

        for (int i = 0; i < 10; i++) {

            longAdder.increment();
        }

        long longValue = longAdder.longValue();
        System.out.println(longValue);
    }
}
