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

package com.breeze.boot.modules.flow.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.flow.model.query.UserTaskQuery;
import com.breeze.boot.modules.flow.model.vo.CurrentUserTaskVO;
import com.breeze.boot.modules.flow.model.vo.UserTaskVO;
import com.breeze.boot.modules.flow.service.IFlowTaskTodoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 流程任务控制器
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/flow/v1/task")
@Tag(name = "流程任务管理模块", description = "FlowTodoController")
public class FlowTaskTodoController {

    private final IFlowTaskTodoService flowTodoService;

    /**
     * 查询用户待办任务
     *
     * @param userTaskQuery 用户任务查询对象
     * @return {@link List}<{@link Task}>
     */
    @GetMapping("/listTodoTask")
    @ResponseBody
    public Result<Page<CurrentUserTaskVO>> listTodoTask(UserTaskQuery userTaskQuery) {
        return Result.ok(flowTodoService.listTodoTask(userTaskQuery));
    }

    /**
     * 查询用户已办任务
     *
     * @param userTaskQuery 用户任务查询对象
     * @return {@link List}<{@link Task}>
     */
    @GetMapping("/listCompletedTask")
    @ResponseBody
    public Result<Page<CurrentUserTaskVO>> listCompletedTask(UserTaskQuery userTaskQuery) {
        return Result.ok(flowTodoService.listCompletedTask(userTaskQuery));
    }

    /**
     * 获取用户任务列表
     *
     * @param map 集合
     * @return {@link List}<{@link UserTaskVO}>
     */
    @GetMapping(value = "/getUserTaskList")
    @ResponseBody
    public List<UserTaskVO> getUserTaskList(@RequestParam Map<String, String> map) {
        return flowTodoService.getUserTaskList(map);
    }

}
