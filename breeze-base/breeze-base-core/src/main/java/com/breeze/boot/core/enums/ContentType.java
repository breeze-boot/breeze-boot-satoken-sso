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
 * 内容类型
 *
 * @author gaoweixuan
 * @date 2023-04-18
 */
@Getter
@RequiredArgsConstructor
public enum ContentType {

    /**
     * 默认
     */
    DEFAULT("default", "application/octet-stream"),
    /**
     * tiff
     */
    TIFF("tiff", "image/tiff"),
    /**
     * tif
     */
    TIF("tif", "image/tiff"),
    /**
     * gif
     */
    GIF("gif", "image/gif"),
    /**
     * png
     */
    PNG("png", "image/png"),
    /**
     * ico
     */
    ICO("ico", "image/x-icon"),
    /**
     * jpeg
     */
    JPEG("jpeg", "image/jpeg"),
    /**
     * jpe
     */
    JPE("jpe", "image/jpeg");

    /**
     * 类型
     */
    private final String type;

    /**
     * 内容类型
     */
    private final String contentType;

    /**
     * 根据文件名获取内容类型
     *
     * @param name 名称
     * @return {@link String}
     */
    public static String getContentType(String name) {
        if (StrUtil.isEmpty(name)) {
            return DEFAULT.contentType;
        }
        name = name.substring(name.lastIndexOf(".") + 1);
        for (ContentType value : ContentType.values()) {
            if (!name.equalsIgnoreCase(value.getType())) {
                continue;
            }
            return value.getContentType();
        }
        return DEFAULT.getContentType();
    }

}
