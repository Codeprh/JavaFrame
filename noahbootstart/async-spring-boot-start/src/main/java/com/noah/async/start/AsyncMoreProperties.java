package com.noah.async.start;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "async")
public class AsyncMoreProperties {

    private String more;

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
