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

package com.breeze.boot.security.service;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.core.base.BaseLoginUser;
import com.breeze.boot.core.utils.BreezeThreadLocal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.model.entity.UserPrincipal;
import com.breeze.boot.security.exception.NotSupportException;
import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 用户服务接口
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
public interface IUserDetailService extends UserDetailsService {

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link UserPrincipal}
     */
    UserPrincipal loadUserByPhone(String phone);

    /**
     * 加载用户通过电子邮件
     *
     * @param email 电子邮件
     * @return {@link UserPrincipal}
     */
    UserPrincipal loadUserByEmail(String email);

    /**
     * 获取登录用户信息
     *
     * @param loginUserResult 登录用户结果
     * @return {@link UserPrincipal}
     */
    default UserPrincipal getLoginUserInfo(Result<BaseLoginUser> loginUserResult) {
        BaseLoginUser baseLoginUser = loginUserResult.getData();
        if (baseLoginUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return this.convertResponseUserData(baseLoginUser);
    }

    /**
     * 获取登录用户
     *
     * @param baseLoginUser 登录用户
     * @return {@link UserPrincipal}
     */
    default UserPrincipal convertResponseUserData(BaseLoginUser baseLoginUser) {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        // 保存权限信息
        Optional.ofNullable(baseLoginUser.getAuthorities()).ifPresent(auth -> getAuthorityList(auth, authorities));
        // 保存角色信息
        Optional.ofNullable(baseLoginUser.getUserRoleCodes()).ifPresent(roleCode -> getAuthorityList(roleCode, authorities));
        return new UserPrincipal(
                baseLoginUser.getUsername(),
                baseLoginUser.getPassword(),
                Objects.equals(baseLoginUser.getIsLock(), 0),
                true,
                true,
                Objects.equals(baseLoginUser.getIsLock(), 0),
                authorities,
                baseLoginUser.getId(),
                baseLoginUser.getDeptId(),
                baseLoginUser.getDeptName(),
                baseLoginUser.getUserCode(),
                baseLoginUser.getAmountName(),
                baseLoginUser.getAvatar(),
                baseLoginUser.getPhone(),
                baseLoginUser.getSex(),
                baseLoginUser.getAmountType(),
                baseLoginUser.getIsLock(),
                baseLoginUser.getEmail(),
                baseLoginUser.getUserRoleCodes(),
                baseLoginUser.getUserRoleIds(),
                baseLoginUser.getTenantId(),
                baseLoginUser.getPermission().getPermissions(),
                baseLoginUser.getPermission().getExcludeColumn());
    }

    /**
     * 获取请求租户Id参数
     */
    default void getTenantId(ServletRequestAttributes requestAttributes) {
        assert requestAttributes != null;
        HttpServletRequest contextRequest = requestAttributes.getRequest();
        String tenantIdHeader = contextRequest.getHeader("X-TENANT-ID");
        if (StrUtil.isAllNotBlank(tenantIdHeader)) {
            BreezeThreadLocal.set(Long.valueOf(tenantIdHeader));
            return;
        }
        String tenantIdParam = contextRequest.getParameter("X-TENANT-ID");
        if (StrUtil.isAllNotBlank(tenantIdParam)) {
            BreezeThreadLocal.set(Long.valueOf(tenantIdParam));
            return;
        }
        throw new NotSupportException("tenantId Not Found");
    }

    /**
     * 获得权限列表
     *
     * @param auth        身份验证
     * @param authorities 当局
     */
    default void getAuthorityList(Set<String> auth, List<GrantedAuthority> authorities) {
        authorities.addAll(AuthorityUtils.createAuthorityList(auth.toArray(new String[0])));
    }

}
