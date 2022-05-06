package com.noah.leetcode._60_排列序列;

/**
 * leetcode版本的逆康托展开
 */
public class AppOne {

    static class Solution {

        //1 <= n <= 9
        //1 <= k <= n!
        public static void main(String[] args) {
            Solution app = new Solution();
            System.out.println(app.getPermutation(4, 5));
        }

        public String getPermutation(int n, int k) {

            // 计算阶乘的数组
            int[] fac = new int[n];
            // 标记数组
            int[] ans = new int[n + 1];
            fac[0] = 1;
            // 计算各个阶乘
            for (int i = 1; i < n; i++) {
                fac[i] = fac[i - 1] * i;
            }
            // k - 1 表示逆康托展开中有 k - 1个比目标排列小的数
            k = k - 1;
            StringBuilder str = new StringBuilder(n);
            for (int i = 1; i <= n; i++) {
                // 获取目标位的当前位有几个数比他小
                int preNum = k / fac[n - i];
                // 拼接上找到的目标数
                str.append(getNum(n, preNum, ans));
                // 获取它的余数
                k %= fac[n - i];
            }
            fac = null;
            ans = null;
            return str.toString();
        }

        // 用于寻找目标排列对应的数
        public int getNum(int n, int preNum, int[] ans) {

            int num = 0;

            for (int j = 1; j <= n; j++) {
                if (ans[j] == 0) {
                    if (num == preNum) {
                        ans[j] = 1;
                        return j;
                    }
                    num++;
                }
            }
            return 0;
        }
    }

}
