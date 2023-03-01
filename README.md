# Breeze-boot 《开发中》

### 平台简介

##### Breeze-boot 是基于RBAC的权限管理系统，包括用户管理、角色管理、菜单管理、权限管理等功能，适合Java开发者入门学习或者直接用此项目。

- 前端采用Vue、Element UI。
- 后端采用Spring Boot、Spring Security、Redis、Jwt、Druid。
- 权限认证使用Jwt、支持按钮级别的权限控制
- 支持加载动态权限菜单，多方式轻松权限控制。
- 前端代码，请移步
  - [github https://github.com/memory1998/breeze-web](https://github.com/memory1998/breeze-web)
  - [gitee https://gitee.com/memoryGiter/breeze-web](https://gitee.com/memoryGiter/breeze-web)

### 特别鸣谢：

- [验证码 https://gitee.com/anji-plus/captcha](https://gitee.com/anji-plus/captcha)
- [流程设计器 https://github.com/GoldSubmarine/workflow-bpmn-modeler](https://github.com/GoldSubmarine/workflow-bpmn-modeler)

# 请遵循 Apache 2.0 协议

### 功能菜单

##### 后端

- 管理系统 \
  ├─ 系统设置 \
  │ ├── 平台管理 \
  │ ├── 用户管理 \
  │ ├── 菜单管理 \
  │ ├── 岗位管理 \
  │ ├── 角色管理 \
  │ ├── 字典管理 \
  │ ├── 日志管理 \
  │ ├── 数据权限 \
  │ ├── 部门管理 \
  │ ├── 租户管理 \
  │ ├── 系统文件 \
  ├─ 消息管理 \
  │ ├── 用户消息 \
  │ ├── 消息公告 \
  ├─ 监控平台 \
  │ ├── swagger \
  │ └── 德鲁伊

### 代码结构

- breeze-boot \
  ├─ breeze \
  │ ├─ src \
  │ │ ├─ src\java\com\breeze\boot\..\flowable 工作流相关的包 \
  │ │ ├─ src\java\com\breeze\boot\..\system 系统相关的包 \
  ├─ breeze-base \
  │ ├─ breeze-base-core \
  │ ├─ breeze-base-datasource\
  │ ├─ breeze-base-log \
  │ ├─ breeze-base-mail \
  │ ├─ breeze-base-oss \
  │ ├─ breeze-base-security \
  │ ├─ breeze-base-validator \
  │ ├─ breeze-base-websocket\
  │ ├─ breeze-base-xss  \
  ├─ breeze-boot-admin-server \
  ├─ src

### 前端界面相关

![login.png](doc/images/login.png)

![img_1.png](doc/images/img_1.png)

![img_2.png](doc/images/img_2.png)

![img_3.png](doc/images/img_3.png)

![img_5.png](doc/images/img_5.png)

![img_6.png](doc/images/img_6.png)

![img_7.png](doc/images/img_7.png)

