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

package com.breeze.boot.modules.flow.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.modules.flow.mapper.ProcessCategoryMapper;
import com.breeze.boot.modules.flow.model.entity.FlowCategory;
import com.breeze.boot.modules.flow.model.form.FlowCategoryForm;
import com.breeze.boot.modules.flow.model.mappers.FlowCategoryMapStruct;
import com.breeze.boot.modules.flow.model.query.FlowCategoryQuery;
import com.breeze.boot.modules.flow.model.vo.FlowCategoryVO;
import com.breeze.boot.modules.flow.service.IFlowCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 流程分类服务impl
 *
 * @author gaoweixuan
 * @since 2023-03-06
 */
@Service
@RequiredArgsConstructor
public class FlowCategoryServiceImpl extends ServiceImpl<ProcessCategoryMapper, FlowCategory> implements IFlowCategoryService {

    private final FlowCategoryMapStruct flowCategoryMapStruct;

    /**
     * 列表页面
     *
     * @param processCategory 流程类别
     * @return {@link IPage}<{@link FlowCategory}>
     */
    @Override
    public Page<FlowCategoryVO> listPage(FlowCategoryQuery processCategory) {
        Page<FlowCategory> flowCategoryPage = this.baseMapper.listPage(new Page<>(processCategory.getCurrent(), processCategory.getSize()), processCategory);
        return flowCategoryMapStruct.entityPage2VOPage(flowCategoryPage);
    }

    /**
     * 按id获取信息
     *
     * @param categoryId 流程分类ID
     * @return {@link FlowCategoryVO }
     */
    @Override
    public FlowCategoryVO getInfoById(Long categoryId) {
        return flowCategoryMapStruct.entity2VO(this.getById(categoryId));
    }

    /**
     * 保存流类别
     *
     * @param flowCategoryForm 流程分类表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveFlowCategory(FlowCategoryForm flowCategoryForm) {
        FlowCategory flowCategory = this.flowCategoryMapStruct.form2Entity(flowCategoryForm);
        return this.save(flowCategory);
    }

    /**
     * 修改流类别
     *
     * @param id               ID
     * @param flowCategoryForm 流程分类表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyFlowCategory(Long id, FlowCategoryForm flowCategoryForm) {
        FlowCategory flowCategory = this.flowCategoryMapStruct.form2Entity(flowCategoryForm);
        flowCategory.setId(id);
        return this.updateById(flowCategory);
    }
}
