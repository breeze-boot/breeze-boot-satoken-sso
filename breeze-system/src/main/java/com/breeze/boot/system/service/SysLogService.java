/*
 * Copyright 2022 the original author or authors.
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
import com.breeze.boot.system.dto.LogDTO;
import com.breeze.boot.system.entity.SysLog;

/**
 * 系统日志服务
 *
 * @author breeze
 * @date 2022-09-02
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 日志列表
     *
     * @param logDTO 日志dto
     * @return {@link Page}<{@link SysLog}>
     */
    Page<SysLog> listLog(LogDTO logDTO);

}

