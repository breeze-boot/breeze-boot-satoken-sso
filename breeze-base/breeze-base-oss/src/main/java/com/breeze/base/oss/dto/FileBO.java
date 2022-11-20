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

package com.breeze.base.oss.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传BO
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "文件上传BO")
public class FileBO {

    /**
     * 文件
     */
    @Schema(description = "文件流")
    private MultipartFile file;

    /**
     * 原文件名称
     */
    @Schema(description = "原文件名称", hidden = true)
    private String originalFilename;

    /**
     * 新文件名字
     */
    @Schema(description = "新的文件名", hidden = true)
    private String newFileName;

    /**
     * 内容类型
     */
    @Schema(description = "文件类型", hidden = true)
    private String contentType;

    /**
     * 路径
     */
    @Schema(description = "文件存放路径", hidden = true)
    private String path;

}
