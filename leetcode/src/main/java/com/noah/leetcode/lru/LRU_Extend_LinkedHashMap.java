package com.noah.leetcode.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU_Extend_LinkedHashMap extends LinkedHashMap<Integer, Integer> {

    private Integer capacity;

    public LRU_Extend_LinkedHashMap(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return getOrDefault(key, -1);

    }

    public void put(int key, int value) {
        put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
