package com.breeze.boot.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.admin.dto.DictDTO;
import com.breeze.boot.admin.entity.SysDictEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统字典映射器
 *
 * @author breeze
 * @date 2022-09-02
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDictEntity> {

    /**
     * 字典列表
     *
     * @param page    分页
     * @param dictDto 字典 dto
     * @return {@link Page}<{@link SysDictEntity}>
     */
    Page<SysDictEntity> listDict(Page<SysDictEntity> page, @Param("dictDto") DictDTO dictDto);

}
