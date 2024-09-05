package com.breeze.boot.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    private static MessageSource messageSource;

    public static String getMessage(String key, String... dynamicValues) {
        return messageSource.getMessage(key, dynamicValues, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String key, Locale locale, String... dynamicValues) {
        return messageSource.getMessage(key, dynamicValues, locale);
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }
}
