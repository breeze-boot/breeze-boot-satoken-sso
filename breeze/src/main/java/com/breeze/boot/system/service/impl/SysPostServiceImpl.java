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
import com.breeze.boot.core.Result;
import com.breeze.boot.system.domain.SysPost;
import com.breeze.boot.system.domain.SysUser;
import com.breeze.boot.system.dto.PostDTO;
import com.breeze.boot.system.mapper.SysPostMapper;
import com.breeze.boot.system.service.SysPostService;
import com.breeze.boot.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统岗服务impl
 *
 * @author breeze
 * @date 2022-11-06
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表页面
     *
     * @param postDTO 帖子dto
     * @return {@link IPage}<{@link List}<{@link SysPost}>>
     */
    @Override
    public IPage<SysPost> listPage(PostDTO postDTO) {
        Page<SysPost> sysPostPage = new Page<>(postDTO.getCurrent(), postDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(postDTO.getPostCode()), SysPost::getPostCode, postDTO.getPostCode())
                .like(StrUtil.isAllNotBlank(postDTO.getPostName()), SysPost::getPostName, postDTO.getPostName())
                .orderByDesc(SysPost::getCreateTime)
                .page(sysPostPage);
    }

    /**
     * 删除后由ids
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




