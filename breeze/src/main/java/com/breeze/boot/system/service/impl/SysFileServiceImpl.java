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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.base.oss.OssStoreService;
import com.breeze.base.oss.dto.FileBO;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.ex.SystemServiceException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    /**
     * oss存储服务
     */
    @Autowired
    private OssStoreService ossStoreService;

    /**
     * 列表页面
     *
     * @param fileSearchDTO 文件搜索DTO
     * @return {@link Page}<{@link SysFile}>
     */
    @Override
    public Page<SysFile> listPage(FileSearchDTO fileSearchDTO) {
        Page<SysFile> logEntityPage = new Page<>(fileSearchDTO.getCurrent(), fileSearchDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(fileSearchDTO.getNewFileName()), SysFile::getNewFileName, fileSearchDTO.getNewFileName())
                .like(StrUtil.isAllNotBlank(fileSearchDTO.getOriginalFileName()), SysFile::getOriginalFileName, fileSearchDTO.getOriginalFileName())
                .page(logEntityPage);
    }

    /**
     * 上传
     *
     * @param fileDTO  文件DTO
     * @param request  请求
     * @param response 响应
     * @return {@link Result}<{@link Boolean}>
     */
    @SneakyThrows
    @Override
    public Result<Boolean> upload(FileDTO fileDTO, HttpServletRequest request, HttpServletResponse response) {
        MultipartFile file = fileDTO.getFile();
        FileBO fileBO = null;
        try {
            fileBO = this.ossStoreService.upload(fileDTO.getOssStyle(), file, String.valueOf(LocalDate.now().getDayOfMonth()), UUID.randomUUID().toString().replace("-", ""));
        } catch (Exception ex) {
            log.error("", ex);
        }
        if (Objects.isNull(fileBO)) {
            return Result.fail("上传失败");
        }
        SysFile sysFile = SysFile.builder()
                .title(fileDTO.getTitle())
                .newFileName(fileBO.getNewFileName())
                .originalFileName(fileBO.getOriginalFilename())
                .path(fileBO.getPath())
                .ossStyle(fileDTO.getOssStyle())
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
    public Result<String> preview(Long fileId) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.isNull(sysFile)) {
            return Result.fail("文件不存在");
        }
        String preview = this.ossStoreService.preview(sysFile.getOssStyle(), sysFile.getPath(), sysFile.getNewFileName());
        return Result.ok(preview);
    }

    /**
     * 下载
     *
     * @param fileId   文件ID
     * @param response 响应
     */
    @Override
    public void download(Long fileId, HttpServletResponse response) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.isNull(sysFile)) {
            throw new SystemServiceException(ResultCode.exception("文件不存在"));
        }
        response.setHeader("original-file-name", sysFile.getOriginalFileName());
        this.ossStoreService.download(sysFile.getOssStyle(), sysFile.getPath(), sysFile.getNewFileName(), response);
    }

    /**
     * 通过id删除文件
     *
     * @param fileIds 文件IDS
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeFileByIds(List<Long> fileIds) {
        List<SysFile> sysFileList = this.listByIds(fileIds);
        if (CollUtil.isEmpty(sysFileList)) {
            return Result.fail(Boolean.FALSE, "文件不存在");
        }
        for (SysFile sysFile : sysFileList) {
            Boolean remove = this.ossStoreService.remove(sysFile.getOssStyle(), sysFile.getPath(), sysFile.getNewFileName());
            if (!remove) {
                // TODO
                return Result.fail(Boolean.FALSE, "删除失败");
            }
            this.removeById(sysFile.getId());
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}
