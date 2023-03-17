/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 测试任务
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@Slf4j
@Component
@PersistJobDataAfterExecution
public class BreezeJobs {

    public void testM(String testP) {
        log.info(testP);
        int i = 1 / 0;
        log.info("Job当前执行时间: " + LocalDateTime.now());
    }

    public void testM(String testP, Integer i, Double d, Long l, Boolean b1, Boolean b2) {
        log.info(testP);
        log.info("{}", i);
        log.info("{}", d);
        log.info("{}", l);
        log.info("{}", b1);
        log.info("{}", b2);
        log.info("Job当前执行时间: " + LocalDateTime.now());
    }
}
