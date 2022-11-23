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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysMsg;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.MsgDTO;
import com.breeze.boot.system.service.SysMsgService;
import com.breeze.boot.system.service.SysUserService;
import com.breeze.websocket.config.MsgVO;
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
public class WebSocketMsgController {

    /**
     * 简单消息模板
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SysMsgService sysMsgService;

    @Autowired
    private SysUserService sysUserService;


    /**
     * 消息广播
     *
     * @param msg 消息
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "广播消息")
    @MessageMapping("/sendMsg")
    @SendTo("/topic/msg")
    public Result<MsgVO> sendMsg(@Payload String msg) {
        log.info(msg);
        return Result.ok(MsgVO.builder().msgTitle("通知").content(msg).type(2).build());
    }

    /**
     * 发送消息给一个用户
     *
     * @param msgId 消息ID
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "发送消息给一个用户")
    @MessageMapping("/sendUserMsg")
    @SendToUser("/queue/userMsg")
    public Result<MsgVO> sendUserMsg(Principal principal, @Payload Long msgId) {
        log.info("msgId {}, username： {}", msgId, principal.getName());
        SysMsg sysMsg = this.sysMsgService.getById(msgId);
        return Result.ok(MsgVO.builder().msgTitle(sysMsg.getMsgTitle()).content(sysMsg.getContent()).type(sysMsg.getMsgType()).build());
    }

    /**
     * 发送信息给指定的部门下的用户
     *
     * @param msgDTO 消息DTO
     */
    @Operation(summary = "发送信息给指定的部门下的用户")
    @MessageMapping("/sendDeptUserMsg")
    public void sendDeptUserMsg(@Payload MsgDTO msgDTO) {
        SysMsg sysMsg = this.sysMsgService.getById(msgDTO.getMsgId());
        MsgVO msgVO = MsgVO.builder().msgTitle(sysMsg.getMsgTitle()).content(sysMsg.getContent()).type(sysMsg.getMsgType()).build();
        List<SysUser> sysUserList = this.sysUserService.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getDeptId, msgDTO.getDeptIds()));
        for (SysUser sysUser : sysUserList) {
            this.simpMessagingTemplate.convertAndSendToUser(sysUser.getUsername(), "/queue/userMsg", msgVO);
        }
    }

    /**
     * 发送消息给多个用户
     *
     * @param msgDTO 消息DTO
     */
    @Operation(summary = "发送消息给多个用户")
    @MessageMapping("/sendMsgToManyUser")
    public void sendMsgToManyUser(@Payload MsgDTO msgDTO) {
        SysMsg sysMsg = this.sysMsgService.getById(msgDTO.getMsgId());
        MsgVO msgVO = MsgVO.builder().msgTitle(sysMsg.getMsgTitle()).content(sysMsg.getContent()).type(sysMsg.getMsgType()).build();
        List<SysUser> sysUserList = this.sysUserService.listByIds(msgDTO.getUserIds());
        for (SysUser sysUser : sysUserList) {
            this.simpMessagingTemplate.convertAndSendToUser(sysUser.getUsername(), "/queue/userMsg", msgVO);
        }
    }

}
