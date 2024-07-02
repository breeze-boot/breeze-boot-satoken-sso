package com.breeze.boot.modules.flow.model.query;

import com.breeze.boot.core.base.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户任务查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserTaskQuery extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;
}
