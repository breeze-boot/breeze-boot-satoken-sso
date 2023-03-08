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

package com.breeze.boot.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.process.domain.ActRuExecution;
import com.breeze.boot.process.dto.ProcessInstanceSearchDTO;
import com.breeze.boot.process.vo.ProcessInstanceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程执行实例映射器
 *
 * @author breeze
 * @date 2023-03-08
 */
@Mapper
public interface ActRuExecutionMapper extends BaseMapper<ActRuExecution> {

    /**
     * 列表页面
     *
     * @param page                     页面
     * @param processInstanceSearchDTO 流程实例搜索DTO
     * @return {@link Page}<{@link ProcessInstanceVO}>
     */
    Page<ProcessInstanceVO> listPage(@Param("page") Page<ProcessInstanceVO> page, @Param("processInstanceSearchDTO") ProcessInstanceSearchDTO processInstanceSearchDTO);

}
