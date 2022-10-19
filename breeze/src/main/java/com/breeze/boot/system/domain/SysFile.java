package com.breeze.boot.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class SysFile extends BaseModel<SysFile> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原文件名称
     */
    private String oldFileName;

    /**
     * 新文件名字
     */
    private String newFileName;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * url
     */
    private String url;

}
