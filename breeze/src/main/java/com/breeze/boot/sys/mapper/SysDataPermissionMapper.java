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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.sys.domain.SysDataPermission;
import com.breeze.boot.sys.query.DataPermissionQuery;
import com.breeze.database.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统数据权限映射器
 *
 * @author gaoweixuan
 * @date 2022-10-30
 */
@Mapper
public interface SysDataPermissionMapper extends BreezeBaseMapper<SysDataPermission> {

    /**
     * 列表分页
     *
     * @param dataPermissionQuery 数据权限查询
     * @param page                页面
     * @return {@link Page}<{@link SysDataPermission}>
     */
    Page<SysDataPermission> listPage(Page<SysDataPermission> page, @Param("dataPermissionQuery") DataPermissionQuery dataPermissionQuery);

}
