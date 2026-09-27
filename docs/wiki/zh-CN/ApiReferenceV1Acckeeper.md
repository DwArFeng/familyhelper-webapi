# API 参考 - V1 acckeeper

本文档说明 `familyhelper-webapi-node` 中 `acckeeper` 业务域提供的 HTTP API。
对应 Java 源码位于 `com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper`，
所有接口的统一路径前缀为 `/api/v1/acckeeper`。

---

## 概述

`acckeeper` 业务域提供账户访问与保护相关的接口，涵盖访问（登录、登出、延期）、派生、登录状态、登录历史、派生历史、登录参数记录、保护详细信息记录、保护器信息、保护器支持、保护器变量以及保护重置。当前版本包含以下控制器：

| 控制器                          | 功能                           | 接口数量 |
|---------------------------------|--------------------------------|---------:|
| `AccessController`              | 动态登录、静态登录、登出、延期 |        4 |
| `DeriveController`              | 动态派生、静态派生             |        2 |
| `LoginHistoryController`        | 登录历史查询                   |        9 |
| `DeriveHistoryController`       | 派生历史查询                   |        9 |
| `LoginStateController`          | 登录状态查询                   |       11 |
| `LoginParamRecordController`    | 登录参数记录查询               |        7 |
| `ProtectDetailRecordController` | 保护详细信息记录查询           |        7 |
| `ProtectorInfoController`       | 保护器信息查询与维护           |        8 |
| `ProtectorSupportController`    | 保护器支持查询                 |        5 |
| `ProtectorVariableController`   | 保护器变量查询与维护           |       10 |
| `ResetController`               | 重置保护                       |        1 |

---

## 公共约定

### 请求与响应格式

包含请求体的接口使用 JSON，请求头通常应包含：

```http
Content-Type: application/json
```

普通接口统一返回以下响应结构：

