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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysUserMsg;
import com.breeze.boot.system.mapper.SysUserMsgMapper;
import com.breeze.boot.system.service.SysUserMsgService;
import org.springframework.stereotype.Service;

/**
 * 系统用户消息服务impl
 *
 * @author breeze
 * @date 2022-11-26
 */
@Service
public class SysUserMsgServiceImpl extends ServiceImpl<SysUserMsgMapper, SysUserMsg> implements SysUserMsgService {

    /**
     * 关闭
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> close(Long id) {
        return Result.ok(this.update(Wrappers.<SysUserMsg>lambdaUpdate().set(SysUserMsg::getClose, 1).eq(SysUserMsg::getId, id)));
    }

    /**
     * 标记已读
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> read(Long id) {
        return Result.ok(this.update(Wrappers.<SysUserMsg>lambdaUpdate().set(SysUserMsg::getRead, 1).eq(SysUserMsg::getId, id)));
    }
}
