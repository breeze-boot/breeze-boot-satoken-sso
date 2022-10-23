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

package com.breeze.boot.system.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.config.LogType;
import com.breeze.boot.system.domain.SysMenu;
import com.breeze.boot.system.domain.SysPlatform;
import com.breeze.boot.system.service.SysMenuService;
import com.breeze.boot.system.service.SysPlatformService;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 公用的接口
 *
 * @author breeze
 * @date 2022-10-08
 */
@RestController
@RequestMapping("/sys/common")
@Tag(name = "通用接口管理模块", description = "CommonController")
public class CommonController {

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysPlatformService platformService;

    @Operation(summary = "菜单树形下拉框", description = "下拉框接口")
    @GetMapping("/selectMenu")
    @BreezeSysLog(description = "菜单树形下拉框", type = LogType.LIST)
    public Result<List<Tree<Long>>> selectMenu() {
        List<SysMenu> menuList = this.menuService.list(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getType, 2));
        List<TreeNode<Long>> treeNodeList = menuList.stream().map(
                menu -> {
                    TreeNode<Long> treeNode = new TreeNode<>();
                    treeNode.setId(menu.getId());
                    treeNode.setParentId(menu.getParentId());
                    treeNode.setName(menu.getName());
                    Map<String, Object> leafMap = Maps.newHashMap();
                    leafMap.put("label", menu.getTitle());
                    leafMap.put("value", menu.getId().toString());
                    treeNode.setExtra(leafMap);
                    return treeNode;
                }
        ).collect(Collectors.toList());
        return Result.ok(TreeUtil.build(treeNodeList, 0L));
    }

    @Operation(summary = "平台下拉框", description = "下拉框接口")
    @GetMapping("/selectPlatform")
    @BreezeSysLog(description = "平台下拉框", type = LogType.LIST)
    public Result<List<Map<String, Object>>> selectPlatform() {
        List<SysPlatform> platformList = this.platformService.list();
        List<Map<String, Object>> collect = platformList.stream().map(sysPlatform -> {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("value", sysPlatform.getId());
            map.put("label", sysPlatform.getPlatformName());
            return map;
        }).collect(Collectors.toList());
        return Result.ok(collect);
    }

}
