package com.noah.spring.retry.controller;

import com.google.common.collect.Lists;

public class Main {

    public static void main(String[] args) {
        int n = 10;
        int[] arr = new int[n + 1];
        for (int i = 0; i <= 10; i++) {
            int i1 = countOne(i);
            int i2 = Integer.bitCount(i);
            System.out.println(i1 + "," + i2);
            arr[i] = i1;
        }
        System.out.println(Lists.newArrayList(arr));
    }

    public static int countOne(int x) {
        int countOne1 = 0;
        while (x > 0) {
            x &= (x - 1);
            countOne1++;
        }
        return countOne1;
    }
}
