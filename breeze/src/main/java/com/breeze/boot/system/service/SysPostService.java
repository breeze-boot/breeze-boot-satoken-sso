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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.domain.SysPost;
import com.breeze.boot.system.dto.PostSearchDTO;
import com.breeze.core.utils.Result;

import java.util.List;

/**
 * 系统岗位服务
 *
 * @author gaoweixuan
 * @date 2022-11-06
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 列表页面
     *
     * @param postSearchDTO 岗位搜索DTO
     * @return {@link IPage}<{@link SysPost}>
     */
    IPage<SysPost> listPage(PostSearchDTO postSearchDTO);

    /**
     * 通过IDS删除
     *
     * @param ids IDS
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removePostByIds(List<Long> ids);

}
