package com.breeze.boot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * wx登录dto
 *
 * @author gaoweixuan
 * @date 2022-11-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户查询参数DTO")
public class WxLoginDTO {

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 头像 url
     */
    @Schema(description = "头像地址")
    private String avatarUrl;

    /**
     * code
     */
    @Schema(description = "code")
    private String code;

}
