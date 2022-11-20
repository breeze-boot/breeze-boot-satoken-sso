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

package com.breeze.base.oss;

import com.breeze.base.oss.dto.FileBO;
import com.breeze.base.oss.local.service.LocalFileService;
import com.breeze.base.oss.minio.service.MinioService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * minio 请求服务
 *
 * @author gaoweixuan
 * @date 2022-11-20
 */
@Slf4j
public class OssStoreService {

    @Autowired
    private LocalFileService localFileService;

    @Autowired
    private MinioService minioService;

    /**
     * 文件下载
     *
     * @param response response
     */
    public void download(Integer ossStyle, String fileName, HttpServletResponse response) {
        switch (ossStyle) {
            case 0:
                if (Objects.isNull(this.localFileService)) {
                    throw new RuntimeException("未配置本地存储方式");
                }
                this.localFileService.download(fileName, response);
                break;
            case 1:
                if (Objects.isNull(this.minioService)) {
                    throw new RuntimeException("未配置minio存储方式");
                }
                this.minioService.download(fileName, response);
                break;
            default:
                log.error("存储类型错误");
        }
    }

    /**
     * 文件上传
     */
    @SneakyThrows
    public Optional<FileBO> upload(Integer ossStyle, MultipartFile file, String path, String newFileName) {
        Optional<FileBO> optionalFileBO;
        switch (ossStyle) {
            case 0:
                optionalFileBO = this.localFileService.uploadFile(file);
                break;
            case 1:
                optionalFileBO = this.minioService.upload2Minio(file.getInputStream(), file.getSize(), path, newFileName, file.getContentType());
                break;
            default:
                optionalFileBO = Optional.empty();
                log.error("存储类型错误");
        }
        return optionalFileBO;
    }

    /**
     * 图片预览
     *
     * @param fileName 文件名称
     * @return
     */
    public Optional<String> preview(Integer ossStyle, String fileName) {
        Optional<String> preView;
        switch (ossStyle) {
            case 0:
                if (Objects.isNull(this.localFileService)) {
                    throw new RuntimeException("未配置本地存储方式");
                }
                preView = this.localFileService.previewImg(fileName);
                break;
            case 1:
                if (Objects.isNull(this.minioService)) {
                    throw new RuntimeException("未配置minio存储方式");
                }
                preView = this.minioService.previewImg(fileName);
                break;
            default:
                preView = Optional.empty();
                log.error("存储类型错误");
        }
        return preView;
    }

    /**
     * 文件删除
     *
     * @param fileName 文件名称
     * @param response response
     */
    public Boolean remove(String fileName, HttpServletResponse response) {
        return true;
    }

}
