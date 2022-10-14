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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.system.domain.SysLog;
import com.breeze.boot.system.dto.LogDTO;
import com.breeze.boot.system.mapper.SysLogMapper;
import com.breeze.boot.system.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统日志服务impl
 *
 * @author breeze
 * @date 2022-09-02
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    /**
     * 日志列表
     *
     * @param logDTO 日志dto
     * @return {@link Page}<{@link SysLog}>
     */
    @Override
    public Page<SysLog> listLog(LogDTO logDTO) {
        Page<SysLog> logEntityPage = new Page<>(logDTO.getCurrent(), logDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(logDTO.getSystemModule()), SysLog::getSystemModule, logDTO.getSystemModule())
                .like(StrUtil.isAllNotBlank(logDTO.getLogTitle()), SysLog::getLogTitle, logDTO.getLogTitle())
                .eq(Objects.nonNull(logDTO.getDoType()), SysLog::getDoType, logDTO.getDoType())
                .eq(Objects.nonNull(logDTO.getRequestType()), SysLog::getRequestType, logDTO.getRequestType())
                .eq(Objects.nonNull(logDTO.getResult()), SysLog::getResult, logDTO.getResult())
                .ge(Objects.nonNull(logDTO.getStartDate()), SysLog::getCreateTime, logDTO.getStartDate())
                .le(Objects.nonNull(logDTO.getEndDate()), SysLog::getCreateTime, logDTO.getEndDate())
                .page(logEntityPage);
    }
}
