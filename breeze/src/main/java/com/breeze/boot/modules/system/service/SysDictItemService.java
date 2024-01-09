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

package com.breeze.boot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.domain.SysDictItem;
import com.breeze.boot.modules.system.domain.query.DictQuery;

import java.util.List;
import java.util.Map;

/**
 * 系统字典项服务
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 字典列表项
     *
     * @param dictQuery 字典查询
     * @return {@link List}<{@link SysDictItem}>
     */
    List<SysDictItem> listDictItem(DictQuery dictQuery);

    /**
     * 查询字典通过类型代码
     *
     * @param dictCodes dict类型代码
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link Map}<{@link String}, {@link Object}>>>>
     */
    Result<Map<String, List<Map<String, Object>>>> listDictByCode(List<String> dictCodes);
}

