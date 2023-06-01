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

package com.breeze.boot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.system.query.UserMsgQuery;
import com.breeze.boot.system.service.SysMsgUserService;
import com.breeze.boot.system.vo.SysMsgUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 系统用户消息控制器
 *
 * @author gaoweixuan
 * @date 2022-11-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/msgUser")
@Tag(name = "系统用户消息管理模块", description = "SysUserMsgController")
public class SysMsgUserController {

    /**
     * 系统用户消息快照服务
     */
    private final SysMsgUserService sysMsgUserService;

    /**
     * 列表
     *
     * @param userMsgQuery 用户消息查询
     * @return {@link Result}<{@link IPage}<{@link SysMsgUserVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:msgUser:list')")
    public Result<IPage<SysMsgUserVO>> list(UserMsgQuery userMsgQuery) {
        return Result.ok(this.sysMsgUserService.listPage(userMsgQuery));
    }

    /**
     * 获取消息列表通过用户名
     *
     * @param username 用户名
     * @return {@link Result}<{@link List}<{@link SysMsgUserVO}>>
     */
    @Operation(summary = "获取消息列表通过用户名")
    @GetMapping("/listMsgByUsername")
    @PreAuthorize("hasAnyAuthority('sys:msgUser:list')")
    public Result<List<SysMsgUserVO>> listMsgByUsername(@RequestParam String username) {
        return Result.ok(this.sysMsgUserService.listMsgByUsername(username));
    }

    /**
     * 关闭
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "关闭")
    @PutMapping("/close/{msgId}")
    public Result<Boolean> close(@PathVariable Long msgId) {
        return this.sysMsgUserService.close(msgId);
    }

    /**
     * 标记已读
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "已读")
    @PutMapping("/read/{msgId}")
    public Result<Boolean> read(@PathVariable Long msgId) {
        return this.sysMsgUserService.read(msgId);
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:msgUser:delete')")
    @BreezeSysLog(description = "消息信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysMsgUserService.removeUserMsgByIds(Arrays.asList(ids));
    }

}
