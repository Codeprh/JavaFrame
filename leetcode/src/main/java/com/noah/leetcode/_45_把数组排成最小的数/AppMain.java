package com.noah.leetcode._45_把数组排成最小的数;

import java.util.Arrays;

public class AppMain {

    public static void main(String[] args) {

        int[] array = new int[]{3, 30, 34, 5, 9};
        //3033459
        System.out.println(minNumber(array));
    }

    public static String minNumber(int[] nums) {
        String[] t = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            t[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(t, (a, b) -> (a + b).compareTo(b + a));
        StringBuffer res = new StringBuffer();
        for (String s : t) {
            res.append(s);
        }
        return res.toString();
    }

    public String minNumber2(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++)
            strs[i] = String.valueOf(nums[i]);
        quickSort(strs, 0, strs.length - 1);
        StringBuilder res = new StringBuilder();
        for (String s : strs)
            res.append(s);
        return res.toString();
    }

    void quickSort(String[] strs, int l, int r) {
        if (l >= r) return;
        int i = l, j = r;
        String tmp = strs[i];
        while (i < j) {
            while ((strs[j] + strs[l]).compareTo(strs[l] + strs[j]) >= 0 && i < j) j--;
            while ((strs[i] + strs[l]).compareTo(strs[l] + strs[i]) <= 0 && i < j) i++;
            tmp = strs[i];
            strs[i] = strs[j];
            strs[j] = tmp;
        }
        strs[i] = strs[l];
        strs[l] = tmp;
        quickSort(strs, l, i - 1);
        quickSort(strs, i + 1, r);
    }

}
