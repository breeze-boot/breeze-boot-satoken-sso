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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysPost;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.PostSearchDTO;
import com.breeze.boot.system.mapper.SysPostMapper;
import com.breeze.boot.system.service.SysPostService;
import com.breeze.boot.system.service.SysUserService;
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
     * @param postSearchDTO 岗位搜索DTO
     * @return {@link IPage}<{@link SysPost}>
     */
    @Override
    public IPage<SysPost> listPage(PostSearchDTO postSearchDTO) {
        Page<SysPost> sysPostPage = new Page<>(postSearchDTO.getCurrent(), postSearchDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(postSearchDTO.getPostCode()), SysPost::getPostCode, postSearchDTO.getPostCode())
                .like(StrUtil.isAllNotBlank(postSearchDTO.getPostName()), SysPost::getPostName, postSearchDTO.getPostName())
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




