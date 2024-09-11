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

package com.breeze.boot.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.config.SaSsoClientConfig;
import cn.dev33.satoken.stp.StpUtil;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.model.User;
import com.breeze.boot.service.UserService;
import com.dtflys.forest.Forest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

@Configuration
@RequiredArgsConstructor
public class SsoClientConfigure {

    private final UserService userService;

    /**
     * 配置SSO相关参数
     *
     * @param ssoClient sso客户端
     */
    @Autowired
    private void configSsoClient(SaSsoClientConfig ssoClient) {
        // 自定义校验 ticket 返回值的处理逻辑 （每次从认证中心获取校验 ticket 的结果后调用）
        ssoClient.ticketResultHandle = (ctr, back) -> {
            System.out.println("--------- 自定义 ticket 校验结果处理函数 ---------");
            System.out.println("此账号在 sso-server 的 userId：" + ctr.loginId);
            System.out.println("此账号在 sso-server 会话剩余有效期：" + ctr.remainSessionTimeout + " 秒");
            System.out.println("此账号返回的 email 信息：" + ctr.result.get("email"));

            // 模拟代码：
            // 根据 id 字段找到此账号在本系统对应的 user 信息
            // 采用方案三： https://sa-token.cc/doc.html#/sso/user-data-sync?id=_3%e3%80%81%e6%96%b9%e6%a1%88%e4%b8%89%ef%bc%9a%e5%ad%97%e6%ae%b5%e5%85%b3%e8%81%94
            String id = (String) ctr.result.get("id");
            User user = userService.loadUserByClientId(id);

            // 如果找不到，说明是首次登录本系统的新用户，提示异常信息或注册一个新账号
            if (user == null) {
                throw new BreezeBizException(ResultCode.USER_NOT_FOUND);
            }

            // 进行登录
            StpUtil.login(user.getId(), ctr.remainSessionTimeout);
            StpUtil.getSession().set("user", user);

            // 一切工作完毕，重定向回 back 页面
            return SaHolder.getResponse().redirect(back);
        };

        // 配置Http请求处理器
        ssoClient.sendHttp = url -> {
            System.out.println("------ 发起请求：" + url);
            String resStr = Forest.get(url + "&" + X_TENANT_ID + "=" + Optional.ofNullable(SaHolder.getRequest().getHeader(X_TENANT_ID)).orElse("")).executeAsString();
            System.out.println("------ 请求结果：" + resStr);
            return resStr;
        };
    }

}
