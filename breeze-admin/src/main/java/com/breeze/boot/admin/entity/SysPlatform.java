/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 系统平台实体
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_platform")
public class SysPlatform extends BaseModel<SysPlatform> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 平台标识
     */
    private String platformCode;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 描述
     */
    private String description;

}
