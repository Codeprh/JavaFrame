package com.noah.practice.thread;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorCompletionServiceTest {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> completionService =
                new ExecutorCompletionService<>(executorService);
        System.out.println("约几个妹子一起吃个饭吧。");
        completionService.submit(() -> {
            System.out.println("小红：好的，哥哥。我化妆要2个小时。等一下哦。");
            TimeUnit.SECONDS.sleep(15);
            System.out.println("小红：我2个小时准时化好了，哥哥来接我吧。");
            return "小红化完了。";
        });
        completionService.submit(() -> {
            System.out.println("小媛：好的，哥哥。我化妆要30分钟。等一下哦。");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("小媛：我30分钟准时化好了，哥哥来接我吧。");
            return "小媛化完了。";
        });
        completionService.submit(() -> {
            System.out.println("小花：好的，哥哥。我化妆要1个小时。等一下哦。");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("小花：我1个小时准时化好了，哥哥来接我吧。");
            return "小花化完了。";
        });
        TimeUnit.SECONDS.sleep(1);
        System.out.println("都通知完,等着吧。");
        //循环3次是因为上面提交了3个异步任务
        for (int i = 0; i < 3; i++) {
            String returnStr = completionService.take().get();
            System.out.println(returnStr + "我去接她");
        }
        Thread.currentThread().join();
    }
}
