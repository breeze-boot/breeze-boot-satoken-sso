package com.breeze.boot.admin.dto;

import lombok.Data;

/**
 * 字典 dto
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
public class DictDTO extends PageDTO {

    /**
     * 字典 id
     */
    private Long id;

    /**
     * 字典 名称
     */
    private String dictName;

    /**
     * 字典 代码
     */
    private String dictCode;

    /**
     * 是开放
     */
    private Integer isOpen;

}
