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
import com.breeze.base.oss.dto.FileBO;
import com.breeze.base.oss.local.service.LocalFileService;
import com.breeze.base.oss.minio.service.MinioService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysFile;
import com.breeze.boot.system.dto.FileDTO;
import com.breeze.boot.system.dto.FileSearchDTO;
import com.breeze.boot.system.mapper.SysFileMapper;
import com.breeze.boot.system.service.SysFileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 系统文件服务impl
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Slf4j
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
    public Result<Boolean> upload(FileDTO fileDTO, HttpServletRequest request, HttpServletResponse response) {
        Optional<FileBO> optionalFileBO;
        MultipartFile file = fileDTO.getFile();
        switch (fileDTO.getOssStyle()) {
            case 0:
                optionalFileBO = this.localFileService.uploadFile(file);
                break;
            case 1:
                optionalFileBO = this.minioService.upload2Minio(file.getInputStream(), fileDTO.getFile().getSize(), "/test", UUID.randomUUID().toString().replace("-", ""), file.getContentType());
                break;
            default:
                optionalFileBO = Optional.empty();
                log.error("存储类型错误");
        }
        FileBO fileBO = optionalFileBO.orElseThrow(RuntimeException::new);
        SysFile sysFile = SysFile.builder()
                .title(fileDTO.getTitle())
                .newFileName(fileBO.getNewFileName())
                .originalFileName(fileBO.getOriginalFilename())
                .path(fileBO.getPath())
                .build();
        return Result.ok(this.save(sysFile));
    }

    /**
     * 预览
     *
     * @param fileId 文件ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> preview(Long fileId) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.isNull(sysFile)) {
            return Result.fail(Boolean.FALSE, "文件不存在");
        }
        Optional<String> preView;
        switch (sysFile.getOssStyle()) {
            case 0:
                preView = this.localFileService.previewImg(sysFile.getNewFileName());
                break;
            case 1:
                preView = this.minioService.previewImg(sysFile.getNewFileName());
                break;
            default:
                preView = Optional.empty();
                log.error("存储类型错误");
        }
        return Result.ok(Boolean.TRUE, preView.get());
    }

    @Override
    public void download(Long fileId, HttpServletResponse response) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.isNull(sysFile)) {
            throw new RuntimeException("");
        }
        switch (sysFile.getOssStyle()) {
            case 0:
                this.localFileService.download(sysFile.getNewFileName(), response);
                break;
            case 1:
                this.minioService.download(sysFile.getNewFileName(), response);
                break;
            default:
                log.error("存储类型错误");
        }
    }

}
