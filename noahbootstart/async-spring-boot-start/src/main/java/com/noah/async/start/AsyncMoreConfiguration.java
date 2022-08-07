package com.noah.async.start;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "async", value = "more", matchIfMissing = true)
@ComponentScan("com.noah.async.start")
public class AsyncMoreConfiguration {
}
