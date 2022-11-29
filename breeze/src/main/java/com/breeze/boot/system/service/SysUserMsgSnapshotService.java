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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.domain.SysUserMsgSnapshot;
import com.breeze.boot.system.dto.UserMsgSearchDTO;

/**
 * 系统用户消息快照服务
 *
 * @author breeze
 * @date 2022-11-26
 */
public interface SysUserMsgSnapshotService extends IService<SysUserMsgSnapshot> {

    /**
     * 列表页面
     *
     * @param userMsgSearchDTO 用户搜索DTO消息
     * @return {@link IPage}<{@link SysUserMsgSnapshot}>
     */
    IPage<SysUserMsgSnapshot> listPage(UserMsgSearchDTO userMsgSearchDTO);
}
