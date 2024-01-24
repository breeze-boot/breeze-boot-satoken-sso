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

package com.breeze.boot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.domain.SysDict;
import com.breeze.boot.modules.system.domain.SysDictItem;
import com.breeze.boot.modules.system.domain.params.DictOpenParam;
import com.breeze.boot.modules.system.domain.query.DictQuery;
import com.breeze.boot.modules.system.mapper.SysDictMapper;
import com.breeze.boot.modules.system.service.SysDictItemService;
import com.breeze.boot.modules.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统字典服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    /**
     * 系统字典项服务
     */
    private final SysDictItemService sysDictItemService;

    /**
     * 开关
     *
     * @param dictOpenParam 字典开关参数
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(DictOpenParam dictOpenParam) {
        return this.update(Wrappers.<SysDict>lambdaUpdate()
                .set(SysDict::getIsOpen, dictOpenParam.getIsOpen())
                .eq(SysDict::getId, dictOpenParam.getId()));
    }

    /**
     * 字典分页
     *
     * @param dictQuery 字典查询
     * @return {@link Page}<{@link SysDict}>
     */
    @Override
    public Page<SysDict> listPage(DictQuery dictQuery) {
        return this.baseMapper.listPage(new Page<>(dictQuery.getCurrent(), dictQuery.getSize()), dictQuery);
    }

    /**
     * 删除字典通过IDS
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteByIds(List<Long> ids) {
        this.sysDictItemService.remove(Wrappers.<SysDictItem>lambdaQuery().in(SysDictItem::getDictId, ids));
        this.removeByIds(ids);
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}
