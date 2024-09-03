package com.breeze.boot.core.enums;

import lombok.Getter;

/**
 * @author gaoweixuan
 * @since 2021/10/1
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    OK("result_ok", "请求成功"),

    /**
     * 失败
     */
    FAIL("result_fail", "请求失败"),

    /**
     * 参数异常
     */
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION("result_http_message_not_readable_exception", "请求失败"),

    /**
     * 演示环境
     */
    PREVIEW("result_preview", "演示环境不可删除修改"),

    /**
     * 令牌无效
     */
    SC_FORBIDDEN("result_sc_forbidden", "未授权资源"),

    /**
     * 认证失败
     */
    AUTHENTICATION_FAILURE("result_authentication_failure", "认证失败"),

    /**
     * 租户未获取到
     */
    TENANT_NOT_FOUND("result_tenant_not_found", "租户未获取到"),

    /**
     * 验证码失败
     */
    VERIFY_UN_FOUND("result_verify_un_found", "验证码失败"),

    /**
     * http消息转换异常
     */
    HTTP_MESSAGE_CONVERSION_EXCEPTION("result_http_message_conversion_exception", "请求参数错误"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND("result_file_not_found", "文件不存在"),

    /**
     * 分页过大
     */
    PAGE_EXCEPTION("result_page_exception", "分页过大"),

    /**
     * 未获取到流程实例
     */
    PROCESS_NOT_FOUND("result_process_not_found","未获取到流程实例"),

    /**
     * 未获取到任务实例
     */
    TASK_NOT_FOUND("result_task_not_found","未获取到任务实例"),

    /**
     * 未获取到流程定义
     */
    DEFINITION_NOT_FOUND("result_definition_not_found","未获取到流程定义"),

    /**
     * 未获取到BPMN模型
     */
    BPM_MODEL_NOT_FOUND("result_bpm_model_not_found","无法获取BPMN模型"),

    /**
     * 未获取到XML
     */
    XML_NOT_FOUND("result_xml_not_found","未获取到XML"),

    /**
     * 流程已挂起
     */
    PROCESS_SUSPENDED("result_process_suspended","流程已挂起"),

    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("result_system_exception","系统异常"),

    /**
     * 邮箱不存在
     */
    EMAIL_UN_FOUND("result_email_un_found", "邮箱不存在"),

    /**
     * 未发现此消息
     */
    MSG_UN_FOUND("result_msg_un_found", "未发现此消息");

    private final String key;

    private final String desc;

    /**
     * 返回结果代码
     *
     * @param key  代码
     * @param desc 描述
     */
    ResultCode(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}
