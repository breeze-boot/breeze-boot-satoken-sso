package com.breeze.boot.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.entity.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 系统文件实体
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file")
@Schema(description = "系统文件实体")
public class SysFile extends BaseModel<SysFile> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件标题
     */
    @Schema(description = "文件标题")
    private String title;

    /**
     * 原文件名称
     */
    @Schema(description = "原文件名称")
    private String originalFileName;

    /**
     * 文件格式
     */
    @Schema(description = "文件格式")
    private String contentType;

    /**
     * 新文件名字
     */
    @Schema(description = "新文件名字")
    private String newFileName;

    /**
     * 路径
     */
    @Schema(description = "路径")
    private String path;

    /**
     * 存储方式
     */
    @Schema(description = "存储方式 0 本地 1 minio")
    private Integer ossStyle;

}
