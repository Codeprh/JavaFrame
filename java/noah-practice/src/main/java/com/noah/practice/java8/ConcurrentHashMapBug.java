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
        //useChm();
        chmBug();

        //System.out.println("f(" + 14 + ") =" + fibonacci(14));
    }

    static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

    /**
     * 入门使用chm
     */
    public static void useChm() {

        Map<String, String> map = new ConcurrentHashMap<>(16);
        String key = "noah";
        log.info("1:" + map.get(key));
        String s = map.computeIfAbsent(key, k -> "hello," + k);
        log.info("2:" + s);

    }

    /**
     * 演示死锁的bug
     */
    private static void chmBug() {

        Map<String, Integer> map = new ConcurrentHashMap<>();

        map.computeIfAbsent(
                "AaAa",
                // if the computation detectably attempts a recursive update to this map that would otherwise never complete
                key -> map.computeIfAbsent("BBBB", key2 -> 16)
        );

        log.info("end method,map:{}", map);
    }

    /**
     * 死锁bug避免
     */
    private static void fixChmBug() {

    }

    /**
     * 斐波那契数列
     *
     * @param i
     * @return
     */
    public static int fibonacci(int i) {
        if (i == 0)
            return i;
        if (i == 1)
            return 1;
        return cache.computeIfAbsent(i, (key) -> {
            System.out.println("Slow calculation of " + key);
            return fibonacci(i - 2) + fibonacci(i - 1);
        });
    }

}
