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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.sys.domain.SysUserMsg;
import com.breeze.boot.sys.domain.SysUserMsgSnapshot;
import com.breeze.boot.sys.query.UserMsgQuery;
import com.breeze.boot.sys.vo.SysUserMsgSnapshotVO;
import com.breeze.database.annotation.BreezeDataPermission;
import com.breeze.database.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户消息快照映射器
 *
 * @author gaoweixuan
 * @date 2022-11-26
 */
@Mapper
public interface SysUserMsgSnapshotMapper extends BreezeBaseMapper<SysUserMsgSnapshot> {

    /**
     * 列表页面
     *
     * @param page         页面
     * @param userMsgQuery 用户消息查询
     * @return {@link IPage}<{@link SysUserMsg}>
     */
    @BreezeDataPermission(scope = "dept_id", own = "user_code")
    IPage<SysUserMsgSnapshotVO> listPage(Page<SysUserMsg> page, @Param("userMsgQuery") UserMsgQuery userMsgQuery);

    /**
     * 获取消息列表通过用户名
     *
     * @param username 用户名
     * @return {@link List}<{@link SysUserMsgSnapshotVO}>
     */
    List<SysUserMsgSnapshotVO> listMsgByUsername(String username);

}
