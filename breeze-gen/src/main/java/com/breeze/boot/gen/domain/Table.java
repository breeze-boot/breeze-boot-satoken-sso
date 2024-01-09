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

package com.breeze.boot.gen.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 表
 *
 * @author gaoweixuan
 * @since 2023/07/03
 */
@Data
public class Table {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表备注
     */
    private String comments;

    /**
     * 编码格式
     */
    private String collation;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 首字母大大写类名，如：sys_user => SysUser
     */
    private String className;

    /**
     * 首字母小写类名，如：sys_user => SysUser
     */
    private String lowCaseClassName;

    /**
     * 主键
     */
    private Column pkColumn;

    /**
     * 列
     */
    private List<Column> columns;

}
