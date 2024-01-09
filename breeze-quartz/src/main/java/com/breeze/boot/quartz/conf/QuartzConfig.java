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

import org.quartz.*;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz配置
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Configuration
public class QuartzConfig {

    //    @Bean("testJob")
    public JobDetail testJobDetail() {
        return JobBuilder.newJob(TestJob.class)
                .withIdentity("testJob", "系统组")
                .usingJobData("msg", "Hello Quartz")
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    //    @Bean("testJob2")
    public JobDetail test2JobDetail() {
        return JobBuilder.newJob(TestJob.class)
                .withIdentity("testJob2", "系统组")
                .usingJobData("msg2", "Hello Quartz")
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    //    @Bean
    public Trigger testJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(testJobDetail())
                .withIdentity("testJobTrigger", "系统触发器组")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    //    @Bean
    public Trigger testJobTrigger2() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(test2JobDetail())
                .withIdentity("testJobTrigger2", "系统触发器组")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
