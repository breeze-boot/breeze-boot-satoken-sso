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

package com.breeze.boot.security.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 邮箱登录参数
 *
 * @author gaoweixuan
 * @date 2022-08-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "邮箱登录参数")
public class EmailLoginParam {

    /**
     * 电子邮箱账号
     */
    @Schema(description = "已绑定用户的邮箱账号")
    private String email;

    /**
     * 代码
     */
    @Schema(description = "邮箱收到的验证码")
    private String code;

}
