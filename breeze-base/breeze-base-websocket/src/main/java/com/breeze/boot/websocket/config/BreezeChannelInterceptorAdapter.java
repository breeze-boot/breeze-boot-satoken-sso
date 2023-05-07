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

package com.breeze.boot.websocket.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;
import java.util.List;

/**
 * 通道拦截器适配器
 *
 * @author gaoweixuan
 * @date 2022-11-16
 */
@Slf4j
public class BreezeChannelInterceptorAdapter implements ChannelInterceptor {

    /**
     * 收到之前
     *
     * @param channel 通道
     * @return boolean
     */
    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    /**
     * 发送前
     *
     * @param message 消息
     * @param channel 通道
     * @return {@link Message}<{@link ?}>
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();
        log.info("[发送前拦截, 状态]: {}", command);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> nativeHeader = accessor.getNativeHeader("username");
            if (CollUtil.isEmpty(nativeHeader)) {
                return null;
            }
            String username = nativeHeader.get(0);
            if (StrUtil.isAllBlank(username)) {
                return null;
            }
            log.info(username);
            Principal principal = () -> username;
            accessor.setUser(principal);
            return message;
        }
        // 检测用户订阅内容（防止用户订阅不合法频道）
        if (StompCommand.SUBSCRIBE.equals(command)) {
            // TODO
        }
        return message;
    }

    /**
     * 发送完成后
     *
     * @param message 消息
     * @param channel 通道
     * @param sent    发送
     * @param ex      异常
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        SimpMessagingTemplate simpMessagingTemplate = SpringUtil.getBean(SimpMessagingTemplate.class);
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        log.info("[发送后拦截, 状态]: {}", command);
        if (StompCommand.SUBSCRIBE.equals(command)) {
            simpMessagingTemplate.convertAndSend("/topic/sendMsg", "消息发送成功");
        }
        if (StompCommand.DISCONNECT.equals(command)) {
            simpMessagingTemplate.convertAndSend("/topic/sendMsg", "{'msg':'用户断开连接'}");
        }
    }
}
