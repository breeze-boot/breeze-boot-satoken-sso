package com.breeze.boot.system.service.impl;

import com.breeze.boot.security.entity.CurrentLoginUser;
import com.breeze.boot.security.entity.LoginUserDTO;
import com.breeze.boot.security.utils.SecurityUtils;
import com.breeze.boot.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户令牌服务
 *
 * @author breeze
 * @date 2022-10-19
 */
@Service
public class UserTokenService {

    /**
     * redis 模板
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 刷新菜单权限
     */
    public void refreshUser() {
        LoginUserDTO loginUserDTO = this.sysUserService.loadUserByUsername(SecurityUtils.getUserName());
        this.redisTemplate.opsForHash().put("sys:login_user", loginUserDTO.getUsername(), loginUserDTO);
    }

    /**
     * 获取当前登录用户
     *
     * @param loginUserDTO 登录用户dto
     * @return {@link UserDetails}
     */
    private CurrentLoginUser getCurrentLoginUser(LoginUserDTO loginUserDTO) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(loginUserDTO.getAuthorities().toArray(new String[0]));
        CurrentLoginUser currentLoginUser = new CurrentLoginUser(loginUserDTO,
                Objects.equals(loginUserDTO.getIsLock(), 0),
                true,
                true,
                Objects.equals(loginUserDTO.getIsLock(), 0),
                authorities);
        // 权限
        this.redisTemplate.opsForHash().put("sys:login_user", currentLoginUser.getUsername(), loginUserDTO);
        return currentLoginUser;
    }

    /**
     * 加载用户用户名
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户名没有发现异常
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserDTO loginUserDTO = sysUserService.loadUserByUsername(username);
        return getCurrentLoginUser(loginUserDTO);
    }

    public UserDetails loadUserByEmail(String email) {
        LoginUserDTO loginUserDTO = sysUserService.loadUserByEmail(email);
        return getCurrentLoginUser(loginUserDTO);
    }

    public UserDetails loadUserByPhone(String phone) {
        LoginUserDTO loginUserDTO = sysUserService.loadUserByPhone(phone);
        return getCurrentLoginUser(loginUserDTO);
    }
}
