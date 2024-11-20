# ChangeLog

### Release_1.4.0_20241120_build_A

#### 功能构建

- (无)

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.0_20240804_build_A

#### 功能构建

- 增加自定义命名值方法参数解析器，以用于解析特定序列化规则的参数。
  - com.dwarfeng.familyhelper.webapi.node.webmvc.Base64RequestParamMethodArgumentResolver。

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.SettingNodeController。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.TextNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.ImageNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo.ImageListNodeController。

- 依赖升级。
  - 升级 `settingrepo` 依赖版本为 `2.0.0.b` 并解决兼容性问题，以应用其新功能。
  - 升级 `familyhelper-plugin` 依赖版本为 `1.6.0.a` 以应用其新功能。
  - 升级 `spring` 依赖版本为 `5.3.37` 以规避漏洞。
  - 升级 `jetty` 依赖版本为 `9.4.55.v20240627` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.108.Final` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.5.2.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.5.a` 以规避漏洞。

#### Bug修复

- 修复部分功能性实体集合类型的字段在映射时有可能产生空指针异常的问题。
  - com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets.JSFixedFastJsonDispItem。

- 去除部分 Controller 中的错误注解。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.CertificateFileController。

#### 功能移除

- (无)

---

### Release_1.2.4_20240429_build_A

#### 功能构建

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.CertificateFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityFileController。

- 配置优化。
  - 优化 `application-context-dubbo.xml` 中的部分 dubbo 消费者的 bean id。

- 依赖升级。
  - 升级 `familyhelper-clannad` 依赖版本为 `1.4.1.a` 以应用其新功能。
  - 升级 `familyhelper-life` 依赖版本为 `1.1.1.a` 以应用其新功能。
  - 升级 `familyhelper-plugin` 依赖版本为 `1.5.0.a` 以应用其新功能。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.3_20240414_build_A

#### 功能构建

- Dubbo 优化。
  - 优化 `dubbo/connection.properties` 中配置的键名。

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemFileController。

- 日志功能优化。
  - 优化默认日志配置，默认配置仅向控制台输出 `INFO` 级别的日志。
  - 优化日志配置结构，提供 `conf/logging/settings.xml` 配置文件及其不同平台的参考配置文件，以供用户自定义日志配置。
  - 优化启动脚本，使服务支持新的日志配置结构。
  - 优化 `assembly.xml`，使项目打包时输出新的日志配置结构。

- 增加依赖。
  - 增加依赖 `familyhelper-plugin` 以应用其功能，版本为 `1.4.0.a`。

