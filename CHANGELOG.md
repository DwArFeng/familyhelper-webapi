# ChangeLog

### Release_1.0.8_20230110_build_A

#### 功能构建

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.ResetController。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.7_20230101_build_A

#### 功能构建

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

- 依赖升级。
  - 升级 `slf4j` 依赖版本为 `1.7.5` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.1.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.9.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.13.b` 以规避漏洞。
  - 升级 `acckeeper` 依赖版本为 `1.4.4.a` 以规避漏洞。
  - 升级 `rbac` 依赖版本为 `1.4.2.a` 以规避漏洞。
  - 升级 `settingrepo` 依赖版本为 `1.0.2.a` 以规避漏洞。
  - 升级 `notify` 依赖版本为 `1.1.0.a` 以规避漏洞。

- 重构 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.DispatcherInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.DispatcherSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.MetaController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.MetaIndicatorController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.NotifySettingController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.RouterInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.RouterSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.SenderInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.SenderSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.SendHistoryController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.TopicController。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.BillFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.CertificateController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.CertificateFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.PoceController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbSetController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PopbController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbItemController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbRecordController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.note.AttachmentFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.note.NoteBookController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.note.NoteItemController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.note.NoteNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.note.PonbController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.ResetController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.RemindDriverInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.finance.RemindDriverSupportController。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.6_20220702_build_A

#### 功能构建

- 取消 ui 模块 eslint 对换行符的检查。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.FormatterSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.SettingCategoryController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.SettingNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.NotifySettingController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.RouterInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.RouterSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.SenderInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.SenderRelationController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.SenderSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.TopicController。

- 优化 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.ProjectController。

- 优化 familyhelper-webapi-ui 打包的行为。

- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.20` 以规避漏洞。
  - 升级 `fastjson` 依赖版本为 `1.2.83` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.15` 以规避漏洞。
  - 升级 `jetty` 依赖版本为 `9.4.43.v20210629` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.77.Final` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.0.21.Final` 以规避漏洞。
  - 升级 `hibernate` 依赖版本为 `5.3.20.Final` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.17.2` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.0.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.7.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.8.a` 以规避漏洞。

- 解决版本升级后调用过时 API 的问题。

#### Bug修复

- (无)

#### 功能移除

- 删除不需要的依赖。
  - 删除 `joda-time` 依赖。
  - 删除 `commons-lang3` 依赖。
  - 删除 `commons-io` 依赖。
  - 删除 `commons-net` 依赖。
  - 删除 `pagehelper` 依赖。
  - 删除 `jsqlparser` 依赖。

---

### Release_1.0.5_20220325_build_A

#### 功能构建

- 优化 ResponseService 实现的代码结构，减少 @SuppressWarnings 注解的覆盖范围。

- 增加并优化 MemoController 中的方法。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.4_20220323_build_A

#### 功能构建

- 升级 `account-keeper` 版本为 `1.4.2.a`。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.NotificationController。

- 优化 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.PermissionController。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.3_20220309_build_A

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
