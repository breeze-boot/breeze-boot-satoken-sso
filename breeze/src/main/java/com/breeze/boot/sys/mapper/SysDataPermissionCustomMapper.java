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

package com.breeze.boot.sys.mapper;

import com.breeze.boot.sys.domain.SysDataPermissionCustom;
import com.breeze.database.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统数据权限自定义映射器
 *
 * @author gaoweixuan
 * @date 2022-12-10
 */
@Mapper
public interface SysDataPermissionCustomMapper extends BreezeBaseMapper<SysDataPermissionCustom> {

    /**
     * 列表数据权限自定义
     *
     * @param dataPermissionId 数据权限id
     * @return {@link List}<{@link SysDataPermissionCustom}>
     */
    List<SysDataPermissionCustom> listDataPermissionCustom(@Param("dataPermissionId") Long dataPermissionId);

}
