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

package com.breeze.boot.system.controller;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.MsgDTO;
import com.breeze.boot.system.service.impl.StompJsMsgService;
import com.breeze.websocket.config.MsgVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * WebSocket消息模块接口
 *
 * @author gaoweixuan
 * @date 2022-10-08
 */
@Slf4j
@RestController
@Tag(name = "webSocket消息模块", description = "WebSocketMsgController")
public class StompJsMsgController {

    @Autowired
    private StompJsMsgService stompJsMsgService;

    /**
     * 消息广播
     *
     * @param msgId 消息ID
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "广播消息")
    @MessageMapping("/sendMsg")
    @SendTo("/topic/msg")
    public Result<MsgVO> sendMsg(@Payload Long msgId) {
        return stompJsMsgService.sendMsg(msgId);
    }

    /**
     * 发送消息给用户
     *
     * @param msgId     消息ID
     * @param principal 主要
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "发送消息给用户")
    @MessageMapping("/sendMsgUser")
    @SendToUser("/queue/userMsg")
    public Result<MsgVO> sendMsgUser(Principal principal, @Payload Long msgId) {
        return stompJsMsgService.sendMsgUser(principal, msgId);
    }

    /**
     * 发送信息给指定的用户
     *
     * @param principal 主要
     * @param msgDTO    消息
     */
    @Operation(summary = "发送信息给指定的用户")
    @MessageMapping("/sendMsgToUser")
    public void sendMsgToUser(Principal principal, @Payload MsgDTO msgDTO) {
        stompJsMsgService.sendMsgToUser(principal, msgDTO);
    }

    /**
     * 用户通过部门id列表
     */
    @Operation(summary = "用户通过部门id列表")
    @PostMapping("/listUserByDeptId")
    public Result<List<SysUser>> listUserByDeptId(@RequestBody List<Long> deptIds) {
        return stompJsMsgService.listUserByDeptId(deptIds);
    }

}
