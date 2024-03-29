package com.noah.jmh.map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Fork(1)
//基准测试类型，当前测试吞吐量
@BenchmarkMode({Mode.All})
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

    static Map<Long, Double> longMap = new HashMap<>();
    static Map<BigInteger, Double> bigIntegerMap = new HashMap<>();

    static List<Long> keyList = new ArrayList<>(10000);

    static {

        IntStream.rangeClosed(1, 10000).forEach(i -> {

            int nextInt = ThreadLocalRandom.current().nextInt(1000, 1000000);
            keyList.add((long) nextInt);

            double doubleValue = Integer.valueOf(nextInt).doubleValue();

            longMap.put((long) nextInt, doubleValue);
            bigIntegerMap.put(new BigInteger(String.valueOf(nextInt)), doubleValue);
        });
    }

    @Benchmark
    public void bigIntegerMap() {
        int index = ThreadLocalRandom.current().nextInt(0, keyList.size() - 1);
        Long mapKey = keyList.get(index);

        bigIntegerMap.get(new BigInteger(String.valueOf(mapKey)));
    }

    @Benchmark
    public void longMap() {
        int index = ThreadLocalRandom.current().nextInt(0, keyList.size() - 1);
        Long mapKey = keyList.get(index);
        longMap.get(mapKey);
    }

    public static void main(String[] args) throws RunnerException {
        System.out.println("hello world");
        Options opt = new OptionsBuilder()
                .include(HashMapBenchmark.class.getSimpleName())
                .result("result_hashmap_plus.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }


}
