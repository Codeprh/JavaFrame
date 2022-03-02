package com.noah.designpatterns.cache;

/**
 * 高效且可伸缩的结果缓存，装饰模式
 *
 * @param <A>
 * @param <V>
 */
public interface Computable<A, V> {
    V compute(A arg) throws Exception;
}
