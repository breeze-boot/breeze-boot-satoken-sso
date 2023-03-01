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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.domain.SysMsg;
import com.breeze.boot.system.dto.MsgSearchDTO;

/**
 * 系统消息服务
 *
 * @author gaoweixuan
 * @date 2022-11-20
 */
public interface SysMsgService extends IService<SysMsg> {

    /**
     * 列表页面
     *
     * @param msgSearchDTO 消息搜索DTO
     * @return {@link IPage}<{@link SysMsg}>
     */
    IPage<SysMsg> listPage(MsgSearchDTO msgSearchDTO);

    /**
     * 得到系统消息
     *
     * @param msgCode 消息代码
     * @return {@link SysMsg}
     */
    SysMsg getSysMsg(String msgCode);

}
