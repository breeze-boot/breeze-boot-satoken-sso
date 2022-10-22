/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.domain;

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
@Schema(name = "基础实体")
@EqualsAndHashCode(callSuper = false)
public class BaseModel<T> extends Model<BaseModel<T>> {

    /**
     * id
     */
    @ExcelIgnore
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private Long id;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    @JsonIgnore
    @ExcelIgnore
    @TableLogic
    @Schema(description = "是否删除 1 已删除 0 未删除")
    private Integer isDelete;

    /**
     * 删除人 工号
     */
    @JsonIgnore
    @ExcelIgnore
    @Schema(description = "删除人编码", hidden = true)
    private String deleteBy;

    /**
     * createdBy
     */
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人编码", hidden = true)
    private String createBy;

    /**
     * createdTime
     */
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人姓名", hidden = true)
    private String createName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @Schema(hidden = true, description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @ExcelIgnore
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改人编码", hidden = true)
    private String updateBy;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改人姓名", hidden = true)
    private String updateName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改时间", hidden = true)
    private LocalDateTime updateTime;

}
