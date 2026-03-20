# ConfDirectory - 配置目录

## 总览

本项目的配置文件位于 `conf/` 目录下，包括：

```text
conf
│
└─familyhelper-webapi
    ├─dubbo
    │      connection.properties
    │
    ├─familyhelper
    │      exception.properties
    │      file-upload.properties
    │      http.properties
    │
    ├─logging
    │      README.md
    │      settings.xml
    │      settings-ref-linux.xml
    │      settings-ref-windows.xml
    │
    └─settingrepo
            public-setting-category.properties
```

**部署说明**：部署时，需将上述 `conf/` 目录放置于 Tomcat 的 `conf` 目录下（与 `webapps` 同级）。
具体而言，将 `conf/familyhelper-webapi` 文件夹复制到 Tomcat 的 `conf` 目录下，使 Tomcat 能够加载本项目的配置。

鉴于大部分配置文件的配置项中都有详细地注释，此处将展示默认的配置，并重点说明一些必须要修改的配置项，
省略的部分将会使用 `etc...` 进行标注。

## dubbo 目录

| 文件名                   | 说明           |
|-----------------------|--------------|
| connection.properties | Dubbo 连接配置文件 |

### connection.properties

Dubbo 连接配置文件，包括 ZooKeeper 注册中心地址、Dubbo 与 Hessian 协议端口及主机、各微服务消费者的分组配置。

```properties
dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
dubbo.registry.zookeeper.timeout=3000
dubbo.protocol.dubbo.port=20000
dubbo.protocol.dubbo.host=your-host-here
dubbo.protocol.hessian.port=30000
dubbo.consumer.acckeeper.group=
dubbo.consumer.rbac.group=
dubbo.consumer.familyhelper_finance.group=
dubbo.consumer.familyhelper_clannad.group=
dubbo.consumer.familyhelper_assets.group=
dubbo.consumer.familyhelper_project.group=
dubbo.consumer.familyhelper_note.group=
dubbo.consumer.familyhelper_life.group=
dubbo.consumer.settingrepo.group=
dubbo.consumer.notify.group=
dubbo.consumer.familyhelper_plugin_assets.group=
dubbo.consumer.familyhelper_plugin_clannad.group=
dubbo.consumer.familyhelper_plugin_life.group=
dubbo.consumer.familyhelper_plugin_settingrepo.group=
dubbo.consumer.snowflake.group=
```

其中，`dubbo.registry.zookeeper.address` 需要配置为 ZooKeeper 的地址，
`dubbo.protocol.dubbo.host` 需要配置为本机的 IP 地址。

如果您需要在本机启动多个 familyhelper-webapi 实例，
那么需要为每个实例配置不同的 `dubbo.protocol.dubbo.port` 和 `dubbo.protocol.hessian.port`。

如果您在本机上部署了多个项目，每个项目中都使用了相同的微服务（如 acckeeper、rbac 等），
那么需要为各 `dubbo.consumer.xxx.group` 配置不同的分组值， 以避免微服务错误的调用。

## familyhelper 目录

| 文件名                    | 说明                               |
|------------------------|----------------------------------|
| exception.properties   | ServiceException 的异常代码的偏移量配置     |
| file-upload.properties | 文件上传的配置                          |
| http.properties        | HTTP 相关配置，包括认证 Token 的 Header 名称 |

### http.properties

HTTP 相关配置文件，包括认证 Token 的 Header 名称。

```properties
# 认证 Token 的 HTTP Header 名称。
familyhelper.token_key=Authentication
```

配置项 `familyhelper.token_key` 用于指定客户端在 HTTP 请求中传递认证 Token 时使用的 Header 名称，默认为 `Authentication`。

### exception.properties

ServiceException 的异常代码的偏移量配置。

```properties
# familyhelper 工程中 subgrade 的异常代号偏移量。
familyhelper.exception_code_offset.subgrade=0
```

Subgrade 框架中，会将微服务抛出的异常映射为 `ServiceException`，每个 `ServiceException` 都有一个异常代码，
用于标识异常的类型。

如果您的项目中使用了多个基于 Subgrade 框架的微服务，那么，您需要为每个微服务配置一个异常代码偏移量，
以免不同的微服务生成异常代码相同的 `ServiceException`。

### file-upload.properties

文件上传的配置文件。

```properties
# 所有文件上传的大小限制。
file_upload.max_size=-1
# 单个文件上传的大小限制。
file_upload.max_size_per_file=-1
```

配置项 `file_upload.max_size` 用于指定所有文件上传的总大小限制，
`file_upload.max_size_per_file` 用于指定单个文件上传的大小限制。
设置为 `-1` 表示不限制。

## logging 目录

| 文件名                      | 说明                     |
|--------------------------|------------------------|
| README.md                | 说明文件                   |
| settings.xml             | 日志配置的配置文件              |
| settings-ref-linux.xml   | Linux 系统中日志配置的配置参考文件   |
| settings-ref-windows.xml | Windows 系统中日志配置的配置参考文件 |

### settings.xml

日志配置及其参考文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">UTF-8</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">logs/familyhelper-webapi</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!-- etc... -->
    </Appenders>

    <Loggers>
        <!-- etc... -->
    </Loggers>
</Configuration>
```

需要注意的是，日志配置 **必须** 定义在 `settings.xml` 中才能生效，所有的 `settings-ref-xxx.xml` 都是参考文件，
在这些文件中进行任何配置的修改 **均不会生效**。

常用的做法是，针对不同的操作系统，将参考文件中的内容直接复制到 `settings.xml` 中，随后对 `settings.xml` 中的内容进行修改。

- 如果服务运行一天产生的日志超过了配置上限，可上调 `rolling_file.rollover.max` 参数。
- 如果存在等保需求，日志至少需要保留 6 个月，需要调整 `rolling_file.rollover.delete_age` 参数至 `200D`。

## settingrepo 目录

| 文件名                                | 说明                 |
|------------------------------------|--------------------|
| public-setting-category.properties | 公共设置类别的 SPEL 表达式配置 |

### public-setting-category.properties

settingrepo 公共设置类别的 SPEL 表达式配置。

```properties
# settingrepo 公共设置类别的 SPEL 表达式，其中 #root 代表设置类别的 ID。
# 有关 SPEL 的说明，请参阅以下文档：
# https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/core.html#expressions
settingrepo.public_setting_category.spel=#{'#{''public$'' + #root}'}
```

该配置文件用于定义 settingrepo 公共设置类别的 SPEL 表达式，一般情况下无需修改。
