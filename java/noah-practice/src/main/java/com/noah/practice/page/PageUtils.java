package com.noah.practice.page;

import cn.hutool.core.collection.ListUtil;

import java.util.Arrays;
import java.util.List;

public class PageUtils {
    public static void main(String[] args) {
        pageStringList();
    }

    // 字符串排序后分页
    public static void pageStringList() {
        String[] strs = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        List<String> list = Arrays.asList(strs);
        // ListUtil.sort(list, (a, b) -> a.compareTo(b));
        //ListUtil.sort(list, String::compareTo);
        // 返回第0页，每页大小为5条数据
        int pageNo = 6;
        int pageSize = 5;
        List<String> results = ListUtil.page(pageNo, pageSize, list);
        System.out.println(results);
    }
}
