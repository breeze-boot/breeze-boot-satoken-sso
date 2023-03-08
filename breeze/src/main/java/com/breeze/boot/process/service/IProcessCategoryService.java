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

package com.breeze.boot.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.process.domain.ProcessCategory;
import com.breeze.boot.process.dto.ProcessCategoryDTO;

/**
 * 流程分类服务
 *
 * @author breeze
 * @date 2023-03-06
 */
public interface IProcessCategoryService extends IService<ProcessCategory> {

    /**
     * 列表页面
     *
     * @param processCategory 流程分类
     * @return {@link IPage}<{@link ProcessCategoryDTO}>
     */
    IPage<ProcessCategory> listPage(ProcessCategoryDTO processCategory);

}
