package com.breeze.boot.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.entity.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 系统文件实体
 *
 * @author breeze
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
     * 原文件名称
     */
    @Schema(description = "原文件名称")
    private String oldFileName;

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
     * 用户编码
     */
    @Schema(description = "用户编码")
    private String userCode;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * url
     */
    @Schema(description = "url")
    private String url;

}
