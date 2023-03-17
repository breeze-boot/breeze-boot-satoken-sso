package com.breeze.boot.process.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程实例签证官
 *
 * @author gaoweixuan
 * @date 2023-03-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceVO {

    private String id;
    private String tenantId;
    private String businessKey;
    private String startActivityId;
    private String procInstId;
    private String startActId;
    private String startUserId;
    private String startUserName;
    private String deploymentId;
    private String processDefinitionId;
    private String version;
    private String key;
    private String name;
    private Date startTime;
    private String email;

}
