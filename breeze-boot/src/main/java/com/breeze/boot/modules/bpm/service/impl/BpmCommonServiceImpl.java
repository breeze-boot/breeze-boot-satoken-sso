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

package com.breeze.boot.modules.bpm.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.bpm.model.entity.BpmCategory;
import com.breeze.boot.modules.bpm.service.IBpmCategoryService;
import com.breeze.boot.modules.bpm.service.IBpmCommonService;
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
public class BpmCommonServiceImpl implements IBpmCommonService {

    private final IBpmCategoryService bpmCategoryService;

    @Override
    @DS("flowable")
    public Result<List<Map<String, Object>>> selectCategory() {
        List<BpmCategory> bpmCategoryList = bpmCategoryService.list();
        return Result.ok(bpmCategoryList.stream().map(item -> {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("label", item.getCategoryName());
            resultMap.put("value", item.getCategoryCode());
            return resultMap;
        }).collect(Collectors.toList()));
    }

}
