package com.breeze.boot.core.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

public class I18NLocaleResolver implements LocaleResolver {

    @NotNull
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取请求中的语言参数
        String language = httpServletRequest.getHeader("Accept-Language");
        if (StringUtils.hasText(language)) {
            return new Locale(language);
        }
        // 默认的 Locale 对象
        return Locale.getDefault();
    }

    @Override
    public void setLocale(@NotNull HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse,
                          Locale locale) {}
}
