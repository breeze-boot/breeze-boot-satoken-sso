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

package com.breeze.boot.modules.auth.model.query;

import com.breeze.boot.core.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 系统sso客户端查询参数
 *
 * @author gaoweixuan
 * @since 2024-09-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统sso客户端查询参数")
public class SsoClientQuery extends PageQuery {

    /**
     * 客户端编码
     */
    @Schema(description = "客户端编码")
    private String clientCode;

    /**
     * 客户端重定向地址
     */
    @Schema(description = "客户端重定向地址")
    private String redirect;

    /**
     * 客户端重定向后跳转地址
     */
    @Schema(description = "客户端重定向后跳转地址")
    private String back;


}
