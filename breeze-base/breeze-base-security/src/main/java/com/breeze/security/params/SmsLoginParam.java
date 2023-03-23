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

package com.breeze.security.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 短信登录参数
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "短信登录参数")
public class SmsLoginParam {

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

}
