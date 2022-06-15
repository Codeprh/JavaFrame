package com.noah.leetcode._60_排列序列;

import java.util.ArrayList;
import java.util.List;

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

    static class Sa{

        public static void main(String[] args) {
            System.out.println(calculateRank(34152));
        }

        //阶乘结果数组
        static int[] arr = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};

        //计算某数字在多少位
        //34152
        public static int calculateRank(int num) {
            int res = 0;
            String s = String.valueOf(num);
            //遍历每一个数字计算结果
            char[] chars = s.toCharArray();
            int len = chars.length;
            for (int i = 0; i < len; i++) {
                int cnt = 0;
                for (int j = i + 1; j < len; j++) {
                    //如果后面的比前面的小
                    if (chars[i] > chars[j]) {
                        cnt++;
                    }
                }
                //小的数字个数x后面的空格数的阶乘
                //第0位后面有： 5 - 0 -1 = 4 个空位
                res += cnt * arr[len - i - 1];
            }
            return res + 1;
        }


    }

    static class Sb {

        //阶乘结果数组
        static int[] arr = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};

        public static String getNumByRank(int n, int k) {
            List<Integer> candidates = new ArrayList<>(n);
            for (int i = 1; i <= n; i++) {
                candidates.add(i);
            }
            k--;
            StringBuilder sb = new StringBuilder();
            for (int i = n - 1; i >= 0; i--) {
                //计算有几个比它小的
                int cnt = k / arr[i];
                //当时1 2 3 4 5 索引 0 1 2 3 4
                //计算出来有4个比他小，那么就是 index = 4 value = 5 此时数组->  1 2 3 4
                //达到了实时删除的结果比较最近最小满足
                Integer num = candidates.remove(cnt);
                sb.append(num);
                k = k % arr[i];
            }
            return sb.toString();
        }

    }

}
