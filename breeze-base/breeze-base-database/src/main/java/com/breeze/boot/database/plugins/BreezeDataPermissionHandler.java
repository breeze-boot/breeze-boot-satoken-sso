//package com.breeze.boot.database.plugins;
//
//import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
//import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
//import com.breeze.boot.security.entity.LoginUserDTO;
//import com.breeze.boot.database.annotation.DataPermission;
//import com.breeze.boot.security.entity.PermissionDTO;
//import com.breeze.boot.security.utils.SecurityUtils;
//import com.google.common.collect.Lists;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.HexValue;
//import net.sf.jsqlparser.expression.LongValue;
//import net.sf.jsqlparser.expression.StringValue;
//import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
//import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
//import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
//import net.sf.jsqlparser.expression.operators.relational.InExpression;
//import net.sf.jsqlparser.expression.operators.relational.ItemsList;
//import net.sf.jsqlparser.schema.Column;
//
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static com.breeze.boot.core.constants.DataPermissionType.*;
//
///**
// * 数据权限处理程序
// *
// * TODO 后期在整，先自定义
// *
// * @author breeze
// * @date 2022-10-29
// */
//@Slf4j
//public class BreezeDataPermissionHandler implements DataPermissionHandler {
//
//    public List<String> ignoreTable() {
//        return Lists.newArrayList("sys_user");
//    }
//
//    /**
//     * @param where             原SQL Where 条件表达式
//     * @param mappedStatementId Mapper接口方法ID
//     * @return
//     */
//    @Override
//    @SneakyThrows
//    public Expression getSqlSegment(Expression where, String mappedStatementId) {
//        if (InterceptorIgnoreHelper.willIgnoreDataPermission(mappedStatementId)) {
//            // 过滤退出执行
//            return where;
//        }
//
//        // 采用判断方法注解方式进行数据权限
//        Class<?> clazz = null;
//        try {
//            // 获取Mapper类
//            clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 获取方法名
//        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
//        assert clazz != null;
//        Method[] methods = clazz.getMethods();
//        // 遍历类的方法
//        for (Method method : methods) {
//            DataPermission annotation = method.getAnnotation(DataPermission.class);
//            // 判断是否存在注解且方法名一致
//            if (annotation != null && methodName.equals(method.getName())) {
//                String scope = annotation.scope();
//                // 获取 deptIds
//                LoginUserDTO currentUser = SecurityUtils.getCurrentUser();
//                if (currentUser == null) {
//                    throw new RuntimeException("未登录，数据权限不可实现");
//                }
//
//                Expression expression = new HexValue("");
//                if (where == null) {
//                    where = expression;
//                }
//                String permissionType = currentUser.getPermissionType();
//                switch (permissionType) {
//                    // 全部权限
//                    case ALL:
//                        return where;
//                    // 本级别部门以及下属部门
//                    case DEPT_LEVEL:
//                        // 创建IN 表达式
//                        // 创建IN范围的元素集合
////                        List<PermissionDTO> deptIds = currentUser.getPermissions();
//                        // 集合转变为 JSQLParser 的元素 List
//                        ItemsList itemsList = new ExpressionList(deptIds.stream().map(LongValue::new).collect(Collectors.toList()));
//                        InExpression inExpression = new InExpression(new Column("dept_id"), itemsList);
//                        return new AndExpression(where, inExpression);
//                    // 查看当前部门的数据
//                    case DEPT_AND_LOWER_LEVEL:
//                        // 拼装 dept_id = deptId
//                        EqualsTo equalsTo = new EqualsTo();
//                        equalsTo.setLeftExpression(new Column("dept_id"));
//                        equalsTo.setRightExpression(new LongValue(currentUser.getPermissions().stream().findFirst().get()));
//                        // 创建 AND 表达式 拼接 WHERE 和 = 表达式
//                        return new AndExpression(where, equalsTo);
//                    // 查看自己的数据
//                    case OWN:
//                        // 拼装 create_by = userId
//                        EqualsTo selfEqualsTo = new EqualsTo();
//                        selfEqualsTo.setLeftExpression(new Column("create_by"));
//                        selfEqualsTo.setRightExpression(new LongValue(currentUser.getId()));
//                        return new AndExpression(where, selfEqualsTo);
//                    case DIY:
//                        return new AndExpression(where, new StringValue(currentUser.getSql()));
//                    default:
//                        break;
//                }
//            }
//        }
//        return where;
//    }
//
//}
