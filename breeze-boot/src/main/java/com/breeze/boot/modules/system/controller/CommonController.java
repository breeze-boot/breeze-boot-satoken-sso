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

package com.breeze.boot.modules.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysUser;
import com.breeze.boot.modules.system.model.params.FileParam;
import com.breeze.boot.modules.system.service.CommonService;
import com.breeze.boot.security.annotation.JumpAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/common")
@Tag(name = "通用接口管理模块", description = "CommonController")
public class CommonController {

    /**
     * 公共服务
     */
    private final CommonService commonService;

    /**
     * 菜单树形下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Operation(summary = "菜单树形下拉框", description = "下拉框接口")
    @GetMapping("/selectMenu")
    public Result<List<Tree<Long>>> selectMenu(@RequestParam(defaultValue = "", required = false) Long id) {
        return this.commonService.selectMenu(id);
    }

    /**
     * 平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "平台下拉框", description = "下拉框接口")
    @GetMapping("/selectPlatform")
    public Result<List<Map<String, Object>>> selectPlatform() {
        return this.commonService.selectPlatform();

    }

    /**
     * 部门下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Operation(summary = "部门下拉框", description = "下拉框接口")
    @GetMapping("/selectDept")
    public Result<List<?>> selectDept(@RequestParam(defaultValue = "", required = false) Long id) {
        return this.commonService.selectDept(id);
    }

    /**
     * 用户列表
     *
     * @param deptId 部门ID
     * @return {@link Result}<{@link List}<{@link SysUser}>>
     */
    @Operation(summary = "用户下拉框", description = "下拉框接口")
    @GetMapping("/listUser")
    public Result<List<SysUser>> listUser(@RequestParam(value = "deptId" ,required = false) Long deptId) {
        return this.commonService.listUser(deptId);
    }

    /**
     * 角色下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "角色下拉框", description = "下拉框接口")
    @GetMapping("/selectRole")
    public Result<List<Map<String, Object>>> selectRole() {
        return this.commonService.selectRole();
    }

    /**
     * 租户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @JumpAuth
    @Operation(summary = "租户下拉框", description = "下拉框接口")
    @GetMapping("/selectTenant")
    public Result<List<Map<String, Object>>> selectTenant() {
        return this.commonService.selectTenant();
    }

    /**
     * 岗位下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "岗位下拉框", description = "下拉框接口")
    @GetMapping("/selectPost")
    public Result<List<Map<String, Object>>> selectPost() {
        return this.commonService.selectPost();
    }

    /**
     * 表名下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "表名下拉框", description = "下拉框接口")
    @GetMapping("/selectTable")
    public Result<List<Map<String, Object>>> selectTable() {
        return this.commonService.selectTable();
    }

    /**
     * 字段下拉框
     *
     * @param tableName 表名
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "字段下拉框", description = "下拉框接口")
    @GetMapping("/selectTableColumn")
    public Result<List<Map<String, Object>>> selectTableColumn(@RequestParam("tableName") String tableName) {
        return this.commonService.selectTableColumn(tableName);
    }

    /**
     * 数据权限下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "数据权限下拉框", description = "下拉框接口")
    @GetMapping("/selectPermission")
    public Result<List<Map<String, Object>>> selectPermission() {
        return this.commonService.selectPermission();
    }

    /**
     * 数据权限下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "数据权限下拉框", description = "下拉框接口")
    @GetMapping("/selectCustomizePermission")
    public Result<List<Map<String, Object>>> selectCustomizePermission() {
        return this.commonService.selectCustomizePermission();
    }

    /**
     * 文件上传到minio
     *
     * @param fileParam 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "文件上传到minio", description = "文件上传到minio")
    @GetMapping("/uploadMinioS3")
    public Result<Map<String, Object>> uploadMinioS3(@Valid FileParam fileParam,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return this.commonService.uploadMinioS3(fileParam, request, response);
    }

    /**
     * 文件上传到本地存储
     *
     * @param fileParam 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "文件上传到本地存储", description = "文件上传到本地存储")
    @GetMapping("/uploadLocalStorage")
    public Result<?> uploadLocalStorage(@Valid FileParam fileParam,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        return this.commonService.uploadLocalStorage(fileParam, request, response);
    }

    /**
     * 下载
     *
     * @param fileId     文件标识
     * @param response   响应
     */
    @Operation(summary = "文件下载")
    @GetMapping("/download")
    public void download(@NotNull(message = "参数不能为空") Long fileId,
                         HttpServletResponse response) {
        this.commonService.download(fileId, response);
    }

}
