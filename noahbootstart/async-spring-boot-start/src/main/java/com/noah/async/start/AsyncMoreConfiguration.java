package com.noah.async.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(prefix = "async", name = "more", matchIfMissing = true)
@ComponentScan
@Slf4j
public class AsyncMoreConfiguration {

    @PostConstruct
    public void init() {
        log.info("i want to use new threadPool no install");
    }

}
