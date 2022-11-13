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
    String ALL = "ALL";

    /**
     * 本级部门和下属部门
     */
    String DEPT_AND_LOWER_LEVEL = "DEPT_AND_LOWER_LEVEL";

    /**
     * 本级部门
     */
    String DEPT_LEVEL = "DEPT_LEVEL";


    /**
     * 自己
     */
    String OWN = "OWN";

    /**
     * 自定义部门
     */
    String DIY_DEPT = "DIY_DEPT";

    /**
     * diy
     */
    String DIY = "DIY";

}
