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

package com.breeze.boot.core.constants;

/**
 * 缓存常量
 *
 * @author gaoweixuan
 * @since 2021/10/1
 */
public class CacheConstants {

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "auth:user_info:";

    /**
     * 验证电话号码
     */
    public static final String VALIDATE_PHONE_CODE = "auth:validate_phone_code:";


    /**
     * 验证电子邮件代码
     */
    public static final String VALIDATE_EMAIL_CODE = "auth:validate_email_code:";

    /**
     * auth 缓存前缀
     */
    public static String prefix = "auth:";

    /**
     * 授权信息
     */
    public static final String AUTHORIZATION = prefix + "authorization:";

    /**
     * 使用 state 查询对应的 authorizationId
     */
    public static final String STATE_OAUTH = prefix + "state:oauth:";

    /**
     * 使用 code 查询对应的 authorizationId
     */
    public static final String CODE_OAUTH = prefix + "code:oauth:";

    /**
     * 使用 access_code 查询对应的 authorizationId
     */
    public static final String ACCESS_OAUTH = prefix + "access:oauth:";

    /**
     * 使用 refresh_code 查询对应的 authorizationId
     */
    public static final String REFRESH_OAUTH = prefix + "refresh:oauth:";

    /**
     * 使用 clientId 查询对应的 authorizationId
     */
    public static final String CLIENT_OAUTH = prefix + "client:oauth:";

    /**
     * 字典目录
     * 键：auth:correlations:authorizationId; 值: redis存放【state,refresh_code,clientId,access_code,code】的key
     * 由此可以获取到此次登录一共存放了多少key
     */
    public static final String CORRELATIONS = prefix + "correlations:";

    /**
     * 授权同意信息
     */
    public static final String CONSENT = prefix + "consent:";

    /**
     * 行权限缓存
     */
    public static final String ROW_PERMISSION = "auth:row:permission:";

}
