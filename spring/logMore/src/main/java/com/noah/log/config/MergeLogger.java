package com.noah.log.config;

import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class MergeLogger implements HttpLoggingInterceptor.Logger {

    public static MergeLogger INSTANCE = new MergeLogger();
    private static ThreadLocal<StringBuilder> tl = new ThreadLocal<>();

    public static void start() {
        tl.set(new StringBuilder());
    }

    public static String stop() {
        StringBuilder stringBuilder = tl.get();

        if (StringUtils.isNotEmpty(stringBuilder)) {
            tl.remove();
            return stringBuilder.toString();
        } else {
            return null;
        }

    }

    private MergeLogger() {

    }

    @Override
    public void log(String message) {

        StringBuilder stringBuilder = tl.get();

        if (Objects.nonNull(stringBuilder) && stringBuilder.length() < 4 * 1024 * 1024) {
            stringBuilder.append("\n ").append(message);
        }
    }
}
