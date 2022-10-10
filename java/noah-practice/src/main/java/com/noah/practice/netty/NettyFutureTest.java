package com.noah.practice.netty;

import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;

public class NettyFutureTest {

    private final Promise defaultPromise = GlobalEventExecutor.INSTANCE.newPromise();

    public static void main(String[] args) {

        NettyFutureTest main = new NettyFutureTest();

        main.isDoneTest();

    }

    private void isDoneTest() {

        defaultPromise.setUncancellable();

        defaultPromise.cancel(false);

        boolean isDone = defaultPromise.isDone();

        System.out.println(isDone);

    }

}
