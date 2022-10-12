package com.breeze.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 系统字典实体
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict")
public class SysDict extends BaseModel<SysDict> implements Serializable {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典代码
     */
    private String dictCode;

    /**
     * 是否开放
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
