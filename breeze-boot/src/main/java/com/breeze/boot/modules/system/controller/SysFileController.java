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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.domain.SysFile;
import com.breeze.boot.modules.system.domain.params.FileParam;
import com.breeze.boot.modules.system.domain.query.FileQuery;
import com.breeze.boot.modules.system.service.SysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;

/**
 * 系统文件控制器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/file")
@Tag(name = "系统文件管理模块", description = "SysFileController")
public class SysFileController {

    /**
     * 系统文件服务
     */
    private final SysFileService sysFileService;

    /**
     * 列表
     *
     * @param fileQuery 文件查询
     * @return {@link Result}<{@link Page}<{@link SysFile}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:file:list')")
    public Result<Page<SysFile>> list(FileQuery fileQuery) {
        return Result.ok(this.sysFileService.listPage(fileQuery));
    }

    /**
     * 图片预览
     *
     * @param fileId 文件ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "图片预览")
    @GetMapping("/preview")
    @PreAuthorize("hasAnyAuthority('sys:file:preview')")
    public Result<String> preview(@NotNull(message = "参数不能为空") Long fileId) {
        return Result.ok(this.sysFileService.preview(fileId));
    }

    /**
     * 下载
     *
     * @param fileId   文件标识
     * @param response 响应
     */
    @Operation(summary = "文件下载")
    @GetMapping("/download")
    @PreAuthorize("hasAnyAuthority('sys:file:download')")
    public void download(@NotNull(message = "参数不能为空") Long fileId,
                         HttpServletResponse response) {
        this.sysFileService.download(fileId, response);
    }

    /**
     * 上传
     *
     * @param fileParam 文件上传参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "文件上传")
    @PostMapping("/uploadMinioS3")
    @PreAuthorize("hasAnyAuthority('sys:file:upload')")
    public Result<Map<String, Object>> uploadMinioS3(@Valid FileParam fileParam,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return this.sysFileService.uploadMinioS3(fileParam, request, response);
    }

    /**
     * 上传
     *
     * @param fileParam 文件上传参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "文件上传")
    @PostMapping("/uploadLocalStorage")
    @PreAuthorize("hasAnyAuthority('sys:file:upload')")
    public Result<Map<String, Object>> uploadLocalStorage(@Valid FileParam fileParam,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        return this.sysFileService.uploadLocalStorage(fileParam, request, response);
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('sys:file:delete')")
    @BreezeSysLog(description = "文件信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysFileService.removeFileByIds(Arrays.asList(ids));
    }

}
