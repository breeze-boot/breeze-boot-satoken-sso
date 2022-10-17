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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysFile;
import com.breeze.boot.system.dto.FileDTO;
import com.breeze.boot.system.service.SysFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统文件控制器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Tag(name = "文件管理模块", description = "文件管理模块")
@RestController
@RequestMapping("/sys/file")
public class SysFileController {

    /**
     * 系统文件服务
     */
    @Autowired
    private SysFileService sysFileService;

    /**
     * 列表
     *
     * @param fileDTO 文件dto
     * @return {@link Result}<{@link List}<{@link SysFile}>>
     */
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:file:list')")
    public Result<Page<SysFile>> list(@RequestBody FileDTO fileDTO) {
        return Result.ok(this.sysFileService.listFile(fileDTO));
    }

    /**
     * 保存
     *
     * @param fileEntity 文件实体
     * @return {@link Result}
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:file:save')")
    public Result<Boolean> save(@RequestBody SysFile fileEntity) {
        return Result.ok(sysFileService.save(fileEntity));
    }

    /**
     * 更新
     *
     * @param fileEntity 文件实体
     * @return {@link Result}
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:file:update')")
    public Result<Boolean> update(@RequestBody SysFile fileEntity) {
        return Result.ok(sysFileService.updateById(fileEntity));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:file:delete')")
    public Result<Boolean> delete(@RequestBody Long[] ids) {
        return Result.ok(this.sysFileService.removeByIds(Arrays.asList(ids)));
    }

}
