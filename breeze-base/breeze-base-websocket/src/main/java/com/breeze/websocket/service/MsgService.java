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

package com.breeze.websocket.service;

import com.breeze.boot.core.utils.Result;
import com.breeze.websocket.dto.MsgDTO;
import com.breeze.websocket.vo.MsgVO;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

/**
 * 消息模块接口
 *
 * @author gaoweixuan
 * @date 2022-10-08
 */
@Slf4j
public abstract class MsgService {

    /**
     * 消息广播
     *
     * @param msgDTO 广播消息dto
     * @return {@link Result}<{@link MsgVO}>
     */
    public abstract Result<MsgVO> sendBroadcastMsg(MsgDTO msgDTO);

    /**
     * 发送消息给用户
     *
     * @param principal 主要
     * @param msgDTO    消息dto
     * @return {@link Result}<{@link MsgVO}>
     */
    public abstract Result<MsgVO> sendMsgToSingleUser(Principal principal, MsgDTO msgDTO);

    /**
     * 发送信息给指定用户
     *
     * @param principal 主要
     * @param msgDTO    用户消息dto
     */
    public abstract void sendMsgToUser(Principal principal, MsgDTO msgDTO);

}
