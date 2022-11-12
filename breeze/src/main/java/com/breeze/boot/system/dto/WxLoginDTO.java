package com.breeze.boot.system.dto;

import lombok.Data;

/**
 * wx登录dto
 *
 * @author gaoweixuan
 * @date 2022-11-09
 */
@Data
public class WxLoginDTO {

    /**
     * 尼克名字
     */
    private String nickName;

    /**
     * 头像 url
     */
    private String avatarUrl;

    /**
     * 代码
     */
    private String code;
}
