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

package com.breeze.boot.admin.controller;

import com.breeze.boot.admin.dto.MenuDTO;
import com.breeze.boot.admin.entity.SysMenuEntity;
import com.breeze.boot.admin.service.SysMenuService;
import com.breeze.boot.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 系统菜单控制器
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Api(tags = "菜单管理模块", value = "菜单管理模块")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    /**
     * 系统菜单服务
     */
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     *
     * @param menuDTO 菜单dto
     * @return {@link Result}
     */
    @ApiOperation(value = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public Result list(@RequestBody MenuDTO menuDTO) {
        return this.sysMenuService.listMenu(menuDTO);
    }

    /**
     * 树菜单列表
     *
     * @param platformCode 平台标识
     * @return {@link Result}
     */
    @ApiOperation(value = "树形菜单列表")
    @GetMapping("/listTreeMenu")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public Result listTreeMenu(@RequestParam(required = false) String platformCode) {
        return this.sysMenuService.listTreeMenu(platformCode);
    }

    /**
     * 列表树许可
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "树形菜单列表")
    @GetMapping("/listTreePermission")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public Result listTreePermission() {
        return this.sysMenuService.listTreePermission();
    }

    /**
     * 信息
     *
     * @param id id
     * @return {@link Result}
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:menu:info')")
    public Result info(@PathVariable("id") Long id) {
        return Result.ok(sysMenuService.getById(id));
    }

    /**
     * 保存
     *
     * @param menuEntity 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:menu:save')")
    public Result<Boolean> save(@RequestBody SysMenuEntity menuEntity) {
        return Result.ok(sysMenuService.save(menuEntity));
    }

    /**
     * 更新
     *
     * @param menuEntity 菜单实体
     * @return {@link Result}<{@link Boolean}>
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:menu:update')")
    public Result<Boolean> update(@RequestBody SysMenuEntity menuEntity) {
        return Result.ok(sysMenuService.updateById(menuEntity));
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:menu:delete')")
    public Result delele(@RequestParam Long id) {
        return this.sysMenuService.deleteById(id);
    }

}
