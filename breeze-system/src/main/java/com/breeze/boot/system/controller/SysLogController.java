/*
 * Copyright 2022 the original author or authors.
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
import com.breeze.boot.system.dto.LogDTO;
import com.breeze.boot.system.entity.SysLog;
import com.breeze.boot.system.service.SysLogService;
import com.breeze.boot.core.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 系统日志控制器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Tag(name = "日志管理模块", description = "日志管理模块")
@RestController
@RequestMapping("/sys/log")
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
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:log:list')")
    public Result<Page<SysLog>> list(@RequestBody LogDTO logDTO) {
        return Result.ok(this.sysLogService.listLog(logDTO));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:log:delete')")
    public Result delete(@RequestBody Long[] ids) {
        return Result.ok(this.sysLogService.removeByIds(Arrays.asList(ids)));
    }

}
