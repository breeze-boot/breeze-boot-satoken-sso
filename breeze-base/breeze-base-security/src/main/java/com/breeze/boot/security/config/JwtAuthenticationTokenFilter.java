///*
// * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.breeze.boot.security.config;
//
//import cn.hutool.jwt.JWTUtil;
//import cn.hutool.jwt.signers.JWTSigner;
//import cn.hutool.jwt.signers.JWTSignerUtil;
//import com.breeze.boot.core.enums.CurrentLoginUser;
//import com.breeze.boot.core.enums.LoginUserDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.BadJwtException;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.annotation.Resource;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//import java.util.Objects;
//
///**
// * jwt身份验证令牌过滤器
// *
// * @author breeze
// * @date 2022-08-31
// */
//@Slf4j
//@Configuration
//public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//
//    /**
//     * 忽略url属性
//     */
//    @Autowired
//    private IgnoreUrlProperties ignoreUrlProperties;
//
//    /**
//     * 复述,模板
//     */
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * 登录jwt译码器
//     */
//    @Autowired
//    private JwtDecoder loginJwtDecoder;
//
//    /**
//     * jwt配置
//     */
//    @Autowired
//    private JwtConfig jwtConfig;
//
//    /**
//     * DO过滤器
//     *
//     * @param request     请求
//     * @param response    响应
//     * @param filterChain 过滤器链
//     * @throws ServletException servlet异常
//     * @throws IOException      IO exception
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (!requireAuthentication(request)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        //获取token
//        String token = request.getHeader("Authorization");
//        if (!StringUtils.hasLength(token)) {
//            //放行
//            throw new BadCredentialsException("令牌错误，请重新登录");
//        }
//        if (!StringUtils.startsWithIgnoreCase(token, "Bearer ")) {
//            //放行
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //解析token
//        Object usernameObj;
//        try {
//            Jwt jwt = loginJwtDecoder.decode(token.replace("Bearer ", ""));
//            JWTSigner jwtSigner = JWTSignerUtil.rs256(jwtConfig.rsaPublicKey);
//            if (!JWTUtil.verify(jwt.getTokenValue(), jwtSigner)) {
//                throw new BadCredentialsException("令牌错误，请重新登录!");
//            }
//            //获取用户名称
//            usernameObj = jwt.getClaims().get("username");
//            if (Objects.isNull(usernameObj)) {
//                throw new BadCredentialsException("令牌错误，请重新登录!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BadJwtException(e.getMessage());
//        }
//        //从redis中获取用户信息
//        LoginUserDTO loginUser = (LoginUserDTO) this.redisTemplate.opsForHash().get("sys:login_user", String.valueOf(usernameObj));
//        // 权限
//        List<GrantedAuthority> authorities
//                = AuthorityUtils.createAuthorityList(loginUser.getAuthorities().toArray(new String[0]));
//        CurrentLoginUser currentLoginUser = new CurrentLoginUser(loginUser,
//                Objects.equals(loginUser.getIsLock(), 0),
//                true,
//                true,
//                Objects.equals(loginUser.getIsLock(), 0),
//                authorities);
//        Authentication auth = new UsernamePasswordAuthenticationToken(currentLoginUser, null, authorities);
//        //存入SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        //放行
//        filterChain.doFilter(request, response);
//    }
//
//
//    /**
//     * 需要身份验证
//     *
//     * @param request 请求
//     * @return boolean
//     */
//    public boolean requireAuthentication(HttpServletRequest request) {
//        if (ignoreUrlProperties.getExcludeUrls() == null || ignoreUrlProperties.getExcludeUrls().size() == 0) {
//            return true;
//        }
//        for (String url : ignoreUrlProperties.getExcludeUrls()) {
//            if (new AntPathRequestMatcher(url).matches(request)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//}
