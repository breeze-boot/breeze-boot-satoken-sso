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

package com.breeze.boot.modules.msg.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.msg.domain.SysMsgUser;
import com.breeze.boot.modules.msg.domain.query.UserMsgQuery;
import com.breeze.boot.modules.msg.domain.vo.SysMsgUserVO;
import com.breeze.boot.modules.msg.mapper.SysMsgUserMapper;
import com.breeze.boot.modules.msg.service.impl.SysMsgUserService;
import com.breeze.boot.security.utils.SecurityUtils;
import com.breeze.boot.websocket.dto.UserMsgDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户消息服务impl
 *
 * @author gaoweixuan
 * @since 2022-11-26
 */
@Service
@RequiredArgsConstructor
public class SysMsgUserServiceImpl extends ServiceImpl<SysMsgUserMapper, SysMsgUser> implements SysMsgUserService {

    /**
     * 列表页面
     *
     * @param userMsgQuery 用户消息查询
     * @return {@link IPage}<{@link SysMsgUserVO}>
     */
    @Override
    public IPage<SysMsgUserVO> listPage(UserMsgQuery userMsgQuery) {
        return this.baseMapper.listPage(new Page<>(userMsgQuery.getCurrent(), userMsgQuery.getSize()), userMsgQuery);
    }

    /**
     * 获取消息列表通过用户名
     *
     * @param username 用户名
     * @return {@link List}<{@link SysMsgUserVO}>
     */
    @Override
    public List<SysMsgUserVO> listMsgByUsername(String username) {
        return this.baseMapper.listMsgByUsername(username);
    }

    /**
     * 保存接收用户消息
     *
     * @param userMsgDTO 用户消息BO
     */
    @Override
    public void saveUserMsg(UserMsgDTO userMsgDTO) {
        // 保存接收消息的用户
        List<SysMsgUser> sysMsgUserList = userMsgDTO.getMsgBodyList().stream()
                .map(msgBody -> SysMsgUser.builder()
                        .msgId(msgBody.getMsgId())
                        .deptId(msgBody.getDeptId())
                        .userId(msgBody.getUserId())
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(sysMsgUserList);
    }


    /**
     * 关闭
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> close(Long msgId) {
        return Result.ok(this.update(Wrappers.<SysMsgUser>lambdaUpdate().set(SysMsgUser::getIsClose, 1)
                .eq(SysMsgUser::getMsgId, msgId)
                .ne(SysMsgUser::getIsClose, 0)
                .eq(SysMsgUser::getUserId, SecurityUtils.getCurrentUser().getId())));
    }

    /**
     * 标记已读
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> read(Long msgId) {
        return Result.ok(this.update(Wrappers.<SysMsgUser>lambdaUpdate().set(SysMsgUser::getIsRead, 1)
                .eq(SysMsgUser::getMsgId, msgId)
                .ne(SysMsgUser::getIsRead, 0)
                .eq(SysMsgUser::getUserId, SecurityUtils.getCurrentUser().getId())));

    }

    /**
     * 删除用户的消息通过ids
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removeUserMsgByIds(List<Long> ids) {
        List<SysMsgUser> sysMsgUserList = this.listByIds(ids);
        if (CollUtil.isEmpty(sysMsgUserList)) {
            return Result.warning("用户消息不存在");
        }
        boolean remove = this.removeByIds(ids);
        if (!remove) {
            return Result.fail(Boolean.FALSE, "删除失败");
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}
