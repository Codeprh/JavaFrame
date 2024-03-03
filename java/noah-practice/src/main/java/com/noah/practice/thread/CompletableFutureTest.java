package com.noah.practice.thread;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 真的异步编程
 */
public class CompletableFutureTest {

    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    @SneakyThrows
    public static void jdkFuture() {

        Future<String> future = executorService.submit(() -> {
            System.out.println("i am callable");
            TimeUnit.SECONDS.sleep(2);
            return "i am return callable";
        });

        StringBuilder futureStr = new StringBuilder("hello ");
        Future<StringBuilder> future1 = executorService.submit(() -> {
            System.out.println("i am runnable and T");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            futureStr.append(",world");
        }, futureStr);

        StringBuilder futureStr2 = new StringBuilder("hello ");
        Future<?> future2 = executorService.submit(() -> {
            System.out.println("i am runnable");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            futureStr2.append(" noah");
        });


        System.out.println("i am do other");
        String str = future.get();
        StringBuilder stringBuilder = future1.get();
        System.out.println("i am get , " + str);
        System.out.println("i am get , " + stringBuilder.toString() + "," + future1.get().toString());

        Object o = future2.get();
        System.out.println("i am get , " + o);
    }

    public static void guavaFuture() {

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        ListenableFuture<String> guavaFuture = listeningExecutorService.submit(() -> {

            System.out.println("i am guava");

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "i am finiah guava";
        });

        System.out.println("i am main do other");
        guavaFuture.addListener(() -> {
            try {
                System.out.println("i am listener," + guavaFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }, listeningExecutorService);

        Futures.addCallback(guavaFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("i am guava , i am success ");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("i am guava , i am fail ");
            }
        }, listeningExecutorService);

        System.out.println("i am main do other v2");

    }

    public static void jdkCompletable() {

        CompletableFuture<StringBuilder> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("i am completable future");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new StringBuilder("hello completable~~");
        }, executorService).handleAsync((sb, ex) -> {
            return new StringBuilder("hello exce");
        }).thenApply((str)->{
            return null;
        });

        completableFuture.whenComplete((sb, ex) -> {
            System.out.println("i am complete and str=" + sb.toString());
        });

    }

    public static void testDeferredResultWithTimeout() throws InterruptedException {
        DeferredResult<String> deferredResult = new DeferredResult<>(500L,"55555555");

        Thread.sleep(1000);


        //CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
        //    // 模拟耗时操作
        //    try {
        //        Thread.sleep(1000);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    return "Hello, async!";
        //});
        //
        //future.thenAccept(result -> {
        //    // 异步操作完成后，设置结果到DeferredResult
        //    deferredResult.setResult(result);
        //});
        //
        //// 设置超时默认逻辑
        //deferredResult.onTimeout(() -> {
        //    deferredResult.setErrorResult("Timeout occurred. Executing default logic.");
        //});

        // 等待结果
        String result = (String) deferredResult.getResult();

        System.out.println("123131321321:"+result);
    }


    public static void main(String[] args) throws InterruptedException {
        testDeferredResultWithTimeout();
        System.out.println(" i am debug");
    }

    static void m1(){
        System.out.println("i am m1");
        m2();
    }

    private static void m2() {
        System.out.println("i am m2");
    }

}
