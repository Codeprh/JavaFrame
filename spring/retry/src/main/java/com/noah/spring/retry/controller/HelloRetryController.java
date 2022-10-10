package com.noah.spring.retry.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import com.noah.spring.retry.service.RetryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/retry")
@Slf4j
public class HelloRetryController {

    @Resource
    RetryService retryService;

    @SneakyThrows
    @GetMapping("jarResource")
    public String jarResource(HttpServletRequest request) {

        List<org.springframework.core.io.Resource> rs = Arrays.asList(new PathMatchingResourcePatternResolver().getResources("testResource/cn/**"));
        List<URL> us = ResourceUtil.getResources("testResource/cn");

        log.info("rs=" + rs.toString());
        log.info("us=" + us.toString());

        URL url1 = ResourceUtil.getResource("testResource/cn");
        File fileOrDirectory = new File(url1.getPath());
        doExtract(fileOrDirectory);

        return "hello jarResource";
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


    @GetMapping("hello")
    public String helloRetry(HttpServletRequest request) {

        String zzName = request.getParameter("z1Name");
        log.info("hello retry,z1Name=" + zzName);

        return "hello retry";
    }

    @GetMapping("helloSleep")
    public String helloRetry1() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello sleep";
    }

}
