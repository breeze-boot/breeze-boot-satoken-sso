/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysFile;
import com.breeze.boot.system.dto.FileDTO;
import com.breeze.boot.system.dto.FileSearchDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统文件服务
 *
 * @author gaoweixuan
 * @date 2022-09-02
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 列表页面
     *
     * @param fileSearchDTO 文件搜索DTO
     * @return {@link Page}<{@link SysFile}>
     */
    Page<SysFile> listPage(FileSearchDTO fileSearchDTO);

    /**
     * 上传
     *
     * @param fileDTO  文件DTO
     * @param request  请求
     * @param response 响应
     * @return {@link Result}<{@link Boolean}>
     */
    Result<String> upload(FileDTO fileDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 预览
     *
     * @param fileId 文件ID
     * @return {@link Result}<{@link Boolean}>
     */
    Result<String> preview(Long fileId);

    /**
     * 下载
     *
     * @param fileId   文件标识
     * @param response 响应
     */
    void download(Long fileId, HttpServletResponse response);

    /**
     * 通过id删除文件
     *
     * @param fileIds 文件IDS
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeFileByIds(List<Long> fileIds);

}
