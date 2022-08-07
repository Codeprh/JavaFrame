package com.noah.async.start.safeQueue.test;

import com.noah.async.start.safeQueue.MemoryLimitCalculator;
import com.noah.async.start.safeQueue.MemorySafeLinkedBlockingQueue;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.springframework.util.Assert;

import java.lang.instrument.Instrumentation;

public class SafeQueueTest {

    public static void main(String[] args) {

        ByteBuddyAgent.install();
        final Instrumentation instrumentation = ByteBuddyAgent.getInstrumentation();

        final long objectSize = instrumentation.getObjectSize((Runnable) () -> {
        });

        int maxFreeAvailable = (int) MemoryLimitCalculator.maxAvailable();

        MemorySafeLinkedBlockingQueue<Runnable> queue = new MemorySafeLinkedBlockingQueue<>(maxFreeAvailable);

        Assert.isTrue(!queue.offer(() -> {
        }), "add all true");

        queue.setMaxFreeMemory((int) (maxFreeAvailable - objectSize));
        Assert.isTrue(queue.offer(() -> {
        }), "add one fail");

        System.out.println("hello world");

    }
}
