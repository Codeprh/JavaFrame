package com.noah.log.service;

import com.noah.log.annotations.MergeLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoService {

    @MergeLog
    public void testLog() {

        log.info("test log ....1");
        log.info("test log ....2");
        log.info("test log ....3");

    }
}
