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

package com.breeze.base.oss.local.service;

import cn.hutool.core.lang.UUID;
import com.breeze.base.oss.dto.FileBO;
import com.breeze.base.oss.local.config.LocalProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * 本地上传 工具
 *
 * @author gaoweixuan
 * @date 2022-11-14
 */
@Slf4j
public class LocalFileService {


    @Autowired
    private LocalProperties localProperties;

    /**
     * 得到扩展
     *
     * @param originalFileName 原始文件名字
     * @return {@link String}
     */
    public String getExtension(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 获得新文件名
     *
     * @param exName 文件类型
     * @return {@link String}
     */
    public String getNewFileName(String exName) {
        return UUID.randomUUID() + exName;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link FileBO}
     */
    public Optional<FileBO> uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("文件错误");
        }
        //文件大小
        long size = file.getSize();
        //文件真实名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String extension = this.getExtension(originalFilename);
        //文件类型
        String contentType = file.getContentType();
        String newFileName = getNewFileName(extension);
        File newFilePath = new File(localProperties.getPath() + File.separator + newFileName);
        try {
            if (log.isInfoEnabled()) {
                log.info("文件名： [{}]， 类型: [{}]，文件大小 [{}]", originalFilename, contentType, size);
            }
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFilePath));
        } catch (IOException e) {
            log.error("上传失败 {}", e.getMessage());
            e.printStackTrace();
        }
        return Optional.ofNullable(FileBO.builder()
                .originalFilename(originalFilename)
                .newFileName(newFileName)
                .path(newFilePath.getPath())
                .contentType(contentType)
                .build());
    }

    public void download(String newFileName, HttpServletResponse response) {

    }

    public Optional<String> previewImg(String newFileName) {
        return Optional.empty();
    }
}
