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

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.sign.config.SaSignConfig;
import cn.dev33.satoken.sign.template.SaSignTemplate;
import cn.dev33.satoken.sso.config.SaSsoClientModel;
import cn.dev33.satoken.sso.error.SaSsoErrorCode;
import cn.dev33.satoken.sso.exception.SaSsoException;
import cn.dev33.satoken.sso.template.SaSsoServerTemplate;
import cn.dev33.satoken.util.SaFoxUtil;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.satoken.config.propertise.AesSecretProperties;
import com.breeze.boot.satoken.model.BaseSysRegisteredClient;
import com.breeze.boot.satoken.spt.IClientService;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
     * 校验配置的 AllowUrl 是否合规，如果不合规则抛出异常
     *
     * @param allowUrlList 待校验的 allow-url 地址列表
     */
    public void checkAllowUrlList(List<String> allowUrlList) {
        checkAllowUrlListStaticMethod(allowUrlList);

        String allRedirectUris = this.clientService.getAllRedirectUris();
        if (allRedirectUris == null || allRedirectUris.trim().isEmpty()) {
            return; // 空值直接返回，避免后续异常
        }

        for (String url : allRedirectUris.split(",")) {
            String trimmedUrl = url.trim();
            if (!isValidUrl(trimmedUrl)) {
                throw new IllegalArgumentException("发现非法 RedirectUri: " + trimmedUrl);
            }
        }
    }

    /**
     * 校验 URL 是否合法（示例简单校验）
     */
    private boolean isValidUrl(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SaSignTemplate getSignTemplate(String client) {
        BaseSysRegisteredClient registeredClient = this.clientService.getByClientId(client);
        AssertUtil.isNotNull(registeredClient, CLIENT_IS_NOT_EXISTS);
        // 从数据库中获取
        String secretKey = SaSecureUtil.aesDecrypt(this.aesSecretProperties.getAesSecret(), registeredClient.getClientSecret());
        SaSignConfig signConfig = new SaSignConfig(secretKey);
        return new SaSignTemplate(signConfig);
    }

    @Override
    public SaSsoClientModel getClientNotNull(String client) {
        if (SaFoxUtil.isEmpty(client)) {
            if (getConfigOfAllowAnonClient()) {
                return getAnonClient();
            } else {
                throw new SaSsoException("client 标识不可为空");
            }
        } else {
            BaseSysRegisteredClient registeredClient = this.clientService.getByClientId(client);
            if (registeredClient == null) {
                throw new SaSsoException("未能获取应用信息，client=" + client).setCode(SaSsoErrorCode.CODE_30013);
            }
            return new SaSsoClientModel()
                    .setClient(registeredClient.getClientId())
                    .setSecretKey(registeredClient.getClientSecret())
                    .setAllow(registeredClient.getRedirectUris().split(","))
                    .setIsSlo(true)
                    .setIsPush(true)
                    ;
        }
    }
}
