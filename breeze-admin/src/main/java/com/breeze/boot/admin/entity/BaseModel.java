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

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 基本模型
 *
 * @author breeze
 * @date 2021-12-06 22:03:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseModel<T> extends Model<BaseModel<T>> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(name = "主键")
    @ExcelIgnore
    private Long id;

    /**
     * 是否删除 1 已删除 0 未删除
     */
    @JsonIgnore
    @Schema(name = "是否删除 1 已删除 0 未删除", hidden = true)
    @ExcelIgnore
    @TableLogic
    private Integer isDelete;

    /**
     * 删除人 工号
     */
    @JsonIgnore
    @Schema(name = "删除人 工号", hidden = true)
    @ExcelIgnore
    private String deleteBy;

    /**
     * createdBy
     */
    @Schema(name = "创建人", hidden = true)
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * createdTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "创建人", hidden = true)
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    private String createName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "创建时间", hidden = true)
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @Schema(name = "修改人", hidden = true)
    @ExcelIgnore
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改人
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "修改人", hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private String updateName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "修改时间", hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
