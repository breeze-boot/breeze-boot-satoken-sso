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

package com.breeze.boot.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.model.entity.SysMsg;
import com.breeze.boot.modules.system.model.form.MsgForm;
import com.breeze.boot.modules.system.model.query.MsgQuery;
import com.breeze.boot.modules.system.service.SysMsgService;
import com.breeze.boot.websocket.vo.MsgVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 系统消息控制器
 *
 * @author gaoweixuan
 * @since 2022-11-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/msg")
@Tag(name = "系统消息管理模块", description = "SysMsgController")
public class SysMsgController {

    /**
     * 系统消息服务
     */
    private final SysMsgService sysMsgService;

    /**
     * 列表
     *
     * @param msgQuery 消息查询
     * @return {@link Result}<{@link IPage}<{@link MsgVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:msg:list')")
    public Result<IPage<MsgVO>> list(MsgQuery msgQuery) {
        return Result.ok(this.sysMsgService.listPage(msgQuery));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}<{@link SysMsg}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:msg:info')")
    public Result<MsgVO> info(@NotNull(message = "不能为空") @PathVariable Long id) {
        return Result.ok(this.sysMsgService.getInfoById(id));
    }

    /**
     * 创建
     *
     * @param msgForm 消息
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys:msg:create')")
    @BreezeSysLog(description = "消息信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody MsgForm msgForm) {
        return Result.ok(this.sysMsgService.saveMsg(msgForm));
    }

    /**
     * 修改
     *
     * @param id      ID
     * @param msgForm 消息表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('sys:msg:modify')")
    @BreezeSysLog(description = "消息信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "消息ID") @NotNull(message = "消息ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody MsgForm msgForm) {
        return Result.ok(this.sysMsgService.modifyMsg(id, msgForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:msg:delete')")
    @BreezeSysLog(description = "消息信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "消息IDS") @NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysMsgService.removeByIds(Arrays.asList(ids)));
    }

}
