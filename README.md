# Breeze-Boot-satoken-sso
### 平台简介

##### Breeze-boot-satoken-sso 是基于 Spring Boot 3、JDK 17 和 sa-token（SSO 模式）的 RBAC 权限管理系统。它提供用户、角色、菜单和权限管理功能。适合 Java 开发者入门学习或实际开发使用。

- 前端采用Vue、Element UI PLUS、VITE、TypeScript。
- 后端采用Spring Boot3 JDK17、sa-token、Redis、Jwt、Druid。
- 支持按钮级别的权限控制。
- 支持加载动态权限菜单。
- 前端代码，请移步。
    - sso 服务端
    - [github：vue + vite + TS 版本](https://github.com/breeze-boot/breeze-vite-ui-satoken-sso.git)
    - [gitee: vue + vite + TS 版本](https://gitee.com/breeze-boot/breeze-vite-ui-satoken-sso.git)
    - sso 客户端
    - [github：vue + vite + TS 版本](https://github.com/breeze-boot/breeze-vite-ui-satoken-sso.git)
    - [gitee: vue + vite + TS 版本](https://gitee.com/breeze-boot/breeze-vite-ui-satoken-sso.git)

### 启动项目
- 分别启动server端和要运行的client端。
  - BreezeBootSsoServerApplication ------------------------------- （sso 服务端）
  - BreezeSpringSecuritySsoClientApplication ------------------- （sso 客户端 springSecurity版本nosdk集成版本）
  - BreezeSsoClientApplication --------------------------------------- （sso 客户端 sa-token版本）
- 再启动前端项目时，需要先启动后端项目，再启动前端项目。
  - breeze-vite-ui-satoken-sso ---------------------------------------- （sso 服务端）
  - breeze-vite-ui-satoken-sso-client ---------------------------------（sso 客户端）

### 特别鸣谢：
- [验证码： https://gitee.com/anji-plus/captcha](https://gitee.com/anji-plus/captcha)
- [流程设计器： https://gitee.com/MiyueSC/bpmn-process-designer](https://gitee.com/MiyueSC/bpmn-process-designer)
- [流程设计器：VUE3版本 https://gitee.com/xlys998/bpmn-vue3](https://gitee.com/xlys998/bpmn-vue3)
- [cron表达式编辑器：VUE3版本 https://github.com/wuchuanpeng/no-vue3-cron](https://github.com/wuchuanpeng/no-vue3-cron)

# 请遵循 Apache 2.0 协议

### 代码结构

- breeze-boot \
  ├─ breeze-boot ---------------------------------------------------------- sso 服务端          
  │ ├─ src \
  │ │ ├─ src\java\com\breeze\boot\..\modules\system---------- 【系统相关的包】 \
  │ │ ├─ src\java\com\breeze\boot\..\modules\auth  ------------- 【权限相关的包】 \
  ├─ breeze-base \
  │ ├─ breeze-base-core \
  │ ├─ breeze-base-log \
  │ ├─ breeze-base-mail \
  │ ├─ breeze-base-oss-s3 \
  │ ├─ breeze-base-sa-sso \
  │ ├─ breeze-base-validator \
  │ ├─ breeze-base-message\
  │ ├─ breeze-base-redis-cache\
  │ ├─ breeze-base-mail \
  │ ├─ breeze-base-xss  \
  │ ├─ breeze-base--doc  \
  │ ├─ breeze-...
  ├─ breeze-monitor                         ----------------------- 监控服务（SpringBoot项目） \
  ├─ breeze-sso-sample                       --------------------------------------- sso客户端（SpringBoot项目） \
  ├─│ ├─ breeze-sso-client                  ----------------------------------- sso 客户端 sa-token版本 \
  ├─│ ├─ breeze-spring-security-sso-client      -------------- sso 客户端 springSecurity版本nosdk集成版本

### 前端界面相关

### 登录页
![login.png](doc/images/login.png)
### 首页
![home.png](doc/images/home.png)
### 布局1
![img_1.png](doc/images/img_1.png)
### 布局2
![img_2.png](doc/images/img_2.png)
### 布局3
![img_3.png](doc/images/img_3.png)
### 布局4
![img_4.png](doc/images/img_4.png)




