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

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * 令牌设置
 *
 * @author gaoweixuan
 * @since 2023/05/15
 */
@Data
public class TokenSettingsVO {
    /**
     * 标识牌签名算法
     */
    @JsonAlias({"settings.token.id-token-signature-algorithm", "idTokenSignatureAlgorithm"}) // 反序列化的别名
    private String idTokenSignatureAlgorithm;
    /**
     * 访问令牌格式
     */
    @JsonAlias({"settings.token.access-token-format", "accessTokenFormat"})
    private String accessTokenFormat;
    /**
     * 授权代码时间
     */
    @JsonAlias({"settings.token.authorization-code-time-to-live", "authorizationCodeTimeToLive"})
    private Integer authorizationCodeTimeToLive;
    /**
     * 访问令牌时间有效时间
     */
    @JsonAlias({"settings.token.access-token-time-to-live", "accessTokenTimeToLive"})
    private Integer accessTokenTimeToLive;
    /**
     * 刷新令牌有效时间
     */
    @JsonAlias({"settings.token.refresh-token-time-to-live", "refreshTokenTimeToLive"})
    private Integer refreshTokenTimeToLive;
    /**
     * 是否使用刷新令牌
     */
    @JsonAlias({"settings.token.reuse-refresh-tokens", "reuseRefreshTokens"})
    private boolean reuseRefreshTokens;
}
