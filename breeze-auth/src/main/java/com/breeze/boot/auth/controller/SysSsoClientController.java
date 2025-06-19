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

package com.breeze.boot.auth.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.auth.model.entity.SysSsoClient;
import com.breeze.boot.auth.model.form.SsoClientForm;
import com.breeze.boot.auth.model.query.SsoClientQuery;
import com.breeze.boot.auth.model.vo.SsoClientHomeVO;
import com.breeze.boot.auth.model.vo.SsoClientVO;
import com.breeze.boot.auth.service.SysSsoClientService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 系统sso客户端维护控制器
 *
 * @author gaoweixuan
 * @since 2024-09-30
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/ssoClient")
@Tag(name = "系统sso客户端维护控制器", description = "SysSsoClientController")
public class SysSsoClientController {

    private final SysSsoClientService sysSsoClientService;

    /**
     * 查询主页sso数据
     */
    @Operation(summary = "查询主页sso数据")
    @GetMapping("/getHomeSsoClient")
    public Result<List<SsoClientHomeVO>> getHomeSsoClient() {
        return Result.ok(this.sysSsoClientService.getHomeSsoClient());
    }

    /**
     * 列表
     *
     * @param ssoClientQuery 客户端查询
     * @return {@link Result}<{@link Page}<{@link SsoClientVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("auth:ssoClient:list")
    public Result<Page<SsoClientVO>> list(SsoClientQuery ssoClientQuery) {
        return Result.ok(this.sysSsoClientService.listPage(ssoClientQuery));
    }

    /**
     * 详情
     *
     * @param ssoClientId 客户端id
     * @return {@link Result}<{@link SsoClientVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{ssoClientId}")
    @SaCheckPermission("auth:ssoClient:info")
    public Result<SsoClientVO> info(@Parameter(description = "客户端ID") @NotNull(message = "客户端ID不能为空")
                                    @PathVariable("ssoClientId") Long ssoClientId) {
        return Result.ok(this.sysSsoClientService.getInfoById(ssoClientId));
    }

    /**
     * 创建
     *
     * @param sysSsoClient sso客户端表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:ssoClient:create")
    @BreezeSysLog(description = "客户端信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysSsoClient sysSsoClient) {
        return Result.ok(this.sysSsoClientService.save(sysSsoClient));
    }

    /**
     * 校验客户端编码是否重复
     *
     * @param clientCode 客户端编码
     * @param clientId   sso客户端ID主键
     * @return {@link Result}<{@link SysSsoClient}>
     */
    @Operation(summary = "校验客户端编码是否重复")
    @GetMapping("/checkSsoClientCode")
    @SaCheckPermission("auth:ssoClient:list")
    public Result<Boolean> checkSsoClientCode(@Parameter(description = "客户端编码") @NotBlank(message = "客户端编码不能为空") @RequestParam("clientCode") String clientCode,
                                              @Parameter(description = "sso客户端ID主键") @RequestParam(value = "clientId", required = false) Long clientId) {
        return Result.ok(Objects.isNull(this.sysSsoClientService.getOne(Wrappers.<SysSsoClient>lambdaQuery()
                .ne(Objects.nonNull(clientId), SysSsoClient::getId, clientId)
                .eq(SysSsoClient::getClientCode, clientCode))));
    }

    /**
     * 修改
     *
     * @param id            ID
     * @param ssoClientForm 客户端表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:ssoClient:modify")
    @BreezeSysLog(description = "客户端信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "客户端ID") @NotNull(message = "客户端ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody SsoClientForm ssoClientForm) {
        return Result.ok(this.sysSsoClientService.modifySsoClient(id, ssoClientForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:ssoClient:delete")
    @BreezeSysLog(description = "客户端信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "客户端IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysSsoClientService.removeSsoClientByIds(ids);
    }

}
