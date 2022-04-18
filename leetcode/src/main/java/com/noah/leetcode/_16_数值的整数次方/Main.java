package com.noah.leetcode._16_数值的整数次方;

import lombok.extern.slf4j.Slf4j;

/**
 * 数值的整数次方
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        Main app = new Main();

        double x = 2;
        int n = 20;

        for (int i=0;i<100;i++){
            if ((i & 1) == 1){
                System.out.println(i);
            }
        }
        //DoubleMath.
        //System.out.println(IntMath.pow((int) x, n));
        System.out.println(Math.pow(x, n));
        System.out.println("-------");
        System.out.println(app.myPow(x, n));
    }

    public double myPow(double x, int n) {

        //参数校验
        if (x == 0) {
            return 0;
        }

        if (n == 0 || x == 1) {
            return 1;
        }

        //n为负数的处理
        long originalN = n;
        long absN = n;
        if (absN < 0) {
            absN = -absN;
        }

        //正常的求解
        double r = 1;

        //减少计算次数
        while (absN > 0) {

            //取余数 n%2 等价于 判断二进制最右一位值n&1 ；
            if ((absN & 1) == 1) {
                System.out.println("N=" + absN + ",cal");
                r *= x;
            }
            x *= x;
            absN >>= 1;
        }

        //for (int i = 0; i < absN; i++) {
        //    r = r * x;
        //}

        //n为负数的处理
        if (originalN < 0) {
            r = 1.0 / r;
        }

        return r;
    }

}
