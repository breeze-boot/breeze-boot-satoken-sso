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

import cn.hutool.extra.spring.SpringUtil;
import com.breeze.boot.core.constants.QuartzConstants;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.utils.JobInvokeUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 不允许并发任务Job
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@Slf4j
@DisallowConcurrentExecution
public class DisallowConcurrentExecutionJob extends QuartzJobBean {

    /**
     * 执行内部
     *
     * @param context 上下文
     */
    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        SysQuartzJob quartzJob = (SysQuartzJob) jobDataMap.get(QuartzConstants.JOB_DATA_KEY);
        String clazzName = quartzJob.getClazzName();
        String beanName = JobInvokeUtils.getBeanName(clazzName);
        String methodName = JobInvokeUtils.getMethodName(clazzName, ".", "(");
        String params = JobInvokeUtils.getParams(clazzName);
        String[] paramArray = params.split(",");
        Class<?>[] parameterTypes = new Class[paramArray.length];
        Object[] parameters = new Object[paramArray.length];
        JobInvokeUtils.getParams(paramArray, parameterTypes, parameters);
        if (clazzName.startsWith("com.")) {
            Object bean = Class.forName(beanName).newInstance();
            Method method = bean.getClass().getMethod(methodName, parameterTypes);
            method.invoke(bean, parameters);
        } else {
            Object bean = SpringUtil.getBean(beanName);
            Method method = bean.getClass().getMethod(methodName, parameterTypes);
            method.invoke(bean, parameters);
        }
    }
}
