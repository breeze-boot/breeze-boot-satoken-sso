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

package com.breeze.boot.modules.system.domain.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文件上传参数
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "文件上传参数")
public class FileParam {

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
     * 文件
     */
    @Schema(description = "文件流")
    @NotNull(message = "文件不能为空")
    private MultipartFile file;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 上传方式
     */
    @Schema(description = "上传方式")
    @NotNull(message = "上传方式不能为空")
    private Integer storeType;

}
