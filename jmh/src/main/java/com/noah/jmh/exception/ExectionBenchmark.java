package com.noah.jmh.exception;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 基准测试
 *
 * @author noah
 * @since 2021/8/23 20:45
 */
// fork一个进程进行基准测试
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
public class ExectionBenchmark {

    private static String s = "Hello, world!";  // s.length() == 13.

    @Benchmark
    public void withException() {
        try {
            s.substring(14);
        } catch (RuntimeException re) {
            // blank line, ignore the exception.
        }
    }

    @Benchmark
    public void noException() {
        try {
            s.substring(13);
        } catch (RuntimeException re) {
            // blank line, ignore the exception.
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ExectionBenchmark.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }

}
