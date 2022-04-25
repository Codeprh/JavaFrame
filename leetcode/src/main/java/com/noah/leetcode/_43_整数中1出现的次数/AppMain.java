package com.noah.leetcode._43_整数中1出现的次数;

public class AppMain {

    public static void main(String[] args) {

    }

    public int countDigitOne(int n) {
        //输入：n = 12 输出：5

        return 0;
    }

    class Solution {
        public int countDigitOne(int n) {
            // mulk 表示 10^k
            // 在下面的代码中，可以发现 k 并没有被直接使用到（都是使用 10^k）
            // 但为了让代码看起来更加直观，这里保留了 k
            long mulk = 1;
            int ans = 0;
            for (int k = 0; n >= mulk; ++k) {
                ans += (n / (mulk * 10)) * mulk + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk);
                mulk *= 10;
            }
            return ans;
        }
    }

    class Solution12 {
        public int countDigitOne(int n) {
            int digit = 1, res = 0;
            int high = n / 10, cur = n % 10, low = 0;
            while(high != 0 || cur != 0) {
                if(cur == 0) res += high * digit;
                else if(cur == 1) res += high * digit + low + 1;
                else res += (high + 1) * digit;
                low += cur * digit;
                cur = high % 10;
                high /= 10;
                digit *= 10;
            }
            return res;
        }

    }


}
