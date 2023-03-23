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

package com.breeze.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.breeze.boot.sys.domain.SysMsg;
import com.breeze.boot.sys.domain.SysUser;
import com.breeze.boot.sys.domain.SysUserMsg;
import com.breeze.boot.sys.domain.SysUserMsgSnapshot;
import com.breeze.boot.sys.service.SysMsgService;
import com.breeze.boot.sys.service.SysUserMsgService;
import com.breeze.boot.sys.service.SysUserMsgSnapshotService;
import com.breeze.boot.sys.service.SysUserService;
import com.breeze.core.utils.Result;
import com.breeze.websocket.bo.UserMsgBO;
import com.breeze.websocket.params.MsgParam;
import com.breeze.websocket.service.MsgSaveEvent;
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
import java.util.stream.Collectors;

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
     * 保存消息事件
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
     * @param msgParam 消息参数
     * @return {@link Result}<{@link MsgVO}>
     */
    @Override
    public Result<MsgVO> sendBroadcastMsg(MsgParam msgParam) {
        SysMsg sysMsg = this.sysMsgService.getById(msgParam.getMsgId());
        List<SysUser> sysUserList = this.sysUserService.list();
        List<UserMsgBO.SysUserMsgBO> sysUserMsgList = Lists.newArrayList();
        for (SysUser sysUser : sysUserList) {
            sysUserMsgList.add(UserMsgBO.SysUserMsgBO.builder()
                    .msgCode(sysMsg.getMsgCode()).userId(sysUser.getId())
                    .build());
        }
        this.toAsyncSendMsg(sysMsg, sysUserMsgList);
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
     * @param msgParam  消息参数
     * @param principal 主要
     * @return {@link Result}<{@link MsgVO}>
     */
    @Override
    public Result<MsgVO> sendMsgToSingleUser(Principal principal, MsgParam msgParam) {
        log.info("msgId {}, username： {}", msgParam, principal.getName());
        SysMsg sysMsg = this.sysMsgService.getById(msgParam);
        // TODO
        this.toAsyncSendMsg(sysMsg, Lists.newArrayList());
        return Result.ok(MsgVO.builder()
                .msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .content(sysMsg.getContent())
                .msgType(sysMsg.getMsgType()).build());
    }

    /**
     * 异步发送消息
     *
     * @param sysMsg           系统消息
     * @param sysUserMsgBOList 系统用户消息BO列表
     */
    private void toAsyncSendMsg(SysMsg sysMsg, List<UserMsgBO.SysUserMsgBO> sysUserMsgBOList) {
        UserMsgBO userMsgBO = new UserMsgBO();
        userMsgBO.setSysUserMsgBOList(sysUserMsgBOList);
        userMsgBO.setSysUserMsgSnapshotBO(this.buildSysUserMsgSnapshot(sysMsg));
        this.publisherSaveMsgEvent.publisherEvent(new MsgSaveEvent(userMsgBO));
    }

    /**
     * 构建系统用户消息快照BO
     *
     * @param sysMsg sys消息
     * @return {@link UserMsgBO.SysUserMsgSnapshotBO}
     */
    private UserMsgBO.SysUserMsgSnapshotBO buildSysUserMsgSnapshot(SysMsg sysMsg) {
        UserMsgBO.SysUserMsgSnapshotBO userMsgSnapshot = UserMsgBO.SysUserMsgSnapshotBO.builder().build();
        BeanUtil.copyProperties(sysMsg, userMsgSnapshot, CopyOptions.create().setIgnoreProperties("id").setIgnoreNullValue(true).setIgnoreError(true));
        userMsgSnapshot.setMsgId(sysMsg.getId());
        return userMsgSnapshot;
    }

    /**
     * 保存消息
     *
     * @param userMsgBO 用户消息BO
     */
    public void saveMsg(UserMsgBO userMsgBO) {
        // 保存的实体
        SysUserMsgSnapshot userMsgContent = SysUserMsgSnapshot.builder().build();
        BeanUtil.copyProperties(userMsgBO.getSysUserMsgSnapshotBO(), userMsgContent,
                CopyOptions.create().setIgnoreProperties("id").setIgnoreNullValue(true).setIgnoreError(true));
        this.sysUserMsgSnapshotService.save(userMsgContent);
        List<SysUserMsg> sysUserMsgList = userMsgBO.getSysUserMsgBOList().stream()
                .map(sysUserMsgBO -> SysUserMsg.builder()
                        .userId(sysUserMsgBO.getUserId())
                        .msgCode(sysUserMsgBO.getMsgCode())
                        .msgSnapshotId(userMsgContent.getId())
                        .build())
                .collect(Collectors.toList());
        this.sysUserMsgService.saveBatch(sysUserMsgList);
    }

    /**
     * 发送消息给指定用户
     *
     * @param msgParam  消息参数
     * @param principal 主要
     */
    @Override
    public void sendMsgToUser(Principal principal, MsgParam msgParam) {
        log.info("msgId {}, username： {}", msgParam.getMsgId(), principal.getName());
        SysMsg sysMsg = this.sysMsgService.getById(msgParam.getMsgId());
        MsgVO msgVO = MsgVO.builder().msgTitle(sysMsg.getMsgTitle())
                .msgCode(sysMsg.getMsgCode())
                .msgLevel(sysMsg.getMsgLevel())
                .content(sysMsg.getContent())
                .msgType(sysMsg.getMsgType()).build();
        List<UserMsgBO.SysUserMsgBO> sysUserMsgList = Lists.newArrayList();
        List<SysUser> sysUserList = this.sysUserService.listByIds(msgParam.getUserIds());
        for (SysUser sysUser : sysUserList) {
            this.simpMessagingTemplate.convertAndSendToUser(sysUser.getUsername(), "/queue/userMsg", Result.ok(msgVO));
            sysUserMsgList.add(UserMsgBO.SysUserMsgBO.builder()
                    .msgCode(sysMsg.getMsgCode()).userId(sysUser.getId())
                    .build());
        }
        this.toAsyncSendMsg(sysMsg, sysUserMsgList);
    }


}
