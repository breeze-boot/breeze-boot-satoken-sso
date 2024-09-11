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

package com.breeze.boot.modules.auth.model.form;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 注册客户端参数
 *
 * @author gaoweixuan
 * @since 2023-05-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "注册客户端参数")
public class RegisteredClientForm implements Serializable {

    private Long id;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端ID发表在
     */
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户秘密
     */
    private String clientSecret;

    /**
     * 客户秘密到期
     */
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 重定向uri
     */
    private Set<String> redirectUris;

    /**
     * 作用域
     */
    private Set<String> scopes;

    public String getClientSecret() {
        return StrUtil.isAllBlank(this.clientSecret) ? null : this.clientSecret;
    }

}
