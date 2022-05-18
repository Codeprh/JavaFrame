package com.noah.leetcode._53_最大数组和;

public class AppMain {

    public static void main(String[] args) {

        int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        //4, -1, 2, 1,最大值6

        //{5,4,-1,7,8} 最大值23
        //int[] arr = new int[]{5, 4, -1, 7, 8};
        System.out.println(maxSubArray(arr));

    }

    /**
     * 最大数组和
     *
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        return 0;
    }

    public static int maxSubArray2(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    public static int maxSubArray3(int[] nums) {
        int pre = 0, sum = nums[0];
        for (int num : nums) {

            pre = Math.max(num, pre + num);
            sum = Math.max(pre, sum);
        }

        return sum;
    }
}
