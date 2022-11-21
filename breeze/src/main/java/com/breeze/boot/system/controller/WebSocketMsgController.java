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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * WebSocket消息模块接口
 *
 * @author gaoweixuan
 * @date 2022-10-08
 */
@Slf4j
@RestController
@Tag(name = "webSocket消息模块", description = "WebSocketMsgController")
public class WebSocketMsgController {

    /**
     * 简单消息模板
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 送消息
     *
     * @param msg 消息
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "发送信息到所有用户")
    @MessageMapping("/sendMsg")
    @SendTo("/topic/msg")
    public Result<?> sendMsg(String msg) {
        log.info(msg);
        return Result.ok(msg);
    }

    /**
     * 发送用户消息
     *
     * @param principal 主要
     * @param msg       消息
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "发送信息到指定用户")
    @MessageMapping("/sendUserMsg")
    @SendToUser("/queue/userMsg")
    public Result<?> sendUserMsg(Principal principal, @Payload String msg) {
        log.info("msg {}, username： {}", msg, principal.getName());
        return Result.ok(msg);
    }

    /**
     * 发送用户msg1
     *
     * @param principal 主要
     * @param msg       消息
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "发送信息给指定的用户")
    @MessageMapping("/toSendUserMsg")
    public Result<?> sendUserMsg1(Principal principal, @Payload String msg) {
        this.simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/userMsg", msg);
        return Result.ok(msg);
    }

}
