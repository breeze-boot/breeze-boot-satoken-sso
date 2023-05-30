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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.domain.SysRegisteredClient;
import com.breeze.boot.system.domain.TokenSettingsVO;
import com.breeze.boot.system.mapper.SysRegisterClientMapper;
import com.breeze.boot.system.params.RegisteredClientParam;
import com.breeze.boot.system.params.ResetClientSecretParam;
import com.breeze.boot.system.query.RegisterClientQuery;
import com.breeze.boot.system.service.SysRegisteredClientService;
import com.breeze.boot.system.vo.ClientSettingsVO;
import com.breeze.boot.system.vo.RegisteredClientVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 重写auth注册客户端库
 *
 * @author gaoweixuan
 * @date 2023/05/09
 */
@Slf4j
@Service
public class SysRegisteredClientServiceImpl extends ServiceImpl<SysRegisterClientMapper, SysRegisteredClient> implements SysRegisteredClientService {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper mapper = new ObjectMapper();

    public SysRegisteredClientServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * set 转换 string + [,]
     *
     * @return {@link Function}<{@link Set}<{@link String}>, {@link String}>
     */
    private static Function<Set<String>, String> convertSet2String() {
        return str -> String.join(",", str);
    }

    /**
     * 列表页面
     *
     * @param registerClientQuery 注册客户端参数
     * @return {@link Page}<{@link RegisteredClientVO}>
     */
    @Override
    public Page<RegisteredClientVO> listPage(RegisterClientQuery registerClientQuery) {
        Page<RegisteredClientVO> registeredClientPage = this.baseMapper.listPage(new Page<>(registerClientQuery.getCurrent(), registerClientQuery.getSize()), registerClientQuery);
        return registeredClientPage.setRecords(registeredClientPage.getRecords().stream().peek(this::getClientVO).collect(Collectors.toList()));
    }

    /**
     * 保存
     *
     * @param client 注册客户端参数
     */
    @SneakyThrows
    @Override
    public Result<Boolean> saveRegisteredClient(RegisteredClientParam client) {
        Assert.notNull(client, "registeredClient cannot be null");

        RegisteredClientParam.ClientSettings clientSettings = client.getClientSettings();
        Assert.notNull(clientSettings, "clientSettings cannot be null");

        RegisteredClientParam.TokenSettings tokenSettings = client.getTokenSettings();
        Assert.notNull(tokenSettings, "tokenSettings cannot be null");
        SysRegisteredClient byClientId = this.getByClientId(client.getClientId());
        if (Objects.nonNull(byClientId)) {
            return Result.fail(Boolean.FALSE, "已经存在此客户端");
        }
        this.insertRegisteredClient(client);
        return Result.ok();
    }

    /**
     * 更新
     *
     * @param client 注册客户端参数
     * @return {@link Boolean}
     */
    @Override
    public Boolean update(RegisteredClientParam client) {
        this.updateRegisteredClient(client);
        return Boolean.TRUE;
    }

    /**
     * 插入注册客户端
     *
     * @param client 注册客户端
     */
    private void insertRegisteredClient(RegisteredClientParam client) {
        this.save(this.buildClient(client));
    }

