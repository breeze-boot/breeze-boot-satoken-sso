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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 系统字典实体
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict")
public class SysDict extends BaseModel<SysDict> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 是否启用 0-关闭 1-启用
     */
    private Integer isOpen;

    /**
     * 字典项列表
     * <p>
     * 【@Validated】：可用在类型、方法和方法参数上，ps：不能用在成员属性（字段）
     * 当校验不通过的时候，程序会抛出400异常，阻止方法中的代码执行，这时需要再写一个全局校验异常捕获处理类，然后返回校验提示。
     * <p>
     * 【@Valid】：可用在方法、构造函数、方法参数和成员属性（字段）
     * 需要用 BindingResult 来做一个校验结果接收。当校验不通过，如果手动不 return，则并不会阻止程序的执行；
     */
    @TableField(exist = false)
    @Valid
    private List<SysDictItem> sysDictDetailList;

}
