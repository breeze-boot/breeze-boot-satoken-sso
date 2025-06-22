/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.satoken.endpoint.thrid;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.stp.StpUtil;
import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import com.breeze.boot.satoken.spt.IUserDetailService;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

import static com.breeze.boot.core.constants.CoreConstants.USER_TYPE;
import static com.breeze.boot.log.enums.LogEnum.LogType.LOGIN;
import static com.breeze.boot.log.enums.LogEnum.Result.FAIL;
import static com.breeze.boot.log.enums.LogEnum.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/dingTalk")
public class DingTalkEndPoint {

    private final IUserDetailService userDetailService;

    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    public DingTalkEndPoint(IUserDetailService userDetailService, PublisherSaveSysLogEvent publisherSaveSysLogEvent) {
        this.userDetailService = userDetailService;
        this.publisherSaveSysLogEvent = publisherSaveSysLogEvent;
    }

    public static Client authClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }

    /**
     * 获取用户token
     * <p>
     * 接口地址：注意/auth与钉钉登录与分享的回调域名地址一致
     *
     * @param authCode 授权码
     * @return {@link String }
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public Result<Map<String, Object>> getAccessToken(@RequestParam(value = "authCode") String authCode) {
        try {
            Client client = authClient();
            GetUserTokenRequest getUserTokenRequest = buildGetUserTokenRequest(authCode);
            GetUserTokenResponse getUserTokenResponse = client.getUserToken(getUserTokenRequest);
            if (getUserTokenResponse == null || getUserTokenResponse.getBody() == null) {
                return Result.fail("钉钉返回数据为空");
            }

            String accessToken = getUserTokenResponse.getBody().getAccessToken();
            GetUserResponseBody userinfo = getUserinfo(accessToken);
            UserPrincipal userPrincipal = userDetailService.loadUserByDingOpenId(userinfo.getOpenId());
            if (userPrincipal == null) {
                return Result.fail("用户未绑定钉钉账户");
            }
            SysLogBO sysLogBO = this.buildLog(userPrincipal.getUsername());
            sysLogBO.setResult(SUCCESS.getCode());
            this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));

            StpUtil.login(userPrincipal.getId());
            StpUtil.getSession().set(USER_TYPE, userPrincipal);
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("access_token", StpUtil.getTokenValue());
            return Result.ok(resultMap, "登录成功");
        } catch (Exception e) {
            log.error("钉钉登录失败", e);
            SysLogBO sysLogBO = this.buildLog("unknown");
            sysLogBO.setResult(FAIL.getCode());
            sysLogBO.setResultMsg(e.getMessage());
            this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
            return Result.fail("登录失败");
        }
    }

    private GetUserTokenRequest buildGetUserTokenRequest(String authCode) {
        return new GetUserTokenRequest()
                .setClientId("dingmdfbjobhznnpqsy4") // 使用配置注入
                .setClientSecret("8Ht4BHDOoWILPUSQRYW27vsf9xs2i9toxs4_gzTt4lpV2uMGA2bJkh2dLzdPEALa") // 使用配置注入
                .setCode(authCode)
                .setGrantType("authorization_code");
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
                .logTitle(LogType.DING_LOGIN.getName())
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

    public static com.aliyun.dingtalkcontact_1_0.Client contactClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkcontact_1_0.Client(config);
    }

    /**
     * 获取用户个人信息
     *
     * @param accessToken 访问令牌
     * @return {@link String }
     * @throws Exception 例外
     */
    public GetUserResponseBody getUserinfo(String accessToken) throws Exception {
        com.aliyun.dingtalkcontact_1_0.Client client = contactClient();
        GetUserHeaders getUserHeaders = new GetUserHeaders();
        getUserHeaders.xAcsDingtalkAccessToken = accessToken;
        //获取用户个人信息
        return client.getUserWithOptions("me", getUserHeaders, new RuntimeOptions()).getBody();
    }

}
