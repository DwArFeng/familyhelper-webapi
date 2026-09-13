# API 参考 - V1 system

本文档说明 `familyhelper-webapi-node` 中 `system` 业务域提供的 HTTP API。
对应 Java 源码位于 `com.dwarfeng.familyhelper.webapi.node.controller.v1.system`，
所有接口的统一路径前缀为 `/api/v1/system`。

---

## 概述

`system` 业务域提供登录状态管理、当前账户权限查看、服务器时间查询以及账户管理功能。当前版本包含以下控制器：

| 控制器                 | 功能                                       | 接口数量 |
|------------------------|--------------------------------------------|---------:|
| `LoginController`      | 登录、退出当前登录状态、延长当前登录状态   |        3 |
| `PermissionController` | 查看当前账户在指定作用域下的权限           |        1 |
| `TimeController`       | 获取服务器当前时间                         |        1 |
| `AccountController`    | 账户查询、注册、更新、删除、密码管理与禁用 |       13 |

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

`<login-state-id>` 是登录接口返回的 `data.login_state_key.string_id`。

账户管理接口除了要求有效登录状态外，还要求当前账户拥有对应的权限。本文档中的“所需权限”均为完整权限标识。

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

| 分类       | 方法 | 路径                                               | 身份认证 |
|------------|------|----------------------------------------------------|----------|
| 登录与会话 | POST | `/api/v1/system/login`                             | 不需要   |
| 登录与会话 | POST | `/api/v1/system/logout-me`                         | 需要     |
| 登录与会话 | POST | `/api/v1/system/postpone-me`                       | 需要     |
| 权限查看   | POST | `/api/v1/system/inspect-permission-of-me`          | 需要     |
| 服务器时间 | POST | `/api/v1/system/current-date`                      | 不需要   |
| 账户管理   | GET  | `/api/v1/system/account/{id}/exists`               | 需要     |
| 账户管理   | GET  | `/api/v1/system/account/{id}`                      | 需要     |
| 账户管理   | GET  | `/api/v1/system/account/all`                       | 需要     |
| 账户管理   | GET  | `/api/v1/system/profile/{profileId}/account/guest` | 需要     |
| 账户管理   | GET  | `/api/v1/system/account/id-like`                   | 需要     |
| 账户管理   | GET  | `/api/v1/system/account/{id}/disp`                 | 需要     |
| 账户管理   | GET  | `/api/v1/system/account/id-like/disp`              | 需要     |
| 账户管理   | POST | `/api/v1/system/account/register`                  | 需要     |
| 账户管理   | POST | `/api/v1/system/account/update`                    | 需要     |
| 账户管理   | POST | `/api/v1/system/account/remove`                    | 需要     |
| 账户管理   | POST | `/api/v1/system/account/update-password`           | 需要     |
| 账户管理   | POST | `/api/v1/system/account/reset-password`            | 需要     |
| 账户管理   | POST | `/api/v1/system/account/invalid`                   | 需要     |

---

## 登录与会话

### 登录

使用账户 ID 和密码创建登录状态。

```http
POST /api/v1/system/login
```

身份认证：不需要。

请求体：

