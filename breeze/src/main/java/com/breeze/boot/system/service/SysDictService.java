/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysDict;
import com.breeze.boot.system.dto.DictOpenDTO;
import com.breeze.boot.system.dto.DictSearchDTO;

import java.util.List;

/**
 * 系统字典服务
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 字典类型列表
     *
     * @param dictSearchDto 字典 dto
     * @return {@link Page}<{@link SysDict}>
     */
    Page<SysDict> listDict(DictSearchDTO dictSearchDto);

    /**
     * 开关
     *
     * @param dictOpenDTO 字典开关DTO
     * @return {@link Boolean}
     */
    Boolean open(DictOpenDTO dictOpenDTO);

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}
     */
    Result<Boolean> deleteByIds(List<Long> ids);

}

