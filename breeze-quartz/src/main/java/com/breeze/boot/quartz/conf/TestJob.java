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


package com.breeze.boot.quartz.conf;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 测试任务
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Slf4j
@Component("testJob")
@PersistJobDataAfterExecution
public class TestJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        context.getJobDetail().getJobDataMap().forEach((k, v) -> log.info("key:: {}, value:: {}", k, v));
        try {
            int i = 1 / 0;
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Job当前执行时间: " + LocalDateTime.now());
    }
}
