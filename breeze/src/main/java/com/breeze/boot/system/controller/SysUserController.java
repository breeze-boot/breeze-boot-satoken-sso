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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.UserOpenDTO;
import com.breeze.boot.system.dto.UserResetPasswordDTO;
import com.breeze.boot.system.dto.UserRolesDTO;
import com.breeze.boot.system.dto.UserSearchDTO;
import com.breeze.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统用户控制器
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@RestController
@RequestMapping("/sys/user")
@Tag(name = "系统用户管理模块", description = "SysUserController")
public class SysUserController {

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     *
     * @param userSearchDTO 用户dto
     * @return {@link Result}
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    public Result<IPage<SysUser>> list(@RequestBody UserSearchDTO userSearchDTO) {
        return Result.ok(this.sysUserService.listPage(userSearchDTO));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:user:info')")
    public Result<SysUser> info(@PathVariable("id") Long id) {
        return sysUserService.getUserById(id);
    }

    /**
     * 保存
     *
     * @param sysUser 系统用户
     * @return {@link Result}
     */
    @Operation(summary = "保存")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:user:save')")
    @BreezeSysLog(description = "用户信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Validated @RequestBody SysUser sysUser) {
        return sysUserService.saveUser(sysUser);
    }

    /**
     * 修改
     *
     * @param sysUser 系统用户
     * @return {@link Result}
     */
    @Operation(summary = "修改")
    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('sys:user:edit')")
    @BreezeSysLog(description = "用户信息修改", type = LogType.EDIT)
    public Result<Boolean> edit(@Validated @RequestBody SysUser sysUser) {
        return Result.ok(sysUserService.updateUserById(sysUser));
    }

    /**
     * 重置密码
     *
     * @param userResetPassword 用户重置密码
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "重置密码")
    @PutMapping("/resetPass")
    @PreAuthorize("hasAnyAuthority('sys:user:edit')")
    @BreezeSysLog(description = "用户重置密码", type = LogType.EDIT)
    public Result<Boolean> resetPass(@Validated @RequestBody UserResetPasswordDTO userResetPassword) {
        return Result.ok(sysUserService.resetPass(userResetPassword));
    }

    /**
     * 开启关闭锁定
     *
     * @param openDTO 打开dto
     * @return {@link Result}
     */
    @Operation(summary = "用户锁定开关")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:user:edit')")
    @BreezeSysLog(description = "用户锁定", type = LogType.EDIT)
    public Result<Boolean> open(@Validated @RequestBody UserOpenDTO openDTO) {
        return Result.ok(sysUserService.open(openDTO));
    }

    /**
     * 用户分配角色
     *
     * @param userRolesDTO 用户角色dto
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "用户分配角色")
    @PutMapping("/userAddRole")
    @PreAuthorize("hasAnyAuthority('sys:user:edit')")
    @BreezeSysLog(description = "用户分配角色", type = LogType.EDIT)
    public Result<Boolean> userAddRole(@Validated @RequestBody UserRolesDTO userRolesDTO) {
        return sysUserService.userAddRole(userRolesDTO);
    }

    /**
     * 删除
     *
     * @param ids 用户 ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
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

}
