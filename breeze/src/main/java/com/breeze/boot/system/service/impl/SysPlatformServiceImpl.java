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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.system.domain.SysPlatform;
import com.breeze.boot.system.dto.PlatformSearchDTO;
import com.breeze.boot.system.mapper.SysPlatformMapper;
import com.breeze.boot.system.service.SysPlatformService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统平台服务impl
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysPlatformServiceImpl extends ServiceImpl<SysPlatformMapper, SysPlatform> implements SysPlatformService {

    /**
     * 列表页面
     *
     * @param platformSearchDTO 平台搜索DTO
     * @return {@link Page}<{@link SysPlatform}>
     */
    @Override
    public Page<SysPlatform> listPage(PlatformSearchDTO platformSearchDTO) {
        Page<SysPlatform> platformEntityPage = new Page<>(platformSearchDTO.getCurrent(), platformSearchDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(platformSearchDTO.getPlatformName()), SysPlatform::getPlatformCode, platformSearchDTO.getPlatformName())
                .like(StrUtil.isAllNotBlank(platformSearchDTO.getPlatformCode()), SysPlatform::getPlatformCode, platformSearchDTO.getPlatformCode())
                .page(platformEntityPage);
    }

    /**
     * 批量保存
     *
     * @param platformSearchDTOList 平台List
     * @return {@link Boolean}
     */
    @Override
    public Boolean saveAllBatch(List<PlatformSearchDTO> platformSearchDTOList) {
        return Boolean.TRUE;
    }

}
