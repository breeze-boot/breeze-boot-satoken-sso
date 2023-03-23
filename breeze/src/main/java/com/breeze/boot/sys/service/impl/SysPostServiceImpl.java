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

package com.breeze.boot.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.sys.domain.SysPost;
import com.breeze.boot.sys.domain.SysUser;
import com.breeze.boot.sys.mapper.SysPostMapper;
import com.breeze.boot.sys.query.PostQuery;
import com.breeze.boot.sys.service.SysPostService;
import com.breeze.boot.sys.service.SysUserService;
import com.breeze.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统岗服务impl
 *
 * @author gaoweixuan
 * @date 2022-11-06
 */
@Service

public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表页面
     *
     * @param postQuery 岗位查询
     * @return {@link IPage}<{@link SysPost}>
     */
    @Override
    public IPage<SysPost> listPage(PostQuery postQuery) {
        Page<SysPost> sysPostPage = new Page<>(postQuery.getCurrent(), postQuery.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(postQuery.getPostCode()), SysPost::getPostCode, postQuery.getPostCode())
                .like(StrUtil.isAllNotBlank(postQuery.getPostName()), SysPost::getPostName, postQuery.getPostName())
                .orderByDesc(SysPost::getCreateTime)
                .page(sysPostPage);
    }

    /**
     * 通过IDS删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removePostByIds(List<Long> ids) {
        List<SysUser> sysUserList = this.sysUserService.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getPostId, ids));
        if (CollUtil.isNotEmpty(sysUserList)) {
            return Result.warning(Boolean.FALSE, "岗位存在用户");
        }
        return Result.ok(this.removeByIds(ids));
    }

}




