# familyhelper-webapi

家庭助手系统的web后端。

该项目提供 restful 风格的 http 接口，以供不同的客户端调用。

---

## 特性

- 使用 SpringMVC 框架作为核心框架，输出 war 包制品。
- 基于 Subgrade 框架构建。
- 基于注解进行登录认证、权限控制、参数校验、行为分析等功能。

## 文档

该项目的文档位于 [docs](../../../docs) 目录下，包括：

### wiki

wiki 为项目的开发人员为本项目编写的详细文档，包含不同语言的版本，主要入口为：

1. [简介](./Introduction.md) - 即本文件。
2. [目录](./Contents.md) - 文档目录。

## 使用说明

1. 下载源码

   使用 git 进行源码下载。

   ```shell
   git clone git@github.com:DwArFeng/familyhelper-webapi.git
   ```

   对于中国用户，可以使用 gitee 进行高速下载。

   ```shell
   git clone git@gitee.com:dwarfeng/familyhelper-webapi.git
   ```

2. 项目打包

   进入项目根目录，执行 maven 命令。

   ```shell
   mvn clean package
   ```

3. 解压

   找到打包后的目标文件。

   ```
   familyhelper-webapi-distribute/target/familyhelper-webapi-distribute-[version]-release.tar.gz
   ```

   将其解压至 windows 系统或者 linux 系统。

4. 配置

   1. 修改 `conf/familyhelper-webapi` 文件夹下的配置文件，着重修改各连接的 url 和各模块的微服务分组。

5. 部署

   将解压并修改后的文件夹部署至 tomcat 服务器中。
   - 将 `conf` 目录下的配置文件复制到 tomcat 的 `conf` 目录下。
   - 将 `webapps` 目录下的文件复制到 tomcat 的 `webapps` 目录下。

6. enjoy it
