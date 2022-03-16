package com.noah.practice.java8;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link ConcurrentHashMap}jdk1.8的死循环bug
 */
@Slf4j
public class ConcurrentHashMapBug {

    public static void main(String[] args) {

        //chmBug();

        System.out.println("f(" + 14 + ") =" + fibonacci(14));
    }

    static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

    static int fibonacci(int i) {
        if (i == 0)
            return i;
        if (i == 1)
            return 1;
        return cache.computeIfAbsent(i, (key) -> {
            System.out.println("Slow calculation of " + key);
            return fibonacci(i - 2) + fibonacci(i - 1);
        });
    }

    private static void chmBug() {
        Map<String, Integer> map = new ConcurrentHashMap<>(16);
        log.info("start method");
        int HASH_BITS = 0x7fffffff;

        map.computeIfAbsent(
                "AaAa",
                key -> {
                    return map.computeIfAbsent(
                            "BBBB",
                            key2 -> 42);
                }
        );

        log.info("end method,map:{}", map);
    }
}
