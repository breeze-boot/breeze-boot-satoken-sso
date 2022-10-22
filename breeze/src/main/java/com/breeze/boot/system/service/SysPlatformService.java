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
import com.breeze.boot.system.domain.SysPlatform;
import com.breeze.boot.system.dto.PlatformDTO;

import java.util.List;

/**
 * 系统平台服务
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
public interface SysPlatformService extends IService<SysPlatform> {

    /**
     * 列表页面
     *
     * @param platformDTO 平台dto
     * @return {@link Page}<{@link SysPlatform}>
     */
    Page<SysPlatform> listPage(PlatformDTO platformDTO);

    /**
     * 批量保存
     *
     * @param platformDTOList 平台 List
     * @return boolean
     */
    Boolean saveAllBatch(List<PlatformDTO> platformDTOList);

}
