package com.breeze.boot.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breeze.boot.admin.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志映射器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

}
