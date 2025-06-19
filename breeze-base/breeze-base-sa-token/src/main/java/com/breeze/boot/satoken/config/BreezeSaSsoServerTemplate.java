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

package com.breeze.boot.satoken.config;

import cn.dev33.satoken.config.SaSignConfig;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.sign.SaSignTemplate;
import cn.dev33.satoken.sso.template.SaSsoServerTemplate;
import com.breeze.boot.satoken.config.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.satoken.model.BaseSysRegisteredClient;
import com.breeze.boot.satoken.spt.IClientService;
import lombok.RequiredArgsConstructor;

import static com.breeze.boot.core.enums.ResultCode.CLIENT_IS_NOT_EXISTS;

/**
 * 自定义 SaSsoServerTemplate 子类
 *
 * @author gaoweixuan
 * @since 2024/09/11
 */
@RequiredArgsConstructor
public class BreezeSaSsoServerTemplate extends SaSsoServerTemplate {

    private final IClientService clientService;
    private final AesSecretProperties aesSecretProperties;

    /**
     * 重写 [获取授权回调地址] 方法，改为从数据库中读取
     */
    @Override
    public String getAllowUrl() {
        return this.clientService.getAllRedirectUris();
    }

    @Override
    public SaSignTemplate getSignTemplate(String client) {
        BaseSysRegisteredClient registeredClient = this.clientService.getByClientId(client);
        AssertUtil.isNotNull(registeredClient, CLIENT_IS_NOT_EXISTS);
        // 从数据库中获取
        return new SaSignTemplate(new SaSignConfig(SaSecureUtil.aesDecrypt(this.aesSecretProperties.getAesSecret(), registeredClient.getClientSecret())));
    }
}