- 依赖升级。
  - 升级 `familyhelper-assets` 依赖版本为 `1.0.8.a` 以应用其新功能。
  - 升级 `subgrade` 依赖版本为 `1.5.2.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.2_20240215_build_A

#### 功能构建

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.MemoController。

- 依赖升级。
  - 升级 `familyhelper-project` 依赖版本为 `1.2.2.a` 以应用其新功能。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.1_20240212_build_A

#### 功能构建

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.DeriveController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.LoginStateController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.LoginHistoryController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.LoginParamRecordController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.ProtectDetailRecordController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.DeriveHistoryController。

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.LoginController。

- 依赖升级。
  - 升级 `acckeeper` 依赖版本为 `1.7.1.a` 并解决兼容性问题，以应用其新功能。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20240129_build_A

#### 功能构建

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad.CertificateFileController。

- 依赖升级。
  - 升级 `familyhelper-clannad` 依赖版本为 `1.4.0.a` 以应用其新功能。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.2_20240128_build_A

#### 功能构建

- 依赖升级。
  - 升级 `acckeeper` 依赖版本为 `1.6.6.a` 以规避漏洞。

#### Bug修复

- 修复注册、更新、删除账号时，账号信息未同步到部分微服务的 bug。

#### 功能移除

- (无)

---

### Release_1.1.1_20240128_build_A

#### 功能构建

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。

- 依赖升级。
  - 升级 `acckeeper` 依赖版本为 `1.6.5.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `commons-fileupload` 依赖版本为 `1.5` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.4.8.b` 以规避漏洞。
  - 升级 `settingrepo` 依赖版本为 `1.1.5.a` 以规避漏洞。

- familyhelper-webapi-ui 模块前端依赖升级。
  - 升级 `core-js` 依赖版本为 `^3.35.1` 以规避漏洞。
  - 升级 `vue` 依赖版本为 `^2.7.16` 以规避漏洞。
  - 升级 `@vue/cli-plugin-babel` 依赖版本为 `^4.5.19` 以规避漏洞。
  - 升级 `@vue/cli-plugin-eslint` 依赖版本为 `^4.5.19` 以规避漏洞。
  - 升级 `@vue/cli-service` 依赖版本为 `^4.5.19` 以规避漏洞。
  - 升级 `@vue/eslint-config-airbnb` 依赖版本为 `^5.3.0` 以规避漏洞。
  - 升级 `eslint` 依赖版本为 `^6.8.0` 以规避漏洞。
  - 升级 `eslint-plugin-import` 依赖版本为 `^2.29.1` 以规避漏洞。
  - 升级 `eslint-plugin-vue` 依赖版本为 `^8.7.1` 以规避漏洞。
  - 升级 `vue-template-compiler` 依赖版本为 `^2.7.16` 以规避漏洞。

- 优化部分响应服务查询可展示实体的速度。
  - com.dwarfeng.familyhelper.webapi.impl.service.assets.ItemResponseServiceImpl。
  - com.dwarfeng.familyhelper.webapi.impl.service.finance.FundChangeResponseServiceImpl。
  - com.dwarfeng.familyhelper.webapi.impl.service.life.ActivityDataItemResponseServiceImpl。
  - com.dwarfeng.familyhelper.webapi.impl.service.life.ActivityDataNodeResponseServiceImpl。
  - com.dwarfeng.familyhelper.webapi.impl.service.life.PbItemResponseServiceImpl。
  - com.dwarfeng.familyhelper.webapi.impl.service.note.NoteItemResponseServiceImpl。
  - com.dwarfeng.familyhelper.webapi.impl.service.note.NoteNodeResponseServiceImpl。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.0_20240125_build_A

#### 功能构建

- 优化 @LoginRequired AOP 配置。

- 执行 npm update，升级前端依赖。
  - familyhelper-webapi-ui。

- 为 Controller 异常添加日志输出。

- 增加 dubbo 微服务的分组配置。
  - acckeeper。
  - rbac。
  - familyhelper-finance。
  - familyhelper-clannad。
  - familyhelper-assets。
  - familyhelper-project。
  - familyhelper-note。
  - settingrepo。
  - notify。

- 增加依赖。
  - 增加依赖 `gson` 以规避漏洞，版本为 `2.8.9`。
  - 增加依赖 `snakeyaml` 以规避漏洞，版本为 `1.33`。
  - 增加依赖 `guava` 以规避漏洞，版本为 `32.0.1-jre`。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.31` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.31` 以规避漏洞。
  - 升级 `jetty` 依赖版本为 `9.4.51.v20230217` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.104` 以规避漏洞。
  - 升级 `curator` 依赖版本为 `4.3.0` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.5.1.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.4.7.a` 并解决兼容问题，以规避漏洞。

- 优化文件格式。
  - 优化 `pom.xml` 文件格式。
  - 优化 `spring/application-context-dubbo.xml` 文件格式。

#### Bug修复

- (无)

#### 功能移除

- 删除不需要的依赖。
  - 删除 `juel` 依赖。
  - 删除 `aopalliance` 依赖。
  - 删除 `spring.data.redis` 依赖。

---

### Release_1.0.10_20240123_build_A

#### 功能构建

- 调整 Controller 及其相关 Bean 的名称。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.PoacController。

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.note.NoteItemController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.note.NoteNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.system.PermissionGroupController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbItemController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.assets.ItemController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityDataRecordController。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTemplateDriverSupportController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTypeIndicatorController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityDataSetController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PbSetController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PoadController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityDataItemController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityDataNodeController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityDataRecordController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTemplateController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PoatController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PoatacController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTemplateCoverController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTemplateParticipantController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTemplateFileController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTemplateDataInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityTemplateDriverInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.PoacController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityCoverController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityParticipantController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.life.ActivityFileController。

- 为 dubbo 的消费者增加了分组设置。
  - snowflake。
  - familyhelper-life。

- 依赖升级。
  - 升级 `familyhelper-note` 依赖版本为 `1.0.4.a` 并解决依赖问题，以应用其新功能。
  - 升级 `familyhelper-life` 依赖版本为 `1.1.0.a` 并解决依赖问题，以应用其新功能。
  - 升级 `familyhelper-clannad` 依赖版本为 `1.3.1.a` 并解决依赖问题，以应用其新功能。

#### Bug修复

- 修正部分 Bean 映射器配置 Bean 注册名称的规范性问题。
  - com.dwarfeng.familyhelper.webapi.node.configuration.finance.BeanTransformerConfiguration。

#### 功能移除

- (无)

---

### Release_1.0.9_20230227_build_A

#### 功能构建

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.MemoRemindDriverInfoController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.MemoRemindDriverSupportController。

- 优化响应服务的行为。
  - com.dwarfeng.familyhelper.webapi.impl.service.system.PexpResponseServiceImpl。
  - com.dwarfeng.familyhelper.webapi.impl.service.system.RoleResponseServiceImpl。

- 新建 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.NotifyHistoryController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.NotifyInfoRecordController。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.NotifySendRecordController。

- 依赖升级。
  - 升级 `familyhelper-project` 依赖版本为 `1.2.0.a` 并解决依赖问题，以应用其新功能。
  - 升级 `familyhelper-clannad` 依赖版本为 `1.3.0.a` 并解决依赖问题，以应用其新功能。
  - 升级 `notify` 依赖版本为 `1.3.0.a` 并解决依赖问题，以应用其新功能。

- 为 Controller 添加新方法。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.project.MemoController。

#### Bug修复

- (无)

#### 功能移除

- 移除 Controller。
  - com.dwarfeng.familyhelper.webapi.node.controller.v1.notify.SendHistoryController。

---

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