```json
{
  "data": null,
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

其中：

| 字段           | 类型     | 说明                                                |
|----------------|----------|-----------------------------------------------------|
| `data`         | 任意类型 | 接口返回的数据；无返回数据或发生异常时通常为 `null` |
| `meta.code`    | 整数     | 响应代码；成功时为 `0`                              |
| `meta.message` | 字符串   | 响应信息；成功时为 `good`，异常时为映射后的错误提示 |

控制器中的业务异常会通过 `meta.code` 和 `meta.message` 表达。调用方应检查 `meta.code`，而不应只根据
`data` 是否为空判断调用是否成功。

### 身份认证

需要身份认证的接口使用以下请求头：

```http
Authentication: <login-state-id>
```

`<login-state-id>` 是一个登录状态 ID，例如 `/api/v1/system/login` 返回的
`data.login_state_key.string_id`，或本业务域“动态登录”“静态登录”接口返回的 `data.login_state_key.string_id`。
请求头名称由配置项 `familyhelper.token_key` 决定，当前配置为 `Authentication`。

除“动态登录”“静态登录”外，本业务域接口均要求有效的登录状态，并需要当前账户拥有对应的权限。
本文档中的“所需权限”均为完整权限标识。

### 日期

`java.util.Date` 在当前 FastJson 配置下序列化为 Unix 毫秒时间戳，例如：

```json
1767225600000
```

### 分页

分页查询使用以下查询参数：

| 参数   | 类型 | 必填 | 说明                               |
|--------|------|------|------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息 |

分页响应的 `data` 使用以下结构：

```json
{
  "current_page": 0,
  "total_pages": 1,
  "rows": 20,
  "count": "1",
  "data": []
}
```

`count` 为避免 JavaScript 整数精度问题而序列化为字符串。

### 空值序列化

当前 FastJson 配置会输出值为 `null` 的对象字段。其中，空字符串字段会序列化为空字符串，空列表字段会序列化为空数组。

---

## 接口一览

| 分类             | 方法   | 路径                                                                          | 身份认证 |
|------------------|--------|-------------------------------------------------------------------------------|----------|
| 访问鉴权         | POST   | `/api/v1/acckeeper/access/dynamic-login`                                      | 不需要   |
| 访问鉴权         | POST   | `/api/v1/acckeeper/access/static-login`                                       | 不需要   |
| 访问鉴权         | POST   | `/api/v1/acckeeper/access/logout`                                             | 需要     |
| 访问鉴权         | POST   | `/api/v1/acckeeper/access/postpone`                                           | 需要     |
| 派生             | POST   | `/api/v1/acckeeper/derive/dynamic-derive`                                     | 需要     |
| 派生             | POST   | `/api/v1/acckeeper/derive/static-derive`                                      | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/{id}/exists`                                 | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/{id}`                                        | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/all`                                         | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/account-id-equals`                           | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/account-id-like`                             | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/{id}/disp`                                   | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/all/disp`                                    | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/account-id-equals/disp`                      | 需要     |
| 登录历史         | GET    | `/api/v1/acckeeper/login-history/account-id-like/disp`                        | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/{id}/exists`                                | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/{id}`                                       | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/all`                                        | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/account-id-equals`                          | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/account-id-like`                            | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/{id}/disp`                                  | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/all/disp`                                   | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/account-id-equals/disp`                     | 需要     |
| 派生历史         | GET    | `/api/v1/acckeeper/derive-history/account-id-like/disp`                       | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/login-state/{id}/exists`                                   | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/login-state/{id}`                                          | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/login-state/all`                                           | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/account/{accountId}/login-state`                           | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/account/{accountId}/login-state/type-dynamic`              | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/account/{accountId}/login-state/type-static`               | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/login-state/{id}/disp`                                     | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/login-state/all/disp`                                      | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/account/{accountId}/login-state/disp`                      | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/account/{accountId}/login-state/type-dynamic/disp`         | 需要     |
| 登录状态         | GET    | `/api/v1/acckeeper/account/{accountId}/login-state/type-static/disp`          | 需要     |
| 登录参数记录     | GET    | `/api/v1/acckeeper/login-param-record/{loginHistoryId}&{recordId}/exists`     | 需要     |
| 登录参数记录     | GET    | `/api/v1/acckeeper/login-param-record/{loginHistoryId}&{recordId}`            | 需要     |
| 登录参数记录     | GET    | `/api/v1/acckeeper/login-param-record/all`                                    | 需要     |
| 登录参数记录     | GET    | `/api/v1/acckeeper/login-history/{loginHistoryId}/login-param-record`         | 需要     |
| 登录参数记录     | GET    | `/api/v1/acckeeper/login-param-record/{loginHistoryId}&{recordId}/disp`       | 需要     |
| 登录参数记录     | GET    | `/api/v1/acckeeper/login-param-record/all/disp`                               | 需要     |
| 登录参数记录     | GET    | `/api/v1/acckeeper/login-history/{loginHistoryId}/login-param-record/disp`    | 需要     |
| 保护详细信息记录 | GET    | `/api/v1/acckeeper/protect-detail-record/{loginHistoryId}&{recordId}/exists`  | 需要     |
| 保护详细信息记录 | GET    | `/api/v1/acckeeper/protect-detail-record/{loginHistoryId}&{recordId}`         | 需要     |
| 保护详细信息记录 | GET    | `/api/v1/acckeeper/protect-detail-record/all`                                 | 需要     |
| 保护详细信息记录 | GET    | `/api/v1/acckeeper/login-history/{loginHistoryId}/protect-detail-record`      | 需要     |
| 保护详细信息记录 | GET    | `/api/v1/acckeeper/protect-detail-record/{loginHistoryId}&{recordId}/disp`    | 需要     |
| 保护详细信息记录 | GET    | `/api/v1/acckeeper/protect-detail-record/all/disp`                            | 需要     |
| 保护详细信息记录 | GET    | `/api/v1/acckeeper/login-history/{loginHistoryId}/protect-detail-record/disp` | 需要     |
| 保护器信息       | GET    | `/api/v1/acckeeper/protector-info/{id}/exists`                                | 需要     |
| 保护器信息       | GET    | `/api/v1/acckeeper/protector-info/{id}`                                       | 需要     |
| 保护器信息       | POST   | `/api/v1/acckeeper/protector-info`                                            | 需要     |
| 保护器信息       | PATCH  | `/api/v1/acckeeper/protector-info`                                            | 需要     |
| 保护器信息       | DELETE | `/api/v1/acckeeper/protector-info/{id}`                                       | 需要     |
| 保护器信息       | GET    | `/api/v1/acckeeper/protector-info/all`                                        | 需要     |
| 保护器信息       | GET    | `/api/v1/acckeeper/protector-info/type-equals`                                | 需要     |
| 保护器信息       | GET    | `/api/v1/acckeeper/protector-info/type-like`                                  | 需要     |
| 保护器支持       | GET    | `/api/v1/acckeeper/protector-support/{id}/exists`                             | 需要     |
| 保护器支持       | GET    | `/api/v1/acckeeper/protector-support/{id}`                                    | 需要     |
| 保护器支持       | GET    | `/api/v1/acckeeper/protector-support/all`                                     | 需要     |
| 保护器支持       | GET    | `/api/v1/acckeeper/protector-support/id-like`                                 | 需要     |
| 保护器支持       | GET    | `/api/v1/acckeeper/protector-support/label-like`                              | 需要     |
| 保护器变量       | GET    | `/api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}/exists`  | 需要     |
| 保护器变量       | GET    | `/api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}`         | 需要     |
| 保护器变量       | POST   | `/api/v1/acckeeper/protector-variable`                                        | 需要     |
| 保护器变量       | PATCH  | `/api/v1/acckeeper/protector-variable`                                        | 需要     |
| 保护器变量       | DELETE | `/api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}`         | 需要     |
| 保护器变量       | GET    | `/api/v1/acckeeper/protector-variable/all`                                    | 需要     |
| 保护器变量       | GET    | `/api/v1/acckeeper/protector-info/{protectorInfoId}/protector-variable`       | 需要     |
| 保护器变量       | GET    | `/api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}/disp`    | 需要     |
| 保护器变量       | GET    | `/api/v1/acckeeper/protector-variable/all/disp`                               | 需要     |
| 保护器变量       | GET    | `/api/v1/acckeeper/protector-info/{protectorInfoId}/protector-variable/disp`  | 需要     |
| 重置保护         | POST   | `/api/v1/acckeeper/reset-protect`                                             | 需要     |

---

## 访问鉴权

访问鉴权接口由 `AccessController` 提供，用于创建、注销和延长登录状态。

### 动态登录

使用账户键与密码创建动态登录状态。

```http
POST /api/v1/acckeeper/access/dynamic-login
```

身份认证：不需要。

请求体：

| 字段           | 类型                        | 必填 | 说明                                     |
|----------------|-----------------------------|------|------------------------------------------|
| `account_key`  | [StringIdKey](#stringidkey) | 是   | 登录账户的键                             |
| `password`     | 字符串                      | 是   | 登录密码，不允许为空字符串               |
| `remark`       | 字符串                      | 否   | 备注，最大长度为 100                     |
| `extra_params` | 对象                        | 否   | 额外参数，字符串键值对（键值均为字符串） |

请求示例：

```json
{
  "account_key": {
    "string_id": "admin"
  },
  "password": "example-password",
  "remark": "",
  "extra_params": {
    "client": "web"
  }
}
```

成功响应示例：

```json
{
  "data": {
    "login_state_key": {
      "string_id": "example-login-state-id"
    },
    "account_key": {
      "string_id": "admin"
    },
    "expire_date": 1767229200000,
    "generated_date": 1767225600000,
    "type": 0,
    "remark": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

登录成功后，将 `data.login_state_key.string_id` 作为后续受保护接口的 `Authentication` 请求头值。

### 静态登录

使用账户键、密码与过期时间创建静态登录状态。

```http
POST /api/v1/acckeeper/access/static-login
```

身份认证：不需要。

请求体：

| 字段           | 类型                        | 必填 | 说明                                              |
|----------------|-----------------------------|------|---------------------------------------------------|
| `account_key`  | [StringIdKey](#stringidkey) | 是   | 登录账户的键                                      |
| `password`     | 字符串                      | 是   | 登录密码，不允许为空字符串                        |
| `expire_date`  | 整数                        | 是   | 登录状态过期时间，Unix 毫秒时间戳，必须为将来时间 |
| `remark`       | 字符串                      | 否   | 备注，最大长度为 100                              |
| `extra_params` | 对象                        | 否   | 额外参数，字符串键值对（键值均为字符串）          |

请求示例：

```json
{
  "account_key": {
    "string_id": "admin"
  },
  "password": "example-password",
  "expire_date": 1767240000000,
  "remark": "",
  "extra_params": {
    "client": "web"
  }
}
```

成功响应的 `data` 为 [LoginResult](#loginresult)，结构同“动态登录”。

### 登出

注销指定登录状态。

```http
POST /api/v1/acckeeper/access/logout
```

身份认证：需要。所需权限：`webapi.controller_permitted.acckeeper.access.logout`。

请求体：

| 字段              | 类型                        | 必填 | 说明       |
|-------------------|-----------------------------|------|------------|
| `login_state_key` | [StringIdKey](#stringidkey) | 是   | 登录状态键 |

请求示例：

```json
{
  "login_state_key": {
    "string_id": "example-login-state-id"
  }
}
```

成功响应的 `data` 为 `null`。

### 延期

延长指定登录状态的有效期。

```http
POST /api/v1/acckeeper/access/postpone
```

身份认证：需要。所需权限：`webapi.controller_permitted.acckeeper.access.postpone`。

请求体：

| 字段              | 类型                        | 必填 | 说明       |
|-------------------|-----------------------------|------|------------|
| `login_state_key` | [StringIdKey](#stringidkey) | 是   | 登录状态键 |

请求示例：

```json
{
  "login_state_key": {
    "string_id": "example-login-state-id"
  }
}
```

成功响应的 `data` 为 [LoginResult](#loginresult)。

---

## 派生

派生接口由 `DeriveController` 提供，用于由已有登录状态派生出新的登录状态。

### 动态派生

基于指定登录状态创建动态登录状态。

```http
POST /api/v1/acckeeper/derive/dynamic-derive
```

身份认证：需要。所需权限：`webapi.controller_permitted.acckeeper.derive.dynamic_derive`。

请求体：

| 字段              | 类型                        | 必填 | 说明                 |
|-------------------|-----------------------------|------|----------------------|
| `login_state_key` | [StringIdKey](#stringidkey) | 是   | 待派生的登录状态键   |
| `remark`          | 字符串                      | 否   | 备注，最大长度为 100 |

请求示例：

```json
{
  "login_state_key": {
    "string_id": "example-login-state-id"
  },
  "remark": ""
}
```

成功响应的 `data` 为 [LoginResult](#loginresult)。

### 静态派生

基于指定登录状态创建带过期时间的静态登录状态。

```http
POST /api/v1/acckeeper/derive/static-derive
```

身份认证：需要。所需权限：`webapi.controller_permitted.acckeeper.derive.static_derive`。

请求体：

| 字段              | 类型                        | 必填 | 说明                                                |
|-------------------|-----------------------------|------|-----------------------------------------------------|
| `login_state_key` | [StringIdKey](#stringidkey) | 是   | 待派生的登录状态键                                  |
| `expire_date`     | 整数                        | 是   | 新登录状态过期时间，Unix 毫秒时间戳，必须为将来时间 |
| `remark`          | 字符串                      | 否   | 备注，最大长度为 100                                |

请求示例：

```json
{
  "login_state_key": {
    "string_id": "example-login-state-id"
  },
  "expire_date": 1767240000000,
  "remark": ""
}
```

成功响应的 `data` 为 [LoginResult](#loginresult)。

---

## 登录历史

登录历史接口由 `LoginHistoryController` 提供，用于查询记录登录行为的登录历史。所有接口均要求有效登录状态与对应权限。

### 判断登录历史是否存在

```http
GET /api/v1/acckeeper/login-history/{id}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.exists`。

路径参数：

| 参数 | 类型 | 必填 | 说明        |
|------|------|------|-------------|
| `id` | 整数 | 是   | 登录历史 ID |

请求体：无。

成功响应示例：

```json
{
  "data": true,
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取登录历史

```http
GET /api/v1/acckeeper/login-history/{id}
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.get`。

路径参数：

| 参数 | 类型 | 必填 | 说明        |
|------|------|------|-------------|
| `id` | 整数 | 是   | 登录历史 ID |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "long_id": "1"
    },
    "account_id": "admin",
    "happened_date": 1767225600000,
    "response_code": 0,
    "message": "",
    "alarm_level": 0,
    "login_remark": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部登录历史

```http
GET /api/v1/acckeeper/login-history/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [LoginHistory](#loginhistory) 类型的分页结构，格式参见[分页](#分页)。

### 按账户 ID 精确查询登录历史

```http
GET /api/v1/acckeeper/login-history/account-id-equals?accountId={accountId}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.account_id_equals`。

查询参数：

| 参数        | 类型   | 必填 | 说明         |
|-------------|--------|------|--------------|
| `accountId` | 字符串 | 是   | 账户 ID      |
| `page`      | 整数   | 是   | 页码         |
| `rows`      | 整数   | 是   | 每页数据条数 |

请求体：无。

成功响应的 `data` 为 [LoginHistory](#loginhistory) 类型的分页结构。

### 按账户 ID 模糊查询登录历史

```http
GET /api/v1/acckeeper/login-history/account-id-like?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.account_id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 账户 ID 的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页数据条数           |

请求体：无。

成功响应的 `data` 为 [LoginHistory](#loginhistory) 类型的分页结构。

### 获取可展示的登录历史

获取指定登录历史及其关联账户的展示信息。

```http
GET /api/v1/acckeeper/login-history/{id}/disp
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.get_disp`。

路径参数：

| 参数 | 类型 | 必填 | 说明        |
|------|------|------|-------------|
| `id` | 整数 | 是   | 登录历史 ID |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "long_id": "1"
    },
    "account_id": "admin",
    "happened_date": 1767225600000,
    "response_code": 0,
    "message": "",
    "alarm_level": 0,
    "login_remark": "",
    "account": {
      "key": {
        "string_id": "admin"
      },
      "password": "",
      "enabled": true,
      "remark": "",
      "serial_version": "1",
      "display_name": "管理员",
      "registered_date": 1767225600000,
      "login_count": 1,
      "password_update_count": 0,
      "derive_count": 0
    }
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部可展示的登录历史

```http
GET /api/v1/acckeeper/login-history/all/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.all_disp`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispLoginHistory](#disploginhistory) 类型的分页结构。

### 按账户 ID 精确查询可展示的登录历史

```http
GET /api/v1/acckeeper/login-history/account-id-equals/disp?accountId={accountId}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.account_id_equals_disp`。

查询参数：

| 参数        | 类型   | 必填 | 说明         |
|-------------|--------|------|--------------|
| `accountId` | 字符串 | 是   | 账户 ID      |
| `page`      | 整数   | 是   | 页码         |
| `rows`      | 整数   | 是   | 每页数据条数 |

请求体：无。

成功响应的 `data` 为 [DispLoginHistory](#disploginhistory) 类型的分页结构。

### 按账户 ID 模糊查询可展示的登录历史

```http
GET /api/v1/acckeeper/login-history/account-id-like/disp?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_history.account_id_like_disp`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 账户 ID 的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页数据条数           |

请求体：无。

成功响应的 `data` 为 [DispLoginHistory](#disploginhistory) 类型的分页结构。

---

## 派生历史

派生历史接口由 `DeriveHistoryController` 提供，用于查询记录派生行为的派生历史。所有接口均要求有效登录状态与对应权限。

### 判断派生历史是否存在

```http
GET /api/v1/acckeeper/derive-history/{id}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.exists`。

路径参数：

| 参数 | 类型 | 必填 | 说明        |
|------|------|------|-------------|
| `id` | 整数 | 是   | 派生历史 ID |

请求体：无。

成功响应的 `data` 为布尔值。

### 获取派生历史

```http
GET /api/v1/acckeeper/derive-history/{id}
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.get`。

路径参数：

| 参数 | 类型 | 必填 | 说明        |
|------|------|------|-------------|
| `id` | 整数 | 是   | 派生历史 ID |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "long_id": "1"
    },
    "account_id": "admin",
    "happened_date": 1767225600000,
    "response_code": 0,
    "derive_remark": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部派生历史

```http
GET /api/v1/acckeeper/derive-history/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DeriveHistory](#derivehistory) 类型的分页结构。

### 按账户 ID 精确查询派生历史

```http
GET /api/v1/acckeeper/derive-history/account-id-equals?accountId={accountId}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.account_id_equals`。

查询参数：

| 参数        | 类型   | 必填 | 说明         |
|-------------|--------|------|--------------|
| `accountId` | 字符串 | 是   | 账户 ID      |
| `page`      | 整数   | 是   | 页码         |
| `rows`      | 整数   | 是   | 每页数据条数 |

请求体：无。

成功响应的 `data` 为 [DeriveHistory](#derivehistory) 类型的分页结构。

### 按账户 ID 模糊查询派生历史

```http
GET /api/v1/acckeeper/derive-history/account-id-like?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.account_id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 账户 ID 的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页数据条数           |

请求体：无。

成功响应的 `data` 为 [DeriveHistory](#derivehistory) 类型的分页结构。

### 获取可展示的派生历史

获取指定派生历史及其关联账户的展示信息。

```http
GET /api/v1/acckeeper/derive-history/{id}/disp
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.get_disp`。

路径参数：

| 参数 | 类型 | 必填 | 说明        |
|------|------|------|-------------|
| `id` | 整数 | 是   | 派生历史 ID |

请求体：无。

成功响应的 `data` 为 [DispDeriveHistory](#dispderivehistory)。

### 获取全部可展示的派生历史

```http
GET /api/v1/acckeeper/derive-history/all/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.all_disp`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispDeriveHistory](#dispderivehistory) 类型的分页结构。

### 按账户 ID 精确查询可展示的派生历史

```http
GET /api/v1/acckeeper/derive-history/account-id-equals/disp?accountId={accountId}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.account_id_equals_disp`。

查询参数：

| 参数        | 类型   | 必填 | 说明         |
|-------------|--------|------|--------------|
| `accountId` | 字符串 | 是   | 账户 ID      |
| `page`      | 整数   | 是   | 页码         |
| `rows`      | 整数   | 是   | 每页数据条数 |

请求体：无。

成功响应的 `data` 为 [DispDeriveHistory](#dispderivehistory) 类型的分页结构。

### 按账户 ID 模糊查询可展示的派生历史

```http
GET /api/v1/acckeeper/derive-history/account-id-like/disp?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.derive_history.account_id_like_disp`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 账户 ID 的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页数据条数           |

请求体：无。

成功响应的 `data` 为 [DispDeriveHistory](#dispderivehistory) 类型的分页结构。

---

## 登录状态

登录状态接口由 `LoginStateController` 提供，用于查询登录状态。所有接口均要求有效登录状态与对应权限。

### 判断登录状态是否存在

```http
GET /api/v1/acckeeper/login-state/{id}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明        |
|------|--------|------|-------------|
| `id` | 字符串 | 是   | 登录状态 ID |

请求体：无。

成功响应的 `data` 为布尔值。

### 获取登录状态

```http
GET /api/v1/acckeeper/login-state/{id}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明        |
|------|--------|------|-------------|
| `id` | 字符串 | 是   | 登录状态 ID |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "string_id": "example-login-state-id"
    },
    "account_key": {
      "string_id": "admin"
    },
    "expire_date": 1767229200000,
    "serial_version": 1,
    "generated_date": 1767225600000,
    "type": 0,
    "remark": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部登录状态

```http
GET /api/v1/acckeeper/login-state/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [LoginState](#loginstate) 类型的分页结构。

### 获取账户的登录状态

```http
GET /api/v1/acckeeper/account/{accountId}/login-state?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.child_for_account`。

路径参数：

| 参数        | 类型   | 必填 | 说明    |
|-------------|--------|------|---------|
| `accountId` | 字符串 | 是   | 账户 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [LoginState](#loginstate) 类型的分页结构。

### 获取账户的动态登录状态

```http
GET /api/v1/acckeeper/account/{accountId}/login-state/type-dynamic?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_dynamic`。

路径参数：

| 参数        | 类型   | 必填 | 说明    |
|-------------|--------|------|---------|
| `accountId` | 字符串 | 是   | 账户 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [LoginState](#loginstate) 类型的分页结构，仅包含动态类型的登录状态。

### 获取账户的静态登录状态

```http
GET /api/v1/acckeeper/account/{accountId}/login-state/type-static?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_static`。

路径参数：

| 参数        | 类型   | 必填 | 说明    |
|-------------|--------|------|---------|
| `accountId` | 字符串 | 是   | 账户 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [LoginState](#loginstate) 类型的分页结构，仅包含静态类型的登录状态。

### 获取可展示的登录状态

```http
GET /api/v1/acckeeper/login-state/{id}/disp
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.get_disp`。

路径参数：

| 参数 | 类型   | 必填 | 说明        |
|------|--------|------|-------------|
| `id` | 字符串 | 是   | 登录状态 ID |

请求体：无。

成功响应的 `data` 为 [DispLoginState](#disploginstate)。

### 获取全部可展示的登录状态

```http
GET /api/v1/acckeeper/login-state/all/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.all_disp`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispLoginState](#disploginstate) 类型的分页结构。

### 获取账户的可展示登录状态

```http
GET /api/v1/acckeeper/account/{accountId}/login-state/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.child_for_account_disp`。

路径参数：

| 参数        | 类型   | 必填 | 说明    |
|-------------|--------|------|---------|
| `accountId` | 字符串 | 是   | 账户 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispLoginState](#disploginstate) 类型的分页结构。

### 获取账户的可展示动态登录状态

```http
GET /api/v1/acckeeper/account/{accountId}/login-state/type-dynamic/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_dynamic_disp`。

路径参数：

| 参数        | 类型   | 必填 | 说明    |
|-------------|--------|------|---------|
| `accountId` | 字符串 | 是   | 账户 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispLoginState](#disploginstate) 类型的分页结构，仅包含动态类型的登录状态。

### 获取账户的可展示静态登录状态

```http
GET /api/v1/acckeeper/account/{accountId}/login-state/type-static/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_static_disp`。

路径参数：

| 参数        | 类型   | 必填 | 说明    |
|-------------|--------|------|---------|
| `accountId` | 字符串 | 是   | 账户 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispLoginState](#disploginstate) 类型的分页结构，仅包含静态类型的登录状态。

---

## 登录参数记录

登录参数记录接口由 `LoginParamRecordController` 提供，用于查询登录过程中携带的参数记录。所有接口均要求有效登录状态与对应权限。

记录使用复合键，其路径形式为 `{loginHistoryId}&{recordId}`，两个 ID 之间以 `&` 分隔。

### 判断登录参数记录是否存在

```http
GET /api/v1/acckeeper/login-param-record/{loginHistoryId}&{recordId}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.login_param_record.exists`。

路径参数：

| 参数             | 类型   | 必填 | 说明        |
|------------------|--------|------|-------------|
| `loginHistoryId` | 整数   | 是   | 登录历史 ID |
| `recordId`       | 字符串 | 是   | 记录 ID     |

请求体：无。

成功响应的 `data` 为布尔值。

### 获取登录参数记录

```http
GET /api/v1/acckeeper/login-param-record/{loginHistoryId}&{recordId}
```

所需权限：`webapi.controller_permitted.acckeeper.login_param_record.get`。

路径参数：

| 参数             | 类型   | 必填 | 说明        |
|------------------|--------|------|-------------|
| `loginHistoryId` | 整数   | 是   | 登录历史 ID |
| `recordId`       | 字符串 | 是   | 记录 ID     |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "login_history_id": "1",
      "record_id": "client"
    },
    "value": "web"
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部登录参数记录

```http
GET /api/v1/acckeeper/login-param-record/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_param_record.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [LoginParamRecord](#loginparamrecord) 类型的分页结构。

### 获取登录历史的登录参数记录

```http
GET /api/v1/acckeeper/login-history/{loginHistoryId}/login-param-record?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_param_record.child_for_login_history`。

路径参数：

| 参数             | 类型 | 必填 | 说明        |
|------------------|------|------|-------------|
| `loginHistoryId` | 整数 | 是   | 登录历史 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [LoginParamRecord](#loginparamrecord) 类型的分页结构。

### 获取可展示的登录参数记录

获取指定登录参数记录及其关联登录历史的展示信息。

```http
GET /api/v1/acckeeper/login-param-record/{loginHistoryId}&{recordId}/disp
```

所需权限：`webapi.controller_permitted.acckeeper.login_param_record.get_disp`。

路径参数：

| 参数             | 类型   | 必填 | 说明        |
|------------------|--------|------|-------------|
| `loginHistoryId` | 整数   | 是   | 登录历史 ID |
| `recordId`       | 字符串 | 是   | 记录 ID     |

请求体：无。

成功响应的 `data` 为 [DispLoginParamRecord](#disploginparamrecord)。

### 获取全部可展示的登录参数记录

```http
GET /api/v1/acckeeper/login-param-record/all/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_param_record.all_disp`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispLoginParamRecord](#disploginparamrecord) 类型的分页结构。

### 获取登录历史的可展示登录参数记录

```http
GET /api/v1/acckeeper/login-history/{loginHistoryId}/login-param-record/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.login_param_record.child_for_login_history_disp`。

路径参数：

| 参数             | 类型 | 必填 | 说明        |
|------------------|------|------|-------------|
| `loginHistoryId` | 整数 | 是   | 登录历史 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispLoginParamRecord](#disploginparamrecord) 类型的分页结构。

---

## 保护详细信息记录

保护详细信息记录接口由 `ProtectDetailRecordController` 提供，用于查询登录过程中的保护详细信息记录。所有接口均要求有效登录状态与对应权限。

记录使用复合键，其路径形式为 `{loginHistoryId}&{recordId}`，两个 ID 之间以 `&` 分隔。

### 判断保护详细信息记录是否存在

```http
GET /api/v1/acckeeper/protect-detail-record/{loginHistoryId}&{recordId}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.protect_detail_record.exists`。

路径参数：

| 参数             | 类型   | 必填 | 说明        |
|------------------|--------|------|-------------|
| `loginHistoryId` | 整数   | 是   | 登录历史 ID |
| `recordId`       | 字符串 | 是   | 记录 ID     |

请求体：无。

成功响应的 `data` 为布尔值。

### 获取保护详细信息记录

```http
GET /api/v1/acckeeper/protect-detail-record/{loginHistoryId}&{recordId}
```

所需权限：`webapi.controller_permitted.acckeeper.protect_detail_record.get`。

路径参数：

| 参数             | 类型   | 必填 | 说明        |
|------------------|--------|------|-------------|
| `loginHistoryId` | 整数   | 是   | 登录历史 ID |
| `recordId`       | 字符串 | 是   | 记录 ID     |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "login_history_id": "1",
      "record_id": "protector"
    },
    "value": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部保护详细信息记录

```http
GET /api/v1/acckeeper/protect-detail-record/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protect_detail_record.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [ProtectDetailRecord](#protectdetailrecord) 类型的分页结构。

### 获取登录历史的保护详细信息记录

```http
GET /api/v1/acckeeper/login-history/{loginHistoryId}/protect-detail-record?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protect_detail_record.child_for_login_history`。

路径参数：

| 参数             | 类型 | 必填 | 说明        |
|------------------|------|------|-------------|
| `loginHistoryId` | 整数 | 是   | 登录历史 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [ProtectDetailRecord](#protectdetailrecord) 类型的分页结构。

### 获取可展示的保护详细信息记录

获取指定保护详细信息记录及其关联登录历史的展示信息。

```http
GET /api/v1/acckeeper/protect-detail-record/{loginHistoryId}&{recordId}/disp
```

所需权限：`webapi.controller_permitted.acckeeper.protect_detail_record.get_disp`。

路径参数：

| 参数             | 类型   | 必填 | 说明        |
|------------------|--------|------|-------------|
| `loginHistoryId` | 整数   | 是   | 登录历史 ID |
| `recordId`       | 字符串 | 是   | 记录 ID     |

请求体：无。

成功响应的 `data` 为 [DispProtectDetailRecord](#dispprotectdetailrecord)。

### 获取全部可展示的保护详细信息记录

```http
GET /api/v1/acckeeper/protect-detail-record/all/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protect_detail_record.all_disp`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispProtectDetailRecord](#dispprotectdetailrecord) 类型的分页结构。

### 获取登录历史的可展示保护详细信息记录

```http
GET /api/v1/acckeeper/login-history/{loginHistoryId}/protect-detail-record/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protect_detail_record.child_for_login_history_disp`。

路径参数：

| 参数             | 类型 | 必填 | 说明        |
|------------------|------|------|-------------|
| `loginHistoryId` | 整数 | 是   | 登录历史 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispProtectDetailRecord](#dispprotectdetailrecord) 类型的分页结构。

---

## 保护器信息

保护器信息接口由 `ProtectorInfoController` 提供，用于查询和维护保护器信息。所有接口均要求有效登录状态与对应权限。写操作仅返回
`null` 或新建主键，不返回完整实体。

### 判断保护器信息是否存在

```http
GET /api/v1/acckeeper/protector-info/{id}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明          |
|------|--------|------|---------------|
| `id` | 字符串 | 是   | 保护器信息 ID |

请求体：无。

成功响应的 `data` 为布尔值。

### 获取保护器信息

```http
GET /api/v1/acckeeper/protector-info/{id}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明          |
|------|--------|------|---------------|
| `id` | 字符串 | 是   | 保护器信息 ID |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "string_id": "example-protector-info-id"
    },
    "type": "example-type",
    "param": "",
    "remark": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 创建保护器信息

```http
POST /api/v1/acckeeper/protector-info
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.insert`。

请求体（使用创建分组校验）：

| 字段     | 类型                        | 必填 | 说明                                        |
|----------|-----------------------------|------|---------------------------------------------|
| `key`    | [StringIdKey](#stringidkey) | 否   | 保护器信息键；当前输入模型未声明非空约束    |
| `type`   | 字符串                      | 是   | 保护器类型，不允许为空字符串，最大长度为 50 |
| `param`  | 字符串                      | 否   | 保护器参数                                  |
| `remark` | 字符串                      | 否   | 备注，最大长度为 100                        |

请求示例：

```json
{
  "key": {
    "string_id": "example-protector-info-id"
  },
  "type": "example-type",
  "param": "",
  "remark": ""
}
```

成功响应示例：

```json
{
  "data": {
    "string_id": "example-protector-info-id"
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

`data` 为 [StringIdKey](#stringidkey)，表示新建实体的主键。

### 更新保护器信息

```http
PATCH /api/v1/acckeeper/protector-info
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.update`。

请求体字段同“创建保护器信息”。

请求示例：

```json
{
  "key": {
    "string_id": "example-protector-info-id"
  },
  "type": "example-type",
  "param": "",
  "remark": ""
}
```

成功响应的 `data` 为 `null`。

### 删除保护器信息

```http
DELETE /api/v1/acckeeper/protector-info/{id}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.delete`。

路径参数：

| 参数 | 类型   | 必填 | 说明          |
|------|--------|------|---------------|
| `id` | 字符串 | 是   | 保护器信息 ID |

请求体：无。

成功响应的 `data` 为 `null`。

### 获取全部保护器信息

```http
GET /api/v1/acckeeper/protector-info/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [ProtectorInfo](#protectorinfo) 类型的分页结构。

### 按类型精确查询保护器信息

```http
GET /api/v1/acckeeper/protector-info/type-equals?type={type}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.type_equals`。

查询参数：

| 参数   | 类型   | 必填 | 说明         |
|--------|--------|------|--------------|
| `type` | 字符串 | 是   | 保护器类型   |
| `page` | 整数   | 是   | 页码         |
| `rows` | 整数   | 是   | 每页数据条数 |

请求体：无。

成功响应的 `data` 为 [ProtectorInfo](#protectorinfo) 类型的分页结构。

### 按类型模糊查询保护器信息

```http
GET /api/v1/acckeeper/protector-info/type-like?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_info.type_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                     |
|-----------|--------|------|--------------------------|
| `pattern` | 字符串 | 是   | 保护器类型的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                     |
| `rows`    | 整数   | 是   | 每页数据条数             |

请求体：无。

成功响应的 `data` 为 [ProtectorInfo](#protectorinfo) 类型的分页结构。

---

## 保护器支持

保护器支持接口由 `ProtectorSupportController` 提供，用于查询保护器支持信息。所有接口均要求有效登录状态与对应权限。

### 判断保护器支持是否存在

```http
GET /api/v1/acckeeper/protector-support/{id}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.protector_support.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明          |
|------|--------|------|---------------|
| `id` | 字符串 | 是   | 保护器支持 ID |

请求体：无。

成功响应的 `data` 为布尔值。

### 获取保护器支持

```http
GET /api/v1/acckeeper/protector-support/{id}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_support.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明          |
|------|--------|------|---------------|
| `id` | 字符串 | 是   | 保护器支持 ID |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "string_id": "example-protector-support-id"
    },
    "label": "示例标签",
    "description": "",
    "example_param": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部保护器支持

```http
GET /api/v1/acckeeper/protector-support/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_support.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [ProtectorSupport](#protectorsupport) 类型的分页结构。

### 按保护器支持 ID 模糊查询

```http
GET /api/v1/acckeeper/protector-support/id-like?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_support.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                         |
|-----------|--------|------|------------------------------|
| `pattern` | 字符串 | 是   | 保护器支持 ID 的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                         |
| `rows`    | 整数   | 是   | 每页数据条数                 |

请求体：无。

成功响应的 `data` 为 [ProtectorSupport](#protectorsupport) 类型的分页结构。

### 按标签模糊查询保护器支持

```http
GET /api/v1/acckeeper/protector-support/label-like?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_support.label_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明               |
|-----------|--------|------|--------------------|
| `pattern` | 字符串 | 是   | 标签的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码               |
| `rows`    | 整数   | 是   | 每页数据条数       |

请求体：无。

成功响应的 `data` 为 [ProtectorSupport](#protectorsupport) 类型的分页结构。

---

## 保护器变量

保护器变量接口由 `ProtectorVariableController` 提供，用于查询和维护保护器变量。所有接口均要求有效登录状态与对应权限。写操作仅返回
`null` 或新建主键，不返回完整实体。

保护器变量使用复合键，其路径形式为 `{protectorInfoId}&{variableId}`，两个 ID 之间以 `&` 分隔。

### 判断保护器变量是否存在

```http
GET /api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}/exists
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.exists`。

路径参数：

| 参数              | 类型   | 必填 | 说明          |
|-------------------|--------|------|---------------|
| `protectorInfoId` | 字符串 | 是   | 保护器信息 ID |
| `variableId`      | 字符串 | 是   | 变量 ID       |

请求体：无。

成功响应的 `data` 为布尔值。

### 获取保护器变量

```http
GET /api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.get`。

路径参数：

| 参数              | 类型   | 必填 | 说明          |
|-------------------|--------|------|---------------|
| `protectorInfoId` | 字符串 | 是   | 保护器信息 ID |
| `variableId`      | 字符串 | 是   | 变量 ID       |

请求体：无。

成功响应示例：

```json
{
  "data": {
    "key": {
      "protector_info_id": "example-protector-info-id",
      "variable_id": "example-variable-id"
    },
    "value": "",
    "remark": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 创建保护器变量

```http
POST /api/v1/acckeeper/protector-variable
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.insert`。

请求体（使用创建分组校验）：

| 字段     | 类型                                          | 必填 | 说明                 |
|----------|-----------------------------------------------|------|----------------------|
| `key`    | [ProtectorVariableKey](#protectorvariablekey) | 是   | 保护器变量键         |
| `value`  | 字符串                                        | 否   | 变量值               |
| `remark` | 字符串                                        | 否   | 备注，最大长度为 100 |

请求示例：

```json
{
  "key": {
    "protector_info_id": "example-protector-info-id",
    "variable_id": "example-variable-id"
  },
  "value": "",
  "remark": ""
}
```

成功响应示例：

```json
{
  "data": {
    "protector_info_id": "example-protector-info-id",
    "variable_id": "example-variable-id"
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

`data` 为 [ProtectorVariableKey](#protectorvariablekey)，表示新建实体的主键。

### 更新保护器变量

```http
PATCH /api/v1/acckeeper/protector-variable
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.update`。

请求体字段同“创建保护器变量”。

请求示例：

```json
{
  "key": {
    "protector_info_id": "example-protector-info-id",
    "variable_id": "example-variable-id"
  },
  "value": "",
  "remark": ""
}
```

成功响应的 `data` 为 `null`。

### 删除保护器变量

```http
DELETE /api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.delete`。

路径参数：

| 参数              | 类型   | 必填 | 说明          |
|-------------------|--------|------|---------------|
| `protectorInfoId` | 字符串 | 是   | 保护器信息 ID |
| `variableId`      | 字符串 | 是   | 变量 ID       |

请求体：无。

成功响应的 `data` 为 `null`。

### 获取全部保护器变量

```http
GET /api/v1/acckeeper/protector-variable/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.all`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [ProtectorVariable](#protectorvariable) 类型的分页结构。

### 获取保护器信息的保护器变量

```http
GET /api/v1/acckeeper/protector-info/{protectorInfoId}/protector-variable?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.child_for_protector_info`。

路径参数：

| 参数              | 类型   | 必填 | 说明          |
|-------------------|--------|------|---------------|
| `protectorInfoId` | 字符串 | 是   | 保护器信息 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [ProtectorVariable](#protectorvariable) 类型的分页结构。

### 获取可展示的保护器变量

获取指定保护器变量及其关联保护器信息的展示信息。

```http
GET /api/v1/acckeeper/protector-variable/{protectorInfoId}&{variableId}/disp
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.get_disp`。

路径参数：

| 参数              | 类型   | 必填 | 说明          |
|-------------------|--------|------|---------------|
| `protectorInfoId` | 字符串 | 是   | 保护器信息 ID |
| `variableId`      | 字符串 | 是   | 变量 ID       |

请求体：无。

成功响应的 `data` 为 [DispProtectorVariable](#dispprotectorvariable)。

### 获取全部可展示的保护器变量

```http
GET /api/v1/acckeeper/protector-variable/all/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.all_disp`。

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispProtectorVariable](#dispprotectorvariable) 类型的分页结构。

### 获取保护器信息的可展示保护器变量

```http
GET /api/v1/acckeeper/protector-info/{protectorInfoId}/protector-variable/disp?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.acckeeper.protector_variable.child_for_protector_info_disp`。

路径参数：

| 参数              | 类型   | 必填 | 说明          |
|-------------------|--------|------|---------------|
| `protectorInfoId` | 字符串 | 是   | 保护器信息 ID |

查询参数：参见[分页](#分页)。

请求体：无。

成功响应的 `data` 为 [DispProtectorVariable](#dispprotectorvariable) 类型的分页结构。

---

## 重置保护

重置保护接口由 `ResetController` 提供。

### 重置保护

触发保护重置操作。

```http
POST /api/v1/acckeeper/reset-protect
```

身份认证：需要。所需权限：`webapi.controller_permitted.acckeeper.reset.reset_protect`。

请求体：无。

成功响应的 `data` 为 `null`。

---

## 数据结构

### StringIdKey

字符串 ID 键。

| 字段        | 类型   | 说明                                    |
|-------------|--------|-----------------------------------------|
| `string_id` | 字符串 | 字符串 ID；作为请求体时不允许为空字符串 |

### LongIdKey

长整型 ID 键。

| 字段      | 类型   | 说明                                                |
|-----------|--------|-----------------------------------------------------|
| `long_id` | 字符串 | 长整型 ID，序列化为字符串以避免 JavaScript 精度问题 |

### RecordKey

记录实体主键。

| 字段               | 类型   | 说明                        |
|--------------------|--------|-----------------------------|
| `login_history_id` | 字符串 | 登录历史 ID，序列化为字符串 |
| `record_id`        | 字符串 | 记录 ID                     |

### ProtectorVariableKey

保护器变量主键。

| 字段                | 类型   | 说明                            |
|---------------------|--------|---------------------------------|
| `protector_info_id` | 字符串 | 保护器信息 ID，不允许为空字符串 |
| `variable_id`       | 字符串 | 变量 ID，不允许为空字符串       |

### Account

账户信息，出现在可展示（`disp`）接口返回的 `account` 字段中。

| 字段                    | 类型                        | 说明                      |
|-------------------------|-----------------------------|---------------------------|
| `key`                   | [StringIdKey](#stringidkey) | 账户键                    |
| `password`              | 字符串                      | 密码（服务返回的存储值）  |
| `enabled`               | 布尔值                      | 账户是否启用              |
| `remark`                | 字符串                      | 备注                      |
| `serial_version`        | 字符串                      | 序列号，序列化为字符串    |
| `display_name`          | 字符串                      | 显示名称                  |
| `registered_date`       | 整数                        | 注册时间，Unix 毫秒时间戳 |
| `login_count`           | 整数                        | 登录次数                  |
| `password_update_count` | 整数                        | 密码更新次数              |
| `derive_count`          | 整数                        | 派生次数                  |

### LoginResult

动态登录、静态登录、动态派生、静态派生与延期接口返回的登录结果。

| 字段              | 类型                        | 说明                                   |
|-------------------|-----------------------------|----------------------------------------|
| `login_state_key` | [StringIdKey](#stringidkey) | 登录状态键                             |
| `account_key`     | [StringIdKey](#stringidkey) | 登录账户键                             |
| `expire_date`     | 整数                        | 登录状态过期时间，Unix 毫秒时间戳      |
| `generated_date`  | 整数                        | 登录状态生成时间，Unix 毫秒时间戳      |
| `type`            | 整数                        | 登录状态类型，具体取值由服务端实现决定 |
| `remark`          | 字符串                      | 备注                                   |

### LoginState

登录状态查询接口返回的登录状态信息。

| 字段             | 类型                        | 说明                                   |
|------------------|-----------------------------|----------------------------------------|
| `key`            | [StringIdKey](#stringidkey) | 登录状态键                             |
| `account_key`    | [StringIdKey](#stringidkey) | 登录账户键                             |
| `expire_date`    | 整数                        | 登录状态过期时间，Unix 毫秒时间戳      |
| `serial_version` | 整数                        | 序列号                                 |
| `generated_date` | 整数                        | 登录状态生成时间，Unix 毫秒时间戳      |
| `type`           | 整数                        | 登录状态类型，具体取值由服务端实现决定 |
| `remark`         | 字符串                      | 备注                                   |

### DispLoginState

可展示的登录状态信息，在 [LoginState](#loginstate) 的基础上附带关联账户。

| 字段             | 类型                        | 说明                              |
|------------------|-----------------------------|-----------------------------------|
| `key`            | [StringIdKey](#stringidkey) | 登录状态键                        |
| `account_key`    | [StringIdKey](#stringidkey) | 登录账户键                        |
| `expire_date`    | 整数                        | 登录状态过期时间，Unix 毫秒时间戳 |
| `serial_version` | 字符串                      | 序列号，序列化为字符串            |
| `generated_date` | 整数                        | 登录状态生成时间，Unix 毫秒时间戳 |
| `type`           | 整数                        | 登录状态类型                      |
| `remark`         | 字符串                      | 备注                              |
| `account`        | [Account](#account)         | 关联账户                          |

### DeriveHistory

派生历史信息。

| 字段            | 类型                    | 说明                               |
|-----------------|-------------------------|------------------------------------|
| `key`           | [LongIdKey](#longidkey) | 派生历史键                         |
| `account_id`    | 字符串                  | 关联账户 ID                        |
| `happened_date` | 整数                    | 派生发生时间，Unix 毫秒时间戳      |
| `response_code` | 整数                    | 响应代码，具体取值由服务端实现决定 |
| `derive_remark` | 字符串                  | 派生备注                           |

### DispDeriveHistory

可展示的派生历史信息，在 [DeriveHistory](#derivehistory) 的基础上附带关联账户。

| 字段            | 类型                    | 说明                               |
|-----------------|-------------------------|------------------------------------|
| `key`           | [LongIdKey](#longidkey) | 派生历史键                         |
| `account_id`    | 字符串                  | 关联账户 ID                        |
| `happened_date` | 整数                    | 派生发生时间，Unix 毫秒时间戳      |
| `response_code` | 整数                    | 响应代码，具体取值由服务端实现决定 |
| `derive_remark` | 字符串                  | 派生备注                           |
| `account`       | [Account](#account)     | 关联账户                           |

### LoginHistory

登录历史信息。

| 字段            | 类型                    | 说明                               |
|-----------------|-------------------------|------------------------------------|
| `key`           | [LongIdKey](#longidkey) | 登录历史键                         |
| `account_id`    | 字符串                  | 关联账户 ID                        |
| `happened_date` | 整数                    | 登录发生时间，Unix 毫秒时间戳      |
| `response_code` | 整数                    | 响应代码，具体取值由服务端实现决定 |
| `message`       | 字符串                  | 登录结果消息                       |
| `alarm_level`   | 整数                    | 告警等级；可能为 `null`            |
| `login_remark`  | 字符串                  | 登录备注                           |

### DispLoginHistory

可展示的登录历史信息，在 [LoginHistory](#loginhistory) 的基础上附带关联账户。

| 字段            | 类型                    | 说明                               |
|-----------------|-------------------------|------------------------------------|
| `key`           | [LongIdKey](#longidkey) | 登录历史键                         |
| `account_id`    | 字符串                  | 关联账户 ID                        |
| `happened_date` | 整数                    | 登录发生时间，Unix 毫秒时间戳      |
| `response_code` | 整数                    | 响应代码，具体取值由服务端实现决定 |
| `message`       | 字符串                  | 登录结果消息                       |
| `alarm_level`   | 整数                    | 告警等级；可能为 `null`            |
| `login_remark`  | 字符串                  | 登录备注                           |
| `account`       | [Account](#account)     | 关联账户                           |

### LoginParamRecord

登录参数记录。

| 字段    | 类型                    | 说明           |
|---------|-------------------------|----------------|
| `key`   | [RecordKey](#recordkey) | 登录参数记录键 |
| `value` | 字符串                  | 记录值         |

### DispLoginParamRecord

可展示的登录参数记录，在 [LoginParamRecord](#loginparamrecord) 的基础上附带关联登录历史。

| 字段            | 类型                          | 说明           |
|-----------------|-------------------------------|----------------|
| `key`           | [RecordKey](#recordkey)       | 登录参数记录键 |
| `value`         | 字符串                        | 记录值         |
| `login_history` | [LoginHistory](#loginhistory) | 关联登录历史   |

### ProtectDetailRecord

保护详细信息记录。

| 字段    | 类型                    | 说明               |
|---------|-------------------------|--------------------|
| `key`   | [RecordKey](#recordkey) | 保护详细信息记录键 |
| `value` | 字符串                  | 记录值             |

### DispProtectDetailRecord

可展示的保护详细信息记录，在 [ProtectDetailRecord](#protectdetailrecord) 的基础上附带关联登录历史。

| 字段            | 类型                          | 说明               |
|-----------------|-------------------------------|--------------------|
| `key`           | [RecordKey](#recordkey)       | 保护详细信息记录键 |
| `value`         | 字符串                        | 记录值             |
| `login_history` | [LoginHistory](#loginhistory) | 关联登录历史       |

### ProtectorInfo

保护器信息。

| 字段     | 类型                        | 说明         |
|----------|-----------------------------|--------------|
| `key`    | [StringIdKey](#stringidkey) | 保护器信息键 |
| `type`   | 字符串                      | 保护器类型   |
| `param`  | 字符串                      | 保护器参数   |
| `remark` | 字符串                      | 备注         |

### ProtectorSupport

保护器支持信息。

| 字段            | 类型                        | 说明         |
|-----------------|-----------------------------|--------------|
| `key`           | [StringIdKey](#stringidkey) | 保护器支持键 |
| `label`         | 字符串                      | 标签         |
| `description`   | 字符串                      | 描述         |
| `example_param` | 字符串                      | 示例参数     |

### ProtectorVariable

保护器变量。

| 字段     | 类型                                          | 说明         |
|----------|-----------------------------------------------|--------------|
| `key`    | [ProtectorVariableKey](#protectorvariablekey) | 保护器变量键 |
| `value`  | 字符串                                        | 变量值       |
| `remark` | 字符串                                        | 备注         |

### DispProtectorVariable

可展示的保护器变量，在 [ProtectorVariable](#protectorvariable) 的基础上附带关联保护器信息。

| 字段             | 类型                                          | 说明           |
|------------------|-----------------------------------------------|----------------|
| `key`            | [ProtectorVariableKey](#protectorvariablekey) | 保护器变量键   |
| `value`          | 字符串                                        | 变量值         |
| `remark`         | 字符串                                        | 备注           |
| `protector_info` | [ProtectorInfo](#protectorinfo)               | 关联保护器信息 |

### PagedData

分页数据结构。

| 字段           | 类型   | 说明                         |
|----------------|--------|------------------------------|
| `current_page` | 整数   | 当前页码                     |
| `total_pages`  | 整数   | 总页数                       |
| `rows`         | 整数   | 每页数据条数                 |
| `count`        | 字符串 | 数据总数，以字符串形式序列化 |
| `data`         | 数组   | 当前页数据                   |
