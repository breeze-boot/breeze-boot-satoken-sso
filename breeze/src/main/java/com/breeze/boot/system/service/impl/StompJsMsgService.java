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

package com.breeze.boot.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysMsg;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.MsgDTO;
import com.breeze.boot.system.service.SysMsgService;
import com.breeze.boot.system.service.SysUserService;
import com.breeze.websocket.config.MsgVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * WebSocket消息模块接口
 *
 * @author gaoweixuan
 * @date 2022-10-08
 */
@Slf4j
@Service
public class StompJsMsgService {

    /**
     * 简单消息模板
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 系统消息服务
     */
    @Autowired
    private SysMsgService sysMsgService;

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 消息广播
     *
     * @param msgId 消息ID
     * @return {@link Result}<{@link MsgVO}>
     */
    public Result<MsgVO> sendMsg(Long msgId) {
        SysMsg sysMsg = this.sysMsgService.getById(msgId);
        return Result.ok(MsgVO.builder()
                .msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .msgType(sysMsg.getMsgType())
                .content(sysMsg.getContent())
                .build());
    }

    /**
     * 发送消息给多个用户
     *
     * @param msgId     消息ID
     * @param principal 主要
     * @return {@link Result}<{@link MsgVO}>
     */
    public Result<MsgVO> sendMsgUser(Principal principal, Long msgId) {
        log.info("msgId {}, username： {}", msgId, principal.getName());
        SysMsg sysMsg = this.sysMsgService.getById(msgId);
        return Result.ok(MsgVO.builder()
                .msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .content(sysMsg.getContent())
                .msgType(sysMsg.getMsgType()).build());
    }

    /**
     * 发送信息给指定用户
     *
     * @param msgDTO 消息DTO
     */
    public void sendMsgToUser(Principal principal, MsgDTO msgDTO) {
        log.info("msgId {}, username： {}", msgDTO.getMsgId(), principal.getName());
        SysMsg sysMsg = this.sysMsgService.getById(msgDTO.getMsgId());
        MsgVO msgVO = MsgVO.builder().msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .content(sysMsg.getContent())
                .msgType(sysMsg.getMsgType()).build();
        List<SysUser> sysUserList = this.sysUserService.listByIds(msgDTO.getUserIds());
        for (SysUser sysUser : sysUserList) {
            this.simpMessagingTemplate.convertAndSendToUser(sysUser.getUsername(), "/queue/userMsg", Result.ok(msgVO));
        }
    }

    /**
     * 用户通过部门id列表
     *
     * @param deptIds 部门id
     * @return {@link Result}<{@link List}<{@link SysUser}>>
     */
    public Result<List<SysUser>> listUserByDeptId(List<Long> deptIds) {
        return Result.ok(this.sysUserService.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getDeptId, deptIds)));
    }

}
