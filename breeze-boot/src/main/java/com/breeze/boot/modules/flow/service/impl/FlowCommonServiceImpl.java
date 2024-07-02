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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.flow.model.entity.FlowCategory;
import com.breeze.boot.modules.flow.service.FlowCommonService;
import com.breeze.boot.modules.flow.service.IFlowCategoryService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@Service
@RequiredArgsConstructor
public class FlowCommonServiceImpl implements FlowCommonService {

    private final IFlowCategoryService processCategoryService;

    @Override
    @DS("flowable")
    public Result<List<Map<String, Object>>> selectCategory() {
        List<FlowCategory> flowCategoryList = processCategoryService.list();
        return Result.ok(flowCategoryList.stream().map(item -> {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("label", item.getCategoryName());
            resultMap.put("value", item.getCategoryCode());
            return resultMap;
        }).collect(Collectors.toList()));
    }

}
