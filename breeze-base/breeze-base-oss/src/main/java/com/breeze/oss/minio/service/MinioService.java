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

package com.breeze.oss.minio.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.breeze.oss.bo.FileBO;
import com.breeze.oss.minio.config.BreezeMinioProperties;
import com.breeze.core.enums.ResultCode;
import com.breeze.core.ex.SystemServiceException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * minio 请求服务
 *
 * @author gaoweixuan
 * @date 2022-11-14
 */
@Slf4j
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private BreezeMinioProperties minioProperties;

    /**
     * 创建bucket
     */
    public void createBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            log.error("Bucket 创建失败", e);
        }
    }

    /**
     * 上传文件添加图片水印
     *
     * @param file               文件
     * @param path               路径
     * @param fileName           文件名称
     * @param minioImgMarkConfig minio img配置
     * @return {@link FileBO}
     */
    public FileBO uploadFileByImgMark(MultipartFile file,
                                      String path,
                                      String fileName,
                                      MinioImgMarkConfig minioImgMarkConfig) {
        if (file.isEmpty()) {
            throw new SystemServiceException(ResultCode.exception("上传文件为空"));
        }
        BufferedImage read;
        Image image = null;
        try {
            InputStream inputStream = file.getInputStream();
            read = ImgUtil.read(inputStream);
            image = ImgUtil.pressImage(
                    read,
                    //水印图片
                    minioImgMarkConfig.getPressImg(),
                    //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                    0,
                    //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                    0,
                    0.8f);
        } catch (IOException e) {
            log.error("", e);
        }
        return this.uploadImg(path, file.getOriginalFilename(), fileName, (BufferedImage) image);
    }

    /**
     * 上传文件添加文本水印
     *
     * @param file               文件
     * @param path               路径
     * @param fileName           文件名称
     * @param minioImgMarkConfig minio img配置
     * @return {@link FileBO}
     */
    public FileBO uploadFileByTextMark(MultipartFile file,
                                       String path,
                                       String fileName,
                                       MinioImgMarkConfig minioImgMarkConfig) {

        if (file.isEmpty()) {
            throw new SystemServiceException(ResultCode.exception("文件不存在"));
        }
        BufferedImage read;
        Image image = null;
        try {
            InputStream inputStream = file.getInputStream();
            read = ImgUtil.read(inputStream);
            image = ImgUtil.pressText(
                    read,
                    minioImgMarkConfig.getPressText(),
                    Color.WHITE,
                    new Font("微软雅黑", Font.BOLD, 40),
                    0,
                    0,
                    0.8F);
        } catch (Exception e) {
            log.error("上传失败", e);
        }
        return this.uploadImg(path, path, fileName, (BufferedImage) image);
    }

    /**
     * 上传img
     *
     * @param path             路径
     * @param fileName         文件名称
     * @param image            图像
     * @param originalFilename 原始文件名
     * @return {@link FileBO}
     */
    public FileBO uploadImg(String path, String originalFilename, String fileName, BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpeg", os);
        } catch (IOException e) {
            log.error("文件转换 bs IO 异常", e);
        }
        return this.upload2Minio(new ByteArrayInputStream(os.toByteArray()), originalFilename, os.size(), path, fileName, "image/jpeg");
    }

    /**
     * 上传minio
     *
     * @param is               文件
     * @param size             大小
     * @param path             路径 ()
     * @param fileName         文件名称
     * @param contentType      内容类型
     * @param originalFilename 原始文件名
     * @return {@link FileBO}
     */
    public FileBO upload2Minio(InputStream is, String originalFilename, long size, String path, String fileName, String contentType) {
        this.createBucket(this.minioProperties.getBucketName());
        ObjectWriteResponse response = null;
        try {
            response = this.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.minioProperties.getBucketName())
                            .object(path + "/" + fileName)
                            .stream(is, size, -1)
                            .contentType(contentType)
                            .build());
        } catch (Exception e) {
            log.error("文件上传失败", e);
        }
        if (Objects.isNull(response)) {
            return null;
        }
        String savePath = path + "/" + fileName;
        String imageUrl = this.minioProperties.getNginxHost() + this.minioProperties.getBucketName() + '/' + savePath;
        log.info(" \n {} \n {}", savePath, imageUrl);
        return FileBO.builder().newFileName(fileName).path(savePath).originalFilename(originalFilename).contentType(contentType).build();
    }

    /**
     * 上传minio
     *
     * @param file     文件
     * @param path     路径
     * @param fileName 文件名称
     * @return {@link FileBO}
     */
    @SneakyThrows
    public FileBO upload2Minio(MultipartFile file, String path, String fileName) {
        return upload2Minio(file.getInputStream(), file.getOriginalFilename(), file.getSize(), path, fileName, file.getContentType());
    }

    /**
     * 桶是存在
     *
     * @param bucketName bucket名称
     * @return boolean
     */
    public Boolean bucketIsExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("Bucket 获取失败", e);
            return false;
        }
    }

    /**
     * 获取文件obj列表
     *
     * @param bucketName bucket名称
     * @return {@link List}<{@link Item}>
     */
    public List<Item> listObj(String bucketName) {
        if (StrUtil.isAllBlank(bucketName) || !bucketIsExists(bucketName)) {
            return Lists.newArrayList();
        }
        Iterable<Result<Item>> itemResult = getItemResult(bucketName);
        List<Item> items = Lists.newArrayList();
        try {
            for (Result<Item> result : itemResult) {
                items.add(result.get());
            }
        } catch (Exception e) {
            log.error("获取文件对象失败", e);
        }
        return items;
    }

    /**
     * 获取文件项
     *
     * @param bucketName bucket名称
     * @return {@link Iterable}<{@link Result}<{@link Item}>>
     */
    private Iterable<Result<Item>> getItemResult(String bucketName) {
        return this.minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除桶
     *
     * @param bucketName bucket名称
     * @return {@link Boolean}
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(
                    RemoveBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("删除Bucket失败", e);
            return false;
        }
        return true;
    }

    /**
     * 得到一个桶所有obj名字
     *
     * @param bucketName 桶名称
     * @return {@link List}<{@link String}>
     */
    public List<String> getOneBucketAllObjName(String bucketName) {
        boolean isExists = this.bucketIsExists(bucketName);
        if (!isExists) {
            throw new SystemServiceException(ResultCode.exception("文件不存在"));
        }
        List<String> objNameList = Lists.newArrayList();
        try {
            Iterable<Result<Item>> resultIterable = getItemResult(bucketName);
            resultIterable.forEach(result -> this.getItem(objNameList, result));
            return objNameList;
        } catch (Exception e) {
            log.error("", e);
        }
        return Lists.newArrayList();
    }

    /**
     * 获取文件项
     *
     * @param objNameList obj名单
     * @param resultItem  文件项
     */
    private void getItem(List<String> objNameList, Result<Item> resultItem) {
        try {
            Item item = resultItem.get();
            objNameList.add(item.objectName());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 查询所有桶
     *
     * @return {@link List}<{@link Bucket}>
     */
    public List<Bucket> listAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error("获取失败", e);
        }
        return Lists.newArrayList();
    }

    /**
     * 删除
     *
     * @param path 路径 + 名称
     * @return boolean
     */
    public boolean remove(String path) {
        try {
            this.minioClient.removeObject(RemoveObjectArgs.builder().bucket(this.minioProperties.getBucketName())
                    .object(path).build());
        } catch (Exception e) {
            log.error("删除失败", e);
            return false;
        }
        return true;
    }

    /**
     * 批量删除文件
     *
     * @param bucket      桶名称
     * @param objectNames 对象名称
     * @return boolean
     */
    public boolean removeBatch(String bucket, List<String> objectNames) {
        boolean bucketIsExists = bucketIsExists(bucket);
        if (!bucketIsExists) {
            return false;
        }
        try {
            List<DeleteObject> deleteObjList = objectNames.stream().map(DeleteObject::new).collect(Collectors.toList());
            this.minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucket).objects(deleteObjList).build());
            return true;
        } catch (Exception e) {
            log.error("删除失败", e);
        }
        return false;
    }

    /**
     * 预览图片
     *
     * @param path 路径 + 名称
     * @return {@link String}
     */
    public String previewImg(String path) {
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                .bucket(this.minioProperties.getBucketName())
                .object(path)
                .method(Method.GET)
                .build();
        try {
            return this.minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            log.error("获取资源失败", e);
        }
        return "";
    }

    /**
     * 下载
     *
     * @param path     路径
     * @param fileName 文件名称
     * @param response response
     */
    public void download(String path, String fileName, HttpServletResponse response) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(this.minioProperties.getBucketName())
                .object(path).build();
        try (GetObjectResponse objectResponse = this.minioClient.getObject(objectArgs)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("image/jpeg");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream os = response.getOutputStream();
            IoUtil.copy(objectResponse, os);
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }

}
