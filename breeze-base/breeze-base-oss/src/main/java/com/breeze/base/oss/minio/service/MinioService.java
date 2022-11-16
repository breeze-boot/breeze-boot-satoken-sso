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

package com.breeze.base.oss.minio.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;
import com.breeze.base.oss.dto.FileDTO;
import com.breeze.base.oss.minio.config.BreezeMinioProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
            e.printStackTrace();
        }
    }

    /**
     * 上传文件添加图片水印
     *
     * @param file               文件
     * @param path               路径
     * @param fileName           文件名称
     * @param minioImgMarkConfig minio img配置
     * @return {@link FileDTO}
     */
    public FileDTO uploadFileByImgMark(MultipartFile file,
                                       String path,
                                       String fileName,
                                       MinioImgMarkConfig minioImgMarkConfig) {
        if (file.isEmpty()) {
            throw new RuntimeException("文件为空");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.uploadImg(this.minioProperties.getBucketName(), path, fileName, (BufferedImage) image);
    }

    /**
     * 上传文件添加文本水印
     *
     * @param file               文件
     * @param path               路径
     * @param fileName           文件名称
     * @param minioImgMarkConfig minio img配置
     * @return {@link FileDTO}
     */
    public FileDTO uploadFileByTextMark(MultipartFile file,
                                        String path,
                                        String fileName,
                                        MinioImgMarkConfig minioImgMarkConfig) {

        if (file.isEmpty()) {
            throw new RuntimeException("文件为空");
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
            e.printStackTrace();
        }
        return this.uploadImg(this.minioProperties.getBucketName(), path, fileName, (BufferedImage) image);
    }

    /**
     * 上传img
     *
     * @param bucketName bucket名称
     * @param path       路径
     * @param fileName   文件名称
     * @param image      图像
     * @return {@link FileDTO}
     */
    public FileDTO uploadImg(String bucketName, String path, String fileName, BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpeg", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.upload2Minio(new ByteArrayInputStream(os.toByteArray()), os.size(), path, fileName);
    }

    /**
     * 上传minio
     *
     * @param is       文件
     * @param path     路径
     * @param fileName 文件名称
     * @param size     大小
     * @return {@link FileDTO}
     */
    public FileDTO upload2Minio(InputStream is, long size, String path, String fileName) {
        createBucket(this.minioProperties.getBucketName());
        try {
            this.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.minioProperties.getBucketName())
                            .object(path + fileName)
                            .stream(is, size, -1)
                            //文件类型 已经全部转为JPG
                            .contentType("image/jpeg")
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String savePath = this.minioProperties.getBucketName() + path + fileName;
        String imageUrl = this.minioProperties.getNginxHost() + savePath;
        log.info(" \n {}", imageUrl);
        return FileDTO.builder().newFileName(fileName).path(savePath).build();

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            throw new RuntimeException("不存在");
        }
        List<String> objNameList = Lists.newArrayList();
        try {
            Iterable<Result<Item>> resultIterable = getItemResult(bucketName);
            resultIterable.forEach(result -> this.getItem(objNameList, result));
            return objNameList;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    /**
     * 删除
     *
     * @param fileName 文件名称
     * @return boolean
     */
    public boolean remove(String fileName) {
        try {
            this.minioClient.removeObject(RemoveObjectArgs.builder().bucket(this.minioProperties.getBucketName()).object(fileName).build());
        } catch (Exception e) {
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
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 预览图片
     *
     * @param fileName 文件名称
     * @return {@link String}
     */
    public String previewImg(String fileName) {
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                .bucket("breeze")
                .object(fileName)
                .method(Method.GET)
                .build();
        try {
            return this.minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param response response
     */
    public void download(String fileName, HttpServletResponse response) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(this.minioProperties.getBucketName())
                .object(fileName).build();
        try (GetObjectResponse objectResponse = this.minioClient.getObject(objectArgs);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ServletOutputStream stream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = objectResponse.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            byte[] bytes = os.toByteArray();
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            stream.write(bytes);
            stream.flush();
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
