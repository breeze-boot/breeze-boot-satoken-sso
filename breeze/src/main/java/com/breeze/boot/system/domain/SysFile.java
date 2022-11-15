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
     * 新文件名字
     */
    @Schema(description = "新文件名字")
    private String newFileName;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 路径
     */
    @Schema(description = "路径")
    private String path;
}
