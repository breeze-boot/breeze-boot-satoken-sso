package com.breeze.boot.modules.flow.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FlowDesignXmlFileParam {

    @Schema(description = "流程部署Key")
    @NotBlank(message = "流程部署Key不能为空")
    private String definitionKey;

    @Schema(description = "表单Key")
    @NotBlank(message = "表单Key不能为空")
    private String formKey;

    /**
     * 名字
     */
    @Schema(description = "流程部署名称")
    @NotBlank(message = "流程部署名称不能为空")
    private String definitionName;

    /**
     * 类别
     */
    @Schema(description = "流程部署分类")
    @NotBlank(message = "流程部署分类不能为空")
    private String categoryCode;

    /**
     * xml
     */
    @Schema(description = "流程部署xml")
    @NotNull(message = "xml不能为空")
    private MultipartFile xmlFile;

    /**
     * 租户
     */
    @Schema(description = "租户")
    @NotNull(message = "租户不能为空")
    private String tenantId;

}