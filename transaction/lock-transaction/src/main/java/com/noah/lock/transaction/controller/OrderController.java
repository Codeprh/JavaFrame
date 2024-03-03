package com.noah.lock.transaction.controller;


import com.noah.lock.transaction.domain.service.main_minor.OrderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author noah
 * @since 2022-10-28
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Data
    static class TemplateImage implements Serializable {
        private static final long serialVersionUID = 1L;

        //识别类型 imageClassifyId
        private Long classifyId;

        private String code;
        //1文字 2图片
        private Integer type;

        //坑位标识
        private String areaUuid;
    }

    public static void main(String[] args) {
        List<TemplateImage> copyTemplates = new ArrayList<>();

        TemplateImage ti = new TemplateImage();
        ti.setClassifyId(0L);
        ti.setCode("");
        ti.setType(0);
        ti.setAreaUuid("");

        copyTemplates.add(ti);
        System.out.println();

        m1();

        if (1 == 1) {
            List<TemplateImage> list = new ArrayList<>();
            TemplateImage t1 = new TemplateImage();
            t1.setClassifyId(1L);
            t1.setCode("");
            t1.setType(1);
            t1.setAreaUuid("");
            list.add(t1);
            copyTemplates = list;
        }

        System.out.println(copyTemplates);
    }

    public static void m1() {
        m2();
    }

    public static void m2() {
        m3();
    }

    public static void m3() {
        System.out.println("hello");
    }

    @Resource
    OrderService orderService;

    @GetMapping("save")
    public String save() {
        orderService.doOrder();
        return "success";
    }

    @Resource
    RestTemplate restTemplate;

    @GetMapping("doCount")
    public String doCount() {

        int max = 500;
        CountDownLatch cdl = new CountDownLatch(max);

        ExecutorService executorService = Executors.newFixedThreadPool(max);

        for (int i = max; i > 0; i--) {

            int finalI = i;
            executorService.execute(() -> {
                long start = 0;
                try {
                    cdl.await();
                    start = System.currentTimeMillis();
                    restTemplate.exchange("http://localhost:8088/order/sleep", HttpMethod.GET, HttpEntity.EMPTY, String.class);
                } catch (Exception e) {
                    //e.printStackTrace();
                } finally {
                    log.info("id=" + finalI + "," + (System.currentTimeMillis() - start));
                }
            });

            cdl.countDown();
        }

        try {
            cdl.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "noah";
    }

    @GetMapping("sleep")
    public String sleep() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
