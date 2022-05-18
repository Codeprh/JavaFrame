package com.noah.leetcode._53_最大数组和;

public class AppMain_220517 {

    public static void main(String[] args) {

        int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(cal(arr));
    }

    public static int cal(int[] arr) {
        int pre = 0, maxVal = arr[0];
        for (int a : arr) {
            pre = Math.max(a, a + pre);
            maxVal = Math.max(pre, maxVal);
        }
        return maxVal;
    }
}
