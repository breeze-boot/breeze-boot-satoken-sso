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

package com.breeze.boot.auth.constants;

import com.google.common.collect.Lists;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义授权授予类型
 *
 * @author gaoweixuan
 * @date 2023-04-24
 */
public final class CustomAuthorizationGrantType implements Serializable {

    private static final long serialVersionUID = 580L;

    public static final AuthorizationGrantType SMS_CODE = new AuthorizationGrantType("sms_code");

    public static final AuthorizationGrantType EMAIL_CODE = new AuthorizationGrantType("email_code");

    private final String value;

    public CustomAuthorizationGrantType(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            CustomAuthorizationGrantType that = (CustomAuthorizationGrantType) obj;
            return this.getValue().equals(that.getValue());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }


    public static List<String> getGrantTypes() {
        // @formatter:off
        return Lists.newArrayList(
                AuthorizationGrantType.PASSWORD.getValue(),
                EMAIL_CODE.getValue(),
                SMS_CODE.getValue()
        );
        // @formatter:on
    }

}