    @SneakyThrows
    private void getClientVO(RegisteredClientVO registeredClientVO) {
        registeredClientVO.setClientSettings(mapper.readValue(registeredClientVO.getJsonClientSettings(), new TypeReference<ClientSettingsVO>() {
        }));
        registeredClientVO.setTokenSettings(mapper.readValue(registeredClientVO.getJsonTokenSettings(), new TypeReference<TokenSettingsVO>() {
        }));
        registeredClientVO.setAuthorizationGrantTypes(Arrays.stream(registeredClientVO.getStrAuthorizationGrantTypes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setScopes(Arrays.stream(registeredClientVO.getStrScopes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setClientAuthenticationMethods(Arrays.stream(registeredClientVO.getStrClientAuthenticationMethods().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setRedirectUris(Arrays.stream(registeredClientVO.getStrRedirectUris().split(",")).collect(Collectors.toSet()));
    }

    @SneakyThrows
    private SysRegisteredClient buildClient(RegisteredClientParam client) {
        // @formatter:off
        SysRegisteredClient registeredClient = SysRegisteredClient.builder()
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                .clientSecret(Optional.ofNullable(client.getClientSecret()).map(this.passwordEncoder::encode).orElse(null))
                .clientAuthenticationMethods(Optional.ofNullable(client.getClientAuthenticationMethods()).map(convertSet2String()).orElse(null))
                .authorizationGrantTypes(Optional.ofNullable(client.getAuthorizationGrantTypes()).map(convertSet2String()).orElse(null))
                .scopes(Optional.ofNullable(client.getScopes()).map(convertSet2String()).orElse(null))
                .redirectUris(Optional.ofNullable(client.getRedirectUris()).map(convertSet2String()).orElse(null))
                .jsonClientSettings(Optional.ofNullable(client.getClientSettings()).map(this::write).orElse(null))
                .jsonTokenSettings(Optional.ofNullable(client.getTokenSettings()).map(this::write).orElse(null))
                .build();
        registeredClient.setId(client.getId());
        return registeredClient;
        // @formatter:on
    }

    /**
     * 更新注册客户端
     *
     * @param client 注册客户端参数
     */
    private void updateRegisteredClient(RegisteredClientParam client) {
        this.updateById(this.buildClient(client));
    }

    /**
     * 获取客户端由客户端id
     *
     * @param clientId 客户端id
     * @return {@link SysRegisteredClient}
     */
    @Override
    public SysRegisteredClient getByClientId(String clientId) {
        return this.baseMapper.getRegisteredClientBy(RegisterClientQuery.builder().clientId(clientId).build());
    }

    /**
     * 获取客户端通过id
     *
     * @param id id
     * @return {@link SysRegisteredClient}
     */
    @Override
    public SysRegisteredClient getById(String id) {
        return this.baseMapper.getRegisteredClientBy(RegisterClientQuery.builder().id(id).build());
    }

    /**
     * 删除通过id
     *
     * @param ids id集合
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteById(List<Long> ids) {
        return Result.ok(this.removeBatchByIds(ids));
    }

    /**
     * 重置客户端密钥
     *
     * @param resetClientSecretParam 重置客户秘密参数
     * @return {@link Boolean}
     */
    @Override
    public Boolean resetClientSecretParam(ResetClientSecretParam resetClientSecretParam) {
        resetClientSecretParam.setClientSecret(this.passwordEncoder.encode(resetClientSecretParam.getClientSecret()));
        return this.update(Wrappers.<SysRegisteredClient>lambdaUpdate()
                .set(SysRegisteredClient::getClientSecret, resetClientSecretParam.getClientSecret())
                .eq(SysRegisteredClient::getId, resetClientSecretParam.getId()));
    }

    /**
     * 信息
     *
     * @param clientId 客户端ID
     * @return {@link RegisteredClientVO}
     */
    @SneakyThrows
    @Override
    public RegisteredClientVO info(Long clientId) {
        SysRegisteredClient registeredClient = this.getById(clientId);
        RegisteredClientVO registeredClientVO = new RegisteredClientVO();
        BeanUtil.copyProperties(registeredClient, registeredClientVO);
        ClientSettingsVO clientSettings = mapper.readValue(registeredClientVO.getJsonClientSettings(), new TypeReference<ClientSettingsVO>() {
        });
        registeredClientVO.setClientSettings(clientSettings);
        TokenSettingsVO tokenSettings = mapper.readValue(registeredClientVO.getJsonTokenSettings(), new TypeReference<TokenSettingsVO>() {
        });
        registeredClientVO.setTokenSettings(tokenSettings);

        registeredClientVO.setAuthorizationGrantTypes(Arrays.stream(registeredClient.getAuthorizationGrantTypes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setScopes(Arrays.stream(registeredClient.getScopes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setClientAuthenticationMethods(Arrays.stream(registeredClient.getClientAuthenticationMethods().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setRedirectUris(Arrays.stream(registeredClient.getRedirectUris().split(",")).collect(Collectors.toSet()));
        return registeredClientVO;
    }

    /**
     * 写
     *
     * @param data 数据
     * @return {@link String}
     */
    public String write(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

}
