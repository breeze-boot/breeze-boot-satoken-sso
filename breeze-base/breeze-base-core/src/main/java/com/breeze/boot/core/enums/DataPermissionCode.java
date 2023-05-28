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

package com.breeze.boot.core.enums;

import lombok.RequiredArgsConstructor;

/**
 * 枚举数据权限
 *
 * @author gaoweixuan
 * @date 2022-10-29
 */
@RequiredArgsConstructor
public enum DataPermissionCode {

    /**
     * 全部
     */
    ALL("ALL", "全部"),

    /**
     * 本级部门
     */
    DEPT_LEVEL("DEPT_LEVEL", "部门范围权限"),

    /**
     * 个人
     */
    OWN("OWN", "个人");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

}
