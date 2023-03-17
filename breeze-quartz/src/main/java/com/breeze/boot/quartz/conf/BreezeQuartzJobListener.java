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

import cn.hutool.core.date.DateUtil;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.domain.SysQuartzJobLog;
import com.breeze.boot.quartz.service.SysQuartzJobLogService;
import com.breeze.core.constants.QuartzConstants;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * 监听器
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@Slf4j
public class BreezeQuartzJobListener implements JobListener {

    private static final ThreadLocal<SysQuartzJobLog> logThreadLocal = ThreadLocal.withInitial(SysQuartzJobLog::new);
    private final SysQuartzJobLogService quartzJobLogService;

    public BreezeQuartzJobListener(SysQuartzJobLogService quartzJobLogService) {
        this.quartzJobLogService = quartzJobLogService;
    }

    /**
     * 将异常信息转化成字符串
     *
     * @param t t
     * @return {@link String}
     */
    private static String exception(Throwable t) {
        if (t == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            t.printStackTrace(new PrintStream(baos));
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toString();
    }

    /**
     * @return JobListener 名称
     */
    @Override
    public String getName() {
        return "BREEZE_QUARTZ_JOB_LISTENER";
    }

    /**
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        log.info("BreezeQuartzJobListener says: " + jobDetail.getKey() + "Job Is about to be executed.");
        SysQuartzJobLog quartzRunningLog = SysQuartzJobLog
                .builder()
                .createTime(LocalDateTime.now())
                .build();
        logThreadLocal.set(quartzRunningLog);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        try {
            JobDetail jobDetail = context.getJobDetail();
            log.info("BreezeQuartzJobListener says: " + jobDetail.getKey() + "Job Execution was vetoed.");
        } finally {
            logThreadLocal.remove();
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        try {
            JobDetail jobDetail = context.getJobDetail();
            log.info("BreezeQuartzJobListener says: " + jobDetail.getKey() + "Job was executed.");

            JobKey jobKey = jobDetail.getKey();
            TriggerKey triggerKey = context.getTrigger().getKey();
            Date fireTime = context.getFireTime();
            Class<? extends Job> jobClass = jobDetail.getJobClass();
            SysQuartzJob quartzJob = (SysQuartzJob) context.getMergedJobDataMap().get(QuartzConstants.JOB_DATA_KEY);
            log.info("JobClass:{}, Job:{}, Trigger:{}, FireTime:{}", jobClass, jobKey, triggerKey, DateUtil.formatDateTime(fireTime));
            SysQuartzJobLog quartzJobLog = logThreadLocal.get();
            quartzJobLog.setJobName(quartzJob.getJobName());
            quartzJobLog.setCronExpression(quartzJob.getCronExpression());
            quartzJobLog.setClazzName(quartzJob.getClazzName());
            quartzJobLog.setJobGroupName(quartzJob.getJobGroupName());
            if (Objects.nonNull(jobException)) {
                // 保存执行失败记录
                quartzJobLog.setStatus(1);
                quartzJobLog.setEndTime(LocalDateTime.now());
                quartzJobLog.setExceptionInfo(exception(jobException));
                long millis = quartzJobLog.getCreateTime().until(quartzJobLog.getEndTime(), ChronoUnit.MILLIS);
                log.info("花费时间: {} - {} = {}", quartzJobLog.getCreateTime(), quartzJobLog.getEndTime(), millis);
                quartzJobLog.setJobMessage("运行时间 " + millis + " 毫秒 ");
                this.quartzJobLogService.save(quartzJobLog);
                return;
            }
            // 保存执行成功记录
            quartzJobLog.setStatus(1);
            quartzJobLog.setEndTime(LocalDateTime.now());
            long millis = quartzJobLog.getCreateTime().until(quartzJobLog.getEndTime(), ChronoUnit.MILLIS);
            log.info("花费时间: {} - {} = {}", quartzJobLog.getCreateTime(), quartzJobLog.getEndTime(), millis);
            quartzJobLog.setJobMessage("运行时间 " + millis + " 毫秒 ");
            this.quartzJobLogService.save(quartzJobLog);
        } finally {
            log.info("释放 => logThreadLocal");
            logThreadLocal.remove();
        }
    }
}
