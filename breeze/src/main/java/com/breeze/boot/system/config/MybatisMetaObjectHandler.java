package com.breeze.boot.system.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.breeze.boot.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * mybatis元对象处理程序
 *
 * @author breeze
 * @date 2022-10-12
 */
@Slf4j
@Configuration
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createBy", SecurityUtils::getUserCode, String.class);
        this.strictInsertFill(metaObject, "createName", SecurityUtils::getUserName, String.class);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateBy", SecurityUtils::getUserCode, String.class);
        this.strictUpdateFill(metaObject, "updateName", SecurityUtils::getUserName, String.class);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }

}

