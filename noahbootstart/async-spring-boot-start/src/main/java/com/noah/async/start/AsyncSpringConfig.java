package com.noah.async.start;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
@Slf4j
public class AsyncSpringConfig implements AsyncConfigurer {

    @Value("${async.more:default}")
    private String asyncMoreModel;

    @Override
    public Executor getAsyncExecutor() {

        String more = asyncMoreModel;
        log.info("now config is:{}", more);

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("noah-async-more").build();
        return Executors.newFixedThreadPool(3, threadFactory);
    }
}