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

package com.breeze.boot.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ContentType;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.SystemServiceException;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.local.operation.LocalStorageTemplate;
import com.breeze.boot.modules.system.domain.SysFile;
import com.breeze.boot.modules.system.domain.params.FileParam;
import com.breeze.boot.modules.system.domain.query.FileQuery;
import com.breeze.boot.modules.system.mapper.SysFileMapper;
import com.breeze.boot.modules.system.service.SysFileService;
import com.breeze.boot.oss.operation.MinioOssTemplate;
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
 * @since 2022-09-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    /**
     * oss存储服务
     */
    private final MinioOssTemplate ossTemplate;

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
                .like(StrUtil.isAllNotBlank(fileQuery.getTitle()), SysFile::getTitle, fileQuery.getTitle())
                .like(StrUtil.isAllNotBlank(fileQuery.getBizType()), SysFile::getBizType, fileQuery.getBizType())
                .like(StrUtil.isAllNotBlank(fileQuery.getCreateName()), SysFile::getCreateName, fileQuery.getCreateName())
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
        Map<String, Object> resultMap = Maps.newHashMap();
        MultipartFile file = fileParam.getFile();
        LocalDate now = LocalDate.now();
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "文件名不能为空");
        String objectName = String.valueOf(now.getYear()) + now.getMonthOfYear() + now.getDayOfMonth() + IdUtil.simpleUUID() + "/" + originalFilename;

        try {
            this.ossTemplate.createBucket(SYSTEM_BUCKET_NAME);
            this.ossTemplate.putObject(SYSTEM_BUCKET_NAME, objectName, file.getInputStream(), ContentType.getContentType(originalFilename));

            SysFile sysFile = SysFile.builder()
                    .title(fileParam.getTitle())
                    .fileName(originalFilename)
                    .objectName(objectName)
                    .path(objectName)
                    .bizId(fileParam.getBizId())
                    .bizType(fileParam.getBizType())
                    .fileFormat(originalFilename.substring(originalFilename.indexOf(".")))
                    .contentType(request.getContentType())
                    .bucket(SYSTEM_BUCKET_NAME)
                    .storeType(fileParam.getStoreType())
                    .build();
            this.save(sysFile);
            resultMap.put("url", this.ossTemplate.getObjectURL(SYSTEM_BUCKET_NAME, objectName, 2));
            resultMap.put("fileId", sysFile.getId());
        } catch (Exception ex) {
            log.error("[文件上传失败]", ex);
        }
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
        String objectName = String.valueOf(now.getYear()) + now.getMonthOfYear() + now.getDayOfMonth() + IdUtil.simpleUUID() + "/" + originalFilename;
        Assert.notNull(originalFilename, "文件名不能为空");

        String path = this.localStorageTemplate.uploadFile(fileParam.getFile(), objectName, originalFilename);
        log.debug("[上传的文件路径]：{}", path);
        SysFile sysFile = SysFile.builder()
                .title(fileParam.getTitle())
                .fileName(originalFilename)
                .objectName(objectName)
                .bizId(fileParam.getBizId())
                .bizType(fileParam.getBizType())
                .fileFormat(originalFilename.substring(originalFilename.indexOf(".")))
                .contentType(request.getContentType())
                .bucket(SYSTEM_BUCKET_NAME)
                .storeType(fileParam.getStoreType())
                .build();
        this.save(sysFile);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("url", this.localStorageTemplate.previewImg(sysFile.getPath(), originalFilename));
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
        return this.ossTemplate.getObjectURL(SYSTEM_BUCKET_NAME, sysFile.getPath(), 2);
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
            throw new SystemServiceException(ResultCode.FILE_NOT_FOUND);
        }
        this.ossTemplate.downloadObject(SYSTEM_BUCKET_NAME, sysFile.getPath(), sysFile.getFileName(), response);
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
            this.ossTemplate.removeObject(SYSTEM_BUCKET_NAME, sysFile.getPath());
            this.removeById(sysFile.getId());
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }


}
