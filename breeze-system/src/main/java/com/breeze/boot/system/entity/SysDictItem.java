package com.breeze.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "value不可为空")
    private String value;

    @NotBlank(message = "label不可为空")
    private String label;

    private Integer sort;

}
