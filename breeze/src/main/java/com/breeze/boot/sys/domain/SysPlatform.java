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

package com.breeze.boot.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.core.base.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 系统平台实体
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_platform")
@Schema(description = "系统平台实体")
public class SysPlatform extends BaseModel<SysPlatform> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 平台编码
     */
    @NotBlank(message = "平台编码不可为空")
    @Schema(description = "平台编码")
    private String platformCode;

    /**
     * 平台名称
     */
    @NotBlank(message = "平台名称不可为空")
    @Schema(description = "平台名称")
    private String platformName;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

}
