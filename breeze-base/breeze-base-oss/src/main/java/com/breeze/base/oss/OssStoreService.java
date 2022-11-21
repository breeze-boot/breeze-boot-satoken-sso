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
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.ex.SystemServiceException;
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
     * 下载
     *
     * @param response response
     * @param ossStyle 存储方式
     * @param fileName 文件名称
     * @param path     路径
     */
    public void download(Integer ossStyle, String fileName, String path, HttpServletResponse response) {
        switch (ossStyle) {
            case 0:
                beanIsExists(Objects.isNull(this.localFileService), "未配置本地存储方式");
                this.localFileService.download(path, response);
                break;
            case 1:
                beanIsExists(Objects.isNull(this.minioService), "未配置minio存储方式");
                this.minioService.download(fileName, path, response);
                break;
            default:
                log.error("存储类型错误");
        }
    }

    /**
     * 上传
     *
     * @param ossStyle    存储方式
     * @param file        文件
     * @param path        路径
     * @param newFileName 新文件名字
     * @return {@link Optional}<{@link FileBO}>
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
     * @param ossStyle 存储方式
     * @return {@link Optional}<{@link String}>
     */
    public String preview(Integer ossStyle, String fileName) {
        String preView = "";
        switch (ossStyle) {
            case 0:
                beanIsExists(Objects.isNull(this.localFileService), "未配置本地存储方式");
                preView = this.localFileService.previewImg(fileName);
                break;
            case 1:
                beanIsExists(Objects.isNull(this.minioService), "未配置minio存储方式");
                preView = this.minioService.previewImg(fileName);
                break;
            default:
                log.error("存储类型错误");
        }
        return preView;
    }

    /**
     * bean是否存在
     *
     * @param aNull 一个空
     * @param msg   信息
     */
    private void beanIsExists(boolean aNull, String msg) {
        if (aNull) {
            throw new SystemServiceException(ResultCode.exception(msg));
        }
    }

    /**
     * 删除
     *
     * @param ossStyle 存储方式
     * @param fileName 文件名称
     * @return {@link Boolean}
     */
    public Boolean remove(Integer ossStyle, String fileName) {
        return true;
    }

}
