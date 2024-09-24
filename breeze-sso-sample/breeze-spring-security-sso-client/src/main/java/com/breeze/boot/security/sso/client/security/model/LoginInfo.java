/*
 * Copyright (c) 2024, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.sso.client.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 登录信息
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Data
@Builder
@Schema(description = "登录对象")
public class LoginInfo {

    @Schema(description = "token")
    private String accessToken;

    @Schema(description = "token类型", example = "Bearer")
    private String tokenType;

    @Schema(description = "过期时间 (单位：毫秒)")
    private Long expires;

}
