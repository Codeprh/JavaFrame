package com.noah.async.start.safeQueue;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MemorySafeLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    public static final long serialVersionUID = 42L;

    /**
     * 默认值，只有JVM可用内存>该值才可以往队列put/offer
     */
    public static int THE_256_MB = 256 * 1024 * 1024;

    /**
     * 剩余总的可用内存
     */
    private int maxFreeMemory;

    public MemorySafeLinkedBlockingQueue() {
        this(THE_256_MB);
    }

    /**
     * Creates a {@code LinkedBlockingQueue} with a capacity of
     * {@link Integer#MAX_VALUE}.
     */
    public MemorySafeLinkedBlockingQueue(int maxFreeMemory) {
        super(Integer.MAX_VALUE);
        this.maxFreeMemory = maxFreeMemory;
    }

    /**
     * Creates a {@code LinkedBlockingQueue} with the given (fixed) capacity.
     *
     * @param capacity the capacity of this queue
     * @throws IllegalArgumentException if {@code capacity} is not greater
     *                                  than zero
     */
    public MemorySafeLinkedBlockingQueue(int capacity, int maxFreeMemory) {
        super(capacity);
        this.maxFreeMemory = maxFreeMemory;
    }

    /**
     * Creates a {@code LinkedBlockingQueue} with a capacity of
     * {@link Integer#MAX_VALUE}, initially containing the elements of the
     * given collection,
     * added in traversal order of the collection's iterator.
     *
     * @param c the collection of elements to initially contain
     * @throws NullPointerException if the specified collection or any
     *                              of its elements are null
     */
    public MemorySafeLinkedBlockingQueue(Collection<? extends E> c, int maxFreeMemory) {
        super(c);
        this.maxFreeMemory = maxFreeMemory;
    }

    public void setMaxFreeMemory(int maxFreeMemory) {
        this.maxFreeMemory = maxFreeMemory;
    }

    public boolean hasRemainedMemory() {
        return MemoryLimitCalculator.maxAvailable() > maxFreeMemory;
    }

    @Override
    public void put(E e) throws InterruptedException {
        if (hasRemainedMemory()) {
            super.put(e);
        }
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {

        return hasRemainedMemory() && super.offer(e, timeout, unit);
    }

    @Override
    public boolean offer(E e) {
        return hasRemainedMemory() && super.offer(e);
    }
}
