/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.system.domain.SysMsg;
import com.breeze.boot.system.dto.MsgSearchDTO;
import com.breeze.boot.system.mapper.SysMsgMapper;
import com.breeze.boot.system.service.SysMsgService;
import org.springframework.stereotype.Service;

/**
 * 系统消息服务
 *
 * @author breeze
 * @date 2022-11-20
 */
@Service
public class SysMsgServiceImpl extends ServiceImpl<SysMsgMapper, SysMsg> implements SysMsgService {

    /**
     * 列表页面
     *
     * @param msgSearchDTO 消息搜索dto
     * @return {@link IPage}<{@link SysMsg}>
     */
    @Override
    public IPage<SysMsg> listPage(MsgSearchDTO msgSearchDTO) {
        Page<SysMsg> msgPage = new Page<>(msgSearchDTO.getCurrent(), msgSearchDTO.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(msgSearchDTO.getMsgTitle()), SysMsg::getMsgTitle, msgSearchDTO.getMsgTitle())
                .like(StrUtil.isAllNotBlank(msgSearchDTO.getMsgCode()), SysMsg::getMsgCode, msgSearchDTO.getMsgCode())
                .page(msgPage);
    }
}
