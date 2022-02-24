package com.noah.practice.guc;

import java.util.concurrent.ConcurrentHashMap;

public class CHMApp {

    public static void main(String[] args) {

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
