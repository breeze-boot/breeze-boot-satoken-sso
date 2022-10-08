package com.breeze.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breeze.boot.system.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志映射器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
