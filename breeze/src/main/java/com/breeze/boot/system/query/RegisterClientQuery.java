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

package com.breeze.boot.system.query;

import com.breeze.boot.core.base.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 注册客户端查询参数
 *
 * @author gaoweixuan
 * @date 2023/05/08
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClientQuery extends PageQuery {

    /**
     * id
     */
    private String id;

    /**
     * 客户机id
     */
    private String clientId;
    private String clientName;

}
