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

package com.breeze.xss.config;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * HttpServletRequestWrapper 采用装饰者模式对HttpServletRequest进行包装
 * 通过继承HttpServletRequestWrapper类去重写getParameterValues、getParameter等方法
 * 但是实际还是调用HttpServletRequest的相对应方法
 * <p>
 *
 * @author gaoweixuan
 * @date 2022-10-21
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * servlet请求
     */
    private final HttpServletRequest servletRequest;

    /**
     * @param servletRequest servlet请求
     */
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        this.servletRequest = servletRequest;
    }

    /**
     * 获取输入流
     *
     * @return {@link ServletInputStream}
     * @throws IOException IO异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        String contentType = servletRequest.getContentType();
        if (null != contentType && contentType.startsWith("multipart")) {
            return super.getInputStream();
        }
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return super.getInputStream();
        }
        FastByteArrayOutputStream bo = IoUtil.read(super.getInputStream());
        String json = bo.toString();
        if (StrUtil.isAllBlank(json)) {
            return super.getInputStream();
        }
        json = HtmlUtil.cleanHtmlTag(json);
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return bis.read();
            }
        };
    }

    /**
     * 获取参数
     *
     * @param name 名字
     * @return {@link String}
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(HtmlUtil.filter(name));
        if (StrUtil.isAllNotBlank(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    /**
     * 获取参数值 - 对数组参数进行特殊字符过滤
     *
     * @param name 名字
     * @return {@link String[]}
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return parameters;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = HtmlUtil.filter(parameters[i]);
        }
        return parameters;
    }

    /**
     * 获取请求内容
     *
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


    /**
     * 获取指定参数名的所有值的数组，
     * <p>
     * 如：checkbox，接收数组变量
     *
     * @return {@link Map}<{@link String}, {@link String[]}>
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = HtmlUtil.filter(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    /**
     * 获取头信息
     * <p>
     * 通过super.getHeader(name)来获取原始值
     *
     * @param name 名字
     * @return {@link String}
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(HtmlUtil.filter(name));
        if (StrUtil.isAllNotBlank(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

}
