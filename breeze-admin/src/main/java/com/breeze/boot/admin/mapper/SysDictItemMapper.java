package com.breeze.boot.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breeze.boot.admin.entity.SysDictItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统字典项映射器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Mapper
public interface SysDictItemMapper extends BaseMapper<SysDictItemEntity> {

    /**
     * 字典列表项
     *
     * @param pdictId 字典ID
     * @return {@link List}<{@link SysDictItemEntity}>
     */
    List<SysDictItemEntity> listDictDetailByDictId(@Param("pdictId") Long pdictId);

}
