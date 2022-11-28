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

package com.breeze.websocket.controller;

import com.breeze.boot.core.utils.Result;
import com.breeze.websocket.dto.MsgDTO;
import com.breeze.websocket.service.MsgService;
import com.breeze.websocket.vo.MsgVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 消息模块接口
 *
 * @author gaoweixuan
 * @date 2022-10-08
 */
@Slf4j
@RestController
@Tag(name = "消息模块", description = "MsgController")
public class MsgController {

    /**
     * 消息服务
     */
    @Autowired
    private MsgService msgService;

    /**
     * 消息广播
     *
     * @param msgDTO 广播消息
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "广播消息")
    @MessageMapping("/sendBroadcastMsg")
    @SendTo("/topic/msg")
    public Result<MsgVO> sendBroadcastMsg(@Payload MsgDTO msgDTO) {
        return this.msgService.sendBroadcastMsg(msgDTO);
    }

    /**
     * 发送消息给用户
     *
     * @param principal 主要
     * @param msgDTO    用户消息
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "发送消息给用户")
    @MessageMapping("/sendMsgUser")
    @SendToUser("/queue/userMsg")
    public Result<MsgVO> sendMsgUser(Principal principal, @Payload MsgDTO msgDTO) {
        return this.msgService.sendMsgToSingleUser(principal, msgDTO);
    }

    /**
     * 发送信息给指定的用户
     *
     * @param principal 主要
     * @param msgDTO    用户消息
     */
    @Operation(summary = "发送信息给指定的用户")
    @MessageMapping("/sendMsgToUser")
    public void sendMsgToUser(Principal principal, @Payload MsgDTO msgDTO) {
        this.msgService.sendMsgToUser(principal, msgDTO);
    }

}
