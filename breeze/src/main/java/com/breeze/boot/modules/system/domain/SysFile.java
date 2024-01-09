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

package com.breeze.boot.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.base.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 系统文件实体
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file")
@Schema(description = "系统文件实体")
public class SysFile extends BaseModel<SysFile> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    private String fileName;

    /**
     * 文件扩展名
     */
    @Schema(description = "文件扩展名")
    private String fileFormat;

    /**
     * 上传格式
     */
    @Schema(description = "上传格式")
    private String contentType;

    /**
     * 桶
     */
    @Schema(description = "桶")
    private String bucket;

    /**
     * 对象名称
     */
    @Schema(description = "对象名称")
    private String objectName;

    /**
     * 存储的路径
     */
    @Schema(description = "存储的路径")
    private String path;

    /**
     * 业务ID
     */
    @Schema(description = "业务ID")
    private Long bizId;

    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private String bizType;

    /**
     * 存储方式
     */
    @Schema(description = "存储方式 0 本地 1 minio")
    private Integer storeType;

}
