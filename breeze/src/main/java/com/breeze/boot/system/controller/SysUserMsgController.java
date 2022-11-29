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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysUserMsgSnapshot;
import com.breeze.boot.system.dto.UserMsgSearchDTO;
import com.breeze.boot.system.service.SysUserMsgService;
import com.breeze.boot.system.service.SysUserMsgSnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 系统用户消息控制器
 *
 * @author gaoweixuan
 * @date 2022-11-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/userMsg")
@Tag(name = "系统用户消息管理模块", description = "SysUserMsgController")
public class SysUserMsgController {

    /**
     * 系统用户消息快照服务
     */
    private final SysUserMsgSnapshotService sysUserMsgSnapshotService;

    /**
     * 系统用户消息服务
     */
    private final SysUserMsgService sysUserMsgService;

    /**
     * 列表
     *
     * @param userMsgSearchDTO 消息搜索DTO
     * @return {@link Result}<{@link IPage}<{@link SysUserMsgSnapshot}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:userMsg:list')")
    public Result<IPage<SysUserMsgSnapshot>> list(@RequestBody UserMsgSearchDTO userMsgSearchDTO) {
        return Result.ok(this.sysUserMsgSnapshotService.listPage(userMsgSearchDTO));
    }

    /**
     * 关闭
     *
     * @param msgCode 消息编码
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "关闭")
    @PutMapping("/close/{msgCode}")
    @PreAuthorize("hasAnyAuthority('sys:userMsg:modify')")
    public Result<Boolean> close(@PathVariable String msgCode) {
        return this.sysUserMsgService.close(msgCode);
    }

    /**
     * 标记已读
     *
     * @param msgCode 消息编码
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "已读")
    @PutMapping("/read/{msgCode}")
    @PreAuthorize("hasAnyAuthority('sys:userMsg:modify')")
    public Result<Boolean> read(@PathVariable String msgCode) {
        return this.sysUserMsgService.read(msgCode);
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:userMsg:delete')")
    @BreezeSysLog(description = "消息信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysUserMsgService.removeByIds(Arrays.asList(ids)));
    }

}
