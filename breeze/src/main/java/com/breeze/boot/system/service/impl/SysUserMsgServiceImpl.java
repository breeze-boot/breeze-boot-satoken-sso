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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.utils.SecurityUtils;
import com.breeze.boot.system.domain.SysUserMsg;
import com.breeze.boot.system.domain.SysUserMsgSnapshot;
import com.breeze.boot.system.mapper.SysUserMsgMapper;
import com.breeze.boot.system.service.SysUserMsgService;
import com.breeze.boot.system.service.SysUserMsgSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户消息服务impl
 *
 * @author gaoweixuan
 * @date 2022-11-26
 */
@Service
public class SysUserMsgServiceImpl extends ServiceImpl<SysUserMsgMapper, SysUserMsg> implements SysUserMsgService {

    /**
     * 系统用户消息快照服务
     */
    @Autowired
    private SysUserMsgSnapshotService sysUserMsgSnapshotService;

    /**
     * 关闭
     *
     * @param msgCode 消息编码
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> close(String msgCode) {
        return Result.ok(this.update(Wrappers.<SysUserMsg>lambdaUpdate().set(SysUserMsg::getMarkClose, 1)
                .eq(SysUserMsg::getMsgCode, msgCode)
                .eq(SysUserMsg::getUserId, SecurityUtils.getCurrentUser().getId())));
    }

    /**
     * 标记已读
     *
     * @param msgCode 消息编码
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> read(String msgCode) {
        return Result.ok(this.update(Wrappers.<SysUserMsg>lambdaUpdate().set(SysUserMsg::getMarkRead, 1)
                .eq(SysUserMsg::getMsgCode, msgCode)
                .eq(SysUserMsg::getUserId, SecurityUtils.getCurrentUser().getId())));

    }

    /**
     * 删除用户的消息通过ids
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removeUserMsgByIds(List<Long> ids) {
        List<SysUserMsg> sysUserMsgList = this.listByIds(ids);
        if (CollUtil.isEmpty(sysUserMsgList)) {
            return Result.warning("用户消息不存在");
        }
        boolean remove = this.removeByIds(ids);
        if (!remove) {
            return Result.fail(Boolean.FALSE, "删除失败");
        }
        List<SysUserMsg> otherSysUserMsgList = this.list(Wrappers.<SysUserMsg>lambdaQuery().notIn(SysUserMsg::getId, ids)
                .eq(SysUserMsg::getMsgCode, sysUserMsgList.get(0).getMsgCode()));
        if (CollUtil.isEmpty(otherSysUserMsgList)) {
            List<Long> idList = sysUserMsgList.stream().map(SysUserMsg::getMsgSnapshotId).collect(Collectors.toList());
            this.sysUserMsgSnapshotService.remove(Wrappers.<SysUserMsgSnapshot>lambdaQuery().in(SysUserMsgSnapshot::getId, idList));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }
}
