package com.breeze.boot.core.constants;

/**
 * 枚举数据权限
 *
 * @author gaoweixuan
 * @date 2022-10-29
 */
public interface DataPermissionType {

    /**
     * 全部
     */
    Integer ALL = 0;
    /**
     * 本级部门
     */
    Integer DEPT_LEVEL = 1;

    /**
     * 本级部门和下属部门
     */
    Integer DEPT_AND_LOWER_LEVEL = 2;

    /**
     * 自己
     */
    Integer OWN = 3;

    /**
     * diy
     */
    Integer DIY = 9999;

}
