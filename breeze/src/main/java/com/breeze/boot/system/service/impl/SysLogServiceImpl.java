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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.log.dto.SysLogDTO;
import com.breeze.boot.system.domain.SysLog;
import com.breeze.boot.system.dto.LogSearchDTO;
import com.breeze.boot.system.mapper.SysLogMapper;
import com.breeze.boot.system.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统日志服务impl
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    /**
     * 日志列表
     *
     * @param logSearchDTO 日志搜索DTO
     * @return {@link Page}<{@link SysLog}>
     */
    @Override
    public Page<SysLog> listLog(LogSearchDTO logSearchDTO) {
        Page<SysLog> logEntityPage = new Page<>(logSearchDTO.getCurrent(), logSearchDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(logSearchDTO.getSystemModule()), SysLog::getSystemModule, logSearchDTO.getSystemModule())
                .like(StrUtil.isAllNotBlank(logSearchDTO.getLogTitle()), SysLog::getLogTitle, logSearchDTO.getLogTitle())
                .eq(Objects.nonNull(logSearchDTO.getDoType()), SysLog::getDoType, logSearchDTO.getDoType())
                .eq(Objects.nonNull(logSearchDTO.getRequestType()), SysLog::getRequestType, logSearchDTO.getRequestType())
                .eq(Objects.nonNull(logSearchDTO.getResult()), SysLog::getResult, logSearchDTO.getResult())
                .ge(Objects.nonNull(logSearchDTO.getStartDate()), SysLog::getCreateTime, logSearchDTO.getStartDate())
                .le(Objects.nonNull(logSearchDTO.getEndDate()), SysLog::getCreateTime, logSearchDTO.getEndDate())
                .orderByDesc(SysLog::getCreateTime)
                .page(logEntityPage);
    }

    /**
     * 保存系统日志
     *
     * @param sysLogDTO 系统日志DTO
     */
    @Override
    public void saveSysLog(SysLogDTO sysLogDTO) {
        SysLog sysLog = new SysLog();
        BeanUtil.copyProperties(sysLogDTO, sysLog);
        sysLog.setSystemModule("权限系统");
        this.save(sysLog);
    }

    /**
     * 清空
     */
    @Override
    public void clear() {
        this.baseMapper.clear();
    }

}
