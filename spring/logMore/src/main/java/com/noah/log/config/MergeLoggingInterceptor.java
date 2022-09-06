package com.noah.log.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

@Slf4j(topic = "mergeLog")
public class MergeLoggingInterceptor implements Interceptor {

    private HttpLoggingInterceptor httpLoggingInterceptor;

    public MergeLoggingInterceptor() {
        this.httpLoggingInterceptor = new HttpLoggingInterceptor(MergeLogger.INSTANCE);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        MergeLogger.start();
        Response response = httpLoggingInterceptor.intercept(chain);
        String stop = MergeLogger.stop();

        log.info(stop);
        return response;
    }
}
