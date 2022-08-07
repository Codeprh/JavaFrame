package com.noah.leetcode._1_两数之和;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AppMain {

    public static void main(String[] args) {
        int []arr=new int[]{3,3};
        int tar=6;
        int[] ints = sumMatch(arr, tar);
        System.out.println(ints);
    }

    private static int[] sumMatch(int[] arr, int target) {

        Map<Integer, Integer> maps = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {

            int i1 = arr[i];
            Integer index = maps.get(target - i1);
            if (Objects.isNull(index)) {
                maps.put(i1, i);
            } else {
                return new int[]{i, index};
            }
        }

        return null;
    }
}
