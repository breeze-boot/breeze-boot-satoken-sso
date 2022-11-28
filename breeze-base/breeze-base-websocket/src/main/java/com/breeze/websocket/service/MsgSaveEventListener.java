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

package com.breeze.websocket.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;

/**
 * 消息保存事件监听器
 *
 * @author gaoweixuan
 * @date 2022-11-28
 */
@Slf4j
@AllArgsConstructor
public class MsgSaveEventListener {

    /**
     * 消费者
     * <p>
     * 去执行保存逻辑
     */
    private Consumer<MsgSaveEvent> consumer;

    /**
     * 应用程序事件
     *
     * @param msgSaveEvent 事件
     */
    @Async
    @EventListener(MsgSaveEvent.class)
    public void onApplicationEvent(MsgSaveEvent msgSaveEvent) {
        log.info("消息投递");
        consumer.accept(msgSaveEvent);
    }

}
