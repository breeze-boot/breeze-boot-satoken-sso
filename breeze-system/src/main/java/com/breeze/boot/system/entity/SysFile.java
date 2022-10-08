package com.breeze.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统文件实体
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file")
public class SysFile extends BaseModel<SysFile> implements Serializable {
    /**
     * 旧文件名称
     */
    private String oldFileName;
    /**
     * 新文件名字
     */
    private String newFileName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户代码
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
