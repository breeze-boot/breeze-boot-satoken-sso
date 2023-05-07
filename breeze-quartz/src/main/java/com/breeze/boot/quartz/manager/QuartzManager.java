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

package com.breeze.boot.quartz.manager;

import com.breeze.boot.core.constants.QuartzConstants;
import com.breeze.boot.quartz.conf.AllowConcurrentExecutionJob;
import com.breeze.boot.quartz.conf.BreezeQuartzJobListener;
import com.breeze.boot.quartz.conf.DisallowConcurrentExecutionJob;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.service.SysQuartzJobLogService;
import com.google.common.collect.Maps;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.breeze.boot.core.constants.QuartzConstants.*;
import static com.breeze.boot.core.constants.QuartzConstants.MisfirePolicy.*;

/**
 * quartz经理
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@Component
public class QuartzManager {

    @Autowired
    private SysQuartzJobLogService quartzJobLogService;

    @Autowired
    private Scheduler scheduler;

    @SuppressWarnings("unchecked")
    private static Class<? extends QuartzJobBean> getClass(String classname) throws Exception {
        return (Class<? extends QuartzJobBean>) Class.forName(classname);
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends QuartzJobBean> getClass(Integer concurrent) {
        return concurrent == 1 ? AllowConcurrentExecutionJob.class : DisallowConcurrentExecutionJob.class;
    }

    /**
     * 添加任务
     *
     * @param quartzJob quartz任务
     */
    public void addOrUpdateJob(SysQuartzJob quartzJob) {
        try {
            Class<? extends QuartzJobBean> jobClass = getClass(quartzJob.getConcurrent());
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(JOB_DATA_KEY, quartzJob);
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName())
                    .usingJobData(jobDataMap)
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzJob.getId() + ":" + TRIGGER_NAME, quartzJob.getJobGroupName())
                    .withSchedule(this.getScheduleBuilder(quartzJob))
                    .build();

            // 针对特定的JobDetail进行监听
            BreezeQuartzJobListener listener = new BreezeQuartzJobListener(this.quartzJobLogService);
            // Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
            // this.scheduler.getListenerManager().addJobListener(listener, matcher);

            // 设置全局监听
            this.scheduler.getListenerManager().addJobListener(listener);

            JobKey jobKey = JobKey.jobKey(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
            if (scheduler.checkExists(jobKey)) {
                // 删除重复流程
                scheduler.deleteJob(jobKey);
            }

            // 绑定trigger
            this.scheduler.scheduleJob(jobDetail, trigger);
            // 根据用户提交的参数判断任务是否启动
            if (Objects.equals(quartzJob.getStatus(), QuartzConstants.Status.PAUSE.getStatus())) {
                scheduler.pauseJob(jobKey);
            } else {
                if (!this.scheduler.isShutdown()) {
                    this.scheduler.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CronScheduleBuilder getScheduleBuilder(SysQuartzJob quartzJob) {
        if (Objects.equals(quartzJob.getMisfirePolicy(), DO_NOTHING.getCode())) {
            return CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
        } else if (Objects.equals(quartzJob.getMisfirePolicy(), IGNORE_MISFIRE.getCode())) {
            return CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()).withMisfireHandlingInstructionIgnoreMisfires();
        } else if (Objects.equals(quartzJob.getMisfirePolicy(), FIRE_AND_PROCEED.getCode())) {
            return CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();
        }
        throw new RuntimeException("策略不存在");
    }

    /**
     * 删除任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (this.scheduler.checkExists(jobKey)) {
                this.scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
                this.scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));
                this.scheduler.deleteJob(jobKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                this.scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                this.scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 现在运行job
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public void runJobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                this.scheduler.triggerJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有任务
     */
    public List<Map<String, Object>> listAllJob() {
        List<Map<String, Object>> jobList = new ArrayList<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeyList = this.scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeyList) {
                List<? extends Trigger> triggerList = this.scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggerList) {
                    Map<String, Object> jobDetailMaps = Maps.newHashMap();
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        jobDetailMaps.put("cronExpression", cronTrigger.getCronExpression());
                        jobDetailMaps.put("timeZone", cronTrigger.getTimeZone().getDisplayName());
                    }
                    jobDetailMaps.put("TriggerGroupName", trigger.getKey().getName());
                    jobDetailMaps.put("TriggerName", trigger.getKey().getGroup());
                    jobDetailMaps.put("JobGroupName", jobKey.getGroup());
                    jobDetailMaps.put("JobName", jobKey.getName());
                    jobDetailMaps.put("StartTime", trigger.getStartTime());
                    jobDetailMaps.put("JobClassName", this.scheduler.getJobDetail(jobKey).getJobClass().getName());
                    jobDetailMaps.put("NextFireTime", trigger.getNextFireTime());
                    jobDetailMaps.put("PreviousFireTime", trigger.getPreviousFireTime());
                    jobDetailMaps.put("Status", this.scheduler.getTriggerState(trigger.getKey()).name());
                    jobList.add(jobDetailMaps);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

}
