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

package com.breeze.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.sys.domain.SysLog;
import com.breeze.boot.sys.mapper.SysLogMapper;
import com.breeze.boot.sys.query.LogQuery;
import com.breeze.boot.sys.service.SysLogService;
import com.breeze.log.bo.SysLogBO;
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
     * @param logQuery 日志查询
     * @return {@link Page}<{@link SysLog}>
     */
    @Override
    public Page<SysLog> listPage(LogQuery logQuery) {
        Page<SysLog> logEntityPage = new Page<>(logQuery.getCurrent(), logQuery.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(logQuery.getSystemModule()), SysLog::getSystemModule, logQuery.getSystemModule())
                .like(StrUtil.isAllNotBlank(logQuery.getLogTitle()), SysLog::getLogTitle, logQuery.getLogTitle())
                .eq(Objects.nonNull(logQuery.getDoType()), SysLog::getDoType, logQuery.getDoType())
                .eq(Objects.nonNull(logQuery.getRequestType()), SysLog::getRequestType, logQuery.getRequestType())
                .eq(Objects.nonNull(logQuery.getResult()), SysLog::getResult, logQuery.getResult())
                .ge(Objects.nonNull(logQuery.getStartDate()), SysLog::getCreateTime, logQuery.getStartDate())
                .le(Objects.nonNull(logQuery.getEndDate()), SysLog::getCreateTime, logQuery.getEndDate())
                .orderByDesc(SysLog::getCreateTime)
                .page(logEntityPage);
    }

    /**
     * 保存系统日志
     *
     * @param sysLogBO 系统日志BO
     */
    @DS("master")
    @Override
    public void saveSysLog(SysLogBO sysLogBO) {
        SysLog sysLog = new SysLog();
        BeanUtil.copyProperties(sysLogBO, sysLog);
        sysLog.setSystemModule("权限系统");
        this.save(sysLog);
    }

    /**
     * 清空
     */
    @Override
    public void truncate() {
        this.baseMapper.truncate();
    }

}
