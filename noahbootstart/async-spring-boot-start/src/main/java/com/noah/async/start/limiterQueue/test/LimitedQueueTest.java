package com.noah.async.start.limiterQueue.test;

import com.noah.async.start.limiterQueue.MemoryLimitedLinkedBlockingQueue;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.springframework.util.Assert;

import java.lang.instrument.Instrumentation;

public class LimitedQueueTest {

    public static void main(String[] args) {

        ByteBuddyAgent.install();
        Instrumentation instrumentation = ByteBuddyAgent.getInstrumentation();

        MemoryLimitedLinkedBlockingQueue<Runnable> queue = new MemoryLimitedLinkedBlockingQueue<>(instrumentation, 1);
        Assert.isTrue(!queue.offer(() -> System.out.println("add fail")), "add fail true");

        queue.setMemoryLimiter(Integer.MAX_VALUE);
        Assert.isTrue(queue.offer(() -> System.out.println("add success")), "add success true");
    }
}
