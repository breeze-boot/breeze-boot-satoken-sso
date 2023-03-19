package com.breeze.boot.sys.service.impl;

import com.anji.captcha.service.CaptchaCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码缓存服务
 * <p>
 * 对于分布式部署的应用，我们建议应用自己实现CaptchaCacheService，比如用Redis，参考service/spring-boot代码示例。
 * 如果应用是单点的，也没有使用redis，那默认使用内存。
 * 内存缓存只适合单节点部署的应用，否则验证码生产与验证在节点之间信息不同步，导致失败。
 * <p>
 * ☆☆☆ SPI： 在resources目录新建META-INF.services文件夹(两层)，参考当前服务resources。
 *
 * @author lide1202@hotmail.com
 * @Title: 使用redis缓存
 * @date 2020-05-12
 */
@Service
public class CaptchaCacheServiceRedisImpl implements CaptchaCacheService {

    /**
     * 字符串复述,模板
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 类型
     *
     * @return {@link String}
     */
    @Override
    public String type() {
        return "redis";
    }

    /**
     * 设置
     *
     * @param key              KEY
     * @param value            价值
     * @param expiresInSeconds 在几秒钟内到期
     */
    @Override
    public void set(String key, String value, long expiresInSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
    }

    /**
     * 是否存在
     *
     * @param key KEY
     * @return boolean
     */
    @Override
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 删除
     *
     * @param key KEY
     */
    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * get
     *
     * @param key KEY
     * @return {@link String}
     */
    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 增加
     *
     * @param key KEY
     * @param val 瓦尔
     * @return {@link Long}
     */
    @Override
    public Long increment(String key, long val) {
        return stringRedisTemplate.opsForValue().increment(key, val);
    }
}
