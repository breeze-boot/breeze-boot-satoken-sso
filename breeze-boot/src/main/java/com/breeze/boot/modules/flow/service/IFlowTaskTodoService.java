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

package com.breeze.boot.modules.flow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.modules.flow.model.query.UserTaskQuery;
import com.breeze.boot.modules.flow.model.vo.CurrentUserTaskVO;
import com.breeze.boot.modules.flow.model.vo.UserTaskVO;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

/**
 * 流程任务管理服务接口
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
public interface IFlowTaskTodoService {

    /**
     * 获取待办列表
     *
     * @param map map
     * @return {@link List}<{@link UserTaskVO}>
     */
    List<UserTaskVO> getUserTaskList(Map<String, String> map);

    /**
     * 查询用户待办任务
     *
     * @param userTaskQuery 用户任务查询对象
     * @return {@link List }<{@link Task }>
     */
    Page<CurrentUserTaskVO> listTodoTask(UserTaskQuery userTaskQuery);

    /**
     * 查询用户已办任务
     *
     * @param userTaskQuery 用户任务查询
     * @return {@link Page }<{@link CurrentUserTaskVO }>
     */
    Page<CurrentUserTaskVO> listCompletedTask(UserTaskQuery userTaskQuery);

}
