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

    @Test
    void threadPoolTryAcquire() throws InterruptedException {
        CounterRateLimiter counterRateLimiter = new CounterRateLimiter().creat(4);

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    counterRateLimiter.tryAcquire();
                }
            });
        }
        Thread.sleep(60 * 1000);
    }
}