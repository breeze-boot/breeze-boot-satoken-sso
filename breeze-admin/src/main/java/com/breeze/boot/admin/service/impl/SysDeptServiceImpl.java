/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.admin.dto.DeptDTO;
import com.breeze.boot.admin.entity.SysDeptEntity;
import com.breeze.boot.admin.entity.SysUserEntity;
import com.breeze.boot.admin.mapper.SysDeptMapper;
import com.breeze.boot.admin.service.SysDeptService;
import com.breeze.boot.admin.service.SysUserService;
import com.breeze.boot.core.Result;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统部门服务impl
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements SysDeptService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 部门列表
     *
     * @param deptDTO 部门dto
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    @Override
    public List<Tree<Long>> listDept(DeptDTO deptDTO) {
        List<SysDeptEntity> deptEntityList = this.list(Wrappers.<SysDeptEntity>lambdaQuery().eq(StrUtil.isAllNotBlank(deptDTO.getDeptName()), SysDeptEntity::getDeptName, deptDTO.getDeptName()));
        List<TreeNode<Long>> treeNodeList = deptEntityList.stream().map(
                sysDeptEntity -> {
                    TreeNode<Long> treeNode = new TreeNode<>();
                    treeNode.setId(sysDeptEntity.getId());
                    treeNode.setParentId(sysDeptEntity.getParentId());
                    treeNode.setName(sysDeptEntity.getDeptName());
                    Map<String, Object> leafMap = Maps.newHashMap();
                    leafMap.put("deptName", sysDeptEntity.getDeptName());
                    leafMap.put("deptCode", sysDeptEntity.getDeptName());
                    leafMap.put("isClose", sysDeptEntity.getDeptName());
                    treeNode.setExtra(leafMap);
                    return treeNode;
                }
        ).collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, 0L);
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Override
    public Result deleteById(Long id) {
        List<SysDeptEntity> deptEntityList = this.list(Wrappers.<SysDeptEntity>lambdaQuery().eq(SysDeptEntity::getParentId, id));
        if (CollUtil.isNotEmpty(deptEntityList)) {
            return Result.warning(Boolean.FALSE, "此部门存在下级部门,不允许删除");
        }
        List<SysUserEntity> userEntityList = this.sysUserService.list(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getDeptId, id));
        if (CollUtil.isNotEmpty(userEntityList)) {
            return Result.warning(Boolean.FALSE, "此部门内有在职员工,不允许删除");
        }
        boolean remove = this.removeById(id);
        if (remove) {
            return Result.ok(Boolean.TRUE, "删除成功");
        }
        return Result.fail(Boolean.FALSE, "删除失败");
    }

}
