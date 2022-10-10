package com.noah.spring.retry.service;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class ResourcesServiceTest {

    @Test
    public void testResources() throws IOException {

        List<Resource> rs = Arrays.asList(new PathMatchingResourcePatternResolver().getResources("testResource/cn/**"));
        List<URL> us = ResourceUtil.getResources("testResource/cn");

        URL url1 = ResourceUtil.getResource("testResource/cn");
        File fileOrDirectory = new File(url1.getPath());

        log.info(rs.toString());

        doExtract(fileOrDirectory);
        log.info("hello rs");
    }

    private void doExtract(File fileOrDirectory) {

        if (fileOrDirectory.isFile()) {
            return;
        }

        //递归处理目录
        File[] listFiles = fileOrDirectory.listFiles((file) -> {

            //递归处理
            if (file.isDirectory()) {
                return true;
            }

            String absolutePath = file.getAbsolutePath();
            log.info("path=" + absolutePath);

            return false;
        });

        if (Objects.nonNull(listFiles) && listFiles.length > 0) {
            //递归处理
            for (File f : listFiles) {
                doExtract(f);
            }
        }

    }


    @Test
    public void testList() {

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        List<Integer> sub = getSub(list);

        System.out.println(list);
        log.info("hello test");

    }

    private List<Integer> getSub(List<Integer> list) {
        list = list.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
        return list;
    }
}