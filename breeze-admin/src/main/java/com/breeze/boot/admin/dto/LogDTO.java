package com.breeze.boot.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志dto
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
public class LogDTO extends PageDTO {
    /**
     * 系统模块
     */
    private String systemModule;
    /**
     * 日志标题
     */
    private String logTitle;
    /**
     * 日志类型 0 普通日志 1 登录日志
     */
    private Long logType;
    /**
     * 请求类型 0 GET 1 POST 2 PUT 3 DELETE
     */
    private Long requestType;
    /**
     * 操作类型 0 添加 1 删除 2 修改 3 查询
     */
    private Long doType;
    /**
     * 结果 0 失败 1 成功
     */
    private Long result;

    /**
     * 开始日期
     */
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    private LocalDateTime endDate;
}
