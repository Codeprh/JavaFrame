package com.noah.leetcode._20_表示数值的字符;

import org.apache.commons.lang3.StringUtils;

public class AppMain {

    public static void main(String[] args) {

        String[] arry = new String[]{"+100", "5e2", "-123", "3.1416", "-1E-16", "0123"};
        for (String a : arry) {

            System.out.println(a + "," + StringUtils.isNumeric(a));
            //System.out.println(a + "," + Character.isDigit(a.toCharArray()));
        }

    }

}
