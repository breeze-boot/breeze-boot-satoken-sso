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

package com.breeze.boot.modules.auth.controller;

import com.breeze.boot.modules.auth.service.SysRoleColumnPermissionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统角色菜单字段数据权限管理模块
 *
 * @author gaoweixuan
 * @since 2024-03-18
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/roleColumnPermission")
@Tag(name = "系统角色菜单字段数据权限管理模块", description = "SysRoleColumnPermissionController")
public class SysRoleColumnPermissionController {

    /**
     * 系统角色菜单字段数据权限服务
     */
    private final SysRoleColumnPermissionService sysRoleColumnPermissionService;


}
