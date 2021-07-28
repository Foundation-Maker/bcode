package com.pastarhome.bcode;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class BcodeFlowLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(BcodeFlowLimitApplication.class, args);
        log.info("启动成功");
        consoleLog();
    }

    /**
     * 打印日志
     */
    public static void consoleLog() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("日志, {}", LocalDateTime.now());
        }
    }
}
