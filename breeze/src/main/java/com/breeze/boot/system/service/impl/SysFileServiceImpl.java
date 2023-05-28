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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ContentType;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.oss.s3.operation.OssTemplate;
import com.breeze.boot.storage.local.template.LocalStorageTemplate;
import com.breeze.boot.system.domain.SysFile;
import com.breeze.boot.system.mapper.SysFileMapper;
import com.breeze.boot.system.params.FileParam;
import com.breeze.boot.system.query.FileQuery;
import com.breeze.boot.system.service.SysFileService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.breeze.boot.core.constants.CoreConstants.SYSTEM_BUCKET_NAME;

/**
 * 系统文件服务impl
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    /**
     * oss存储服务
     */
    private final OssTemplate ossTemplate;

    /**
     * 本地存储模板
     */
    private final LocalStorageTemplate localStorageTemplate;

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
     * 上传minio
     *
     * @param fileParam 文件上传参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @SneakyThrows
    @Override
    public Result<Map<String, Object>> uploadMinioS3(FileParam fileParam, HttpServletRequest request, HttpServletResponse response) {
        LocalDate now = LocalDate.now();

        MultipartFile file = fileParam.getFile();
        String originalFilename = file.getOriginalFilename();
        Assert.isNull(originalFilename, "文件名不能为空");
        String newFileName = now.getYear() + now.getMonthOfYear() + now.getDayOfMonth() + RandomUtil.randomInt(6)
                + originalFilename.substring(originalFilename.lastIndexOf("."));
        String path = now.getYear() + "/" + now.getMonthOfYear() + "/" + now.getDayOfMonth() + "/";

        try {
            this.ossTemplate.createBucket(SYSTEM_BUCKET_NAME);
            this.ossTemplate.putObject(SYSTEM_BUCKET_NAME, path + newFileName, file.getInputStream(), ContentType.getContentType(originalFilename));
        } catch (Exception ex) {
            log.error("[文件上传失败]", ex);
        }
        SysFile sysFile = SysFile.builder()
                .title(fileParam.getTitle())
                .newFileName(newFileName)
                .originalFileName(originalFilename)
                .path(path)
                .ossStyle(fileParam.getOssStyle())
                .build();
        this.save(sysFile);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("url", this.ossTemplate.getObjectURL(SYSTEM_BUCKET_NAME, sysFile.getPath() + sysFile.getNewFileName(), 2));
        resultMap.put("fileId", sysFile.getId());
        return Result.ok(resultMap);
    }

    /**
     * 上传本地存储
     *
     * @param fileParam 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Result<Map<String, Object>> uploadLocalStorage(FileParam fileParam, HttpServletRequest request, HttpServletResponse response) {
        String originalFilename = fileParam.getFile().getOriginalFilename();
        LocalDate now = LocalDate.now();
        String newFileName = now.getYear() + now.getMonthOfYear() + now.getDayOfMonth() + RandomUtil.randomInt(6)
                + originalFilename.substring(originalFilename.lastIndexOf("."));
        String path = now.getYear() + "/" + now.getMonthOfYear() + "/" + now.getDayOfMonth() + "/";
        String filePath = this.localStorageTemplate.uploadFile(fileParam.getFile(), path, newFileName);
        log.debug("[上传的文件路径]：{}", filePath);
        SysFile sysFile = SysFile.builder()
                .title(fileParam.getTitle())
                .newFileName(newFileName)
                .originalFileName(originalFilename)
                .path(path)
                .ossStyle(fileParam.getOssStyle())
                .build();
        this.save(sysFile);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("url", this.localStorageTemplate.previewImg(sysFile.getPath(), sysFile.getNewFileName()));
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
        return this.ossTemplate.getObjectURL(SYSTEM_BUCKET_NAME, sysFile.getPath() + sysFile.getNewFileName(), 2);
    }

    /**
     * 下载
     *
     * @param fileId   文件ID
     * @param response 响应
     */
    @SneakyThrows
    @Override
    public void download(Long fileId, HttpServletResponse response) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.isNull(sysFile)) {
            throw new SystemServiceException(ResultCode.exception("文件不存在"));
        }
        this.ossTemplate.downloadObject(SYSTEM_BUCKET_NAME, sysFile.getPath() + sysFile.getNewFileName(), sysFile.getOriginalFileName(), response);
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
            this.ossTemplate.removeObject(SYSTEM_BUCKET_NAME, sysFile.getPath() + sysFile.getNewFileName());
            this.removeById(sysFile.getId());
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }


}
