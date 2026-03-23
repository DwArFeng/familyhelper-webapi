# ChangeLog

## Release_2.0.0_20260321_build_A

### 功能构建

- 增加依赖。
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
