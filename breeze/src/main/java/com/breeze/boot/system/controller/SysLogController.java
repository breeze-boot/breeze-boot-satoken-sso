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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysLog;
import com.breeze.boot.system.dto.LogDTO;
import com.breeze.boot.system.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 系统日志控制器
 *
 * @author breeze
 * @date 2022-09-02
 */
@RestController
@RequestMapping("/sys/log")
@Tag(name = "系统日志管理模块", description = "SysLogController")
public class SysLogController {

    /**
     * 系统日志服务
     */
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     *
     * @param logDTO 日志dto
     * @return {@link Result}<{@link Page}<{@link SysLog}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:log:list')")
    public Result<Page<SysLog>> list(@Validated @RequestBody LogDTO logDTO) {
        return Result.ok(this.sysLogService.listLog(logDTO));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:log:delete')")
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysLogService.removeByIds(Arrays.asList(ids)));
    }

}
