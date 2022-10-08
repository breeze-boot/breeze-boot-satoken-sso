package com.breeze.boot.system.controller;

import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.Result;
import com.breeze.boot.system.entity.SysMenu;
import com.breeze.boot.system.service.SysMenuService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 开放控制器
 *
 * @author breeze
 * @date 2022-10-08
 */
@RestController
@RequestMapping("/sys/open")
public class OpenController {

    @Autowired
    private SysMenuService menuService;

    @GetMapping("/selectMenu")
    public Result selectMenu() {
        List<SysMenu> menuList = this.menuService.list(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getType, "btn"));
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

}
