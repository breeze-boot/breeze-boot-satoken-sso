package com.breeze.boot.system.dto;

import lombok.Data;

/**
 * 文件dto
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
public class FileDTO extends PageDTO {
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
