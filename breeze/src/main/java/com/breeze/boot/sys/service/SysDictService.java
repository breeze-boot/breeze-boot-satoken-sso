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

package com.breeze.boot.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.sys.domain.SysDict;
import com.breeze.boot.sys.dto.DictOpenDTO;
import com.breeze.boot.sys.dto.DictSearchDTO;
import com.breeze.core.utils.Result;

import java.util.List;

/**
 * 系统字典服务
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 字典分页
     *
     * @param dictSearchDto 字典搜索DTO
     * @return {@link Page}<{@link SysDict}>
     */
    Page<SysDict> listPage(DictSearchDTO dictSearchDto);

    /**
     * 开关
     *
     * @param dictOpenDTO 字典开关DTO
     * @return {@link Boolean}
     */
    Boolean open(DictOpenDTO dictOpenDTO);

    /**
     * 删除字典通过IDS
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> deleteByIds(List<Long> ids);

}

