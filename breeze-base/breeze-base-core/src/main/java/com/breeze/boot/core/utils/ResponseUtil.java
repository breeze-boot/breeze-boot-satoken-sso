/*
 * Copyright 2022 the original author or authors.
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

package com.breeze.boot.core.utils;

import com.breeze.boot.core.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 响应实效
 * response 返回JSON工具类
 *
 * @author breeze
 * @date 2021/10/2
 */
public class ResponseUtil {

    /**
     * 响应
     *
     * @param response  响应
     * @param resultMsg 结果味精
     */
    public static void response(HttpServletResponse response, String resultMsg) {
        ServletOutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            out = response.getOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            out.write(objectMapper.writeValueAsString(Result.fail(resultMsg)).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
