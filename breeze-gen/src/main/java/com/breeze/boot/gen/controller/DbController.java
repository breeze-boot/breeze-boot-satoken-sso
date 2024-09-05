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

package com.breeze.boot.gen.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.gen.domain.Table;
import com.breeze.boot.gen.domain.param.TableParam;
import com.breeze.boot.gen.service.DbService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * db控制器
 *
 * @author gaoweixuan
 * @since 2023/07/03
 */
@RestController
@RequiredArgsConstructor
public class DbController {

    private final DbService dbService;

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("auth:user:list")
    public Result<IPage<Table>> list(TableParam tableParam) {
        return Result.ok(dbService.listPage(tableParam));
    }

    /**
     * /**
     * 生成代码
     */
    @RequestMapping("/gen")
    public void code(List<String> tables, HttpServletResponse response) {
        byte[] data = dbService.generatorCode(tables);
    }

}
