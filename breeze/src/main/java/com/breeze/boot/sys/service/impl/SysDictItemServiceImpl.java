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

package com.breeze.boot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.sys.domain.SysDictItem;
import com.breeze.boot.sys.dto.DictSearchDTO;
import com.breeze.boot.sys.mapper.SysDictItemMapper;
import com.breeze.boot.sys.service.SysDictItemService;
import com.breeze.core.utils.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统字典项服务impl
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService, InitializingBean {

    /**
     * 字典列表项
     *
     * @param dictSearchDTO 字典搜索DTO
     * @return {@link List}<{@link SysDictItem}>
     */
    @Override
    public List<SysDictItem> listDictItem(DictSearchDTO dictSearchDTO) {
        return this.baseMapper.listDictDetailByDictId(dictSearchDTO.getId());
    }

    /**
     * 查询字典通过代码
     *
     * @param dictCodes dict类型代码
     * @return {@link Result}<{@link Map}<{@link String}, {@link List}<{@link Map}<{@link String}, {@link Object}>>>>
     */
    @Override
    public Result<Map<String, List<Map<String, Object>>>> listDictByCode(List<String> dictCodes) {
        List<Map<String, Object>> dictItemListMap = this.baseMapper.listDictByCode(dictCodes);
        Map<String, List<Map<String, Object>>> resultMap = dictItemListMap.stream().collect(Collectors.groupingBy(dict -> (String) dict.get("dictCode")));
        return Result.ok(resultMap);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //   List<Map<String, Object>> dictItemListMap = this.baseMapper.listDictByCode(Lists.newArrayList());
        //  Map<String, List<Map<String, Object>>> resultMap = dictItemListMap.stream().collect(Collectors.groupingBy(dict -> (String) dict.get("dictCode")));
    }
}
