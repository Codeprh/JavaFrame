package com.noah.practice.map;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;

public class ConcurrentSkipListMapTest {

    @SneakyThrows
    public static void main(String[] args) {

        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        map.put(100, "100");
        map.put(1, "1");
        map.put(10, "10");

        System.out.println(map.toString());

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        Class<?> aClass = Class.forName("java.util.concurrent.ThreadLocalRandom");
        Method nextSecondarySeed = aClass.getDeclaredMethod("nextSecondarySeed");
        nextSecondarySeed.setAccessible(true);

        for (int i = 0; i < 10; i++) {
            System.out.println(nextSecondarySeed.invoke(threadLocalRandom));
        }

        boolean a = false;
        boolean b = true;
        if (!a && b) {
            System.out.println("hello true???");
        }
    }
}
