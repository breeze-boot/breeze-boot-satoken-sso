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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.base.BaseLoginUser;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.security.utils.SecurityUtils;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.params.UserOpenParam;
import com.breeze.boot.system.params.UserResetParam;
import com.breeze.boot.system.params.UserRolesParam;
import com.breeze.boot.system.query.UserQuery;
import com.breeze.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * 系统用户控制器
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/user")
@Tag(name = "系统用户管理模块", description = "SysUserController")
public class SysUserController {

    /**
     * 系统用户服务
     */
    private final SysUserService sysUserService;

    /**
     * 列表
     *
     * @param userQuery 用户查询
     * @return {@link Result}<{@link IPage}<{@link SysUser}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    public Result<IPage<SysUser>> list(UserQuery userQuery) {
        return Result.ok(this.sysUserService.listPage(userQuery));
    }

    /**
     * 创建
     *
     * @param sysUser 系统用户
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('sys:user:create')")
    @BreezeSysLog(description = "用户信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysUser sysUser) {
        return sysUserService.saveUser(sysUser);
    }

    /**
     * 修改
     *
     * @param sysUser 系统用户
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('sys:user:modify')")
    @BreezeSysLog(description = "用户信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody SysUser sysUser) {
        return Result.ok(sysUserService.updateUserById(sysUser));
    }

    /**
     * 详情
     *
     * @param userId 用户id
     * @return {@link Result}<{@link SysUser}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAnyAuthority('sys:user:info')")
    public Result<SysUser> info(@PathVariable("userId") Long userId) {
        return this.sysUserService.getUserById(userId);
    }

    /**
     * 校验用户名是否重复
     *
     * @param username 平台编码
     * @param userId   用户ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "校验用户名是否重复")
    @GetMapping("/checkUsername")
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    public Result<Boolean> checkUsername(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username,
                                         @RequestParam(value = "userId", required = false) Long userId) {
        return Result.ok(Objects.isNull(this.sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .ne(Objects.nonNull(userId), SysUser::getId, userId)
                .eq(SysUser::getUsername, username))));
    }

    /**
     * 查询用户通过部门id
     *
     * @param deptIds 部门id
     * @return {@link Result}<{@link List}<{@link SysUser}>>
     */
    @Operation(summary = "查询用户通过部门id")
    @PostMapping("/listUserByDeptId")
    public Result<List<SysUser>> listUserByDeptId(@RequestBody List<Long> deptIds) {
        return Result.ok(this.sysUserService.listUserByDeptId(deptIds));
    }

    /**
     * 删除
     *
     * @param ids 用户ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:user:delete')")
    @BreezeSysLog(description = "用户信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody List<Long> ids) {
        List<SysUser> sysUserList = this.sysUserService.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, ids));
        if (CollUtil.isEmpty(sysUserList)) {
            return Result.fail(Boolean.FALSE, "用户不存在");
        }
        for (SysUser sysUser : sysUserList) {
            this.sysUserService.removeUser(sysUser);
        }
        return Result.ok();
    }

    /**
     * 导出
     *
     * @param response 响应
     */
    @Operation(summary = "导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        this.sysUserService.export(response);
    }

    /**
     * 重置密码
     *
     * @param userResetParam 用户重置密码参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "重置密码")
    @PutMapping("/reset")
    @PreAuthorize("hasAnyAuthority('sys:user:reset')")
    @BreezeSysLog(description = "用户重置密码", type = LogType.EDIT)
    public Result<Boolean> reset(@Valid @RequestBody UserResetParam userResetParam) {
        return Result.ok(sysUserService.reset(userResetParam));
    }

    /**
     * 开启关闭锁定
     *
     * @param userOpenParam 用户开关参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "用户锁定开关")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:user:modify')")
    @BreezeSysLog(description = "用户锁定", type = LogType.EDIT)
    public Result<Boolean> open(@Valid @RequestBody UserOpenParam userOpenParam) {
        return Result.ok(sysUserService.open(userOpenParam));
    }

    /**
     * 用户添加角色
     * 用户分配角色
     *
     * @param userRolesParam 用户角色参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "用户分配角色")
    @PutMapping("/setRole")
    @PreAuthorize("hasAnyAuthority('sys:user:setRole')")
    @BreezeSysLog(description = "用户分配角色", type = LogType.EDIT)
    public Result<Boolean> setRole(@Valid @RequestBody UserRolesParam userRolesParam) {
        return sysUserService.setRole(userRolesParam);
    }

    /**
     * 查询用户信息
     *
     * @return {@link String }
     */
    @Operation(summary = "查询用户信息")
    @PreAuthorize("hasAnyAuthority('sys:user:info')")
    @GetMapping("/userInfo")
    public Result<BaseLoginUser> userInfo() {
        return Result.ok(SecurityUtils.getCurrentUser());
    }
}
