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

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.config.SaSsoClientConfig;
import com.breeze.boot.sso.spt.IUserDetailService;
import com.dtflys.forest.Forest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.function.Supplier;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

/**
 * sso客户端配置
 *
 * @author gaoweixuan
 * @since 2024/09/11
 */
@Slf4j
@RequiredArgsConstructor
public class SsoClientConfigure {

    private final static String BCRYPT = "{bcrypt}";

    private final Supplier<IUserDetailService> userDetailServiceSupplier;

    /**
     * 配置SSO相关参数
     *
     * @param ssoClient sso客户端
     */
    @Autowired
    private void configSsoClient(SaSsoClientConfig ssoClient) {

        // 配置Http请求处理器
        ssoClient.sendHttp = url -> {
            System.out.println("------ 发起请求：" + url);
            String resStr = Forest.get(url + "&" + X_TENANT_ID + "=" + Optional.ofNullable(SaHolder.getRequest().getHeader(X_TENANT_ID)).orElse("")).executeAsString();
            System.out.println("------ 请求结果：" + resStr);
            return resStr;
        };

    }

}
