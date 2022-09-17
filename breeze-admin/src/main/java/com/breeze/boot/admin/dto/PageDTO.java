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

package com.breeze.boot.admin.dto;

/**
 * 页面dto
 *
 * @author breeze
 * @date 2022-08-31
 */
public class PageDTO {

    /**
     * 当前页
     */
    private Integer current;
    /**
     * 大小
     */
    private Integer size;

    /**
     * 页面dto
     */
    public PageDTO() {
    }

    /**
     * 得到当前
     *
     * @return {@link Integer}
     */
    public Integer getCurrent() {
        if (this.current == null) {
            return 1;
        }
        return current;
    }

    /**
     * 设置当前
     *
     * @param current 当前
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * 得到大小
     *
     * @return {@link Integer}
     */
    public Integer getSize() {
        if (this.size == null) {
            return 10;
        }
        return size;
    }

    /**
     * 设置大小
     *
     * @param size 大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }
}
