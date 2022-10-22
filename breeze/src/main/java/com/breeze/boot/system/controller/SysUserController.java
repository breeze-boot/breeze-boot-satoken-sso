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

import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.UserDTO;
import com.breeze.boot.system.dto.UserOpenDTO;
import com.breeze.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 系统用户控制器
 *
 * @author breeze
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
     * @param userDTO 用户dto
     * @return {@link Result}
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    public Result list(@Validated @RequestBody UserDTO userDTO) {
        return Result.ok(this.sysUserService.listPage(userDTO));
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
        return Result.ok(sysUserService.getById(id));
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
    public Result<Boolean> save(@Validated @RequestBody SysUser sysUser) {
        return sysUserService.saveUser(sysUser);
    }

    /**
     * 更新
     *
     * @param sysUser 系统用户
     * @return {@link Result}
     */
    @Operation(summary = "更新")
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:user:update')")
    public Result<Boolean> update(@Validated @RequestBody SysUser sysUser) {
        return Result.ok(sysUserService.updateUserById(sysUser));
    }

    /**
     * 重置密码
     *
     * @param sysUser 系统用户
     * @return {@link Result}
     */
    @Operation(summary = "重置密码")
    @PutMapping("/resetPass")
    @PreAuthorize("hasAnyAuthority('sys:user:update')")
    public Result<Boolean> resetPass(@Validated @RequestBody SysUser sysUser) {
        return Result.ok(sysUserService.resetPass(sysUser));
    }

    /**
     * 开启关闭锁定
     *
     * @param openDTO 打开dto
     * @return {@link Result}
     */
    @Operation(summary = "更新")
    @PutMapping("/open")
    @PreAuthorize("hasAnyAuthority('sys:user:update')")
    public Result<Boolean> open(@Validated @RequestBody UserOpenDTO openDTO) {
        return Result.ok(sysUserService.open(openDTO));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}
     */
    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:user:delete')")
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return sysUserService.deleteByIds(Arrays.asList(ids));
    }

}
