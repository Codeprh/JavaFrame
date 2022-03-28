package com.noah.leetcode.lru;

public class LRU {

    public static void main(String[] args) {

    }

    private final Integer lruLength;
    private final Node[] lruArry;

    public LRU(Integer lruLength) {
        this.lruLength = lruLength;
        this.lruArry = new Node[lruLength - 1];
    }

    public void add(Node node) {

        if (this.lruArry.length < lruLength) {
            addCount();
            lruArry[this.lruArry.length - 1] = node;
        }

    }

    public void addCount() {

        for (int i = 0; i < lruArry.length; i++) {
            lruArry[i].addCount();
        }
    }

    public void remove() {

    }

    static class Node {

        private Object object;
        private Long counter;

        public void addCount() {
            counter++;
        }
    }

}
