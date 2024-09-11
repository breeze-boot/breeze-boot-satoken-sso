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

package com.breeze.boot.sso.spt;

import com.breeze.boot.sso.model.BaseSysRegisteredClient;

/**
 * 客户端服务接口
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
public interface IClientService {

    /**
     * 通过客户端ID获取客户端信息
     *
     * @param clientId 客户端Id
     * @return {@link BaseSysRegisteredClient}
     */
    BaseSysRegisteredClient getByClientId(String clientId);

    /**
     * 获取所有客户端回调地址
     *
     * @return {@link String }
     */
    String getAllRedirectUris();

}
