/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 行政区划级别枚举
 */
public enum DistrictLevel {
    COUNTRY("country", "国家"),
    PROVINCE("province", "省份"),
    CITY("city", "城市"),
    DISTRICT("district", "区县"),
    STREET("street", "街道");

    private final String code;
    private final String description;

    private static final Map<String, DistrictLevel> codeMap = Arrays.stream(DistrictLevel.values())
            .collect(Collectors.toMap(DistrictLevel::getCode, level -> level));

    DistrictLevel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static DistrictLevel fromCode(String code) {
        if (code == null) {
            return null;
        }
        DistrictLevel level = codeMap.get(code);
        if (level == null) {
            throw new IllegalArgumentException("Invalid DistrictLevel code: " + code);
        }
        return level;
    }
}