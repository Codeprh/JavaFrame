package com.noah.leetcode._60_排列序列;

import java.math.BigInteger;

/**
 * 树状数组：逆托康展开
 */
public class ThreeCantor {

    static class Solution {

        public static void main(String[] args) {

            Solution solution = new Solution();
            int n = 10;
            int fn = getFactorial(n).intValue();

            for (int i = 1; i < fn; i++) {
                String permutation = solution.getPermutation(n, i);
            }

        }

        /**
         * 计算阶层
         *
         * @param x
         * @return
         */
        public static BigInteger getFactorial(int x) {
            if (x == 0)
                return new BigInteger(String.valueOf(1));
            BigInteger res = new BigInteger(String.valueOf(x));
            for (int i = 1; i < x; i++) {
                res = res.multiply(new BigInteger(String.valueOf(i)));
            }
            return new BigInteger(String.valueOf(res));
        }

        public String getPermutation(int n, int k) {
            int N = n;
            long K = k;

            BigInteger f[] = new BigInteger[N];
            f[0] = new BigInteger("1");
            for (int i = 1; i < N; i++) {
                f[i] = f[i - 1].multiply(new BigInteger(String.valueOf(i)));
            }

            KthUtil kthUtil = new KthUtil(N);
            String rs = new String();
            for (int i = N - 1; i > 0; i--) {
                BigInteger ki = new BigInteger(String.valueOf(K)).divide(f[i]);
                int a = kthUtil.find_kth(ki.intValue() + 1);
                rs += a;
                kthUtil.del(a);
                K = K - ki.multiply(f[i]).intValue();
            }

            rs += kthUtil.find_kth(1);
            return rs;
        }

        /**
         * 树状数组工具类
         */
        class KthUtil {

            int N;
            int a[];
            int BIT;

            public KthUtil(int n) {
                N = n;
                a = new int[N + 1];
                BIT = (int) (Math.log(N) / Math.log(2)) + 1;//BIT = log2(N)
                for (int i = 0; i <= N; i++) {
                    a[i] = 0;
                }
                for (int i = 1; i <= N; i++) {
                    for (int j = i; j <= N; j += lowbit(j)) {
                        a[j]++;
                    }
                }
            }


            int lowbit(int p) {
                return p & -p;
            }

            void del(int p) {
                for (int j = p; j <= N; j += lowbit(j)) {
                    a[j]--;
                }
            }

            int find_kth(int k) {
                int c = 0;
                int s = 0;
                for (int i = BIT; i >= 0; i--) {
                    c += 1 << i;
                    if (c > N || s + a[c] >= k) {
                        c -= 1 << i;
                    } else {
                        s += a[c];
                    }
                }
                return c + 1;
            }

        }
    }
}
