package com.noah.spring.retry.service;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Service
public class GuavaRetryService {

    public static void main(String[] args) {
        doXx();
    }

    public static void doXx() {

        Callable<String> callable = new Callable<String>() {

            int count = 0;

            @Override
            public String call() throws Exception {
                count++;
                System.out.println("i am do count=" + count);

                if (count < 5) {
                    return null;
                }

                return "hello true"; // do something useful here
            }
        };

        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                .retryIfResult(Predicates.<String>isNull())
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .build();
        try {
            String call = retryer.call(callable);
            System.out.println(call);
        } catch (RetryException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
