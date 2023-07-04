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

package com.breeze.boot.oss.s3.operation;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * OSS操作
 *
 * @author gaoweixuan
 * @date 2023-04-18
 */
public interface OssOperations {

    /**
     * 创建桶
     *
     * @param bucketName bucket名称
     */
    void createBucket(String bucketName);

    /**
     * 删除桶
     *
     * @param bucketName bucket名称
     */
    void removeBucket(String bucketName);

    /**
     * 列出所有桶
     *
     * @return {@link List}<{@link Bucket}>
     */
    List<Bucket> listAllBuckets();

    /**
     * 上传对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param stream     文件流
     * @return
     * @throws Exception 异常
     */
    PutObjectResult putObject(String bucketName, String objectName, InputStream stream, String contentType);

    /**
     * 上传对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param file       文件
     * @throws Exception 异常
     */
    void putObject(String bucketName, String objectName, File file);

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return S3Object
     */
    S3Object getObject(String bucketName, String objectName);

    /**
     * 获取对象的url
     *
     * @param bucketName
     * @param objectName
     * @param expires
     * @return
     */
    String getObjectURL(String bucketName, String objectName, Integer expires);

    /**
     * 使用bucketName objectName删除对象
     *
     * @param bucketName
     * @param objectName
     * @throws Exception
     */
    void removeObject(String bucketName, String objectName);

    /**
     * 下载
     *
     * @param bucketName
     * @param objectName
     * @param response
     * @param originalFilename
     * @return
     * @throws Exception
     */
    void downloadObject(String bucketName, String objectName, String response, HttpServletResponse originalFilename);

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param stream     流
     */
    @SneakyThrows
    default void putObject(String bucketName, String objectName, InputStream stream) {
        this.putObject(bucketName, objectName, stream, "application/octet-stream");
    }

}
