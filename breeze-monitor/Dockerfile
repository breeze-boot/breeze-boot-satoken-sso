# 基础镜像
FROM openjdk:8

# 作者信息
MAINTAINER gaoweixuan (breeze-cloud@foxmail.com)

# 挂载目录
VOLUME ./breeze-monitor

# 创建目录
RUN mkdir -p /usr/local/boot/breeze
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

# 指定路径
WORKDIR /usr/local/boot

# 复制jar文件到路径
COPY ./target/breeze-monitor.jar /usr/local/boot/breeze-monitor.jar

# 启动应用
ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+08","breeze-monitor.jar"]


