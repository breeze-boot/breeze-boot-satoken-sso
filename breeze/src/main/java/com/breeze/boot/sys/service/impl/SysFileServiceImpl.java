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

package com.breeze.boot.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.sys.domain.SysFile;
import com.breeze.boot.sys.mapper.SysFileMapper;
import com.breeze.boot.sys.params.FileParam;
import com.breeze.boot.sys.query.FileQuery;
import com.breeze.boot.sys.service.SysFileService;
import com.breeze.core.enums.ResultCode;
import com.breeze.core.ex.SystemServiceException;
import com.breeze.core.utils.Result;
import com.breeze.oss.OssStoreService;
import com.breeze.oss.bo.FileBO;
import com.google.common.collect.Maps;
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
import java.util.Map;
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
     * @param fileQuery 文件查询
     * @return {@link Page}<{@link SysFile}>
     */
    @Override
    public Page<SysFile> listPage(FileQuery fileQuery) {
        Page<SysFile> logEntityPage = new Page<>(fileQuery.getCurrent(), fileQuery.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(fileQuery.getNewFileName()), SysFile::getNewFileName, fileQuery.getNewFileName())
                .like(StrUtil.isAllNotBlank(fileQuery.getOriginalFileName()), SysFile::getOriginalFileName, fileQuery.getOriginalFileName())
                .page(logEntityPage);
    }

    /**
     * 上传
     *
     * @param fileParam 文件上传参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @SneakyThrows
    @Override
    public Result<Map<String, Object>> upload(FileParam fileParam, HttpServletRequest request, HttpServletResponse response) {
        MultipartFile file = fileParam.getFile();
        String newFileName = UUID.randomUUID().toString().replace("-", "");
        FileBO fileBO = null;
        try {
            fileBO = this.ossStoreService.upload(fileParam.getOssStyle(), file,
                    String.valueOf(LocalDate.now().getDayOfMonth()), newFileName);
        } catch (Exception ex) {
            log.error("", ex);
        }
        if (Objects.isNull(fileBO)) {
            return Result.fail("上传失败");
        }
        SysFile sysFile = SysFile.builder()
                .title(fileParam.getTitle())
                .newFileName(fileBO.getNewFileName())
                .originalFileName(fileBO.getOriginalFilename())
                .path(fileBO.getPath())
                .ossStyle(fileParam.getOssStyle())
                .build();
        this.save(sysFile);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("url", this.ossStoreService.preview(sysFile.getOssStyle(), sysFile.getPath()));
        resultMap.put("path", fileBO.getPath());
        resultMap.put("fileId", sysFile.getId());
        return Result.ok(resultMap);
    }

    /**
     * 预览
     *
     * @param fileId 文件ID
     * @return {@link Result}<{@link Boolean}>
     */
    @SneakyThrows
    @Override
    public String preview(Long fileId) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.isNull(sysFile)) {
            // TODO 缺省图
            return "";
        }
        return this.ossStoreService.preview(sysFile.getOssStyle(), sysFile.getPath());
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
            Boolean remove = this.ossStoreService.remove(sysFile.getOssStyle(), sysFile.getPath());
            if (!remove) {
                // TODO
                return Result.fail(Boolean.FALSE, "删除失败");
            }
            this.removeById(sysFile.getId());
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}
