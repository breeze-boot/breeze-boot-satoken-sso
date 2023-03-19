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

package com.breeze.boot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.sys.domain.SysUserMsg;
import com.breeze.core.utils.Result;

import java.util.List;

/**
 * 系统用户消息服务
 *
 * @author gaoweixuan
 * @date 2022-11-26
 */
public interface SysUserMsgService extends IService<SysUserMsg> {

    /**
     * 关闭
     *
     * @param msgCode 消息编码
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> close(String msgCode);

    /**
     * 标记已读
     *
     * @param msgCode 消息编码
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> read(String msgCode);

    /**
     * 删除用户的消息通过ids
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeUserMsgByIds(List<Long> ids);

}
