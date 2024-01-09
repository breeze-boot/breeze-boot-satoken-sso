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

package com.breeze.boot.oss.operation;

import cn.hutool.core.io.IoUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.breeze.boot.core.enums.ContentType;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * 操作系统模板
 *
 * @author gaoweixuan
 * @since 2023-04-18
 */
@Slf4j
public class OssTemplate implements OssOperations {

    /**
     * amazon s3
     */
    @Setter
    private AmazonS3 amazonS3;

    /**
     * 创建桶
     *
     * @param bucketName bucket名称
     */
    @Override
    public void createBucket(String bucketName) {
        if (this.amazonS3.doesBucketExistV2(bucketName)) {
            log.debug("[桶已存在无需创建]：{}", bucketName);
            return;
        }
        this.amazonS3.createBucket(bucketName);
    }

    /**
     * 删除桶
     *
     * @param bucketName bucket名称
     */
    @Override
    public void removeBucket(String bucketName) {
        if (!this.amazonS3.doesBucketExistV2(bucketName)) {
            log.debug("[桶不存在无需删除]：{}", bucketName);
            return;
        }
        this.amazonS3.deleteBucket(bucketName);
    }

    /**
     * 列出所有桶
     *
     * @return {@link List}<{@link Bucket}>
     */
    @Override
    public List<Bucket> listAllBuckets() {
        return this.amazonS3.listBuckets();
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  对象名称
     * @param stream      流
     * @param contentType 内容类型
     * @return {@link PutObjectResult}
     */
    @Override
    @SneakyThrows
    public PutObjectResult putObject(String bucketName, String objectName, InputStream stream, String contentType) {
        return this.putObject(bucketName, objectName, stream, stream.available(), contentType);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param file       文件
     */
    @Override
    public void putObject(String bucketName, String objectName, File file) {
        this.amazonS3.putObject(bucketName, objectName, file);
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  对象名称
     * @param stream      流
     * @param contextType 上下文类型
     * @return {@link PutObjectResult}
     */
    protected PutObjectResult putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) {
        byte[] bytes;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        ByteArrayInputStream bs = null;
        try {
            bytes = IOUtils.toByteArray(stream);
            objectMetadata.setContentLength(size);
            objectMetadata.setContentType(contextType);
            bs = new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            log.error("上传失败", e);
        }
        // 上传
        return this.amazonS3.putObject(bucketName, objectName, bs, objectMetadata);
    }

    /**
     * 得到对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @return {@link S3Object}
     */
    @Override
    public S3Object getObject(String bucketName, String objectName) {
        return amazonS3.getObject(bucketName, objectName);
    }

    /**
     * 得到对象url
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param expires    到期
     * @return {@link String}
     */
    @Override
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        LocalDateTime now = LocalDateTime.now().plusDays(expires);
        ZonedDateTime zdt = now.atZone(ZoneId.systemDefault());
        Date expirationDate = Date.from(zdt.toInstant());
        URL url = amazonS3.generatePresignedUrl(bucketName, objectName, expirationDate);
        return url.toString();
    }

    /**
     * 删除对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     */
    @Override
    public void removeObject(String bucketName, String objectName) {
        this.amazonS3.deleteObject(bucketName, objectName);
    }

    /**
     * 下载
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param fileName   原始文件名
     * @param response   响应
     */
    @Override
    public void downloadObject(String bucketName, String objectName, String fileName, HttpServletResponse response) {
        try (S3Object object = amazonS3.getObject(bucketName, objectName)) {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(ContentType.getContentType(objectName));
            response.setHeader("Content-disposition", "attachment;filename*=utf-8" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
            OutputStream os = response.getOutputStream();
            IoUtil.copy(object.getObjectContent(), os);
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }


}
