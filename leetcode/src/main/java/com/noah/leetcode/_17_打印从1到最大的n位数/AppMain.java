package com.noah.leetcode._17_打印从1到最大的n位数;

import com.google.common.collect.Lists;

import java.util.stream.IntStream;

public class AppMain {

    public static void main(String[] args) {
        AppMain app = new AppMain();
        for (int i : app.printNumbers(3)) {
            System.out.print(i + ",");
        }
    }

    public int[] printNumbers(int n) {

        int v = (int) Math.pow(10, n);
        return IntStream.range(1, v).toArray();
    }

}
