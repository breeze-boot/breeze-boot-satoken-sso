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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.base.oss.dto.FileDTO;
import com.breeze.base.oss.local.service.LocalFileService;
import com.breeze.base.oss.minio.service.MinioService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysFile;
import com.breeze.boot.system.dto.FileSearchDTO;
import com.breeze.boot.system.mapper.SysFileMapper;
import com.breeze.boot.system.service.SysFileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 系统文件服务impl
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {


    @Autowired
    private LocalFileService localFileService;

    @Autowired
    private MinioService minioService;

    /**
     * 列表文件
     *
     * @param fileSearchDTO 日志dto
     * @return {@link Page}<{@link SysFile}>
     */
    @Override
    public Page<SysFile> listFile(FileSearchDTO fileSearchDTO) {
        Page<SysFile> logEntityPage = new Page<>(fileSearchDTO.getCurrent(), fileSearchDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(fileSearchDTO.getNewFileName()), SysFile::getNewFileName, fileSearchDTO.getNewFileName())
                .like(StrUtil.isAllNotBlank(fileSearchDTO.getOriginalFileName()), SysFile::getOriginalFileName, fileSearchDTO.getOriginalFileName())
                .page(logEntityPage);
    }

    @SneakyThrows
    @Override
    public Result<Boolean> upload(FileDTO file, HttpServletRequest request, HttpServletResponse response) {
        FileDTO fileDTO = this.localFileService.uploadFile(file.getFile());
        FileDTO breeze = this.minioService.upload2Minio(file.getFile().getInputStream(), file.getFile().getSize(), "/test", UUID.randomUUID().toString());
        SysFile sysFile = SysFile.builder()
                .title(file.getTitle())
                .newFileName(fileDTO.getNewFileName())
                .originalFileName(fileDTO.getOriginalFilename())
                // .userId(SecurityUtils.getCurrentUser().getId())
                .path(fileDTO.getPath())
                .build();
        this.minioService.download("testd074fbdc-3e61-492b-8d3c-f370d2432d36", response);
        return Result.ok(this.save(sysFile));
    }

}
