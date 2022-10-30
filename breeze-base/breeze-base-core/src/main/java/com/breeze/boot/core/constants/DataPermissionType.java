package com.breeze.boot.core.constants;

/**
 * 枚举数据权限
 *
 * @author breeze
 * @date 2022-10-29
 */
public interface DataPermissionType {

    /**
     * 全部
     */
    String ALL = "0";
    /**
     * 本级部门
     */
    String DEPT_LEVEL = "1";

    /**
     * 本级部门和下属部门
     */
    String DEPT_AND_LOWER_LEVEL = "2";

    /**
     * 自己
     */
    String OWN = "3";

    /**
     * diy
     */
    String DIY = "9999";

}
