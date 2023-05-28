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

package com.breeze.boot.core.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 不需要register的ObjectMapper工具类
 *
 * @author gaoweixuan
 * @date 2023/05/07
 */
public class MapperUtils {

    /**
     * 对象映射器
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 写
     *
     * @param data 数据
     * @return {@link String}
     */
    public static <T> String write(T data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 解析
     *
     * @param data      数据
     * @param reference 参考
     */
    public static <T> T parse(String data, TypeReference<T> reference) {
        try {
            return objectMapper.readValue(data, reference);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if (StrUtil.isAllBlank(json) || clazz == null) {
            throw new RuntimeException("参数不能为null");
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json转化异常");
        }
    }

}
