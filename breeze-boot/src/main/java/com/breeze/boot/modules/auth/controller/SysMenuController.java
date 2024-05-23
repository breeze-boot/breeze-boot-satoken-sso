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

package com.breeze.boot.modules.auth.controller;

import cn.hutool.core.lang.tree.Tree;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.entity.SysMenu;
import com.breeze.boot.modules.auth.model.query.MenuQuery;
import com.breeze.boot.modules.auth.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统菜单控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/menu")
@Tag(name = "系统菜单管理模块", description = "SysMenuController")
public class SysMenuController {

    /**
     * 系统菜单服务
     */
    private final SysMenuService sysMenuService;

    /**
     * 列表
     *
     * @param menuQuery 菜单查询
     * @return {@link Result}
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('auth:menu:list')")
    public Result<?> list(MenuQuery menuQuery) {
        return this.sysMenuService.listMenu(menuQuery);
    }

    /**
     * 树形菜单
     *
     * @param platformCode 平台标识
     * @param i18n         国际化
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Operation(summary = "树形菜单")
    @GetMapping("/listTreeMenu")
    public Result<List<Tree<Long>>> listTreeMenu(@RequestParam(required = false) String platformCode,
                                                 @RequestParam(required = false) String i18n) {
        return this.sysMenuService.listTreeMenu(platformCode, i18n);
    }

    /**
     * 树形权限列表
     * <p>
     * 不加权限标识
     *
     * @return {@link Result}
     */
    @Operation(summary = "树形权限列表")
    @GetMapping("/listTreePermission")
    public Result<List<Tree<Long>>> listTreePermission() {
        return this.sysMenuService.listTreePermission();
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('auth:menu:info')")
    public Result<SysMenu> info(@NotNull(message = "不能为空") @PathVariable("id") Long id) {
        return Result.ok(sysMenuService.getById(id));
    }

    /**
     * 创建
     *
     * @param sysMenu 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('auth:menu:create')")
    @BreezeSysLog(description = "菜单信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysMenu sysMenu) {
        return this.sysMenuService.saveMenu(sysMenu);
    }

    /**
     * 修改
     *
     * @param sysMenu 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('auth:menu:modify')")
    @BreezeSysLog(description = "菜单信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody SysMenu sysMenu) {
        return sysMenuService.updateMenuById(sysMenu);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('auth:menu:delete')")
    @BreezeSysLog(description = "菜单信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long id) {
        return this.sysMenuService.deleteById(id);
    }

}
