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
import com.breeze.boot.core.base.UserInfoDTO;
import com.breeze.boot.core.utils.BreezeThreadLocal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.security.model.entity.UserPrincipal;
import com.breeze.boot.security.exception.TenantNotSupportException;
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

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

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
     * @param userResultData 登录用户结果
     * @return {@link UserPrincipal}
     */
    default UserInfoDTO getUserPrincipal(Result<UserInfoDTO> userResultData) {
        UserInfoDTO userInfoDTO = userResultData.getData();
        if (userInfoDTO == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return userInfoDTO;
    }

    /**
     * 获取登录用户
     *
     * @param userInfo 登录用户
     * @return {@link UserPrincipal}
     */
    default UserPrincipal convertResponseUserData(UserInfoDTO userInfo) {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        // 保存权限信息
        Optional.ofNullable(userInfo.getAuthorities()).ifPresent(auth -> getAuthorityList(auth, authorities));
        // 保存角色信息
        Optional.ofNullable(userInfo.getUserRoleCodes()).ifPresent(roleCode -> getAuthorityList(roleCode, authorities));
        return new UserPrincipal(
                userInfo.getUsername(),
                userInfo.getPassword(),
                Objects.equals(userInfo.getIsLock(), 0),
                true,
                true,
                Objects.equals(userInfo.getIsLock(), 0),
                authorities,
                userInfo.getUserId(),
                userInfo.getDeptId(),
                userInfo.getDeptName(),
                userInfo.getUserCode(),
                userInfo.getDisplayName(),
                userInfo.getAvatar(),
                userInfo.getPhone(),
                userInfo.getSex(),
                userInfo.getAmountType(),
                userInfo.getIsLock(),
                userInfo.getEmail(),
                userInfo.getUserRoleCodes(),
                userInfo.getUserRoleIds(),
                userInfo.getTenantId(),
                userInfo.getPermissionType(),
                userInfo.getRowPermissionCode());
    }

    /**
     * 获取请求租户Id参数
     */
    default void getTenantId(ServletRequestAttributes requestAttributes) {
        assert requestAttributes != null;
        HttpServletRequest contextRequest = requestAttributes.getRequest();
        String tenantIdHeader = contextRequest.getHeader(X_TENANT_ID);
        if (StrUtil.isAllNotBlank(tenantIdHeader)) {
            BreezeThreadLocal.set(Long.valueOf(tenantIdHeader));
            return;
        }
        String tenantIdParam = contextRequest.getParameter(X_TENANT_ID);
        if (StrUtil.isAllNotBlank(tenantIdParam)) {
            BreezeThreadLocal.set(Long.valueOf(tenantIdParam));
            return;
        }
        throw new TenantNotSupportException("tenantId Not Found");
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
