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

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.core.constants.QuartzConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 允许并发的Job
 *
 * @author gaoweixuan
 * @date 2023-03-16
 */
@Slf4j
public class QuartzJob extends QuartzJobBean {

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        SysQuartzJob quartzJob = (SysQuartzJob) jobDataMap.get(QuartzConstants.JOB_DATA_KEY);
        String clazzName = quartzJob.getClazzName();
        String beanName = StrUtil.sub(clazzName, 0, clazzName.lastIndexOf("."));
        String methodName = StrUtil.sub(clazzName, clazzName.lastIndexOf(".") + 1, clazzName.lastIndexOf("("));
        String params = StrUtil.sub(clazzName, clazzName.lastIndexOf("(") + 1, clazzName.lastIndexOf(")"));
        String[] paramArray = params.split(",");
        Class<?>[] parameterTypes = new Class[paramArray.length];
        Object[] parameters = new Object[paramArray.length];
        for (int i = 0; i < paramArray.length; i++) {
            String param = paramArray[i].trim();
            if (param.endsWith("L")) {
                String trim = getTrim(param);
                parameters[i] = Long.parseLong(trim);
                parameterTypes[i] = Long.class;
            } else if (param.endsWith("D")) {
                String trim = getTrim(param);
                parameters[i] = Double.parseDouble(trim);
                parameterTypes[i] = Double.class;
            } else if ("false".equals(param)) {
                parameters[i] = Boolean.FALSE;
                parameterTypes[i] = Boolean.class;
            } else if ("true".equals(param)) {
                parameters[i] = Boolean.TRUE;
                parameterTypes[i] = Boolean.class;
            } else if (param.endsWith("'") || param.endsWith("\"")) {
                parameters[i] = param
                        .substring(0, param.length() - 1)
                        .substring(1, param.length() - 1);
                parameterTypes[i] = String.class;
            } else {
                parameters[i] = Integer.parseInt(param);
                parameterTypes[i] = Integer.class;
            }
        }
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

    @NotNull
    private String getTrim(String param) {
        return param.substring(0, param.length() - 1).trim();
    }
}
