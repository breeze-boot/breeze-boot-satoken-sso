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

package com.breeze.boot.sys.config;

import com.breeze.boot.sys.service.SysLogService;
import com.breeze.boot.sys.service.impl.StompJsMsgServiceImpl;
import com.breeze.log.config.SysLogSaveEventListener;
import com.breeze.log.dto.SysLogDTO;
import com.breeze.websocket.bo.UserMsgBO;
import com.breeze.websocket.service.MsgSaveEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统日志收集的配置
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Configuration
public class ListenerConfig {

    /**
     * 系统日志服务
     */
    @Autowired
    private SysLogService sysLogService;

    /**
     * stomp js消息服务
     */
    @Autowired
    private StompJsMsgServiceImpl stompJsMsgService;

    /**
     * 侦听器
     *
     * @return {@link SysLogSaveEventListener}
     */
    @Bean
    public SysLogSaveEventListener sysLogSaveEventListener() {
        return new SysLogSaveEventListener((source) -> this.sysLogService.saveSysLog((SysLogDTO) source.getSource()));
    }

    /**
     * 侦听器
     *
     * @return {@link SysLogSaveEventListener}
     */
    @Bean
    public MsgSaveEventListener msgSaveEventListener() {
        return new MsgSaveEventListener((source) -> this.stompJsMsgService.saveMsg((UserMsgBO) source.getSource()));
    }

}
