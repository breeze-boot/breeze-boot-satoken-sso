package com.breeze.boot.system.service.impl;

import com.breeze.boot.jwtlogin.entity.CurrentLoginUser;
import com.breeze.boot.jwtlogin.entity.LoginUserDTO;
import com.breeze.boot.jwtlogin.service.IUserDetailsService;
import com.breeze.boot.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 本地用户服务impl
 *
 * @author breeze
 * @date 2022-08-31
 */
@Component
public class LocalUserServiceImpl implements IUserDetailsService {

    /**
     * 系统用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 复述,模板
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 加载用户用户名
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户名没有发现异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        LoginUserDTO loginUserDTO = sysUserService.loadUserByUsername(username);
        if (Objects.isNull(loginUserDTO)) {
            throw new UsernameNotFoundException("此用户不存在");
        } // 权限
        CurrentLoginUser currentLoginUser = getCurrentLoginUser(loginUserDTO);
        this.redisTemplate.opsForHash().put("sys:login_user", currentLoginUser.getUsername(), loginUserDTO);
        return currentLoginUser;
    }

    @Override
    public CurrentLoginUser loadUserByEmail(String email) {
        // 查询用户
        LoginUserDTO loginUserDTO = sysUserService.loadUserByEmail(email);
        if (Objects.isNull(loginUserDTO)) {
            throw new UsernameNotFoundException("此用户不存在");
        }
        // 权限
        CurrentLoginUser currentLoginUser = getCurrentLoginUser(loginUserDTO);
        this.redisTemplate.opsForHash().put("sys:login_email", currentLoginUser.getEmail(), loginUserDTO);
        return currentLoginUser;
    }

    @Override
    public CurrentLoginUser loadUserByPhone(String phone) {
        // 查询用户
        LoginUserDTO loginUserDTO = sysUserService.loadUserByPhone(phone);
        if (Objects.isNull(loginUserDTO)) {
            throw new UsernameNotFoundException("此用户不存在");
        }  // 权限
        CurrentLoginUser currentLoginUser = getCurrentLoginUser(loginUserDTO);
        this.redisTemplate.opsForHash().put("sys:login_phone", currentLoginUser.getUsername(), loginUserDTO);
        return currentLoginUser;
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
        return currentLoginUser;
    }
}
