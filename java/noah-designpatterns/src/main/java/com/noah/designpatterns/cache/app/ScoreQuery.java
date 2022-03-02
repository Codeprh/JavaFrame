package com.noah.designpatterns.cache.app;

import com.noah.designpatterns.cache.Computable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 装饰器模式：真正执行业务的地方（耗时比较长的操作）
 *
 * @param <A>
 * @param <V>
 */
@Slf4j
public class ScoreQuery<A, V> implements Computable<String, Integer> {

    @Override
    public Integer compute(String arg) throws Exception {

        log.info("{},开始从db查成绩了～～", arg);
        TimeUnit.SECONDS.sleep(1);

        return ThreadLocalRandom.current().nextInt(400, 500);
    }
}
