package com.noah.leetcode._60_排列序列.noah;

/**
 * @description: 有一个集合，里面初始化有1～N个数，可以删除某个数，也可以查询第K小的数
 * 树状数组优化，单次查询时间复杂度logn
 **/
public class KthUtil {

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

    public static void main(String[] args) {
        KthUtil kthUtil = new KthUtil(20);
        kthUtil.del(1);
        kthUtil.del(2);
        kthUtil.del(4);
        System.out.println(kthUtil.find_kth(2));
        System.out.println(kthUtil.find_kth(15));
    }

}
