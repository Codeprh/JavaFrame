package com.noah.guava.guc;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class FeturesTest {
    public static void main(String[] args) {

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        ListenableFuture<Object> transform = Futures.transform(null, null, null);
        ListenableFuture<Object> objectListenableFuture = Futures.withTimeout(transform, null, null);

        ListenableFuture<String> explosion = service.submit(
                new Callable<String>() {
                    public String call() {
                        if (1 == 1) {
                            throw new RuntimeException("i am failerro");
                        }
                        return "noahsuccess";
                    }
                });


        Futures.addCallback(
                explosion,
                new FutureCallback<String>() {
                    // we want this handler to run immediately after we push the big red button!
                    public void onSuccess(String str) {
                        System.out.println("i am sucees,and str=" + str);
                    }

                    public void onFailure(Throwable thrown) {
                        System.out.println("i am run fail");
                    }
                },
                service);
    }
}
