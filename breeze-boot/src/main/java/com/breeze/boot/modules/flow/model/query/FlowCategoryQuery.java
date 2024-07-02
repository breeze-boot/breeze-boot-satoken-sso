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

package com.breeze.boot.modules.flow.model.query;


import com.breeze.boot.core.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 流程分类查询参数
 *
 * @author gaoweixuan
 * @since 2023-03-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程分类查询参数")
public class FlowCategoryQuery extends PageQuery {

    /**
     * 流程分类编码
     */
    @Size(max = 64, message = "编码长度不能超过64")
    @Length(max = 64, message = "编码长度不能超过64")
    @NotBlank(message = "分类编码不能为空")
    @Schema(description = "流程分类编码")
    private String categoryCode;

    /**
     * 流程分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 128, message = "名称长度不能超过128")
    @Length(max = 128, message = "名称长度不能超过128")
    @Schema(description = "流程分类名称")
    private String categoryName;

}
