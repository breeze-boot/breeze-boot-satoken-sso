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

package com.breeze.boot.auth.config;

import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.events.LocalSysLogSaveEventListener;
import com.breeze.boot.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统日志配置
 *
 * @author gaoweixuan
 * @date 2023/05/15
 */
@Configuration
@RequiredArgsConstructor
public class SysLogConfiguration {

    /**
     * 系统日志服务
     */
    private final SysLogService sysLogService;

    /**
     * 登录日志保存侦听器
     *
     * @return {@link LocalSysLogSaveEventListener}
     */
    @Bean
    public LocalSysLogSaveEventListener loginLogSaveEventListener() {
        return new LocalSysLogSaveEventListener((source) -> this.sysLogService.saveSysLog((SysLogBO) source.getSource()));
    }

}


