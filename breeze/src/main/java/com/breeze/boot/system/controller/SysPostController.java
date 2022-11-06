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
import com.breeze.boot.core.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysPost;
import com.breeze.boot.system.dto.PostDTO;
import com.breeze.boot.system.service.SysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统岗位控制器
 *
 * @author breeze
 * @date 2022-11-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/post")
@Tag(name = "系统岗位管理模块", description = "SysPostController")
public class SysPostController {

    /**
     * 系统岗位服务
     */
    private final SysPostService sysPostService;

    /**
     * 列表
     *
     * @param postDTO 岗位dto
     * @return {@link Result}
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:post:list')")
    public Result<IPage<SysPost>> list(@RequestBody PostDTO postDTO) {
        return Result.ok(this.sysPostService.listPage(postDTO));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:post:info')")
    public Result<SysPost> info(@PathVariable("id") Long id) {
        return Result.ok(this.sysPostService.getById(id));
    }

    /**
     * 保存
     *
     * @param post 平台实体入参
     * @return {@link Result}
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:post:save')")
    @BreezeSysLog(description = "岗位信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysPost post) {
        return Result.ok(this.sysPostService.save(post));
    }

    /**
     * 修改
     *
     * @param sysPost 平台实体
     * @return {@link Result}
     */
    @Operation(summary = "修改")
    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('sys:post:edit')")
    @BreezeSysLog(description = "岗位信息修改", type = LogType.EDIT)
    public Result<Boolean> edit(@Validated @RequestBody SysPost sysPost) {
        return Result.ok(this.sysPostService.updateById(sysPost));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:post:delete')")
    @BreezeSysLog(description = "岗位信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysPostService.removePostByIds(ids);
    }

}
