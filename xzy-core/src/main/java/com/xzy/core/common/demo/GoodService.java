package com.xzy.core.common.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GoodService {
    @Async
    public String getasnyc() throws InterruptedException {
        Thread.sleep(5000);
        log.info("方法执行 准备返回");
        return "方法执行";
    }
}
