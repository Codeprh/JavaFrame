package com.noah.async.domain;

import org.springframework.stereotype.Service;
import org.springframework.util.function.SingletonSupplier;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 定义单例资源
 */
@Service
public class SingletonResource {

    private SingletonSupplier<Executor> defaultExecutor;

    @PostConstruct
    public void SingletonResource() {
        defaultExecutor = new SingletonSupplier(Executors.newFixedThreadPool(3), null);
    }
}
