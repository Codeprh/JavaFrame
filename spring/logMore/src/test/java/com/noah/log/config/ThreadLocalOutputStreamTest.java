package com.noah.log.config;

import com.noah.log.service.DoService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
@Slf4j
class ThreadLocalOutputStreamTest {

    @Resource
    DoService doService;

    @Test
    public void doTest() {
        doService.testLog();
    }

    @Test
    public void testGet() {
        try {
            Request request = new Request.Builder().url("https://www.baidu.com").build();
            Response response = OkHttpClientUtil.getOkHttpClient().newCall(request).execute();

            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}