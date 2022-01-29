package com.noah.dubbo.apache.api;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * dubbo接口逻辑
 */
public interface HelloService {

    String greeting(String name);

    default String replyGreeting(String name) {
        return "Fine, " + name;
    }

    default CompletableFuture<String> greeting(String name, byte signal) {
        return CompletableFuture.completedFuture(greeting(name));
    }
}
