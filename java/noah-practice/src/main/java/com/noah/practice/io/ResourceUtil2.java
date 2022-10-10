package com.noah.practice.io;

import cn.hutool.core.io.resource.ResourceUtil;

import java.net.URL;
import java.util.List;

public class ResourceUtil2 {
    public static void main(String[] args) {
        List<URL> resources = ResourceUtil.getResources("a/*");
        System.out.println(resources);
    }
}
