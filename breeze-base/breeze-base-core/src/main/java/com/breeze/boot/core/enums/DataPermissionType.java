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

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 枚举数据权限
 *
 * @author gaoweixuan
 * @since 2022-10-29
 */
@Getter
@RequiredArgsConstructor
public enum DataPermissionType {

    /**
     * 全部
     */
    ALL("ALL", "全部", 1),

    /**
     * 本级部门
     */
    DEPT_LEVEL("DEPT_LEVEL", "部门范围权限", 2),

    /**
     * 本级部门以及子部门
     */
    SUB_DEPT_LEVEL("SUB_DEPT_LEVEL", "本级部门以及子部门", 3),

    /**
     * 个人
     */
    OWN("OWN", "个人", 4),

    /**
     * 自定义
     */
    CUSTOMIZES("CUSTOMIZES", "自定义", 9999);

    /**
     * 编码
     */
    private final String type;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 级别
     */
    private final Integer level;


    /**
     * @param permissionCode 权限编码
     * @return {@link Boolean}
     */
    public static Boolean checkInEnum(String permissionCode) {
        for (DataPermissionType value : DataPermissionType.values()) {
            if (StrUtil.equals(value.name(), permissionCode)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
