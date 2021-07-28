package com.pastarhome.bcode.util.concurrent;



import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
public class CounterRateLimiterTest {

    @Autowired
    CounterRateLimiter counterRateLimiter;


    @Test
    void singleThreadTryAcquire() {
        for (int i = 0; i < 20; i++) {
            log.info("请求开始,[i={}]",i);
            counterRateLimiter.tryAcquire(i);
            log.info("请求结束,[i={}]",i);
        }
    }

    @Test
    void threadPoolTryAcquire() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            log.info("请求开始,[i={}]",i);
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    counterRateLimiter.tryAcquire(index);
                }
            });
            log.info("请求结束,[i={}]",i);
        }
        Thread.sleep(60*1000);
    }
}