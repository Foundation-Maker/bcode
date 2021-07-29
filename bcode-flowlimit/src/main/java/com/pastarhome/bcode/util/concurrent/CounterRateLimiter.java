package com.pastarhome.bcode.util.concurrent;

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
    private static Long secCounter = System.currentTimeMillis();

    /**
     * TPS计数
     */
    private AtomicInteger TPSCounter = new AtomicInteger(0);

    private Integer perSecLimit;

    public CounterRateLimiter CounterRateLimiter() {
        return this;
    }

    public CounterRateLimiter creat(Integer perSecLimit) {
        this.perSecLimit = perSecLimit;
        return this;
    }

    /**
     * 一秒内的流量限制不超过 perSecLimit TPS
     */
    public boolean tryAcquire() {
        if ((System.currentTimeMillis() - secCounter) > 1000) {
            secCounter = System.currentTimeMillis();
            TPSCounter = new AtomicInteger(0);
            return true;
        }

        if (TPSCounter.get() < perSecLimit) {
            TPSCounter.incrementAndGet();
            return true;
        } else {
            return false;
        }

    }
}

