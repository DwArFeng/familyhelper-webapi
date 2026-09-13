# ChangeLog

## Release_2.1.0_20260913_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/ApiReferenceV1System.md。
  - docs/wiki/zh-CN/CompileBySource.md。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/SystemRequirements.md。
  - docs/wiki/zh-CN/VersionBlacklist.md。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExportConfController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExporterInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExporterSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExportFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExportMetadataController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExportTaskController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExportTaskSettingController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ExportTemplateController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImportConfController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImporterInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImporterSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImportFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImportMetadataController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImportTaskController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImportTaskSettingController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ImportTemplateController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ReaderInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ReaderSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.ResetController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.WriterInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio.WriterSupportController。

- 增加依赖。
  - 增加依赖 `fileio` 以应用其新功能，版本为 `1.0.1.a`。

- 依赖升级。
  - 升级 `familyhelper-plugin` 依赖版本为 `1.8.0.a` 以应用其新功能。
  - 升级 `subgrade` 依赖版本为 `1.9.0.a` 以应用其新功能。
  - 升级 `fastjson` 依赖版本为 `1.2.84` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.23` 以规避漏洞。
  - 升级 `jetty` 依赖版本为 `9.4.58.v20250814` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.5` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.25.4` 以规避漏洞。
  - 升级 `mapstruct` 依赖版本为 `1.5.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.2.a-beta` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `2.0.2.a` 以规避漏洞。
  - 升级 `acckeeper` 依赖版本为 `3.0.1.a` 以规避漏洞。
  - 升级 `rbac` 依赖版本为 `3.0.1.a` 以规避漏洞。
  - 升级 `settingrepo` 依赖版本为 `3.0.1.a` 以规避漏洞。
  - 升级 `notify` 依赖版本为 `2.0.0.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `web.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

- 优化开发环境支持。
  - 在 .gitignore 中添加 Vibe Coding 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.0.2_20260410_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/CompileBySource.md。

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac.PermissionController。

- 依赖升级。
  - 升级 `rbac` 依赖版本为 `2.0.3.a` 以应用其新功能。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.0.1_20260407_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/SystemRequirements.md。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.KvNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.KvNodeItemController。

- familyhelper-webapi-ui 模块前端依赖升级。
  - 升级 `vite` 依赖版本为 `^6.4.2` 以规避漏洞。

- 依赖升级。
  - 升级 `settingrepo` 依赖版本为 `2.5.0.a` 以应用其新功能。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.0.0_20260330_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/ApiReferenceV1.md。
  - docs/wiki/zh-CN/ApiReferenceV1Acckeeper.md。
  - docs/wiki/zh-CN/ApiReferenceV1Assets.md。
  - docs/wiki/zh-CN/ApiReferenceV1Clannad.md。
  - docs/wiki/zh-CN/ApiReferenceV1Finance.md。
  - docs/wiki/zh-CN/ApiReferenceV1Life.md。
  - docs/wiki/zh-CN/ApiReferenceV1Note.md。
  - docs/wiki/zh-CN/ApiReferenceV1Notify.md。
  - docs/wiki/zh-CN/ApiReferenceV1Project.md。
  - docs/wiki/zh-CN/ApiReferenceV1Rbac.md。
  - docs/wiki/zh-CN/ApiReferenceV1Settingrepo.md。
  - docs/wiki/zh-CN/ApiReferenceV1System.md。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper.ResetController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac.ResetController。

- 增加依赖。
  - 增加依赖 `dwarfeng:account-keeper-impl` 以应用其新功能。
  - 增加依赖 `dwarfeng:rbac-distributed-service-impl` 以应用其新功能。

- `familyhelper-webapi-sdk` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.BeanMapper。
  - com.dwarfeng.familyhelper.webapi.sdk.bean.life.BeanMapper。
  - com.dwarfeng.familyhelper.webapi.sdk.bean.notify.BeanMapper。
  - com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.BeanMapper。
  - com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.BeanMapper。
  - com.dwarfeng.familyhelper.webapi.sdk.bean.system.BeanMapper。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.7.3.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `acckeeper` 依赖版本为 `2.1.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `rbac` 依赖版本为 `2.0.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dutil` 依赖版本为 `0.4.1.a-beta` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.8.3.a` 以规避漏洞。

- 项目配置结构优化。
  - 使用 `Configuration API` 代替 `application-context-*.xml` 进行 `LoginAop` 配置。
  - 使用 `Configuration API` 代替 `application-context-*.xml` 进行 `PermissionAop` 配置。
  - 使用 `Configuration API` 代替 `application-context-*.xml` 进行 `BindingCheckAop` 配置。

- 依赖结构优化。
  - 将 `com.dwarfeng:snowflake-distributed-service-api` 依赖提升至 `familyhelper-webapi-node` 子模块。
  - 将 `com.dwarfeng:account-keeper-api` 依赖提升至 `familyhelper-webapi-node` 子模块。
  - 将 `com.dwarfeng:rbac-distributed-service-api` 依赖提升至 `familyhelper-webapi-node` 子模块。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
