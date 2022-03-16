package com.noah.practice.guc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Slf4j
public class CHMApp {

    public static void main(String[] args) {

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        IntStream.rangeClosed(1, 3).forEach(i -> {
            Object noah = concurrentHashMap.computeIfAbsent("noah", k -> "noah123");
            log.info(noah.toString());
        });

        //testNull();
    }

    private static void testNull() {
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

        try {
            concurrentHashMap.put("key", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            concurrentHashMap.put(null, "value");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
