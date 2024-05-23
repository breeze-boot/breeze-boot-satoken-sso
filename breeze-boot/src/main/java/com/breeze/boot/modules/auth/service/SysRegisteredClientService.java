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

package com.breeze.boot.modules.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysRegisteredClient;
import com.breeze.boot.modules.auth.model.params.RegisteredClientParam;
import com.breeze.boot.modules.auth.model.params.ResetClientSecretParam;
import com.breeze.boot.modules.auth.model.query.RegisteredClientQuery;
import com.breeze.boot.modules.auth.model.vo.RegisteredClientVO;
import com.breeze.boot.security.model.entity.BaseSysRegisteredClient;
import com.breeze.boot.security.service.ISysRegisteredClientService;

import java.util.List;

/**
 * 注册客户服务接口
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
public interface SysRegisteredClientService extends IService<SysRegisteredClient>, ISysRegisteredClientService {

    /**
     * 发现由客户端Id
     *
     * @param clientId 客户端Id
     * @return {@link BaseSysRegisteredClient}
     */
    @Override
    SysRegisteredClient getByClientId(String clientId);

    /**
     * 发现通过id
     *
     * @param id id
     * @return {@link BaseSysRegisteredClient}
     */
    @Override
    SysRegisteredClient getById(String id);

    /**
     * 列表页面
     *
     * @param registeredClientQuery 注册客户端参数
     * @return {@link Page}<{@link RegisteredClientVO}>
     */
    Page<RegisteredClientVO> listPage(RegisteredClientQuery registeredClientQuery);

    /**
     * 保存注册客户端
     *
     * @param registeredClientParam 注册客户端参数
     * @return {@link Boolean}
     */
    Result<Boolean> saveRegisteredClient(RegisteredClientParam registeredClientParam);

    /**
     * 更新
     *
     * @param registeredClientParam 注册客户端参数
     * @return {@link Boolean}
     */
    Boolean update(RegisteredClientParam registeredClientParam);

    /**
     * 删除通过id
     *
     * @param ids id集合
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> deleteById(List<Long> ids);

    /**
     * 重置客户端密钥
     *
     * @param resetClientSecretParam 重置客户端密钥
     * @return {@link Boolean}
     */
    Boolean resetClientSecretParam(ResetClientSecretParam resetClientSecretParam);

    /**
     * 信息
     *
     * @param clientId 客户端ID
     * @return {@link RegisteredClientVO}
     */
    RegisteredClientVO info(Long clientId);

}
