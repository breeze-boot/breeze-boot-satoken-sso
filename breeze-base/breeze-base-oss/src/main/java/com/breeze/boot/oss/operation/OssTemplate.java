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
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
@Setter
@Slf4j
public class OssTemplate implements OssOperations {

    /**
     * amazon s3
     */
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
     * 获取对象url
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
     * 删除指定存储桶中的对象
     * <p>
     *  本方法通过调用Amazon S3的deleteObject方法，实现对指定存储桶中对象的删除操作
     * @param bucketName 指定要从中删除对象的存储桶的名称
     * @param objectName 指定要删除的对象的名称
     */
    @Override
    public void removeObject(String bucketName, String objectName) {
        this.amazonS3.deleteObject(bucketName, objectName); // 直接调用Amazon S3服务的客户端实例，执行删除操作
    }


    /**
     * 从指定存储桶下载对象，并将其作为附件发送到客户端
     *
     * @param bucketName 对象所在的S3存储桶名称
     * @param objectName 需要下载的对象在存储桶中的名称
     * @param fileName   下载文件的原始名称，将用于生成HTTP响应中Content-disposition头信息
     * @param response   HTTP响应对象，通过此对象将对象内容输出至客户端，触发浏览器下载操作
     */
    @Override
    public void downloadObject(String bucketName, String objectName, String fileName, HttpServletResponse response) {
        try (S3Object object = amazonS3.getObject(bucketName, objectName)) {
            // 设置响应的字符编码和内容类型为UTF-8字节流
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/octet-stream; charset=UTF-8");

            // 对文件名进行URL编码并确保安全，替换掉非字母、数字、点、破折号及下划线的字符
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            String safeFileName = encodeSafeFileName(encodedFileName);

            // 设置Content-disposition头信息以触发浏览器下载，使用安全文件名为下载后的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + safeFileName);

            // 将对象内容写入HTTP响应的输出流，实现文件下载
            OutputStream os = response.getOutputStream();
            IoUtil.copy(object.getObjectContent(), os);
        } catch (Exception e) {
            // 记录无法下载文件时的异常情况
            log.error("无法获取到要下载的文件资源: {}", e.getMessage());
        }
    }

    /**
     * 编码文件名，移除不安全的字符，确保文件名在不同操作系统和浏览器下都能正确处理。
     *
     * @param fileName 待编码的原始文件名
     * @return 安全的、经过编码处理的文件名
     */
    private String encodeSafeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.\\-\\_]", "_");
    }

}
