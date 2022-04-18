package com.noah.practice.algorithm;

public class RandomPage {
    public static void main(String[] args) {

        Long key = 102499991L;
        Long page = 20L;

        for (int i = 1; i < 10; i++) {

            Long open = 100000000000L + ((page * i) ^ key);

            System.out.println("RIndex=" + i + ",index=" + open);

            long toDecode = open - 100000000000L;
            System.out.println("decodeIndex=" + (toDecode ^ key) / page);

        }
    }
}
