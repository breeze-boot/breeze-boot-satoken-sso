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

package com.breeze.boot.sso.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.sso.config.SaSsoServerConfig;
import cn.dev33.satoken.sso.exception.SaSsoException;
import cn.dev33.satoken.stp.StpUtil;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.jackson.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.AesUtil;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.BreezeThreadLocal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import com.breeze.boot.sso.spt.IUserDetailService;
import com.dtflys.forest.Forest;
import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;

import static com.breeze.boot.core.constants.CoreConstants.USER_TYPE;
import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;
import static com.breeze.boot.log.enums.LogEnum.LogType.LOGIN;
import static com.breeze.boot.log.enums.LogEnum.Result.FAIL;
import static com.breeze.boot.log.enums.LogEnum.Result.SUCCESS;

/**
 * oauth令牌配置
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RequiredArgsConstructor
public class SaTokenSsoServerConfigure {

    private final static String BCRYPT = "{bcrypt}";
    private final String EXCEPTION = "登录异常";
    private final IUserDetailService userDetailService;
    private final AesSecretProperties aesSecretProperties;
    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;
    private final Function<HttpServletRequest, Boolean> captchaServiceFunction;

    /**
     * 配置SSO相关参数
     *
     * @param ssoServer 单点登录服务器
     */
    @Autowired
    private void configSsoServer(SaSsoServerConfig ssoServer) {
        // 配置：Ticket校验函数
        ssoServer.checkTicketAppendData = (loginId, result) -> {
            log.info("-------- 追加返回信息到 sso-client --------");
            try {
                String tenantId = SaHolder.getRequest().getParam(X_TENANT_ID);
                AssertUtil.isNotNull(tenantId, ResultCode.TENANT_NOT_FOUND);
                BreezeThreadLocal.set(Long.valueOf(tenantId));
                // 在校验 ticket 后，给 sso-client 端追加返回信息的函数
                UserPrincipal userPrincipal = this.userDetailService.loadUserByUserId(String.valueOf(loginId));
                // 你也可以将整个user 对象的信息都返回到 sso-client
                result.set("userPrincipal", userPrincipal);
            } finally {
                BreezeThreadLocal.remove();
            }
            return result;
        };

        // 配置：未登录时返回的View
        ssoServer.notLoginView = () -> new ModelAndView("sa-login.html");

        // 配置：登录处理函数
        ssoServer.doLoginHandle = (name, pwd) -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Assert.notNull(requestAttributes, "requestAttributes is null");
            SysLogBO sysLogBO = this.buildLog(requestAttributes.getRequest(), name);
            try {
                // AssertUtil.isTrue(captchaServiceFunction.apply(requestAttributes.getRequest()), ResultCode.VERIFY_UN_FOUND);
                String decodePwd = AesUtil.decryptStr(pwd, this.aesSecretProperties.getAesSecret());
                UserPrincipal userPrincipal = this.userDetailService.loadUserByUsername(name);
                String pw_hash = BCrypt.hashpw(pwd, BCrypt.gensalt());
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
            } catch (Exception e) {
                sysLogBO.setResult(FAIL.getCode());
                sysLogBO.setResultMsg(e.getMessage());
                this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
            }
            throw new SaSsoException(EXCEPTION);
        };

        // 配置 Http 请求处理器 （在模式三的单点注销功能下用到，如不需要可以注释掉）
        ssoServer.sendHttp = url -> {
            try {
                log.info("------ 发起请求：" + url);
                String resStr = Forest.get(url).executeAsString();
                log.info("------ 请求结果：" + resStr);
                return resStr;
            } catch (Exception e) {
                log.error("", e);
                return null;
            }
        };
    }

    /**
     * 执行日志 业务
     *
     * @param request  请求
     * @param username 参数
     * @return {@link SysLogBO }
     */
    @SneakyThrows
    private SysLogBO buildLog(HttpServletRequest request, String username) {
        String userAgent = request.getHeader("User-Agent");
        return SysLogBO.builder()
                .system(userAgent)
                .logTitle(LogType.USERNAME_LOGIN.getName())
                .doType(LogType.USERNAME_LOGIN.getCode())
                .logType(LOGIN.getCode())
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .paramContent(username)
                .createTime(LocalDateTime.now())
                .createBy(username)
                .createName(username)
                .build();
    }

}
