package com.noah.async.more.controller;

import com.noah.async.more.service.AsyncService;
import com.noah.async.start.limiterQueue.MemoryLimitedLinkedBlockingQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/async")
public class AsyncController {

    @Resource
    AsyncService asyncService;

    @GetMapping("/sayH")
    public String sayHi() {
        return asyncService.doAsync();
    }
}
