package com.noah.jmh.map;

import com.noah.jmh.exception.ExectionBenchmark;
import io.netty.util.collection.IntObjectHashMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Fork(1)
//基准测试类型，当前测试吞吐量
@BenchmarkMode({Mode.Throughput})
//基准测试结果的时间类型，当前时间设置为秒
@OutputTimeUnit(TimeUnit.SECONDS)
//预热的迭代次数
@Warmup(iterations = 100, time = 1, timeUnit = TimeUnit.MILLISECONDS)
//测试线程数量
@Threads(1)
//该状态为每个线程独享
@State(Scope.Benchmark)
//度量:iterations进行测试的轮次，time每轮进行的时长，timeUnit时长单位,batchSize批次数量
@Measurement(iterations = 100, time = 1, timeUnit = TimeUnit.MILLISECONDS, batchSize = -1)
public class HashMapBenchmark {

    static IntObjectHashMap<String> map = new IntObjectHashMap<>();

    static HashMap<Integer, String> hashMap = new HashMap<>();

    static final String a = "a";

    static {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            map.put(i, a + i);
            hashMap.put(i, a + i);

        });
    }

    @Benchmark
    public void netty() {
        int index = ThreadLocalRandom.current().nextInt(1, 100);
        map.get(index);
    }

    @Benchmark
    public void jdk() {
        int index = ThreadLocalRandom.current().nextInt(1, 100);
        map.get(index);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(HashMapBenchmark.class.getSimpleName())
                .result("result_hashmap.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }


}
