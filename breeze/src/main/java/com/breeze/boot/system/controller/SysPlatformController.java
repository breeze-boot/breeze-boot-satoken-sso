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

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.Result;
import com.breeze.boot.log.annotation.SysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysMenu;
import com.breeze.boot.system.domain.SysPlatform;
import com.breeze.boot.system.dto.PlatformDTO;
import com.breeze.boot.system.service.SysMenuService;
import com.breeze.boot.system.service.SysPlatformService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统平台控制器
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@RestController
@Tag(name = "平台管理模块", description = "平台管理模块")
@RequestMapping("/sys/platform")
@AllArgsConstructor
public class SysPlatformController {

    /**
     * 系统平台服务
     */
    private final SysPlatformService sysPlatformService;

    /**
     * 系统菜单服务
     */
    private final SysMenuService sysMenuService;

    /**
     * 列表
     *
     * @param platformDTO 平台dto
     * @return {@link Result}
     */
    @PostMapping("/list")
    @SysLog(description = "查询", type = LogType.LIST)
    @PreAuthorize("hasAnyAuthority('sys:platform:list')")
    public Result<Page<SysPlatform>> list(@RequestBody PlatformDTO platformDTO) {
        int i = 1 / 0;
        return Result.ok(this.sysPlatformService.listPage(platformDTO));
    }

    /**
     * 信息
     *
     * @param id id
     * @return {@link Result}
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:platform:info')")
    public Result<SysPlatform> info(@PathVariable("id") Long id) {
        return Result.ok(this.sysPlatformService.getById(id));
    }

    /**
     * 保存
     *
     * @param platform 平台实体入参
     * @return {@link Result}
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:platform:save')")
    public Result<Boolean> save(@Validated @RequestBody SysPlatform platform) {
        return Result.ok(this.sysPlatformService.save(platform));
    }

    /**
     * 更新
     *
     * @param platformEntity 平台实体
     * @return {@link Result}
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:platform:update')")
    public Result<Boolean> update(@RequestBody SysPlatform platformEntity) {
        return Result.ok(this.sysPlatformService.updateById(platformEntity));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:platform:delete')")
    public Result<Boolean> delete(@RequestBody Long[] ids) {
        List<SysMenu> menuEntityList = this.sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getPlatformId, ids));
        if (CollectionUtil.isNotEmpty(menuEntityList)) {
            return Result.warning(Boolean.FALSE, "该平台有菜单配置");
        }
        return Result.ok(this.sysPlatformService.removeByIds(Arrays.asList(ids)));
    }

}
