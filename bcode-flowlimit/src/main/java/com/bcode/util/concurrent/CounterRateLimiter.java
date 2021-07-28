package com.bcode.util.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 流量计数器模式
 * 一秒内的流量限制不超过5TPS
 *
 * @author wei youchen
 * @date 2021/7/28
 */
@Slf4j
@Component
public class CounterRateLimiter {

    /**
     * 秒计数
     */
    private static Long secCounter;

    /**
     * TPS计数
     */
    private AtomicInteger TPSCounter;


    /**
     * 一秒内的流量限制不超过4TPS
     *
     * @param index
     */
    public void tryAcquire(int index) {

        if (secCounter == null) {
            log.info("重置,[index={},secCounter={},TPSCounter={}]", index, secCounter, TPSCounter);
            secCounter = System.currentTimeMillis();
            TPSCounter = new AtomicInteger(0);
        }

        if ((System.currentTimeMillis() - secCounter) > 1000) {
            log.info("时间重置,[index={},secCounter={},TPSCounter={}]", index, secCounter, TPSCounter);
            secCounter = null;
        }

        if (TPSCounter.intValue() > 4) {
            log.info("TPS限流,[index={},secCounter={},TPSCounter={}]", index, secCounter, TPSCounter);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tryAcquire(index);
        } else {
            log.info("正常访问,[index={},secCounter={},TPSCounter={}]", index, secCounter, TPSCounter);
            TPSCounter.incrementAndGet();
        }

    }
}
