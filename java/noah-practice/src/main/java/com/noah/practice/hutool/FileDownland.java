package com.noah.practice.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FileDownland {

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @SneakyThrows
    public static void main(String[] args) {

        List<String> fileUrls = Lists.newArrayList();
        String demoUrl = "https://fp-dev.webapp.163.com/gcom/file/635b8048173f915fdeaf1b7d6ArtT30202";
        for (int i = 0; i < 1000; i++) {
            fileUrls.add(demoUrl);
        }

        long startTime = System.currentTimeMillis();
        CountDownLatch cdl = new CountDownLatch(fileUrls.size());
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.currentTimeMillis();
        fileUrls.stream().forEach(s -> {


            executorService.execute(() -> {
                try {

                    HttpUtil.downloadFile(s, FileUtil.file("/Users/noah/Desktop/netease-temp/sql-supplied/" + startTime + atomicInteger.getAndIncrement() + ".png"), 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });

        });

        cdl.await();
        long useTime = System.currentTimeMillis() - startTime;
        System.out.println("useTime=" + useTime + "ms");

        executorService.shutdownNow();
    }
}
