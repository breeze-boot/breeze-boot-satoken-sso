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
public class CoreConstants {

    /**
     * 列
     */
    public static final String TENANT_ID_COLUMN = "tenant_id";

    /**
     * 参数
     */
    public static final String X_TENANT_ID = "X-Tenant-Id";

    public static final Long ROOT = 1111111111111111111L;
    /**
     * 成功标记
     */
    public static final String SUCCESS = "0000";

    /**
     * 失败标记
     */
    public static final String FAIL = "0001";

    public final static String USER_INFO = "user_info";
    public static final String USER_TYPE = "userPrincipal";

}
