package com.breeze.boot.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.admin.dto.FileDTO;
import com.breeze.boot.admin.entity.SysFileEntity;

/**
 * 系统文件服务
 *
 * @author breeze
 * @date 2022-09-02
 */
public interface SysFileService extends IService<SysFileEntity> {

    /**
     * 列表文件
     *
     * @param fileDTO 文件dto
     * @return {@link Page}<{@link SysFileEntity}>
     */
    Page<SysFileEntity> listFile(FileDTO fileDTO);
}
