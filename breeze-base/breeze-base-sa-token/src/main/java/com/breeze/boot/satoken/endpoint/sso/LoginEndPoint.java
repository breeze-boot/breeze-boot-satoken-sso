/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.satoken.endpoint.sso;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.core.utils.AesUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import com.breeze.boot.satoken.config.propertise.AesSecretProperties;
import com.breeze.boot.satoken.spt.IUserDetailService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

import static com.breeze.boot.core.constants.CoreConstants.USER_TYPE;
import static com.breeze.boot.log.enums.LogEnum.LogType.LOGIN;
import static com.breeze.boot.log.enums.LogEnum.Result.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginEndPoint {
    private final static String BCRYPT = "{bcrypt}";
    private final AesSecretProperties aesSecretProperties;
    private final IUserDetailService userDetailService;
    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    @RequestMapping("/simpleLogin")
    public Result<Map<String, @Nullable Object>> login(String username, String password) {
        SysLogBO sysLogBO = this.buildLog(username);
        String decodePwd = AesUtil.decryptStr(password, this.aesSecretProperties.getAesSecret());
        UserPrincipal userPrincipal = this.userDetailService.loadUserByUsername(username);
        if (BCrypt.checkpw(decodePwd, userPrincipal.getPassword().replace(BCRYPT, ""))) {
            sysLogBO.setResult(SUCCESS.getCode());
            this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
            StpUtil.login(userPrincipal.getId());
            StpUtil.getSession().set(USER_TYPE, userPrincipal);
            Map<String, @Nullable Object> resultMap = Maps.newHashMap();
            resultMap.put("access_token", StpUtil.getTokenValue());
            resultMap.put("user_info", userPrincipal);
            return Result.ok(resultMap, "登录成功");
        }
        return Result.fail("用户名或密码错误");
    }

    /**
     * 执行日志 业务
     *
     * @param username 参数
     * @return {@link SysLogBO }
     */
    @SneakyThrows
    private SysLogBO buildLog(String username) {
        SaRequest request = SaHolder.getRequest();
        String userAgent = request.getHeader("User-Agent");
        return SysLogBO.builder()
                .system(userAgent)
                .logTitle(LogType.USERNAME_LOGIN.getName())
                .doType(LogType.USERNAME_LOGIN.getCode())
                .logType(LOGIN.getCode())
                .ip(request.getHost())
                .requestType(request.getMethod())
                .paramContent(username)
                .createTime(LocalDateTime.now())
                .createBy(username)
                .createName(username)
                .build();
    }
}
