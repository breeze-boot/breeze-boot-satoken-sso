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

/**
 * 列
 */
@Data
public class Column {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 数据库字段类型
     */
    private String dataType;

    /**
     * 数据库字段备注
     */
    private String comments;

    /**
     * 首字母大写属性名称，如：user_name => UserName
     */
    private String attrName;

    /**
     * 首字母小写属性名称，如：user_name => userName
     */
    private String lowCaseAttrName;

    /**
     * 转换成java类后的属性类型
     */
    private String attrType;

}
