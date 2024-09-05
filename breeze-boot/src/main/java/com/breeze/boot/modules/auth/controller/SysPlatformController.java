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

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.entity.SysMenu;
import com.breeze.boot.modules.auth.model.entity.SysPlatform;
import com.breeze.boot.modules.auth.model.form.PlatformForm;
import com.breeze.boot.modules.auth.model.query.PlatformQuery;
import com.breeze.boot.modules.auth.model.vo.PlatformVO;
import com.breeze.boot.modules.auth.service.SysMenuService;
import com.breeze.boot.modules.auth.service.SysPlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 系统平台控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/platform")
@Tag(name = "系统平台管理模块", description = "SysPlatformController")
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
     * @param platformQuery 平台查询
     * @return {@link Result}<{@link Page}<{@link PlatformVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("auth:platform:list")
    public Result<Page<PlatformVO>> list(PlatformQuery platformQuery) {
        return Result.ok(this.sysPlatformService.listPage(platformQuery));
    }

    /**
     * 详情
     *
     * @param platformId 平台id
     * @return {@link Result}<{@link PlatformVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{platformId}")
    @SaCheckPermission("auth:platform:info")
    public Result<PlatformVO> info(@Parameter(description = "平台ID") @PathVariable("platformId") Long platformId) {
        return Result.ok(this.sysPlatformService.getInfoById(platformId));
    }

    /**
     * 校验平台编码是否重复
     *
     * @param platformCode 平台编码
     * @param platformId   平台ID
     * @return {@link Result}<{@link SysPlatform}>
     */
    @Operation(summary = "校验平台编码是否重复")
    @GetMapping("/checkPlatformCode")
    @SaCheckPermission("auth:platform:list")
    public Result<Boolean> checkPlatformCode(@Parameter(description = "平台编码") @NotBlank(message = "平台编码不能为空") @RequestParam("platformCode") String platformCode,
                                             @Parameter(description = "平台ID") @RequestParam(value = "platformId", required = false) Long platformId) {
        return Result.ok(Objects.isNull(this.sysPlatformService.getOne(Wrappers.<SysPlatform>lambdaQuery()
                .ne(Objects.nonNull(platformId), SysPlatform::getId, platformId)
                .eq(SysPlatform::getPlatformCode, platformCode))));
    }

    /**
     * 创建
     *
     * @param platformForm 平台表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:platform:create")
    @BreezeSysLog(description = "平台信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody PlatformForm platformForm) {
        return Result.ok(this.sysPlatformService.savePlatform(platformForm));
    }

    /**
     * 修改
     *
     * @param platformForm 平台表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:platform:modify")
    @BreezeSysLog(description = "平台信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "菜单ID") @PathVariable Long id,
                                  @Valid @RequestBody PlatformForm platformForm) {
        return Result.ok(this.sysPlatformService.modifyPlatform(id, platformForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:platform:delete")
    @BreezeSysLog(description = "平台信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "平台IDS") @NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        List<SysMenu> menuEntityList = this.sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getPlatformId, (Object[]) ids));
        if (CollectionUtil.isNotEmpty(menuEntityList)) {
            return Result.fail(Boolean.FALSE, "该平台有菜单配置");
        }
        return Result.ok(this.sysPlatformService.removeByIds(Arrays.asList(ids)));
    }

}
