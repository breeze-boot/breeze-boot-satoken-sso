package com.breeze.boot.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统字典项实体
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_item")
public class SysDictItem extends BaseModel<SysDictItem> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @NotBlank(message = "字典ID不能为空")
    private Long dictId;

    /**
     * 字典项的值
     */
    @NotBlank(message = "value不可为空")
    private String value;

    /**
     * 字典项的名称
     */
    @NotBlank(message = "label不可为空")
    private String label;

    /**
     * 排序
     */
    private Integer sort;

}
