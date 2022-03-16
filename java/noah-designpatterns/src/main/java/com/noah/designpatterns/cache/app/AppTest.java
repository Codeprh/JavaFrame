package com.noah.designpatterns.cache.app;

import com.noah.designpatterns.cache.Memoizer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppTest {

    public static void main(String[] args) throws Exception {

        Memoizer<String, Integer> memoizer = new Memoizer<String, Integer>(new ScoreQuery());

        log.info(memoizer.compute("noah") + "");
        log.info(memoizer.compute("cute") + "");
    }
}
