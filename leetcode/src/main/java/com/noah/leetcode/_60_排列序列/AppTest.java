package com.noah.leetcode._60_排列序列;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class AppTest {

    public static void main(String[] args) {
        //int fac[] = new int[1000];
        //for (int i = 1; i < 100; i++) fac[i] = fac[i - 1] * i;
        //System.out.println(fac);
        //for (int i = 0; i < 90; i++) {
        //    //System.out.println("i=" + i + ",r=" + getFactorial(i));
        //}
        //System.out.println(Integer.MAX_VALUE + "");
        long begin = System.currentTimeMillis();
        System.out.println(inverseCantorExpansion(10000, 1));//52413
        long second = (System.currentTimeMillis() - begin) / 1000;
        System.out.println("====");
        System.out.println(second);
        //System.out.println(inverseCantorExpansion(8, 27));// 12354768
    }

    /**
     * 给出 [1,2,3,...,n] 数组以及该数组全排列的第 K 个序列编号，求第 K 个数是啥
     *
     * @param n 数组的最大值
     * @param k 序号
     * @return 第K个数的值
     */
    public static String inverseCantorExpansion(int n, int k) {
        // 先搞一个1~n的数组，用来在里面选择合适的数字加入 ans 中
        ArrayList<Integer> array = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            array.add(i + 1);
        }

        StringBuilder ans = new StringBuilder();
        k -= 1;

        // 执行 n 次循环得到 n 个数，组合成一个答案
        for (int i = 0; i < n; i++) {
            // 计算从 (n-1)! 开始的阶乘
            BigInteger factorial = getFactorial(n - i - 1);


            int index = new BigInteger(String.valueOf(k)).divide(factorial).intValue();
            //int index = k / Xfactorial;
            // 获取该数字
            ans.append(array.get(index)+",");
            // 更新 k 为上一次除法计算的余数
            k = new BigInteger(String.valueOf(k)).mod(factorial).intValue();
            //k %= Xfactorial;
            // 同时更新数组，删除已经获得过的数字
            array.remove(index);
            //System.out.println(array.get(index));
        }
        return ans.toString();
    }

    // 求x的阶乘
    public static BigInteger getFactorial(int x) {
        if (x == 0)
            return new BigInteger(String.valueOf(1));
        BigInteger res = new BigInteger(String.valueOf(x));
        for (int i = 1; i < x; i++) {
            res = res.multiply(new BigInteger(String.valueOf(i)));
        }
        return new BigInteger(String.valueOf(res));
    }

    public static void main1(String[] args) {
        int n = 20;
        int k = 1;
        //System.out.println(getPermutation(n, k));

        //Solution1 solution1 = new AppTest.Solution1();
        //System.out.println(solution1.getPermutation(n, k));

        Solution2 solution2 = new AppTest.Solution2();
        solution2.main();


    }

    public static String getPermutation(int n, int k) {
        int[] factorial = new int[n];
        factorial[0] = 1;
        for (int i = 1; i < n; ++i) {
            factorial[i] = factorial[i - 1] * i;
        }

        --k;
        StringBuffer ans = new StringBuffer();
        int[] valid = new int[n + 1];
        Arrays.fill(valid, 1);
        for (int i = 1; i <= n; ++i) {
            int order = k / factorial[n - i] + 1;
            for (int j = 1; j <= n; ++j) {
                order -= valid[j];
                if (order == 0) {
                    ans.append(j);
                    valid[j] = 0;
                    break;
                }
            }
            k %= factorial[n - i];
        }
        return ans.toString();
    }

    static class Solution2 {
        int fac[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};//阶乘
        int ans[] = new int[10];

        int cantor(int a[], int n) {
            int s = 0;
            for (int i = 0; i < n; i++) {
                int num = 0;
                for (int j = i + 1; j < n; j++)
                    if (a[i] > a[j])
                        num++;
                s += (num * fac[n - i - 1]);
            }
            return s;
        }

        void decantor(int s, int n) {
            LinkedList<Integer> a = new LinkedList<>();
            for (int i = 1; i <= n; i++) a.addLast(i);
            for (int i = 4; i >= 0; i--) {
                int pos = s / fac[i];
                s %= fac[i];
                ans[n - i - 1] = a.indexOf(pos);
                a.remove(pos);
                //a.erase(a.begin() + pos);
            }
        }

        int main() {
            int a[] = {4, 5, 3, 1, 2};
            int n = 5;
            System.out.println(cantor(a, n));

            decantor(cantor(a, n), n);

            for (int i = 0; i < n; i++) {
                //printf("%d%c", ans[i], i + 1 == n ? '\n' : ' ');
            }
            return 0;
        }
    }


    static class Solution1 {
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
