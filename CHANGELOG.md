# ChangeLog

### Release_1.0.3_20220205_build_A

#### 功能构建

- 升级部分依赖版本。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.TaskController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.TaskTypeIndicatorController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.ProjectController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.PopController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.MemoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.MemoFileController。

- 优化 BeanTransformer 的 bean 名称。

- 添加项目的许可证。

- 升级 `log4j2` 依赖版本为 `2.17.1`。

#### Bug修复

- 修正 pom.xml 中不正确的配置。

#### 功能移除

- (无)

---

### Release_1.0.2_20220130_build_A

#### 功能构建

- 升级 `log4j2` 依赖版本为 `2.15.0` 以规避 `CVE-2021-44228` 漏洞。

- 升级 `log4j2` 依赖版本为 `2.17.0` 以规避 `CVE-2021-45105` 漏洞。

- 更新 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.AccountBookController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.AccountController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.AccountBookController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.BankCardController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.FundChangeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemFileController。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.PoabController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.AssetCatalogController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.PoacController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemTypeIndicatorController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemLabelController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemCoverController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemFileController。

- 升级 `familyhelper-clannad` 依赖版本为 `1.2.0.a`，并解决兼容性问题。

- 为 `dubbo` 增加超时时间的配置选项。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.1_20211204_build_A

#### 功能构建

- 完成我与家庭 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.ProfileTypeIndicatorController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.ProfileController。
  - com.dwarfeng.familyhelper.webapi.stack.service.clannad.NicknameResponseService。
  - com.dwarfeng.familyhelper.webapi.stack.service.clannad.AvatarController。

- 优化用户的注册逻辑。
  - 用户注册时，自动插入一个字段完全是空字符串的个人简介。

- 实现时间 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.TimeController。
  - 解决了潜在的客户端与服务端时间不一致导致的无法登陆的问题。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.0_20211026_build_A

#### 功能构建

- 完成部分系统设置 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.AccountController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.LoginController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.PermissionController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.PermissionGroupController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.PexpController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.RoleController。

- 完成资金管理 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.AccountBookController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.BankCardBalanceHistoryController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.BankCardController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.BankCardTypeIndicatorController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.FundChangeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.FundChangeTypeIndicatorController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.TotalBalanceHistoryController。

#### Bug修复

- (无)

#### 功能移除

- (无)
