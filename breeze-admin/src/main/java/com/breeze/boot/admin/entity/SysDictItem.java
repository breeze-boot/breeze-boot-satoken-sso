package com.breeze.boot.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统字典项实体
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_item")
public class SysDictItem extends BaseModel<SysDictItem> implements Serializable {

    private Long dictId;

    private String value;

    private String label;

    private Integer sort;

}
