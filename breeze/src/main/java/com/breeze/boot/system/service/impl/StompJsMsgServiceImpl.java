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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.config.SysLogSaveEvent;
import com.breeze.boot.system.domain.SysMsg;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.domain.SysUserMsg;
import com.breeze.boot.system.domain.SysUserMsgSnapshot;
import com.breeze.boot.system.service.SysMsgService;
import com.breeze.boot.system.service.SysUserMsgService;
import com.breeze.boot.system.service.SysUserMsgSnapshotService;
import com.breeze.boot.system.service.SysUserService;
import com.breeze.websocket.dto.MsgDTO;
import com.breeze.websocket.dto.UserMsgDTO;
import com.breeze.websocket.service.MsgService;
import com.breeze.websocket.service.PublisherSaveMsgEvent;
import com.breeze.websocket.vo.MsgVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * stompJs 消息模块接口impl
 *
 * @author gaoweixuan
 * @date 2022-10-08
 */
@Slf4j
@Service("stompJsMsgService")
public class StompJsMsgServiceImpl extends MsgService {

    /**
     * 简单消息模板
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 事件
     */
    @Autowired
    private PublisherSaveMsgEvent publisherSaveMsgEvent;

    /**
     * 系统用户消息服务
     */
    @Autowired
    private SysUserMsgService sysUserMsgService;

    /**
     * 系统用户消息快照服务
     */
    @Autowired
    private SysUserMsgSnapshotService sysUserMsgSnapshotService;

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
     * @param msgDTO 消息dto
     * @return {@link Result}<{@link MsgVO}>
     */
    @Override
    public Result<MsgVO> sendBroadcastMsg(MsgDTO msgDTO) {
        SysMsg sysMsg = this.sysMsgService.getById(msgDTO.getMsgId());
        SysUserMsgSnapshot userMsgContent = SysUserMsgSnapshot.builder().build();
        BeanUtil.copyProperties(sysMsg, userMsgContent, CopyOptions.create().setIgnoreProperties("id").setIgnoreNullValue(true).setIgnoreError(true));
        userMsgContent.setMsgId(sysMsg.getId());
        this.sysUserMsgSnapshotService.save(userMsgContent);
        List<SysUser> sysUserList = this.sysUserService.list();
        for (SysUser sysUser : sysUserList) {
            SysUserMsg userMsg = SysUserMsg.builder().userId(sysUser.getId()).msgSnapshotId(userMsgContent.getId()).build();
            this.sysUserMsgService.save(userMsg);
        }
        return Result.ok(MsgVO.builder()
                .msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .msgType(sysMsg.getMsgType())
                .content(sysMsg.getContent())
                .build());
    }

    /**
     * 发送消息给用户
     *
     * @param msgDTO    消息ID
     * @param principal 主要
     * @return {@link Result}<{@link MsgVO}>
     */
    @Override
    public Result<MsgVO> sendMsgToSingleUser(Principal principal, MsgDTO msgDTO) {
        log.info("msgId {}, username： {}", msgDTO, principal.getName());
        SysMsg sysMsg = this.sysMsgService.getById(msgDTO);
        SysUserMsgSnapshot userMsgContent = SysUserMsgSnapshot.builder().build();
        BeanUtil.copyProperties(sysMsg, userMsgContent, CopyOptions.create().setIgnoreProperties("id").setIgnoreNullValue(true).setIgnoreError(true));
        userMsgContent.setMsgId(sysMsg.getId());
        this.sysUserMsgSnapshotService.save(userMsgContent);
        // 查询用户
        SysUserMsg userMsg = SysUserMsg.builder().userId(1L).msgSnapshotId(userMsgContent.getId()).build();
        this.sysUserMsgService.save(userMsg);

        return Result.ok(MsgVO.builder()
                .msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .content(sysMsg.getContent())
                .msgType(sysMsg.getMsgType()).build());

    }

    public void saveMsg(UserMsgDTO userMsgDTO) {
        // TODO
    }

    /**
     * 发送信息给指定用户
     *
     * @param msgDTO    消息DTO
     * @param principal 主要
     */
    @Override
    public void sendMsgToUser(Principal principal, MsgDTO msgDTO) {
        log.info("msgId {}, username： {}", msgDTO.getMsgId(), principal.getName());
        SysMsg sysMsg = this.sysMsgService.getById(msgDTO.getMsgId());

        MsgVO msgVO = MsgVO.builder().msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .content(sysMsg.getContent())
                .msgType(sysMsg.getMsgType()).build();
        SysUserMsgSnapshot userMsgContent = SysUserMsgSnapshot.builder().build();
        BeanUtil.copyProperties(sysMsg, userMsgContent, CopyOptions.create().setIgnoreProperties("id").setIgnoreNullValue(true).setIgnoreError(true));
        userMsgContent.setMsgId(sysMsg.getId());
        this.sysUserMsgSnapshotService.save(userMsgContent);
        List<SysUser> sysUserList = this.sysUserService.listByIds(msgDTO.getUserIds());
        List<SysUserMsg> sysUserMsgList = Lists.newArrayList();
        for (SysUser sysUser : sysUserList) {
            this.simpMessagingTemplate.convertAndSendToUser(sysUser.getUsername(), "/queue/userMsg", Result.ok(msgVO));
            SysUserMsg userMsg = SysUserMsg.builder().userId(sysUser.getId()).msgSnapshotId(userMsgContent.getId()).build();
            sysUserMsgList.add(userMsg);
        }
        this.publisherSaveMsgEvent.publisherEvent(new SysLogSaveEvent(null));
    }


}
