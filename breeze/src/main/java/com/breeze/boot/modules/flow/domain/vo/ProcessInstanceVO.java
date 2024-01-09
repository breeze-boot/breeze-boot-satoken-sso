package com.breeze.boot.modules.flow.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程实例签证官
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceVO {

    /**
     * id
     */
    private String id;
    /**
     * 承租者id
     */
    private String tenantId;
    /**
     * 业务关键
     */
    private String businessKey;
    /**
     * 开始活动id
     */
    private String startActivityId;
    /**
     * proc本月id
     */
    private String procInstId;
    /**
     * 开始行动id
     */
    private String startActId;
    /**
     * 开始用户id
     */
    private String startUserId;
    /**
     * 开始用户名
     */
    private String startUserName;
    /**
     * 部署id
     */
    private String deploymentId;
    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 版本
     */
    private String version;
    /**
     * 关键
     */
    private String key;
    /**
     * 名字
     */
    private String name;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 电子邮件
     */
    private String email;

}
