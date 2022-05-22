package com.noah.leetcode._60_排列序列.noah;

import java.math.BigInteger;

/**
 * @description:
 **/
public class Kangtuo {
    static int N = 4;
    static long K = 2; //TODO toBigInteger

    static BigInteger f[] = new BigInteger[N];

    public static void main(String[] args) {
        f[0] = new BigInteger("1");
        for (int i = 1; i < N; i++) {
            f[i] = f[i - 1].multiply(new BigInteger(String.valueOf(i)));
        }
//        String s = f[999].toString(36);
//        System.out.println(s);

        KthUtil kthUtil = new KthUtil(N);

        for (int i = N - 1; i > 0; i--) {
            BigInteger k = new BigInteger(String.valueOf(K)).divide(f[i]);
            int a = kthUtil.find_kth(k.intValue() + 1);
            System.out.println(a);
            kthUtil.del(a);
            K = K - k.multiply(f[i]).intValue();
        }
        System.out.println(kthUtil.find_kth(1));
    }
}
