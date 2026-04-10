# Compile By Source - 从源码编译

## 准备依赖

该项目的部分依赖不在中央仓库下，需要从其它仓库下载，或者下载源码自行编译。

### 使用其它仓库

您可以在 `settings.xml` 中添加如下配置，以使用其它仓库，通常 `settings.xml` 在 `$HOME/.m2/` 文件目录下。

```xml
<settings
        xmlns="http://maven.apache.org/SETTINGS/1.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
        http://maven.apache.org/xsd/settings-1.0.0.xsd"
>

    <servers>
        <server>
            <id>xxx-releases</id>
            <username>your-username-here</username>
            <password>your-password-here</password>
        </server>
        <server>
            <id>rdc-snapshots</id>
            <username>your-username-here</username>
            <password>your-password-here</password>
        </server>
    </servers>

    <profiles>
        <profile>
            <id>xxx</id>
            <properties>
                <altReleaseDeploymentRepository>
                    xxx-releases::default::https://your-repository-url-here/
                </altReleaseDeploymentRepository>
                <altSnapshotDeploymentRepository>
                    xxx-snapshots::default::https://your-repository-url-here/
                </altSnapshotDeploymentRepository>
            </properties>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>xxx</activeProfile>
    </activeProfiles>
</settings>
```

使用的仓库需要保证有如下依赖，否则编译过程会因为找不到依赖而失败：

- com.dwarfeng:dutil
- com.dwarfeng:snowflake-distributed-service
- com.dwarfeng:subgrade
- com.dwarfeng:account-keeper
- com.dwarfeng:rbac-distributed-service
- com.dwarfeng:familyhelper-finance
- com.dwarfeng:familyhelper-clannad
- com.dwarfeng:familyhelper-assets
- com.dwarfeng:familyhelper-project
- com.dwarfeng:familyhelper-life
- com.dwarfeng:familyhelper-note
- com.dwarfeng:settingrepo
- com.dwarfeng:notify
- com.dwarfeng:familyhelper-plugin

### 下载依赖源码

您可以在 [Dwarfeng's Github](https://github.com/DwArFeng) 或 [Dwarfeng's Gitee](https://gitee.com/dwarfeng)
下载克隆依赖的源码， 然后使用 `mvn install` 命令将其安装到本地仓库中。

- com.dwarfeng:dutil

  github: [https://github.com/DwArFeng/dutil](https://github.com/DwArFeng/dutil)

  gitee: [https://gitee.com/dwarfeng/dutil](https://gitee.com/dwarfeng/dutil)


- com.dwarfeng:snowflake-distributed-service

  github: [https://github.com/DwArFeng/snowflake-distributed-service](https://github.com/DwArFeng/snowflake-distributed-service)

  gitee: [https://gitee.com/dwarfeng/snowflake-distributed-service](https://gitee.com/dwarfeng/snowflake-distributed-service)


- com.dwarfeng:subgrade

  github: [https://github.com/DwArFeng/subgrade](https://github.com/DwArFeng/subgrade)

  gitee: [https://gitee.com/dwarfeng/subgrade](https://gitee.com/dwarfeng/subgrade)


- com.dwarfeng:account-keeper

  github: [https://github.com/DwArFeng/account-keeper](https://github.com/DwArFeng/account-keeper)

  gitee: [https://gitee.com/dwarfeng/account-keeper](https://gitee.com/dwarfeng/account-keeper)


- com.dwarfeng:rbac-distributed-service

  github: [https://github.com/DwArFeng/rbac-distributed-service](https://github.com/DwArFeng/rbac-distributed-service)

  gitee: [https://gitee.com/dwarfeng/rbac-distributed-service](https://gitee.com/dwarfeng/rbac-distributed-service)


- com.dwarfeng:familyhelper-finance

  github: [https://github.com/DwArFeng/familyhelper-finance](https://github.com/DwArFeng/familyhelper-finance)

  gitee: [https://gitee.com/dwarfeng/familyhelper-finance](https://gitee.com/dwarfeng/familyhelper-finance)


- com.dwarfeng:familyhelper-clannad

  github: [https://github.com/DwArFeng/familyhelper-clannad](https://github.com/DwArFeng/familyhelper-clannad)

  gitee: [https://gitee.com/dwarfeng/familyhelper-clannad](https://gitee.com/dwarfeng/familyhelper-clannad)


- com.dwarfeng:familyhelper-assets

  github: [https://github.com/DwArFeng/familyhelper-assets](https://github.com/DwArFeng/familyhelper-assets)

  gitee: [https://gitee.com/dwarfeng/familyhelper-assets](https://gitee.com/dwarfeng/familyhelper-assets)


- com.dwarfeng:familyhelper-project

  github: [https://github.com/DwArFeng/familyhelper-project](https://github.com/DwArFeng/familyhelper-project)

  gitee: [https://gitee.com/dwarfeng/familyhelper-project](https://gitee.com/dwarfeng/familyhelper-project)


- com.dwarfeng:familyhelper-life

  github: [https://github.com/DwArFeng/familyhelper-life](https://github.com/DwArFeng/familyhelper-life)

  gitee: [https://gitee.com/dwarfeng/familyhelper-life](https://gitee.com/dwarfeng/familyhelper-life)


- com.dwarfeng:familyhelper-note

  github: [https://github.com/DwArFeng/familyhelper-note](https://github.com/DwArFeng/familyhelper-note)

  gitee: [https://gitee.com/dwarfeng/familyhelper-note](https://gitee.com/dwarfeng/familyhelper-note)


- com.dwarfeng:settingrepo

  github: [https://github.com/DwArFeng/settingrepo](https://github.com/DwArFeng/settingrepo)

  gitee: [https://gitee.com/dwarfeng/settingrepo](https://gitee.com/dwarfeng/settingrepo)


- com.dwarfeng:notify

  github: [https://github.com/DwArFeng/notify](https://github.com/DwArFeng/notify)

  gitee: [https://gitee.com/dwarfeng/notify](https://gitee.com/dwarfeng/notify)


- com.dwarfeng:familyhelper-plugin

  github: [https://github.com/DwArFeng/familyhelper-plugin](https://github.com/DwArFeng/familyhelper-plugin)

  gitee: [https://gitee.com/dwarfeng/familyhelper-plugin](https://gitee.com/dwarfeng/familyhelper-plugin)

## 下载源码

使用 git 进行源码下载。

```shell
git clone git@github.com:DwArFeng/familyhelper-webapi.git
```

对于中国用户，可以使用 gitee 进行下载。

```shell
git clone git@gitee.com:dwarfeng/familyhelper-webapi.git
```

## 项目编译、打包

进入项目根目录，执行 maven 命令。

```shell
mvn clean package
```

如果上述命令执行失败，请仔细阅读报错内容，绝大部分情况下是因为上述依赖缺失。请您重复上述步骤，直到编译成功。

## 寻找打包后的目标文件

找到打包后的目标文件

```
familyhelper-webapi-distribute/target/familyhelper-webapi-distribute-${version}-release.tar.gz
```

如能找到该文件，则说明编译成功。
