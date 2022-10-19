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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.system.domain.SysPlatform;
import com.breeze.boot.system.dto.PlatformDTO;
import com.breeze.boot.system.mapper.SysPlatformMapper;
import com.breeze.boot.system.service.SysPlatformService;
import org.springframework.stereotype.Service;

/**
 * 系统平台服务impl
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysPlatformServiceImpl extends ServiceImpl<SysPlatformMapper, SysPlatform> implements SysPlatformService {

    /**
     * 列表
     *
     * @param platformDTO 平台dto
     * @return {@link Page}<{@link SysPlatform}>
     */
    @Override
    public Page<SysPlatform> listPage(PlatformDTO platformDTO) {
        Page<SysPlatform> platformEntityPage = new Page<>(platformDTO.getCurrent(), platformDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(platformDTO.getPlatformName()), SysPlatform::getPlatformCode, platformDTO.getPlatformName())
                .like(StrUtil.isAllNotBlank(platformDTO.getPlatformCode()), SysPlatform::getPlatformCode, platformDTO.getPlatformCode())
                .page(platformEntityPage);
    }

}
