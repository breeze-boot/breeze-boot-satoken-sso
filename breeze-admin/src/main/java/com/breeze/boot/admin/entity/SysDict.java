package com.breeze.boot.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     */
    @TableField(exist = false)
    private List<SysDictItem> sysDictDetailList;
}
