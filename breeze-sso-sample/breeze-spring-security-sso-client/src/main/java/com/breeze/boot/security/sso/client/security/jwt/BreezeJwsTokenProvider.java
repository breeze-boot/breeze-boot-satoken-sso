/*
 * Copyright (c) 2024, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.security.sso.client.security.jwt;

import cn.hutool.core.convert.Convert;
import com.breeze.boot.security.sso.client.security.model.UserInfo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用于生成/校验/解析 JWS Token
 *
 * @author gaoweixuan
 * @since 2024/09/24
 */
@Slf4j
@Component
public class BreezeJwsTokenProvider {

    /**
     * 签名密钥，用于签名 Access Token
     */
    @Value("${spring.security.jwt.secret-key:890u8u8s9j10ks01ks8j34qnd91902uee1982je1ue89j182je1892j991j12je23}")
    private String sharedSecret;

    @Value("${spring.security.jwt.expiration:7200}")
    private int expiration;

    /**
     * 生成hmac jws签名器
     *
     * @return {@link JWSSigner }
     */
    @SneakyThrows
    public JWSSigner generateHmacJwsSigner() {
        SecureRandom random = new SecureRandom();
        random.nextBytes(sharedSecret.getBytes());
        return new MACSigner(sharedSecret);
    }

    /**
     * 获取hmac jws验证器
     *
     * @return {@link JWSVerifier }
     */
    @SneakyThrows
    public JWSVerifier getHmacJwsVerifier() {
        SecureRandom random = new SecureRandom();
        random.nextBytes(sharedSecret.getBytes());
        return new MACVerifier(sharedSecret);
    }

    /**
     * 构建jwtClaimsSet
     *
     * @param userDetails 用户详细信息
     * @return {@link JWTClaimsSet }
     */
    public JWTClaimsSet buildJWTClaimsSet(UserInfo userDetails) {
        Calendar signTime = Calendar.getInstance();
        Date signTimeTime = signTime.getTime();
        signTime.add(Calendar.MINUTE, expiration);
        Date expireTime = signTime.getTime();

        return new JWTClaimsSet.Builder()
                .issuer("http://localhost:18080")
                .subject(String.valueOf(userDetails.getUserId()))
                .audience(List.of("http://localhost:18080"))
                .expirationTime(expireTime)
                .notBeforeTime(signTimeTime)
                .issueTime(signTimeTime)
                .jwtID(UUID.randomUUID().toString())
                .claim("USER_ID", userDetails.getUserId())
                .claim("USERNAME", userDetails.getUsername())
                .claim("DEPT_ID", userDetails.getDeptId())
                .build();
    }

    /**
     * 创建jwt令牌
     *
     * @param authentication 身份验证
     * @return {@link String }
     */
    @SneakyThrows
    public String createJwtToken(Authentication authentication) {
        UserInfo userDetails = (UserInfo) authentication.getPrincipal();
        // 传入header 和 payload
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build(), this.buildJWTClaimsSet(userDetails));
        // 进行签名
        signedJWT.sign(this.generateHmacJwsSigner());

        String result = signedJWT.serialize();
        log.info("HMAC token is: \n" + result);
        return result;
    }

    /**
     * 验证 hmac token
     *
     * @param jwtToken iwt令牌
     */
    @SneakyThrows
    public void verifyHMACToken(String jwtToken) {
        SignedJWT parse = SignedJWT.parse(jwtToken);
        if (!parse.verify(this.getHmacJwsVerifier())) {
            throw new RuntimeException("invalid token");
        }
        verifyClaimsSet(parse.getJWTClaimsSet());
    }

    /**
     * 验证 ClaimsSet
     *
     * @param jwtClaimsSet jwt索赔集
     */
    private void verifyClaimsSet(JWTClaimsSet jwtClaimsSet) {
        boolean result = Calendar.getInstance().getTime().before(jwtClaimsSet.getExpirationTime());
        if (!result) {
            throw new RuntimeException("token expired");
        }
    }

    /**
     * 获取令牌str
     *
     * @param request 请求
     * @return {@link String }
     */
    public String getTokenStr(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return "";
    }

    /**
     * 获取身份验证
     *
     * @param token 代币
     * @return {@link Authentication }
     */
    @SneakyThrows
    public Authentication getAuthentication(String token) {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        Long userId = Convert.toLong(claimsSet.getClaim("USER_ID"));
        String username = Convert.toStr(claimsSet.getClaim("USERNAME"));
        Long deptId = Convert.toLong(claimsSet.getClaim("DEPT_ID"));

        UserInfo userDetails = new UserInfo();
        userDetails.setUserId(userId);
        userDetails.setUsername(username);
        userDetails.setDeptId(deptId);

        // 角色集合
        List<String> authorityList = (List<String>) Convert.toList(claimsSet.getClaim("AUTHORITIES"));
        Set<SimpleGrantedAuthority> authorities = authorityList != null ?
                authorityList.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet()) : Collections.emptySet();

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

}
