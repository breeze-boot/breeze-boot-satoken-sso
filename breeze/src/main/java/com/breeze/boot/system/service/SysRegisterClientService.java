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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysRegisteredClient;
import com.breeze.boot.system.params.RegisteredClientParam;
import com.breeze.boot.system.params.ResetClientSecretParam;
import com.breeze.boot.system.query.RegisterClientQuery;
import com.breeze.boot.system.vo.RegisteredClientVO;

import java.util.List;

/**
 * auth注册客户服务接口
 *
 * @author gaoweixuan
 * @date 2023/05/09
 */
public interface SysRegisterClientService extends IService<SysRegisteredClient> {

    /**
     * 列表页面
     *
     * @param registerClientQuery 注册客户端参数
     * @return {@link Page}<{@link RegisteredClientVO}>
     */
    Page<RegisteredClientVO> listPage(RegisterClientQuery registerClientQuery);

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
     * 发现由客户端Id
     *
     * @param clientId 客户端Id
     * @return {@link SysRegisteredClient}
     */
    SysRegisteredClient getByClientId(String clientId);

    /**
     * 发现通过id
     *
     * @param id id
     * @return {@link SysRegisteredClient}
     */
    SysRegisteredClient getById(String id);

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
     * @param clientId 客户机id
     * @return {@link RegisteredClientVO}
     */
    RegisteredClientVO info(Long clientId);

}
