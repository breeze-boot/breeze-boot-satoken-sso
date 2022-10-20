package com.breeze.boot.system.config;

import com.breeze.boot.log.config.SysLogSaveEventListener;
import com.breeze.boot.log.dto.SysLogDTO;
import com.breeze.boot.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统日志收集的配置
 *
 * @author breeze
 * @date 2022-08-31
 */
@Configuration
public class SysLogConfig {

    @Autowired
    private SysLogService sysLogService;

    @Bean
    public SysLogSaveEventListener listener() {
        return new SysLogSaveEventListener((sysLog) -> this.sysLogService.saveSysLog((SysLogDTO) sysLog.getSource()));
    }

}
