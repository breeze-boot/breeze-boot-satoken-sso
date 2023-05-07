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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * utils
 *
 * @author gaoweixuan
 * @date 2023/05/07
 */
@AllArgsConstructor
public class Utils<T> {

    /**
     * 对象映射器
     */
    @Getter
    @Setter
    private ObjectMapper objectMapper;

    /**
     * 写
     *
     * @param data 数据
     * @return {@link String}
     */
    public String write(T data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 解析
     *
     * @param data 数据
     * @return {@link T}
     */
    public T parse(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<T>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 注册
     *
     * @param modules 模块
     */
    public void register(Module... modules) {
        objectMapper.registerModules(modules);
    }

    /**
     * 注册
     *
     * @param module 模块
     */
    public void register(Module module) {
        objectMapper.registerModule(module);
    }

    /**
     * 注册
     *
     * @param modules 模块
     */
    public void register(List<Module> modules) {
        objectMapper.registerModules(modules);
    }


}