| 字段          | 类型                        | 必填 | 说明                       |
|---------------|-----------------------------|------|----------------------------|
| `account_key` | [StringIdKey](#stringidkey) | 是   | 登录账户的键               |
| `password`    | 字符串                      | 是   | 登录密码，不允许为空字符串 |

请求示例：

```json
{
  "account_key": {
    "string_id": "admin"
  },
  "password": "example-password"
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

登录成功后，将 `login_state_key.string_id` 作为后续受保护接口的 `Authentication` 请求头值。

### 退出当前登录状态

注销 `Authentication` 请求头指定的当前登录状态。

```http
POST /api/v1/system/logout-me
```

身份认证：需要。无额外权限标识。

请求体：无。

成功响应示例：

```json
{
  "data": null,
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 延长当前登录状态

延长 `Authentication` 请求头指定的当前登录状态，并返回延期后的登录状态信息。

```http
POST /api/v1/system/postpone-me
```

身份认证：需要。无额外权限标识。

请求体：无。

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
    "expire_date": 1767232800000,
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

---

## 权限查看

### 查看当前账户权限

查看当前登录账户在指定权限作用域下拥有的权限。

```http
POST /api/v1/system/inspect-permission-of-me
```

身份认证：需要。无额外权限标识。

控制器没有声明 `@LoginRequired`，但接口会读取 `Authentication` 请求头并据此解析当前账户，因此调用时仍须提供有效登录状态。

请求体：

| 字段        | 类型                        | 必填 | 说明                 |
|-------------|-----------------------------|------|----------------------|
| `scope_key` | [StringIdKey](#stringidkey) | 是   | 待查看的权限作用域键 |

请求示例：

```json
{
  "scope_key": {
    "string_id": "familyhelper"
  }
}
```

成功响应示例：

```json
{
  "data": {
    "permissions": [
      {
        "key": {
          "scope_string_id": "familyhelper",
          "permission_string_id": "webapi.controller_permitted.system.account.get"
        },
        "group_key": {
          "scope_string_id": "familyhelper",
          "permission_group_string_id": "system.account"
        },
        "name": "查看账户",
        "remark": "",
        "level": 0,
        "group_path": [
          "system",
          "account"
        ]
      }
    ]
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

---

## 服务器时间

### 获取服务器当前时间

获取服务器当前时间。

```http
POST /api/v1/system/current-date
```

身份认证：不需要。

请求体：无。

成功响应示例：

```json
{
  "data": 1767225600000,
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

---

## 账户管理

账户管理接口全部要求有效的 `Authentication` 请求头和对应权限。

### 判断账户是否存在

```http
GET /api/v1/system/account/{id}/exists
```

所需权限：`webapi.controller_permitted.system.account.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 账户 ID |

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

### 获取账户

```http
GET /api/v1/system/account/{id}
```

所需权限：`webapi.controller_permitted.system.account.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 账户 ID |

成功响应示例：

```json
{
  "data": {
    "key": {
      "string_id": "admin"
    },
    "name": "管理员",
    "enabled": true,
    "remark": ""
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取全部账户

```http
GET /api/v1/system/account/all?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.system.account.all`。

查询参数：参见[分页](#分页)。

成功响应示例：

```json
{
  "data": {
    "current_page": 0,
    "total_pages": 1,
    "rows": 20,
    "count": "1",
    "data": [
      {
        "key": {
          "string_id": "admin"
        },
        "name": "管理员",
        "enabled": true,
        "remark": ""
      }
    ]
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 获取档案的访客账户

获取指定档案对应的访客账户分页数据。

```http
GET /api/v1/system/profile/{profileId}/account/guest?page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.system.account.child_for_profile_guest`。

路径参数：

| 参数        | 类型   | 必填 | 说明    |
|-------------|--------|------|---------|
| `profileId` | 字符串 | 是   | 档案 ID |

查询参数：参见[分页](#分页)。

成功响应的 `data` 为 [Account](#account) 类型的分页结构，格式与“获取全部账户”接口一致。

### 按账户 ID 模糊查询

```http
GET /api/v1/system/account/id-like?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.system.account.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 账户 ID 的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页数据条数           |

成功响应的 `data` 为 [Account](#account) 类型的分页结构。

### 获取可展示账户

获取指定账户的展示信息。展示结果会结合当前登录账户生成 `display_name`。

```http
GET /api/v1/system/account/{id}/disp
```

所需权限：`webapi.controller_permitted.system.account.get_disp`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 账户 ID |

成功响应示例：

```json
{
  "data": {
    "key": {
      "string_id": "user"
    },
    "name": "普通用户",
    "enabled": true,
    "remark": "",
    "display_name": "家庭成员"
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 按账户 ID 模糊查询可展示账户

```http
GET /api/v1/system/account/id-like/disp?pattern={pattern}&page={page}&rows={rows}
```

所需权限：`webapi.controller_permitted.system.account.id_like_disp`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 账户 ID 的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页数据条数           |

成功响应的 `data` 为 [DispAccount](#dispaccount) 类型的分页结构。

### 注册账户

```http
POST /api/v1/system/account/register
```

所需权限：`webapi.controller_permitted.system.account.register`。

请求体：

| 字段           | 类型                        | 必填 | 说明                                       |
|----------------|-----------------------------|------|--------------------------------------------|
| `account_key`  | [StringIdKey](#stringidkey) | 否   | 待注册的账户键；当前输入模型未声明非空约束 |
| `display_name` | 字符串                      | 否   | 显示名称，最大长度为 20                    |
| `enabled`      | 布尔值                      | 是   | 是否启用账户                               |
| `remark`       | 字符串                      | 否   | 备注，最大长度为 100                       |
| `password`     | 字符串                      | 是   | 初始密码，不允许为空字符串                 |

请求示例：

```json
{
  "account_key": {
    "string_id": "user"
  },
  "display_name": "普通用户",
  "enabled": true,
  "remark": "",
  "password": "example-password"
}
```

成功响应示例：

```json
{
  "data": null,
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

### 更新账户

```http
POST /api/v1/system/account/update
```

所需权限：`webapi.controller_permitted.system.account.update`。

请求体：

| 字段           | 类型                        | 必填 | 说明                    |
|----------------|-----------------------------|------|-------------------------|
| `account_key`  | [StringIdKey](#stringidkey) | 是   | 待更新的账户键          |
| `display_name` | 字符串                      | 否   | 显示名称，最大长度为 20 |
| `enabled`      | 布尔值                      | 是   | 是否启用账户            |
| `remark`       | 字符串                      | 否   | 备注，最大长度为 100    |

请求示例：

```json
{
  "account_key": {
    "string_id": "user"
  },
  "display_name": "家庭成员",
  "enabled": true,
  "remark": "已更新"
}
```

成功响应的 `data` 为 `null`。

### 删除账户

```http
POST /api/v1/system/account/remove
```

所需权限：`webapi.controller_permitted.system.account.remove`。

请求体为 [StringIdKey](#stringidkey)：

```json
{
  "string_id": "user"
}
```

成功响应的 `data` 为 `null`。

### 更新账户密码

使用旧密码校验后更新目标账户密码。

```http
POST /api/v1/system/account/update-password
```

所需权限：`webapi.controller_permitted.system.account.update_password`。

请求体：

| 字段           | 类型                        | 必填 | 说明                                   |
|----------------|-----------------------------|------|----------------------------------------|
| `account_key`  | [StringIdKey](#stringidkey) | 否   | 目标账户键；当前输入模型未声明非空约束 |
| `old_password` | 字符串                      | 是   | 旧密码，不允许为空字符串               |
| `new_password` | 字符串                      | 是   | 新密码，不允许为空字符串               |

请求示例：

```json
{
  "account_key": {
    "string_id": "user"
  },
  "old_password": "old-example-password",
  "new_password": "new-example-password"
}
```

成功响应的 `data` 为 `null`。

### 重置账户密码

直接设置目标账户的新密码，不要求提供旧密码。

```http
POST /api/v1/system/account/reset-password
```

所需权限：`webapi.controller_permitted.system.account.reset_password`。

请求体：

| 字段           | 类型                        | 必填 | 说明                                   |
|----------------|-----------------------------|------|----------------------------------------|
| `account_key`  | [StringIdKey](#stringidkey) | 否   | 目标账户键；当前输入模型未声明非空约束 |
| `new_password` | 字符串                      | 是   | 新密码，不允许为空字符串               |

请求示例：

```json
{
  "account_key": {
    "string_id": "user"
  },
  "new_password": "new-example-password"
}
```

成功响应的 `data` 为 `null`。

### 禁用账户

将指定账户设置为无效状态。

```http
POST /api/v1/system/account/invalid
```

所需权限：`webapi.controller_permitted.system.account.invalid`。

请求体为 [StringIdKey](#stringidkey)：

```json
{
  "string_id": "user"
}
```

成功响应的 `data` 为 `null`。

---

## 数据结构

### StringIdKey

字符串 ID 键。

| 字段        | 类型   | 说明          |
|-------------|--------|---------------|
| `string_id` | 字符串 | 非空字符串 ID |

### Account

账户信息。

| 字段      | 类型                        | 说明         |
|-----------|-----------------------------|--------------|
| `key`     | [StringIdKey](#stringidkey) | 账户键       |
| `name`    | 字符串                      | 账户名称     |
| `enabled` | 布尔值                      | 账户是否启用 |
| `remark`  | 字符串                      | 备注         |

### DispAccount

可展示账户信息。

| 字段           | 类型                        | 说明                           |
|----------------|-----------------------------|--------------------------------|
| `key`          | [StringIdKey](#stringidkey) | 账户键                         |
| `name`         | 字符串                      | 账户名称                       |
| `enabled`      | 布尔值                      | 账户是否启用                   |
| `remark`       | 字符串                      | 备注                           |
| `display_name` | 字符串                      | 面向当前查看账户生成的显示名称 |

### LoginResult

登录结果和延期结果使用相同的 JSON 字段结构。

| 字段              | 类型                        | 说明                              |
|-------------------|-----------------------------|-----------------------------------|
| `login_state_key` | [StringIdKey](#stringidkey) | 登录状态键                        |
| `account_key`     | [StringIdKey](#stringidkey) | 登录账户键                        |
| `expire_date`     | 整数                        | 登录状态过期时间，Unix 毫秒时间戳 |
| `generated_date`  | 整数                        | 登录状态生成时间，Unix 毫秒时间戳 |
| `type`            | 整数                        | 登录状态类型                      |
| `remark`          | 字符串                      | 备注                              |

### Permission

权限查看接口返回的权限信息。

| 字段                                   | 类型       | 说明          |
|----------------------------------------|------------|---------------|
| `key`                                  | 对象       | 权限键        |
| `key.scope_string_id`                  | 字符串     | 权限作用域 ID |
| `key.permission_string_id`             | 字符串     | 权限 ID       |
| `group_key`                            | 对象       | 权限组键      |
| `group_key.scope_string_id`            | 字符串     | 权限作用域 ID |
| `group_key.permission_group_string_id` | 字符串     | 权限组 ID     |
| `name`                                 | 字符串     | 权限名称      |
| `remark`                               | 字符串     | 备注          |
| `level`                                | 整数       | 权限等级      |
| `group_path`                           | 字符串数组 | 权限组路径    |

### PagedData

分页数据结构。

| 字段           | 类型   | 说明                         |
|----------------|--------|------------------------------|
| `current_page` | 整数   | 当前页码                     |
| `total_pages`  | 整数   | 总页数                       |
| `rows`         | 整数   | 每页数据条数                 |
| `count`        | 字符串 | 数据总数，以字符串形式序列化 |
| `data`         | 数组   | 当前页数据                   |
