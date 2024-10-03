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

package com.breeze.boot.modules.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysSsoClientMapper;
import com.breeze.boot.modules.auth.model.entity.SysSsoClient;
import com.breeze.boot.modules.auth.model.form.SsoClientForm;
import com.breeze.boot.modules.auth.model.mappers.SysSsoClientMapStruct;
import com.breeze.boot.modules.auth.model.query.SsoClientQuery;
import com.breeze.boot.modules.auth.model.vo.SsoClientVO;
import com.breeze.boot.modules.auth.service.SysSsoClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统sso客户端维护服务impl
 *
 * @author gaoweixuan
 * @since 2024-09-30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysSsoClientServiceImpl extends ServiceImpl<SysSsoClientMapper, SysSsoClient> implements SysSsoClientService {

    private final SysSsoClientMapStruct sysSsoClientMapStruct;
    private final static String url = "" + "/sso/auth?redirect=%s?back=%s";

    @Override
    public List<String> getHomeSsoClient() {
        return this.list().stream().map(item -> {
            return String.format(url, item.getRedirect(), item.getBack());
        }).collect(Collectors.toList());
    }

    @Override
    public Page<SsoClientVO> listPage(SsoClientQuery ssoClientQuery) {
        Page<SysSsoClient> sysSsoClientPage = new Page<>(ssoClientQuery.getCurrent(), ssoClientQuery.getSize());
        QueryWrapper<SysSsoClient> queryWrapper = new QueryWrapper<>();
        ssoClientQuery.getSortQueryWrapper(queryWrapper);
        queryWrapper.like(StrUtil.isAllNotBlank(ssoClientQuery.getClientCode()), "client_code", ssoClientQuery.getClientCode());
        Page<SysSsoClient> page = this.page(sysSsoClientPage, queryWrapper);
        return this.sysSsoClientMapStruct.page2PageVO(page);
    }

    @Override
    public SsoClientVO getInfoById(Long ssoClientId) {
        return this.sysSsoClientMapStruct.entity2VO(this.getById(ssoClientId));
    }

    @Override
    public Boolean modifySsoClient(Long id, SsoClientForm ssoClientForm) {
        SysSsoClient sysSsoClient = this.sysSsoClientMapStruct.form2Entity(ssoClientForm);
        sysSsoClient.setId(id);
        return this.updateById(sysSsoClient);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeSsoClientByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

}
