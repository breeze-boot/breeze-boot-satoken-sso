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

package com.breeze.boot.modules.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.modules.system.mapper.SysLogMapper;
import com.breeze.boot.modules.system.model.entity.SysLog;
import com.breeze.boot.modules.system.model.mappers.SysLogMapStruct;
import com.breeze.boot.modules.system.model.query.LogQuery;
import com.breeze.boot.modules.system.model.vo.LogVO;
import com.breeze.boot.modules.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统日志服务impl
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    private final SysLogMapStruct sysLogMapStruct;

    /**
     * 日志列表
     *
     * @param logQuery 日志查询
     * @return {@link Page}<{@link LogVO}>
     */
    @Override
    public Page<LogVO> listPage(LogQuery logQuery) {
        Page<SysLog> sysLogPage = this.baseMapper.listPage(new Page<>(logQuery.getCurrent(), logQuery.getLimit()), logQuery);
        return this.sysLogMapStruct.entityPage2VOPage(sysLogPage);
    }

    @Override
    public LogVO getInfoById(Long logId) {
        return this.sysLogMapStruct.entity2VO(this.getById(logId));
    }

    /**
     * 保存系统日志
     *
     * @param sysLogBO 系统日志BO
     */
    @DS("master")
    @Override
    public void saveSysLog(SysLogBO sysLogBO) {
        SysLog sysLog = this.sysLogMapStruct.bo2Entity(sysLogBO);
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
