package com.breeze.security.utils;

import cn.hutool.core.util.StrUtil;
import com.breeze.security.userextension.CurrentLoginUser;
import com.breeze.security.userextension.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.SpringSecurityMessageSource;

import java.util.Objects;
import java.util.function.Function;

/**
 * 登录检查
 *
 * @author gaoweixuan
 * @date 2022-11-09
 */
@Slf4j
public class LoginCheck {

    /**
     * 消息
     */
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    /**
     * 登录校验
     *
     * @param authentication 身份验证
     * @param loginUser      登录用户
     */
    public void checkCode(CurrentLoginUser loginUser, AbstractAuthenticationToken authentication, Function<LoginUser, String> function) {
        if (Objects.isNull(authentication.getCredentials())) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String code = function.apply(loginUser.getLoginUser());
        String presentCode = authentication.getCredentials().toString();
        if (!StrUtil.equals(presentCode, String.valueOf(code))) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

}
