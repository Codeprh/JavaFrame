package com.noah.practice.math;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MathApp {

    public static void main(String[] args) {

        int count = 20;

        for (int i = 0; i < 1000; i++) {
            int r = i % count;
            log.info("i=" + i + ",r=" + r);
        }
    }
}
