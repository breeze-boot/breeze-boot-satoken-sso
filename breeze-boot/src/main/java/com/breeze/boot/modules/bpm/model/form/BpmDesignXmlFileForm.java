package com.breeze.boot.modules.bpm.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BpmDesignXmlFileForm {

    @Schema(description = "流程部署Key")
    @NotBlank(message = "流程部署Key不能为空")
    private String procDefKey;

    @Schema(description = "流程部署名称")
    @NotBlank(message = "流程部署名称不能为空")
    private String procDefName;

    @Schema(description = "流程部署分类")
    @NotBlank(message = "流程部署分类不能为空")
    private String categoryCode;

    @Schema(description = "流程部署xml")
    @NotNull(message = "xml不能为空")
    private MultipartFile xmlFile;

}