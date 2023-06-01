
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

package com.breeze.boot.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import com.breeze.boot.system.domain.SysRegisteredClient;
import com.breeze.boot.system.query.RegisteredClientQuery;
import com.breeze.boot.system.vo.RegisteredClientVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统注册客户端映射器
 *
 * @author gaoweixuan
 * @date 2023/05/10
 */
@Mapper
public interface SysRegisteredClientMapper extends BreezeBaseMapper<SysRegisteredClient> {

    /**
     * 列表页面
     *
     * @param page                页面
     * @param registeredClientQuery 注册客户端查询
     * @return {@link Page}<{@link SysRegisteredClient}>
     */
    Page<RegisteredClientVO> listPage(@Param("page") Page<SysRegisteredClient> page, @Param("registerClientQuery") RegisteredClientQuery registeredClientQuery);

    /**
     * 获取注册客户端
     *
     * @param registeredClient 注册客户端
     * @return {@link SysRegisteredClient}
     */
    SysRegisteredClient getRegisteredClientBy(@Param("registeredClient") RegisteredClientQuery registeredClient);

}
