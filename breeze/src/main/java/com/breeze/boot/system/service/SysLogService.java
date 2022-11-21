/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.log.dto.SysLogDTO;
import com.breeze.boot.system.domain.SysLog;
import com.breeze.boot.system.dto.LogSearchDTO;

/**
 * 系统日志服务
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 日志列表
     *
     * @param logSearchDTO 日志搜索dto
     * @return {@link Page}<{@link SysLog}>
     */
    Page<SysLog> listLog(LogSearchDTO logSearchDTO);

    /**
     * 保存系统日志
     *
     * @param sysLog 系统日志
     */
    void saveSysLog(SysLogDTO sysLog);

    /**
     * 清空
     */
    void clear();

}

