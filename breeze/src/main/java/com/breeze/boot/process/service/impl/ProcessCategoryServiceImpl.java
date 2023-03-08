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

package com.breeze.boot.process.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.process.domain.ProcessCategory;
import com.breeze.boot.process.dto.ProcessCategoryDTO;
import com.breeze.boot.process.mapper.ProcessCategoryMapper;
import com.breeze.boot.process.service.IProcessCategoryService;
import org.springframework.stereotype.Service;

/**
 * 流程分类服务impl
 *
 * @author breeze
 * @date 2023-03-06
 */
@Service
public class ProcessCategoryServiceImpl extends ServiceImpl<ProcessCategoryMapper, ProcessCategory> implements IProcessCategoryService {

    /**
     * 列表页面
     *
     * @param processCategory 流程类别
     * @return {@link IPage}<{@link ProcessCategory}>
     */
    @Override
    public IPage<ProcessCategory> listPage(ProcessCategoryDTO processCategory) {
        return this.baseMapper.listPage(new Page<>(processCategory.getCurrent(), processCategory.getSize()), processCategory);
    }
}
