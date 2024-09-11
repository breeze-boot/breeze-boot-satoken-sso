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

package com.breeze.boot.sso.config;

import cn.dev33.satoken.config.SaSignConfig;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.sign.SaSignTemplate;
import cn.dev33.satoken.sso.template.SaSsoServerTemplate;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.jackson.propertise.AesSecretProperties;
import com.breeze.boot.sso.model.BaseSysRegisteredClient;
import com.breeze.boot.sso.spt.IClientService;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

/**
 * 自定义 SaSsoServerTemplate 子类
 *
 * @author gaoweixuan
 * @since 2024/09/11
 */
@RequiredArgsConstructor
public class BreezeSaSsoServerTemplate extends SaSsoServerTemplate {

    private final Supplier<IClientService> clientServiceSupplier;
    private final Supplier<AesSecretProperties> aesSecretPropertiesSupplier;

    /**
     * 重写 [获取授权回调地址] 方法，改为从数据库中读取
     */
    @Override
    public String getAllowUrl() {
        return clientServiceSupplier.get().getAllRedirectUris();
    }

    @Override
    public SaSignTemplate getSignTemplate(String client) {
        BaseSysRegisteredClient registeredClient = clientServiceSupplier.get().getByClientId(client);
        if (registeredClient == null) {
            throw new BreezeBizException(ResultCode.CLIENT_IS_NOT_EXISTS);
        }
        // 从数据库中获取
        return new SaSignTemplate(new SaSignConfig(SaSecureUtil.aesDecrypt(aesSecretPropertiesSupplier.get().getAesSecret(), registeredClient.getClientSecret())));
    }
}
