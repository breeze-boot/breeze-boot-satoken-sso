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

package com.breeze.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * 通道拦截器适配器
 *
 * @author gaoweixuan
 * @date 2022-11-16
 */
public class BreezeChannelInterceptorAdapter implements ChannelInterceptor {

    /**
     * 简单消息模板
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

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
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        // 检测用户订阅内容（防止用户订阅不合法频道）
        if (StompCommand.SUBSCRIBE.equals(command)) {
            // 如果该用户订阅的频道不合法直接返回null前端用户就接受不到该频道信息
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
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (StompCommand.SUBSCRIBE.equals(command)) {
            this.simpMessagingTemplate.convertAndSend("/topic/sendMsg", "消息发送成功");
        }
        if (StompCommand.DISCONNECT.equals(command)) {
            this.simpMessagingTemplate.convertAndSend("/topic/sendMsg", "{'msg':'用户断开连接'}");
        }
    }
}
