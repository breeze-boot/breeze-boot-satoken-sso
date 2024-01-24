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

package com.breeze.boot.modules.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.domain.SysDept;
import com.breeze.boot.modules.auth.domain.query.DeptQuery;
import com.breeze.boot.modules.auth.mapper.SysDeptMapper;
import com.breeze.boot.modules.auth.service.SysDeptService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CoreConstants.ROOT;

/**
 * 系统部门服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 部门列表
     *
     * @param deptQuery 部门查询
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    @Override
    public List<?> listDept(DeptQuery deptQuery) {
        List<SysDept> deptEntityList = this.list(Wrappers.<SysDept>lambdaQuery()
                .like(Objects.nonNull(deptQuery) && StrUtil.isAllNotBlank(deptQuery.getDeptName()),
                        SysDept::getDeptName, deptQuery.getDeptName()));
        if (StrUtil.isAllNotBlank(deptQuery.getDeptName())) {
            return deptEntityList;
        }
        List<TreeNode<Long>> treeNodeList = deptEntityList.stream().map(
                sysDept -> {
                    TreeNode<Long> treeNode = new TreeNode<>();
                    treeNode.setId(sysDept.getId());
                    treeNode.setParentId(sysDept.getParentId());
                    treeNode.setName(sysDept.getDeptName());
                    Map<String, Object> leafMap = Maps.newHashMap();
                    leafMap.put("deptName", sysDept.getDeptName());
                    leafMap.put("deptCode", sysDept.getDeptCode());
                    if (Objects.equals(deptQuery.getId(), sysDept.getId())) {
                        leafMap.put("disabled", Boolean.TRUE);
                    }
                    leafMap.put("isChecked", Boolean.FALSE);
                    leafMap.put("key", sysDept.getId());
                    leafMap.put("value", sysDept.getDeptName());
                    treeNode.setExtra(leafMap);
                    return treeNode;
                }
        ).collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, ROOT);
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteById(Long id) {
        List<SysDept> deptEntityList = this.list(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getParentId, id));
        if (CollUtil.isNotEmpty(deptEntityList)) {
            return Result.warning(Boolean.FALSE, "此部门存在下级部门,不允许删除");
        }
        boolean remove = this.removeById(id);
        if (remove) {
            return Result.ok(Boolean.TRUE, "删除成功");
        }
        return Result.fail(Boolean.FALSE, "删除失败");
    }

    /**
     * 选择部门通过id
     *
     * @param permissions 权限
     * @return {@link List}<{@link Long}>
     */
    @Override
    public List<Long> selectDeptById(String permissions) {
        List<SysDept> sysDeptList = this.baseMapper.selectDeptById(Long.valueOf(permissions));
        List<Long> idList = Lists.newArrayList();
        for (SysDept sysDept : sysDeptList) {
            idList.add(sysDept.getId());
            this.filterDeptTree(idList, sysDept);
        }
        return idList;
    }

    /**
     * 过滤器部门树
     *
     * @param idList  id列表
     * @param sysDept 系统部门
     */
    public void filterDeptTree(List<Long> idList, SysDept sysDept) {
        if (CollUtil.isEmpty(sysDept.getSysDeptList())) {
            return;
        }
        for (SysDept dept : sysDept.getSysDeptList()) {
            idList.add(dept.getId());
        }
    }

}
