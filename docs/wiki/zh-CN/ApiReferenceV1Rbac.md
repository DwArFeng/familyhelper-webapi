# API 参考 - V1 rbac

本文档说明 `familyhelper-webapi-node` 中 `rbac` 业务域提供的 HTTP API。
对应 Java 源码位于 `com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac`，
所有接口的统一路径前缀为 `/api/v1/rbac`。

---

## 概述

`rbac` 业务域提供过滤器支持查询、权限查看、作用域管理、用户管理、角色管理、角色用户关系管理、
权限组管理、权限管理、权限表达式管理和重置操作。当前版本包含以下控制器：

| 控制器                       | 功能         | 接口数量 |
|------------------------------|--------------|----------|
| `FilterSupportController`    | 过滤器支持   | 5        |
| `InspectController`          | 权限查看     | 3        |
| `ScopeController`            | 作用域管理   | 8        |
| `UserController`             | 用户管理     | 7        |
| `RoleController`             | 角色管理     | 10       |
| `RoleUserRelationController` | 角色用户关系 | 12       |
| `PermissionGroupController`  | 权限组管理   | 24       |
| `PermissionController`       | 权限管理     | 21       |
| `PexpController`             | 权限表达式   | 12       |
| `ResetController`            | 重置操作     | 2        |

共记录 104 个映射方法。

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

| 字段           | 类型     | 说明                                                  |
|----------------|----------|-------------------------------------------------------|
| `data`         | 任意类型 | 接口返回的数据；无返回数据或发生异常时通常为 `null`。 |
| `meta.code`    | 整数     | 响应代码；成功时为 `0`。                              |
| `meta.message` | 字符串   | 响应信息；成功时为 `good`，异常时为映射后的错误提示。 |

控制器中的业务异常会通过 `meta.code` 和 `meta.message` 表达。调用方应检查 `meta.code`，而不应只根据
`data` 是否为空判断调用是否成功。

### 身份认证

当前 `rbac` 控制器中的全部接口都同时带有 `@LoginRequired` 和 `@PermissionRequired` 注解。
需要身份认证的接口使用以下请求头：

```http
Authentication: <login-state-id>
```

`<login-state-id>` 是登录接口返回的 `data.login_state_key.string_id`。

本文档中的“所需权限”均为完整权限标识。

### 路径参数与复合键

路径中的参数按 HTTP 路径段传入；复合键在 URL 中按源码定义直接使用 `&` 连接，例如：

```text
/api/v1/rbac/permission/{scopeId}&{permissionStringId}
```

### 分页

分页查询使用以下查询参数：

| 参数      | 类型   | 必填             | 说明                                         |
|-----------|--------|------------------|----------------------------------------------|
| `page`    | 整数   | 是               | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是               | 每页数据条数，直接传入后端分页信息。         |
| `pattern` | 字符串 | 模糊匹配接口使用 | 模糊匹配模式；具体匹配规则由服务端实现决定。 |

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

### 请求校验

带请求体的接口使用 Bean Validation。文件中的“必填”列来自模型上的 `@NotNull`、`@NotEmpty` 等约束；
未声明非空约束的字段不按必填处理。校验失败时返回统一错误响应。

---

## 接口一览

| 分类         | 方法   | 路径                                                                                               | 身份认证 |
|--------------|--------|----------------------------------------------------------------------------------------------------|----------|
| 过滤器支持   | GET    | `/api/v1/rbac/filter-support/{id}/exists`                                                          | 需要     |
| 过滤器支持   | GET    | `/api/v1/rbac/filter-support/{id}`                                                                 | 需要     |
| 过滤器支持   | GET    | `/api/v1/rbac/filter-support/all`                                                                  | 需要     |
| 过滤器支持   | GET    | `/api/v1/rbac/filter-support/id-like`                                                              | 需要     |
| 过滤器支持   | GET    | `/api/v1/rbac/filter-support/label-like`                                                           | 需要     |
| 权限查看     | POST   | `/api/v1/rbac/inspect/user-view-of-permission`                                                     | 需要     |
| 权限查看     | POST   | `/api/v1/rbac/inspect/permission-view-of-role`                                                     | 需要     |
| 权限查看     | POST   | `/api/v1/rbac/inspect/permission-view-of-user`                                                     | 需要     |
| 作用域管理   | GET    | `/api/v1/rbac/scope/{id}/exists`                                                                   | 需要     |
| 作用域管理   | GET    | `/api/v1/rbac/scope/{id}`                                                                          | 需要     |
| 作用域管理   | POST   | `/api/v1/rbac/scope`                                                                               | 需要     |
| 作用域管理   | PATCH  | `/api/v1/rbac/scope`                                                                               | 需要     |
| 作用域管理   | DELETE | `/api/v1/rbac/scope/{id}`                                                                          | 需要     |
| 作用域管理   | GET    | `/api/v1/rbac/scope/all`                                                                           | 需要     |
| 作用域管理   | GET    | `/api/v1/rbac/scope/id-like`                                                                       | 需要     |
| 作用域管理   | GET    | `/api/v1/rbac/scope/name-like`                                                                     | 需要     |
| 用户管理     | GET    | `/api/v1/rbac/user/{id}/exists`                                                                    | 需要     |
| 用户管理     | GET    | `/api/v1/rbac/user/{id}`                                                                           | 需要     |
| 用户管理     | POST   | `/api/v1/rbac/user`                                                                                | 需要     |
| 用户管理     | PATCH  | `/api/v1/rbac/user`                                                                                | 需要     |
| 用户管理     | DELETE | `/api/v1/rbac/user/{id}`                                                                           | 需要     |
| 用户管理     | GET    | `/api/v1/rbac/user/all`                                                                            | 需要     |
| 用户管理     | GET    | `/api/v1/rbac/user/id-like`                                                                        | 需要     |
| 角色管理     | GET    | `/api/v1/rbac/role/{id}/exists`                                                                    | 需要     |
| 角色管理     | GET    | `/api/v1/rbac/role/{id}`                                                                           | 需要     |
| 角色管理     | POST   | `/api/v1/rbac/role`                                                                                | 需要     |
| 角色管理     | PATCH  | `/api/v1/rbac/role`                                                                                | 需要     |
| 角色管理     | DELETE | `/api/v1/rbac/role/{id}`                                                                           | 需要     |
| 角色管理     | GET    | `/api/v1/rbac/role/all`                                                                            | 需要     |
| 角色管理     | GET    | `/api/v1/rbac/user/{userKey}/role/available`                                                       | 需要     |
| 角色管理     | GET    | `/api/v1/rbac/role/enabled`                                                                        | 需要     |
| 角色管理     | GET    | `/api/v1/rbac/role/id-like`                                                                        | 需要     |
| 角色管理     | GET    | `/api/v1/rbac/user/{userKey}/not-child-role`                                                       | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/role-user-relation/{roleId}&{userId}/exists`                                         | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/role-user-relation/{roleId}&{userId}`                                                | 需要     |
| 角色用户关系 | POST   | `/api/v1/rbac/role-user-relation`                                                                  | 需要     |
| 角色用户关系 | PATCH  | `/api/v1/rbac/role-user-relation`                                                                  | 需要     |
| 角色用户关系 | DELETE | `/api/v1/rbac/role-user-relation/{roleId}&{userId}`                                                | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/role-user-relation/all`                                                              | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/role/{roleId}/role-user-relation`                                                    | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/user/{userId}/role-user-relation`                                                    | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/role-user-relation/{roleId}&{userId}/disp`                                           | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/role-user-relation/all/disp`                                                         | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/role/{roleId}/role-user-relation/disp`                                               | 需要     |
| 角色用户关系 | GET    | `/api/v1/rbac/user/{userId}/role-user-relation/disp`                                               | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/exists`                               | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}`                                      | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/all`                                                                | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/scope/{scopeId}/permission-group`                                                    | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{parentId}/children`                                      | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/permission-group-string-id-like`                                    | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like`      | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/name-like`                                                          | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/name-like`                            | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/scope/{scopeId}/permission-group/root`                                               | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/path-from-root`                       | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/disp`                                 | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/all/disp`                                                           | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/scope/{scopeId}/permission-group/disp`                                               | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/disp`                                 | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/permission-group-string-id-like/disp`                               | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like/disp` | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/name-like/disp`                                                     | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/name-like/disp`                       | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/scope/{scopeId}/permission-group/root/disp`                                          | 需要     |
| 权限组管理   | GET    | `/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/path-from-root/disp`                  | 需要     |
| 权限组管理   | POST   | `/api/v1/rbac/permission-group/create`                                                             | 需要     |
| 权限组管理   | PATCH  | `/api/v1/rbac/permission-group/update`                                                             | 需要     |
| 权限组管理   | POST   | `/api/v1/rbac/permission-group/remove`                                                             | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/{scopeId}&{permissionStringId}/exists`                                    | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/{scopeId}&{permissionStringId}`                                           | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/all`                                                                      | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/scope/{scopeId}/permission`                                                          | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission-group/{scopeId}&{permissionGroupStringId}/permission`                     | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/permission-string-id-like`                                                | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/name-like`                                                                | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/scope/{scopeId}/permission/permission-string-id-like`                                | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/scope/{scopeId}/permission/group-is-null`                                            | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/scope/{scopeId}/permission/group-is-null/permission-string-id-like`                  | 需要     |
| 权限管理     | POST   | `/api/v1/rbac/permission/create`                                                                   | 需要     |
| 权限管理     | PATCH  | `/api/v1/rbac/permission/update`                                                                   | 需要     |
| 权限管理     | POST   | `/api/v1/rbac/permission/remove`                                                                   | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/{scopeId}&{permissionStringId}/disp`                                      | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/all/disp`                                                                 | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/scope/{scopeId}/permission/disp`                                                     | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission-group/{scopeId}&{permissionGroupStringId}/permission/disp`                | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/permission-string-id-like/disp`                                           | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/permission/name-like/disp`                                                           | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/scope/{scopeId}/permission/group-is-null/disp`                                       | 需要     |
| 权限管理     | GET    | `/api/v1/rbac/scope/{scopeId}/permission/group-is-null/permission-string-id-like/disp`             | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}/exists`                                       | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}`                                              | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/scope/{scopeId}/pexp`                                                                | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/role/{roleId}/pexp`                                                                  | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/scope/{scopeId}/role/{roleId}/pexp`                                                  | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}/disp`                                         | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/scope/{scopeId}/pexp/disp`                                                           | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/role/{roleId}/pexp/disp`                                                             | 需要     |
| 权限表达式   | GET    | `/api/v1/rbac/scope/{scopeId}/role/{roleId}/pexp/disp`                                             | 需要     |
| 权限表达式   | POST   | `/api/v1/rbac/pexp/create`                                                                         | 需要     |
| 权限表达式   | PATCH  | `/api/v1/rbac/pexp/update`                                                                         | 需要     |
| 权限表达式   | POST   | `/api/v1/rbac/pexp/remove`                                                                         | 需要     |
| 重置操作     | POST   | `/api/v1/rbac/reset-filter`                                                                        | 需要     |
| 重置操作     | POST   | `/api/v1/rbac/reset-analysis`                                                                      | 需要     |

---

## 过滤器支持

提供过滤器支持信息的查询能力。

<!-- endpoint:GET:/api/v1/rbac/filter-support/{id}/exists -->

### 判断过滤器支持是否存在

判断过滤器支持是否存在。

```http
GET /api/v1/rbac/filter-support/{id}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.filter_support.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/filter-support/sample-id/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/filter-support/{id} -->

### 查询过滤器支持

查询过滤器支持。

```http
GET /api/v1/rbac/filter-support/{id}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.filter_support.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/filter-support/sample-id
```

成功响应的 `data` 为 [FastJsonFilterSupport](#fastjsonfiltersupport)。

<!-- endpoint:GET:/api/v1/rbac/filter-support/all -->

### 分页查询全部过滤器支持

分页查询全部过滤器支持。

```http
GET /api/v1/rbac/filter-support/all
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.filter_support.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/filter-support/all?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonFilterSupport](#fastjsonfiltersupport)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/filter-support/id-like -->

### 按 ID 模糊匹配分页查询过滤器支持

按 ID 模糊匹配分页查询过滤器支持。

```http
GET /api/v1/rbac/filter-support/id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.filter_support.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/filter-support/id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonFilterSupport](#fastjsonfiltersupport)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/filter-support/label-like -->

### 按标签模糊匹配分页查询过滤器支持

按标签模糊匹配分页查询过滤器支持。

```http
GET /api/v1/rbac/filter-support/label-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.filter_support.label_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/filter-support/label-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonFilterSupport](#fastjsonfiltersupport)
。分页字段见 [PagedData](#pageddata)。

---

## 权限查看

提供用户、角色与权限关系的查看分析能力。

<!-- endpoint:POST:/api/v1/rbac/inspect/user-view-of-permission -->

### 查看指定权限对应的用户视图

查看指定权限对应的用户视图。

```http
POST /api/v1/rbac/inspect/user-view-of-permission
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.inspect.inspect_user_view_of_permission`。

请求体为 [WebInputUserViewOfPermissionInspectInfo](#webinputuserviewofpermissioninspectinfo)，字段如下：

| 字段                       | 类型                                             | 必填 | 约束与说明                                 |
|----------------------------|--------------------------------------------------|------|--------------------------------------------|
| `permission_key`           | [WebInputPermissionKey](#webinputpermissionkey)  | 否   | 目标权限键；未声明非空约束。               |
| `user_key_matched`         | [WebInputStringIdKey](#webinputstringidkey)      | 否   | 要求匹配的用户键；未声明非空约束。         |
| `all_of_user_keys_matched` | [WebInputStringIdKey](#webinputstringidkey) 数组 | 否   | 要求全部匹配的用户键列表；未声明非空约束。 |
| `any_of_user_keys_matched` | [WebInputStringIdKey](#webinputstringidkey) 数组 | 否   | 要求任意匹配的用户键列表；未声明非空约束。 |
| `detail_mode`              | 布尔值                                           | 否   | 是否请求详细信息；字段缺省时使用 `false`。 |

请求体示例：

```json
{
  "permission_key": {
    "scope_string_id": "scope-example",
    "permission_string_id": "permission-example"
  },
  "user_key_matched": {
    "string_id": "user-example"
  },
  "all_of_user_keys_matched": [
    {
      "string_id": "user-example"
    }
  ],
  "any_of_user_keys_matched": [],
  "detail_mode": true
}
```

成功响应的 `data` 为 [FastJsonUserViewOfPermissionInspectResult](#fastjsonuserviewofpermissioninspectresult)。

<!-- endpoint:POST:/api/v1/rbac/inspect/permission-view-of-role -->

### 查看指定作用域角色的权限视图

查看指定作用域角色的权限视图。

```http
POST /api/v1/rbac/inspect/permission-view-of-role
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.inspect.inspect_permission_view_of_role`。

请求体为 [WebInputPermissionViewOfRoleInspectInfo](#webinputpermissionviewofroleinspectinfo)，字段如下：

| 字段                             | 类型                                                 | 必填 | 约束与说明                                 |
|----------------------------------|------------------------------------------------------|------|--------------------------------------------|
| `scoped_role_key`                | [WebInputScopedRoleKey](#webinputscopedrolekey)      | 否   | 作用域角色键；未声明非空约束。             |
| `permission_key_matched`         | [WebInputPermissionKey](#webinputpermissionkey)      | 否   | 要求匹配的权限键；未声明非空约束。         |
| `all_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求全部匹配的权限键列表；未声明非空约束。 |
| `any_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求任意匹配的权限键列表；未声明非空约束。 |
| `detail_mode`                    | 布尔值                                               | 否   | 是否请求详细信息；字段缺省时使用 `false`。 |

请求体示例：

```json
{
  "scoped_role_key": {
    "scope_string_id": "scope-example",
    "role_string_id": "role-example"
  },
  "permission_key_matched": {
    "scope_string_id": "scope-example",
    "permission_string_id": "permission-example"
  },
  "all_of_permission_keys_matched": [
    {
      "scope_string_id": "scope-example",
      "permission_string_id": "permission-example"
    }
  ],
  "any_of_permission_keys_matched": [],
  "detail_mode": true
}
```

成功响应的 `data` 为 [FastJsonPermissionViewOfRoleInspectResult](#fastjsonpermissionviewofroleinspectresult)。

<!-- endpoint:POST:/api/v1/rbac/inspect/permission-view-of-user -->

### 查看指定作用域用户的权限视图

查看指定作用域用户的权限视图。

```http
POST /api/v1/rbac/inspect/permission-view-of-user
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.inspect.inspect_permission_view_of_user`。

请求体为 [WebInputPermissionViewOfUserInspectInfo](#webinputpermissionviewofuserinspectinfo)，字段如下：

| 字段                             | 类型                                                 | 必填 | 约束与说明                                 |
|----------------------------------|------------------------------------------------------|------|--------------------------------------------|
| `scoped_user_key`                | [WebInputScopedUserKey](#webinputscopeduserkey)      | 否   | 作用域用户键；未声明非空约束。             |
| `permission_key_matched`         | [WebInputPermissionKey](#webinputpermissionkey)      | 否   | 要求匹配的权限键；未声明非空约束。         |
| `all_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求全部匹配的权限键列表；未声明非空约束。 |
| `any_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求任意匹配的权限键列表；未声明非空约束。 |
| `detail_mode`                    | 布尔值                                               | 否   | 是否请求详细信息；字段缺省时使用 `false`。 |

请求体示例：

```json
{
  "scoped_user_key": {
    "scope_string_id": "scope-example",
    "user_string_id": "user-example"
  },
  "permission_key_matched": {
    "scope_string_id": "scope-example",
    "permission_string_id": "permission-example"
  },
  "all_of_permission_keys_matched": [
    {
      "scope_string_id": "scope-example",
      "permission_string_id": "permission-example"
    }
  ],
  "any_of_permission_keys_matched": [],
  "detail_mode": true
}
```

成功响应的 `data` 为 [FastJsonPermissionViewOfUserInspectResult](#fastjsonpermissionviewofuserinspectresult)。

---

## 作用域管理

提供权限作用域的查询与增删改能力。

<!-- endpoint:GET:/api/v1/rbac/scope/{id}/exists -->

### 判断作用域是否存在

判断作用域是否存在。

```http
GET /api/v1/rbac/scope/{id}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-id/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/scope/{id} -->

### 查询作用域

查询作用域。

```http
GET /api/v1/rbac/scope/{id}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-id
```

成功响应的 `data` 为 [FastJsonScope](#fastjsonscope)。

<!-- endpoint:POST:/api/v1/rbac/scope -->

### 新增作用域

新增作用域。

```http
POST /api/v1/rbac/scope
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.insert`。

请求体为 [WebInputScope](#webinputscope)，字段如下：

| 字段     | 类型                                        | 必填 | 约束与说明                                            |
|----------|---------------------------------------------|------|-------------------------------------------------------|
| `key`    | [WebInputStringIdKey](#webinputstringidkey) | 是   | 作用域键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `name`   | 字符串                                      | 是   | 作用域名称；不允许为空，最大长度 150。                |
| `remark` | 字符串                                      | 否   | 备注；最大长度 200。                                  |

请求体示例：

```json
{
  "key": {
    "string_id": "scope-example"
  },
  "name": "示例作用域",
  "remark": "示例备注"
}
```

成功响应的 `data` 为 [FastJsonStringIdKey](#fastjsonstringidkey)。

<!-- endpoint:PATCH:/api/v1/rbac/scope -->

### 更新作用域

更新作用域。

```http
PATCH /api/v1/rbac/scope
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.update`。

请求体为 [WebInputScope](#webinputscope)，字段如下：

| 字段     | 类型                                        | 必填 | 约束与说明                                            |
|----------|---------------------------------------------|------|-------------------------------------------------------|
| `key`    | [WebInputStringIdKey](#webinputstringidkey) | 是   | 作用域键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `name`   | 字符串                                      | 是   | 作用域名称；不允许为空，最大长度 150。                |
| `remark` | 字符串                                      | 否   | 备注；最大长度 200。                                  |

请求体示例：

```json
{
  "key": {
    "string_id": "scope-example"
  },
  "name": "示例作用域",
  "remark": "示例备注"
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:DELETE:/api/v1/rbac/scope/{id} -->

### 删除作用域

删除作用域。

```http
DELETE /api/v1/rbac/scope/{id}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.delete`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
DELETE /api/v1/rbac/scope/sample-id
```

成功响应的 `data` 为 `null`。

<!-- endpoint:GET:/api/v1/rbac/scope/all -->

### 分页查询全部作用域

分页查询全部作用域。

```http
GET /api/v1/rbac/scope/all
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/all?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonScope](#fastjsonscope)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/id-like -->

### 按 ID 模糊匹配分页查询作用域

按 ID 模糊匹配分页查询作用域。

```http
GET /api/v1/rbac/scope/id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonScope](#fastjsonscope)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/name-like -->

### 按名称模糊匹配分页查询作用域

按名称模糊匹配分页查询作用域。

```http
GET /api/v1/rbac/scope/name-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.scope.name_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/name-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonScope](#fastjsonscope)
。分页字段见 [PagedData](#pageddata)。

---

## 用户管理

提供 RBAC 用户的查询与增删改能力。

<!-- endpoint:GET:/api/v1/rbac/user/{id}/exists -->

### 判断用户是否存在

判断用户是否存在。

```http
GET /api/v1/rbac/user/{id}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.user.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/sample-id/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/user/{id} -->

### 查询用户

查询用户。

```http
GET /api/v1/rbac/user/{id}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.user.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/sample-id
```

成功响应的 `data` 为 [FastJsonUser](#fastjsonuser)。

<!-- endpoint:POST:/api/v1/rbac/user -->

### 新增用户

新增用户。

```http
POST /api/v1/rbac/user
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.user.insert`。

请求体为 [WebInputUser](#webinputuser)，字段如下：

| 字段     | 类型                                        | 必填 | 约束与说明                                          |
|----------|---------------------------------------------|------|-----------------------------------------------------|
| `key`    | [WebInputStringIdKey](#webinputstringidkey) | 是   | 用户键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `remark` | 字符串                                      | 否   | 备注；最大长度 200。                                |

请求体示例：

```json
{
  "key": {
    "string_id": "user-example"
  },
  "remark": "示例备注"
}
```

成功响应的 `data` 为 [FastJsonStringIdKey](#fastjsonstringidkey)。

<!-- endpoint:PATCH:/api/v1/rbac/user -->

### 更新用户

更新用户。

```http
PATCH /api/v1/rbac/user
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.user.update`。

请求体为 [WebInputUser](#webinputuser)，字段如下：

| 字段     | 类型                                        | 必填 | 约束与说明                                          |
|----------|---------------------------------------------|------|-----------------------------------------------------|
| `key`    | [WebInputStringIdKey](#webinputstringidkey) | 是   | 用户键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `remark` | 字符串                                      | 否   | 备注；最大长度 200。                                |

请求体示例：

```json
{
  "key": {
    "string_id": "user-example"
  },
  "remark": "示例备注"
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:DELETE:/api/v1/rbac/user/{id} -->

### 删除用户

删除用户。

```http
DELETE /api/v1/rbac/user/{id}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.user.delete`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
DELETE /api/v1/rbac/user/sample-id
```

成功响应的 `data` 为 `null`。

<!-- endpoint:GET:/api/v1/rbac/user/all -->

### 分页查询全部用户

分页查询全部用户。

```http
GET /api/v1/rbac/user/all
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.user.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/all?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonUser](#fastjsonuser)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/user/id-like -->

### 按 ID 模糊匹配分页查询用户

按 ID 模糊匹配分页查询用户。

```http
GET /api/v1/rbac/user/id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.user.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonUser](#fastjsonuser)
。分页字段见 [PagedData](#pageddata)。

---

## 角色管理

提供角色的查询、启用状态筛选和增删改能力。

<!-- endpoint:GET:/api/v1/rbac/role/{id}/exists -->

### 判断角色是否存在

判断角色是否存在。

```http
GET /api/v1/rbac/role/{id}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/sample-id/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/role/{id} -->

### 查询角色

查询角色。

```http
GET /api/v1/rbac/role/{id}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/sample-id
```

成功响应的 `data` 为 [FastJsonRole](#fastjsonrole)。

<!-- endpoint:POST:/api/v1/rbac/role -->

### 新增角色

新增角色。

```http
POST /api/v1/rbac/role
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.insert`。

请求体为 [WebInputRole](#webinputrole)，字段如下：

| 字段      | 类型                                        | 必填 | 约束与说明                                          |
|-----------|---------------------------------------------|------|-----------------------------------------------------|
| `key`     | [WebInputStringIdKey](#webinputstringidkey) | 是   | 角色键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `name`    | 字符串                                      | 是   | 角色名称；不允许为空，最大长度 150。                |
| `enabled` | 布尔值                                      | 否   | 角色是否启用；字段缺省时使用 `false`。              |
| `remark`  | 字符串                                      | 否   | 备注；最大长度 200。                                |

请求体示例：

```json
{
  "key": {
    "string_id": "role-example"
  },
  "name": "示例角色",
  "enabled": true,
  "remark": "示例备注"
}
```

成功响应的 `data` 为 [FastJsonStringIdKey](#fastjsonstringidkey)。

<!-- endpoint:PATCH:/api/v1/rbac/role -->

### 更新角色

更新角色。

```http
PATCH /api/v1/rbac/role
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.update`。

请求体为 [WebInputRole](#webinputrole)，字段如下：

| 字段      | 类型                                        | 必填 | 约束与说明                                          |
|-----------|---------------------------------------------|------|-----------------------------------------------------|
| `key`     | [WebInputStringIdKey](#webinputstringidkey) | 是   | 角色键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `name`    | 字符串                                      | 是   | 角色名称；不允许为空，最大长度 150。                |
| `enabled` | 布尔值                                      | 否   | 角色是否启用；字段缺省时使用 `false`。              |
| `remark`  | 字符串                                      | 否   | 备注；最大长度 200。                                |

请求体示例：

```json
{
  "key": {
    "string_id": "role-example"
  },
  "name": "示例角色",
  "enabled": true,
  "remark": "示例备注"
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:DELETE:/api/v1/rbac/role/{id} -->

### 删除角色

删除角色。

```http
DELETE /api/v1/rbac/role/{id}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.delete`。

路径参数：

| 参数 | 类型   | 必填 | 说明                  |
|------|--------|------|-----------------------|
| `id` | 字符串 | 是   | 目标资源的字符串 ID。 |

请求体：无。

请求示例：

```http
DELETE /api/v1/rbac/role/sample-id
```

成功响应的 `data` 为 `null`。

<!-- endpoint:GET:/api/v1/rbac/role/all -->

### 分页查询全部角色

分页查询全部角色。

```http
GET /api/v1/rbac/role/all
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/all?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRole](#fastjsonrole)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/user/{userKey}/role/available -->

### 分页查询指定用户尚可绑定的角色

分页查询指定用户尚可绑定的角色。

```http
GET /api/v1/rbac/user/{userKey}/role/available
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.child_for_user_available`。

路径参数：

| 参数      | 类型   | 必填 | 说明            |
|-----------|--------|------|-----------------|
| `userKey` | 字符串 | 是   | 用户字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/sample-user/role/available?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRole](#fastjsonrole)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/role/enabled -->

### 分页查询已启用角色

分页查询已启用角色。

```http
GET /api/v1/rbac/role/enabled
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.enabled`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/enabled?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRole](#fastjsonrole)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/role/id-like -->

### 按 ID 模糊匹配分页查询角色

按 ID 模糊匹配分页查询角色。

```http
GET /api/v1/rbac/role/id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRole](#fastjsonrole)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/user/{userKey}/not-child-role -->

### 分页查询指定用户尚未绑定的角色

分页查询指定用户尚未绑定的角色。

```http
GET /api/v1/rbac/user/{userKey}/not-child-role
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role.not_child_for_user`。

路径参数：

| 参数      | 类型   | 必填 | 说明            |
|-----------|--------|------|-----------------|
| `userKey` | 字符串 | 是   | 用户字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/sample-user/not-child-role?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRole](#fastjsonrole)
。分页字段见 [PagedData](#pageddata)。

---

## 角色用户关系

提供角色与用户绑定关系的查询与增删改能力。

<!-- endpoint:GET:/api/v1/rbac/role-user-relation/{roleId}&{userId}/exists -->

### 判断角色用户关系是否存在

判断角色用户关系是否存在。

```http
GET /api/v1/rbac/role-user-relation/{roleId}&{userId}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.exists`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |
| `userId` | 字符串 | 是   | 用户字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role-user-relation/sample-role&sample-user/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/role-user-relation/{roleId}&{userId} -->

### 查询角色用户关系

查询角色用户关系。

```http
GET /api/v1/rbac/role-user-relation/{roleId}&{userId}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.get`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |
| `userId` | 字符串 | 是   | 用户字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role-user-relation/sample-role&sample-user
```

成功响应的 `data` 为 [FastJsonRoleUserRelation](#fastjsonroleuserrelation)。

<!-- endpoint:POST:/api/v1/rbac/role-user-relation -->

### 新增角色用户关系

新增角色用户关系。

```http
POST /api/v1/rbac/role-user-relation
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.insert`。

请求体为 [WebInputRoleUserRelation](#webinputroleuserrelation)，字段如下：

| 字段      | 类型                                                        | 必填 | 约束与说明                             |
|-----------|-------------------------------------------------------------|------|----------------------------------------|
| `key`     | [WebInputRoleUserRelationKey](#webinputroleuserrelationkey) | 是   | 角色用户关系键。                       |
| `enabled` | 布尔值                                                      | 否   | 关系是否启用；字段缺省时使用 `false`。 |
| `remark`  | 字符串                                                      | 否   | 备注；最大长度 200。                   |

请求体示例：

```json
{
  "key": {
    "role_string_id": "role-example",
    "user_string_id": "user-example"
  },
  "enabled": true,
  "remark": "示例备注"
}
```

成功响应的 `data` 为 [FastJsonRoleUserRelationKey](#fastjsonroleuserrelationkey)。

<!-- endpoint:PATCH:/api/v1/rbac/role-user-relation -->

### 更新角色用户关系

更新角色用户关系。

```http
PATCH /api/v1/rbac/role-user-relation
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.update`。

请求体为 [WebInputRoleUserRelation](#webinputroleuserrelation)，字段如下：

| 字段      | 类型                                                        | 必填 | 约束与说明                             |
|-----------|-------------------------------------------------------------|------|----------------------------------------|
| `key`     | [WebInputRoleUserRelationKey](#webinputroleuserrelationkey) | 是   | 角色用户关系键。                       |
| `enabled` | 布尔值                                                      | 否   | 关系是否启用；字段缺省时使用 `false`。 |
| `remark`  | 字符串                                                      | 否   | 备注；最大长度 200。                   |

请求体示例：

```json
{
  "key": {
    "role_string_id": "role-example",
    "user_string_id": "user-example"
  },
  "enabled": true,
  "remark": "示例备注"
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:DELETE:/api/v1/rbac/role-user-relation/{roleId}&{userId} -->

### 删除角色用户关系

删除角色用户关系。

```http
DELETE /api/v1/rbac/role-user-relation/{roleId}&{userId}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.delete`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |
| `userId` | 字符串 | 是   | 用户字符串 ID。 |

请求体：无。

请求示例：

```http
DELETE /api/v1/rbac/role-user-relation/sample-role&sample-user
```

成功响应的 `data` 为 `null`。

<!-- endpoint:GET:/api/v1/rbac/role-user-relation/all -->

### 分页查询全部角色用户关系

分页查询全部角色用户关系。

```http
GET /api/v1/rbac/role-user-relation/all
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role-user-relation/all?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRoleUserRelation](#fastjsonroleuserrelation)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/role/{roleId}/role-user-relation -->

### 分页查询指定角色的用户关系

分页查询指定角色的用户关系。

```http
GET /api/v1/rbac/role/{roleId}/role-user-relation
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.child_for_role`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/sample-role/role-user-relation?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRoleUserRelation](#fastjsonroleuserrelation)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/user/{userId}/role-user-relation -->

### 分页查询指定用户的角色关系

分页查询指定用户的角色关系。

```http
GET /api/v1/rbac/user/{userId}/role-user-relation
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.child_for_user`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `userId` | 字符串 | 是   | 用户字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/sample-user/role-user-relation?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonRoleUserRelation](#fastjsonroleuserrelation)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/role-user-relation/{roleId}&{userId}/disp -->

### 查询指定角色用户关系的可展示视图

查询指定角色用户关系的可展示视图。

```http
GET /api/v1/rbac/role-user-relation/{roleId}&{userId}/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.get_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |
| `userId` | 字符串 | 是   | 用户字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role-user-relation/sample-role&sample-user/disp
```

成功响应的 `data` 为 [FastJsonDispRoleUserRelation](#fastjsondisproleuserrelation)。

<!-- endpoint:GET:/api/v1/rbac/role-user-relation/all/disp -->

### 分页查询全部角色用户关系的可展示视图

分页查询全部角色用户关系的可展示视图。

```http
GET /api/v1/rbac/role-user-relation/all/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.all_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role-user-relation/all/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispRoleUserRelation](#fastjsondisproleuserrelation)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/role/{roleId}/role-user-relation/disp -->

### 分页查询指定角色的用户关系可展示视图

分页查询指定角色的用户关系可展示视图。

```http
GET /api/v1/rbac/role/{roleId}/role-user-relation/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.child_for_role_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/sample-role/role-user-relation/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispRoleUserRelation](#fastjsondisproleuserrelation)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/user/{userId}/role-user-relation/disp -->

### 分页查询指定用户的角色关系可展示视图

分页查询指定用户的角色关系可展示视图。

```http
GET /api/v1/rbac/user/{userId}/role-user-relation/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.role_user_relation.child_for_user_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `userId` | 字符串 | 是   | 用户字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/user/sample-user/role-user-relation/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispRoleUserRelation](#fastjsondisproleuserrelation)。分页字段见 [PagedData](#pageddata)。

---

## 权限组管理

提供权限组的树形查询、路径查询、可展示视图和增删改能力。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/exists -->

### 判断权限组是否存在

判断权限组是否存在。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.exists`。

路径参数：

| 参数                | 类型   | 必填 | 说明              |
|---------------------|--------|------|-------------------|
| `scopeId`           | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionGroupId` | 字符串 | 是   | 权限组字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-group/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId} -->

### 查询权限组

查询权限组。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.get`。

路径参数：

| 参数                | 类型   | 必填 | 说明              |
|---------------------|--------|------|-------------------|
| `scopeId`           | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionGroupId` | 字符串 | 是   | 权限组字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-group
```

成功响应的 `data` 为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/all -->

### 分页查询全部权限组

分页查询全部权限组。

```http
GET /api/v1/rbac/permission-group/all
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/all?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission-group -->

### 分页查询指定作用域下的权限组

分页查询指定作用域下的权限组。

```http
GET /api/v1/rbac/scope/{scopeId}/permission-group
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_scope`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission-group?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{parentId}/children -->

### 分页查询指定父权限组下的直接子权限组

分页查询指定父权限组下的直接子权限组。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{parentId}/children
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_parent`。

路径参数：

| 参数       | 类型   | 必填 | 说明                |
|------------|--------|------|---------------------|
| `scopeId`  | 字符串 | 是   | 作用域字符串 ID。   |
| `parentId` | 字符串 | 是   | 父权限组字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-parent/children?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/permission-group-string-id-like -->

### 按权限组 ID 模糊匹配分页查询权限组

按权限组 ID 模糊匹配分页查询权限组。

```http
GET /api/v1/rbac/permission-group/permission-group-string-id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.permission_group_string_id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/permission-group-string-id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like -->

### 在指定父权限组下按 ID 模糊匹配分页查询权限组

在指定父权限组下按 ID 模糊匹配分页查询权限组。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_parent_permission_group_string_id_like`。

路径参数：

| 参数       | 类型   | 必填 | 说明                |
|------------|--------|------|---------------------|
| `scopeId`  | 字符串 | 是   | 作用域字符串 ID。   |
| `parentId` | 字符串 | 是   | 父权限组字符串 ID。 |

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-parent/children/permission-group-string-id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/name-like -->

### 按名称模糊匹配分页查询权限组

按名称模糊匹配分页查询权限组。

```http
GET /api/v1/rbac/permission-group/name-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.name_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/name-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/name-like -->

### 在指定父权限组下按名称模糊匹配分页查询权限组

在指定父权限组下按名称模糊匹配分页查询权限组。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{parentId}/children/name-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_parent_name_like`。

路径参数：

| 参数       | 类型   | 必填 | 说明                |
|------------|--------|------|---------------------|
| `scopeId`  | 字符串 | 是   | 作用域字符串 ID。   |
| `parentId` | 字符串 | 是   | 父权限组字符串 ID。 |

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-parent/children/name-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission-group/root -->

### 分页查询指定作用域下的根权限组

分页查询指定作用域下的根权限组。

```http
GET /api/v1/rbac/scope/{scopeId}/permission-group/root
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_scope_root`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission-group/root?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/path-from-root -->

### 查询权限组从根开始的路径

查询权限组从根开始的路径。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/path-from-root
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.path_from_root`。

路径参数：

| 参数                | 类型   | 必填 | 说明              |
|---------------------|--------|------|-------------------|
| `scopeId`           | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionGroupId` | 字符串 | 是   | 权限组字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-group/path-from-root
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermissionGroup](#fastjsonpermissiongroup)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/disp -->

### 查询权限组的可展示视图

查询权限组的可展示视图。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.get_disp`。

路径参数：

| 参数                | 类型   | 必填 | 说明              |
|---------------------|--------|------|-------------------|
| `scopeId`           | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionGroupId` | 字符串 | 是   | 权限组字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-group/disp
```

成功响应的 `data` 为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/all/disp -->

### 分页查询全部权限组的可展示视图

分页查询全部权限组的可展示视图。

```http
GET /api/v1/rbac/permission-group/all/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.all_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/all/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission-group/disp -->

### 分页查询指定作用域下权限组的可展示视图

分页查询指定作用域下权限组的可展示视图。

```http
GET /api/v1/rbac/scope/{scopeId}/permission-group/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_scope_disp`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission-group/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/disp -->

### 分页查询指定父权限组下直接子权限组的可展示视图

分页查询指定父权限组下直接子权限组的可展示视图。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{parentId}/children/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_parent_disp`。

路径参数：

| 参数       | 类型   | 必填 | 说明                |
|------------|--------|------|---------------------|
| `scopeId`  | 字符串 | 是   | 作用域字符串 ID。   |
| `parentId` | 字符串 | 是   | 父权限组字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-parent/children/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/permission-group-string-id-like/disp -->

### 按权限组 ID 模糊匹配分页查询权限组可展示视图

按权限组 ID 模糊匹配分页查询权限组可展示视图。

```http
GET /api/v1/rbac/permission-group/permission-group-string-id-like/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.permission_group_string_id_like_disp`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/permission-group-string-id-like/disp?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like/disp -->

### 在指定父权限组下按 ID 模糊匹配分页查询权限组可展示视图

在指定父权限组下按 ID 模糊匹配分页查询权限组可展示视图。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_parent_permission_group_string_id_like_disp`。

路径参数：

| 参数       | 类型   | 必填 | 说明                |
|------------|--------|------|---------------------|
| `scopeId`  | 字符串 | 是   | 作用域字符串 ID。   |
| `parentId` | 字符串 | 是   | 父权限组字符串 ID。 |

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-parent/children/permission-group-string-id-like/disp?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/name-like/disp -->

### 按名称模糊匹配分页查询权限组可展示视图

按名称模糊匹配分页查询权限组可展示视图。

```http
GET /api/v1/rbac/permission-group/name-like/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.name_like_disp`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/name-like/disp?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{parentId}/children/name-like/disp -->

### 在指定父权限组下按名称模糊匹配分页查询权限组可展示视图

在指定父权限组下按名称模糊匹配分页查询权限组可展示视图。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{parentId}/children/name-like/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_parent_name_like_disp`。

路径参数：

| 参数       | 类型   | 必填 | 说明                |
|------------|--------|------|---------------------|
| `scopeId`  | 字符串 | 是   | 作用域字符串 ID。   |
| `parentId` | 字符串 | 是   | 父权限组字符串 ID。 |

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-parent/children/name-like/disp?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission-group/root/disp -->

### 分页查询指定作用域下根权限组的可展示视图

分页查询指定作用域下根权限组的可展示视图。

```http
GET /api/v1/rbac/scope/{scopeId}/permission-group/root/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.child_for_scope_root_disp`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission-group/root/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/path-from-root/disp -->

### 查询权限组从根开始路径的可展示视图

查询权限组从根开始路径的可展示视图。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{permissionGroupId}/path-from-root/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.path_from_root_disp`。

路径参数：

| 参数                | 类型   | 必填 | 说明              |
|---------------------|--------|------|-------------------|
| `scopeId`           | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionGroupId` | 字符串 | 是   | 权限组字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-group/path-from-root/disp
```

成功响应的 `data` 为分页对象，分页对象 `data`
数组的元素类型为 [FastJsonDispPermissionGroup](#fastjsondisppermissiongroup)。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:POST:/api/v1/rbac/permission-group/create -->

### 新增权限组

新增权限组。

```http
POST /api/v1/rbac/permission-group/create
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.create`。

请求体为 [WebInputPermissionGroupCreateInfo](#webinputpermissiongroupcreateinfo)，字段如下：

| 字段         | 类型                                                      | 必填 | 约束与说明                                     |
|--------------|-----------------------------------------------------------|------|------------------------------------------------|
| `key`        | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 是   | 待新增权限组的键。                             |
| `parent_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 父权限组键；未声明非空约束，根权限组可不提供。 |
| `name`       | 字符串                                                    | 是   | 权限组名称；不允许为空，最大长度 150。         |
| `remark`     | 字符串                                                    | 否   | 备注；最大长度 200。                           |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "permission_group_string_id": "group-example"
  },
  "parent_key": {
    "scope_string_id": "scope-example",
    "permission_group_string_id": "parent-group-example"
  },
  "name": "示例权限组",
  "remark": "示例备注"
}
```

成功响应的 `data` 为 [FastJsonPermissionGroupCreateResult](#fastjsonpermissiongroupcreateresult)。

<!-- endpoint:PATCH:/api/v1/rbac/permission-group/update -->

### 更新权限组

更新权限组。

```http
PATCH /api/v1/rbac/permission-group/update
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.update`。

请求体为 [WebInputPermissionGroupUpdateInfo](#webinputpermissiongroupupdateinfo)，字段如下：

| 字段         | 类型                                                      | 必填 | 约束与说明                                     |
|--------------|-----------------------------------------------------------|------|------------------------------------------------|
| `key`        | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 是   | 待更新权限组的键。                             |
| `parent_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 父权限组键；未声明非空约束，根权限组可不提供。 |
| `name`       | 字符串                                                    | 是   | 权限组名称；不允许为空，最大长度 150。         |
| `remark`     | 字符串                                                    | 否   | 备注；最大长度 200。                           |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "permission_group_string_id": "group-example"
  },
  "parent_key": {
    "scope_string_id": "scope-example",
    "permission_group_string_id": "parent-group-example"
  },
  "name": "更新后的示例权限组",
  "remark": "示例备注"
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:POST:/api/v1/rbac/permission-group/remove -->

### 删除权限组

删除权限组。

```http
POST /api/v1/rbac/permission-group/remove
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission_group.remove`。

请求体为 [WebInputPermissionGroupRemoveInfo](#webinputpermissiongroupremoveinfo)，字段如下：

| 字段  | 类型                                                      | 必填 | 约束与说明         |
|-------|-----------------------------------------------------------|------|--------------------|
| `key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 是   | 待删除权限组的键。 |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "permission_group_string_id": "group-example"
  }
}
```

成功响应的 `data` 为 `null`。

---

## 权限管理

提供权限的查询、按作用域或权限组筛选、可展示视图和增删改能力。

<!-- endpoint:GET:/api/v1/rbac/permission/{scopeId}&{permissionStringId}/exists -->

### 判断权限是否存在

判断权限是否存在。

```http
GET /api/v1/rbac/permission/{scopeId}&{permissionStringId}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.exists`。

路径参数：

| 参数                 | 类型   | 必填 | 说明              |
|----------------------|--------|------|-------------------|
| `scopeId`            | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionStringId` | 字符串 | 是   | 权限字符串 ID。   |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/sample-scope&sample-permission/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/permission/{scopeId}&{permissionStringId} -->

### 查询权限

查询权限。

```http
GET /api/v1/rbac/permission/{scopeId}&{permissionStringId}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.get`。

路径参数：

| 参数                 | 类型   | 必填 | 说明              |
|----------------------|--------|------|-------------------|
| `scopeId`            | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionStringId` | 字符串 | 是   | 权限字符串 ID。   |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/sample-scope&sample-permission
```

成功响应的 `data` 为 [FastJsonPermission](#fastjsonpermission)。

<!-- endpoint:GET:/api/v1/rbac/permission/all -->

### 分页查询全部权限

分页查询全部权限。

```http
GET /api/v1/rbac/permission/all
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/all?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission -->

### 分页查询指定作用域下的权限

分页查询指定作用域下的权限。

```http
GET /api/v1/rbac/scope/{scopeId}/permission
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_scope`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{permissionGroupStringId}/permission -->

### 分页查询指定权限组下的权限

分页查询指定权限组下的权限。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{permissionGroupStringId}/permission
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_group`。

路径参数：

| 参数                      | 类型   | 必填 | 说明              |
|---------------------------|--------|------|-------------------|
| `scopeId`                 | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionGroupStringId` | 字符串 | 是   | 权限组字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-group/permission?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission/permission-string-id-like -->

### 按权限 ID 模糊匹配分页查询权限

按权限 ID 模糊匹配分页查询权限。

```http
GET /api/v1/rbac/permission/permission-string-id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.permission_string_id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/permission-string-id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission/name-like -->

### 按名称模糊匹配分页查询权限

按名称模糊匹配分页查询权限。

```http
GET /api/v1/rbac/permission/name-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.name_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/name-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission/permission-string-id-like -->

### 在指定作用域下按权限 ID 模糊匹配分页查询权限

在指定作用域下按权限 ID 模糊匹配分页查询权限。

```http
GET /api/v1/rbac/scope/{scopeId}/permission/permission-string-id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_scope_permission_string_id_like`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission/permission-string-id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission/group-is-null -->

### 分页查询指定作用域下未归属权限组的权限

分页查询指定作用域下未归属权限组的权限。

```http
GET /api/v1/rbac/scope/{scopeId}/permission/group-is-null
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_scope_group_is_null`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission/group-is-null?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission/group-is-null/permission-string-id-like -->

### 在指定作用域下按权限 ID 模糊匹配分页查询未归属权限组的权限

在指定作用域下按权限 ID 模糊匹配分页查询未归属权限组的权限。

```http
GET /api/v1/rbac/scope/{scopeId}/permission/group-is-null/permission-string-id-like
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_scope_group_is_null_permission_string_id_like`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission/group-is-null/permission-string-id-like?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPermission](#fastjsonpermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:POST:/api/v1/rbac/permission/create -->

### 新增权限

新增权限。

```http
POST /api/v1/rbac/permission/create
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.create`。

请求体为 [WebInputPermissionCreateInfo](#webinputpermissioncreateinfo)，字段如下：

| 字段        | 类型                                                      | 必填 | 约束与说明                                               |
|-------------|-----------------------------------------------------------|------|----------------------------------------------------------|
| `key`       | [WebInputPermissionKey](#webinputpermissionkey)           | 是   | 待新增权限的键。                                         |
| `group_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 权限所属权限组键；未声明非空约束。                       |
| `name`      | 字符串                                                    | 是   | 权限名称；不允许为空，最大长度 150。                     |
| `remark`    | 字符串                                                    | 否   | 备注；最大长度 200。                                     |
| `level`     | 整数                                                      | 否   | 权限等级；字段缺省时使用 `0`，具体语义由服务端实现决定。 |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "permission_string_id": "permission-example"
  },
  "group_key": {
    "scope_string_id": "scope-example",
    "permission_group_string_id": "group-example"
  },
  "name": "示例权限",
  "remark": "示例备注",
  "level": 10
}
```

成功响应的 `data` 为 [FastJsonPermissionCreateResult](#fastjsonpermissioncreateresult)。

<!-- endpoint:PATCH:/api/v1/rbac/permission/update -->

### 更新权限

更新权限。

```http
PATCH /api/v1/rbac/permission/update
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.update`。

请求体为 [WebInputPermissionUpdateInfo](#webinputpermissionupdateinfo)，字段如下：

| 字段        | 类型                                                      | 必填 | 约束与说明                                               |
|-------------|-----------------------------------------------------------|------|----------------------------------------------------------|
| `key`       | [WebInputPermissionKey](#webinputpermissionkey)           | 是   | 待更新权限的键。                                         |
| `group_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 权限所属权限组键；未声明非空约束。                       |
| `name`      | 字符串                                                    | 是   | 权限名称；不允许为空，最大长度 150。                     |
| `remark`    | 字符串                                                    | 否   | 备注；最大长度 200。                                     |
| `level`     | 整数                                                      | 否   | 权限等级；字段缺省时使用 `0`，具体语义由服务端实现决定。 |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "permission_string_id": "permission-example"
  },
  "group_key": {
    "scope_string_id": "scope-example",
    "permission_group_string_id": "group-example"
  },
  "name": "更新后的示例权限",
  "remark": "示例备注",
  "level": 20
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:POST:/api/v1/rbac/permission/remove -->

### 删除权限

删除权限。

```http
POST /api/v1/rbac/permission/remove
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.remove`。

请求体为 [WebInputPermissionRemoveInfo](#webinputpermissionremoveinfo)，字段如下：

| 字段  | 类型                                            | 必填 | 约束与说明       |
|-------|-------------------------------------------------|------|------------------|
| `key` | [WebInputPermissionKey](#webinputpermissionkey) | 是   | 待删除权限的键。 |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "permission_string_id": "permission-example"
  }
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:GET:/api/v1/rbac/permission/{scopeId}&{permissionStringId}/disp -->

### 查询权限的可展示视图

查询权限的可展示视图。

```http
GET /api/v1/rbac/permission/{scopeId}&{permissionStringId}/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.get_disp`。

路径参数：

| 参数                 | 类型   | 必填 | 说明              |
|----------------------|--------|------|-------------------|
| `scopeId`            | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionStringId` | 字符串 | 是   | 权限字符串 ID。   |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/sample-scope&sample-permission/disp
```

成功响应的 `data` 为 [FastJsonDispPermission](#fastjsondisppermission)。

<!-- endpoint:GET:/api/v1/rbac/permission/all/disp -->

### 分页查询全部权限的可展示视图

分页查询全部权限的可展示视图。

```http
GET /api/v1/rbac/permission/all/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.all_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/all/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPermission](#fastjsondisppermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission/disp -->

### 分页查询指定作用域下权限的可展示视图

分页查询指定作用域下权限的可展示视图。

```http
GET /api/v1/rbac/scope/{scopeId}/permission/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_scope_disp`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPermission](#fastjsondisppermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission-group/{scopeId}&{permissionGroupStringId}/permission/disp -->

### 分页查询指定权限组下权限的可展示视图

分页查询指定权限组下权限的可展示视图。

```http
GET /api/v1/rbac/permission-group/{scopeId}&{permissionGroupStringId}/permission/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_group_disp`。

路径参数：

| 参数                      | 类型   | 必填 | 说明              |
|---------------------------|--------|------|-------------------|
| `scopeId`                 | 字符串 | 是   | 作用域字符串 ID。 |
| `permissionGroupStringId` | 字符串 | 是   | 权限组字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission-group/sample-scope&sample-group/permission/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPermission](#fastjsondisppermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission/permission-string-id-like/disp -->

### 按权限 ID 模糊匹配分页查询权限可展示视图

按权限 ID 模糊匹配分页查询权限可展示视图。

```http
GET /api/v1/rbac/permission/permission-string-id-like/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.permission_string_id_like_disp`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/permission-string-id-like/disp?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPermission](#fastjsondisppermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/permission/name-like/disp -->

### 按名称模糊匹配分页查询权限可展示视图

按名称模糊匹配分页查询权限可展示视图。

```http
GET /api/v1/rbac/permission/name-like/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.name_like_disp`。

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/permission/name-like/disp?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPermission](#fastjsondisppermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission/group-is-null/disp -->

### 分页查询指定作用域下未归属权限组权限的可展示视图

分页查询指定作用域下未归属权限组权限的可展示视图。

```http
GET /api/v1/rbac/scope/{scopeId}/permission/group-is-null/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_scope_group_is_null_disp`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission/group-is-null/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPermission](#fastjsondisppermission)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/permission/group-is-null/permission-string-id-like/disp -->

### 在指定作用域下按权限 ID 模糊匹配分页查询未归属权限组权限的可展示视图

在指定作用域下按权限 ID 模糊匹配分页查询未归属权限组权限的可展示视图。

```http
GET /api/v1/rbac/scope/{scopeId}/permission/group-is-null/permission-string-id-like/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.permission.child_for_scope_group_is_null_permission_string_id_like_disp`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数      | 类型   | 必填 | 说明                                         |
|-----------|--------|------|----------------------------------------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式；具体匹配规则由服务端实现决定。 |
| `page`    | 整数   | 是   | 页码，直接传入后端分页信息。                 |
| `rows`    | 整数   | 是   | 每页数据条数，直接传入后端分页信息。         |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/permission/group-is-null/permission-string-id-like/disp?pattern=sample-pattern&page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPermission](#fastjsondisppermission)
。分页字段见 [PagedData](#pageddata)。

---

## 权限表达式

提供权限表达式的查询、按作用域或角色筛选、可展示视图和增删改能力。

<!-- endpoint:GET:/api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}/exists -->

### 判断权限表达式是否存在

判断权限表达式是否存在。

```http
GET /api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}/exists
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.exists`。

路径参数：

| 参数           | 类型   | 必填 | 说明                  |
|----------------|--------|------|-----------------------|
| `scopeId`      | 字符串 | 是   | 作用域字符串 ID。     |
| `roleId`       | 字符串 | 是   | 角色字符串 ID。       |
| `pexpStringId` | 字符串 | 是   | 权限表达式字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/pexp/sample-scope&sample-role&sample-pexp/exists
```

成功响应的 `data` 为布尔值。

<!-- endpoint:GET:/api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId} -->

### 查询权限表达式

查询权限表达式。

```http
GET /api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.get`。

路径参数：

| 参数           | 类型   | 必填 | 说明                  |
|----------------|--------|------|-----------------------|
| `scopeId`      | 字符串 | 是   | 作用域字符串 ID。     |
| `roleId`       | 字符串 | 是   | 角色字符串 ID。       |
| `pexpStringId` | 字符串 | 是   | 权限表达式字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/pexp/sample-scope&sample-role&sample-pexp
```

成功响应的 `data` 为 [FastJsonPexp](#fastjsonpexp)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/pexp -->

### 分页查询指定作用域下的权限表达式

分页查询指定作用域下的权限表达式。

```http
GET /api/v1/rbac/scope/{scopeId}/pexp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.child_for_scope`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/pexp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPexp](#fastjsonpexp)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/role/{roleId}/pexp -->

### 分页查询指定角色的权限表达式

分页查询指定角色的权限表达式。

```http
GET /api/v1/rbac/role/{roleId}/pexp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.child_for_role`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/sample-role/pexp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPexp](#fastjsonpexp)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/role/{roleId}/pexp -->

### 分页查询指定作用域和角色的权限表达式

分页查询指定作用域和角色的权限表达式。

```http
GET /api/v1/rbac/scope/{scopeId}/role/{roleId}/pexp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.child_for_scope_child_for_role`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |
| `roleId`  | 字符串 | 是   | 角色字符串 ID。   |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/role/sample-role/pexp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonPexp](#fastjsonpexp)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}/disp -->

### 查询权限表达式的可展示视图

查询权限表达式的可展示视图。

```http
GET /api/v1/rbac/pexp/{scopeId}&{roleId}&{pexpStringId}/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.get_disp`。

路径参数：

| 参数           | 类型   | 必填 | 说明                  |
|----------------|--------|------|-----------------------|
| `scopeId`      | 字符串 | 是   | 作用域字符串 ID。     |
| `roleId`       | 字符串 | 是   | 角色字符串 ID。       |
| `pexpStringId` | 字符串 | 是   | 权限表达式字符串 ID。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/pexp/sample-scope&sample-role&sample-pexp/disp
```

成功响应的 `data` 为 [FastJsonDispPexp](#fastjsondisppexp)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/pexp/disp -->

### 分页查询指定作用域下权限表达式的可展示视图

分页查询指定作用域下权限表达式的可展示视图。

```http
GET /api/v1/rbac/scope/{scopeId}/pexp/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.child_for_scope_disp`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/pexp/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPexp](#fastjsondisppexp)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/role/{roleId}/pexp/disp -->

### 分页查询指定角色权限表达式的可展示视图

分页查询指定角色权限表达式的可展示视图。

```http
GET /api/v1/rbac/role/{roleId}/pexp/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.child_for_role_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明            |
|----------|--------|------|-----------------|
| `roleId` | 字符串 | 是   | 角色字符串 ID。 |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/role/sample-role/pexp/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPexp](#fastjsondisppexp)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:GET:/api/v1/rbac/scope/{scopeId}/role/{roleId}/pexp/disp -->

### 分页查询指定作用域和角色权限表达式的可展示视图

分页查询指定作用域和角色权限表达式的可展示视图。

```http
GET /api/v1/rbac/scope/{scopeId}/role/{roleId}/pexp/disp
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.child_for_scope_child_for_role_disp`。

路径参数：

| 参数      | 类型   | 必填 | 说明              |
|-----------|--------|------|-------------------|
| `scopeId` | 字符串 | 是   | 作用域字符串 ID。 |
| `roleId`  | 字符串 | 是   | 角色字符串 ID。   |

查询参数：

| 参数   | 类型 | 必填 | 说明                                 |
|--------|------|------|--------------------------------------|
| `page` | 整数 | 是   | 页码，直接传入后端分页信息。         |
| `rows` | 整数 | 是   | 每页数据条数，直接传入后端分页信息。 |

请求体：无。

请求示例：

```http
GET /api/v1/rbac/scope/sample-scope/role/sample-role/pexp/disp?page=0&rows=20
```

成功响应的 `data` 为分页对象，分页对象 `data` 数组的元素类型为 [FastJsonDispPexp](#fastjsondisppexp)
。分页字段见 [PagedData](#pageddata)。

<!-- endpoint:POST:/api/v1/rbac/pexp/create -->

### 新增权限表达式

新增权限表达式。

```http
POST /api/v1/rbac/pexp/create
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.create`。

请求体为 [WebInputPexpCreateInfo](#webinputpexpcreateinfo)，字段如下：

| 字段      | 类型                                | 必填 | 约束与说明                                 |
|-----------|-------------------------------------|------|--------------------------------------------|
| `key`     | [WebInputPexpKey](#webinputpexpkey) | 是   | 待新增权限表达式的键。                     |
| `content` | 字符串                              | 是   | 权限表达式内容；不允许为空，最大长度 150。 |
| `remark`  | 字符串                              | 否   | 备注；最大长度 200。                       |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "role_string_id": "role-example",
    "pexp_string_id": "pexp-example"
  },
  "content": "permission-example",
  "remark": "示例备注"
}
```

成功响应的 `data` 为 [FastJsonPexpCreateResult](#fastjsonpexpcreateresult)。

<!-- endpoint:PATCH:/api/v1/rbac/pexp/update -->

### 更新权限表达式

更新权限表达式。

```http
PATCH /api/v1/rbac/pexp/update
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.update`。

请求体为 [WebInputPexpUpdateInfo](#webinputpexpupdateinfo)，字段如下：

| 字段      | 类型                                | 必填 | 约束与说明                                 |
|-----------|-------------------------------------|------|--------------------------------------------|
| `key`     | [WebInputPexpKey](#webinputpexpkey) | 是   | 待更新权限表达式的键。                     |
| `content` | 字符串                              | 是   | 权限表达式内容；不允许为空，最大长度 150。 |
| `remark`  | 字符串                              | 否   | 备注；最大长度 200。                       |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "role_string_id": "role-example",
    "pexp_string_id": "pexp-example"
  },
  "content": "permission-example-updated",
  "remark": "示例备注"
}
```

成功响应的 `data` 为 `null`。

<!-- endpoint:POST:/api/v1/rbac/pexp/remove -->

### 删除权限表达式

删除权限表达式。

```http
POST /api/v1/rbac/pexp/remove
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.pexp.remove`。

请求体为 [WebInputPexpRemoveInfo](#webinputpexpremoveinfo)，字段如下：

| 字段  | 类型                                | 必填 | 约束与说明             |
|-------|-------------------------------------|------|------------------------|
| `key` | [WebInputPexpKey](#webinputpexpkey) | 是   | 待删除权限表达式的键。 |

请求体示例：

```json
{
  "key": {
    "scope_string_id": "scope-example",
    "role_string_id": "role-example",
    "pexp_string_id": "pexp-example"
  }
}
```

成功响应的 `data` 为 `null`。

---

## 重置操作

提供过滤器支持状态和分析状态的重置能力。

<!-- endpoint:POST:/api/v1/rbac/reset-filter -->

### 重置过滤器支持状态

重置过滤器支持状态。

```http
POST /api/v1/rbac/reset-filter
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.reset.reset_filter`。

请求体：无。

请求示例：

```http
POST /api/v1/rbac/reset-filter
```

成功响应的 `data` 为 `null`。

<!-- endpoint:POST:/api/v1/rbac/reset-analysis -->

### 重置分析状态

重置分析状态。

```http
POST /api/v1/rbac/reset-analysis
```

身份认证：需要。

所需权限：`webapi.controller_permitted.rbac.reset.reset_analysis`。

请求体：无。

请求示例：

```http
POST /api/v1/rbac/reset-analysis
```

成功响应的 `data` 为 `null`。

---

## 数据结构

### 公共包装模型

#### FastJsonResponseData

统一响应包装。

| 字段           | 类型     | 说明                                          |
|----------------|----------|-----------------------------------------------|
| `data`         | 任意类型 | 业务数据；写操作或无业务数据时通常为 `null`。 |
| `meta`         | 对象     | 响应元信息。                                  |
| `meta.code`    | 整数     | 响应代码；成功时为 `0`。                      |
| `meta.message` | 字符串   | 响应信息。                                    |

#### PagedData

分页数据结构。

| 字段           | 类型   | 说明                           |
|----------------|--------|--------------------------------|
| `current_page` | 整数   | 当前页码。                     |
| `total_pages`  | 整数   | 总页数。                       |
| `rows`         | 整数   | 每页数据条数。                 |
| `count`        | 字符串 | 数据总数，以字符串形式序列化。 |
| `data`         | 数组   | 当前页数据。                   |

## 响应键模型

### FastJsonStringIdKey

字符串 ID 键。

| 字段        | 类型   | 说明            |
|-------------|--------|-----------------|
| `string_id` | 字符串 | 非空字符串 ID。 |

### FastJsonPermissionGroupKey

权限组复合键。

| 字段                         | 类型   | 说明                          |
|------------------------------|--------|-------------------------------|
| `scope_string_id`            | 字符串 | 权限组所属作用域的字符串 ID。 |
| `permission_group_string_id` | 字符串 | 权限组字符串 ID。             |

### FastJsonPermissionKey

权限复合键。

| 字段                   | 类型   | 说明                        |
|------------------------|--------|-----------------------------|
| `scope_string_id`      | 字符串 | 权限所属作用域的字符串 ID。 |
| `permission_string_id` | 字符串 | 权限字符串 ID。             |

### FastJsonPexpKey

权限表达式复合键。

| 字段              | 类型   | 说明                              |
|-------------------|--------|-----------------------------------|
| `scope_string_id` | 字符串 | 权限表达式所属作用域的字符串 ID。 |
| `role_string_id`  | 字符串 | 权限表达式所属角色的字符串 ID。   |
| `pexp_string_id`  | 字符串 | 权限表达式字符串 ID。             |

### FastJsonRoleUserRelationKey

角色用户关系复合键。

| 字段             | 类型   | 说明            |
|------------------|--------|-----------------|
| `role_string_id` | 字符串 | 角色字符串 ID。 |
| `user_string_id` | 字符串 | 用户字符串 ID。 |

### FastJsonScopedRoleKey

带作用域的角色键。

| 字段              | 类型   | 说明              |
|-------------------|--------|-------------------|
| `scope_string_id` | 字符串 | 作用域字符串 ID。 |
| `role_string_id`  | 字符串 | 角色字符串 ID。   |

### FastJsonScopedUserKey

带作用域的用户键。

| 字段              | 类型   | 说明              |
|-------------------|--------|-------------------|
| `scope_string_id` | 字符串 | 作用域字符串 ID。 |
| `user_string_id`  | 字符串 | 用户字符串 ID。   |

## 实体、可展示视图与结果模型

### FastJsonFilterSupport

过滤器支持信息。

| 字段              | 类型                                        | 说明                   |
|-------------------|---------------------------------------------|------------------------|
| `key`             | [FastJsonStringIdKey](#fastjsonstringidkey) | 过滤器支持键。         |
| `label`           | 字符串                                      | 过滤器支持标签。       |
| `description`     | 字符串                                      | 过滤器支持说明。       |
| `example_pattern` | 字符串                                      | 过滤器支持的示例模式。 |

### FastJsonScope

权限作用域信息。

| 字段     | 类型                                        | 说明         |
|----------|---------------------------------------------|--------------|
| `key`    | [FastJsonStringIdKey](#fastjsonstringidkey) | 作用域键。   |
| `name`   | 字符串                                      | 作用域名称。 |
| `remark` | 字符串                                      | 备注。       |

### FastJsonUser

RBAC 用户信息。

| 字段     | 类型                                        | 说明     |
|----------|---------------------------------------------|----------|
| `key`    | [FastJsonStringIdKey](#fastjsonstringidkey) | 用户键。 |
| `remark` | 字符串                                      | 备注。   |

### FastJsonRole

角色信息。

| 字段      | 类型                                        | 说明           |
|-----------|---------------------------------------------|----------------|
| `key`     | [FastJsonStringIdKey](#fastjsonstringidkey) | 角色键。       |
| `name`    | 字符串                                      | 角色名称。     |
| `enabled` | 布尔值                                      | 角色是否启用。 |
| `remark`  | 字符串                                      | 备注。         |

### FastJsonRoleUserRelation

角色与用户的绑定关系。

| 字段      | 类型                                                        | 说明             |
|-----------|-------------------------------------------------------------|------------------|
| `key`     | [FastJsonRoleUserRelationKey](#fastjsonroleuserrelationkey) | 角色用户关系键。 |
| `enabled` | 布尔值                                                      | 关系是否启用。   |
| `remark`  | 字符串                                                      | 备注。           |

### FastJsonPermissionGroup

权限组信息。

| 字段         | 类型                                                      | 说明                                |
|--------------|-----------------------------------------------------------|-------------------------------------|
| `key`        | [FastJsonPermissionGroupKey](#fastjsonpermissiongroupkey) | 权限组键。                          |
| `parent_key` | [FastJsonPermissionGroupKey](#fastjsonpermissiongroupkey) | 父权限组键；根权限组可能为 `null`。 |
| `name`       | 字符串                                                    | 权限组名称。                        |
| `remark`     | 字符串                                                    | 备注。                              |

### FastJsonPermission

权限信息。

| 字段         | 类型                                                      | 说明                                            |
|--------------|-----------------------------------------------------------|-------------------------------------------------|
| `key`        | [FastJsonPermissionKey](#fastjsonpermissionkey)           | 权限键。                                        |
| `group_key`  | [FastJsonPermissionGroupKey](#fastjsonpermissiongroupkey) | 权限所属权限组键；未归属权限组时可能为 `null`。 |
| `name`       | 字符串                                                    | 权限名称。                                      |
| `remark`     | 字符串                                                    | 备注。                                          |
| `level`      | 整数                                                      | 权限等级；具体语义由服务端实现决定。            |
| `group_path` | 字符串数组                                                | 权限组路径。                                    |

### FastJsonPexp

权限表达式信息。

| 字段      | 类型                                | 说明             |
|-----------|-------------------------------------|------------------|
| `key`     | [FastJsonPexpKey](#fastjsonpexpkey) | 权限表达式键。   |
| `content` | 字符串                              | 权限表达式内容。 |
| `remark`  | 字符串                              | 备注。           |

### FastJsonDispPermissionGroup

权限组可展示视图。

| 字段           | 类型                                                      | 说明                                |
|----------------|-----------------------------------------------------------|-------------------------------------|
| `key`          | [FastJsonPermissionGroupKey](#fastjsonpermissiongroupkey) | 权限组键。                          |
| `parent_key`   | [FastJsonPermissionGroupKey](#fastjsonpermissiongroupkey) | 父权限组键；根权限组可能为 `null`。 |
| `name`         | 字符串                                                    | 权限组名称。                        |
| `remark`       | 字符串                                                    | 备注。                              |
| `scope`        | [FastJsonScope](#fastjsonscope)                           | 权限组所属作用域。                  |
| `parent`       | [FastJsonPermissionGroup](#fastjsonpermissiongroup)       | 父权限组；根权限组可能为 `null`。   |
| `root_flag`    | 布尔值                                                    | 是否为根权限组。                    |
| `has_no_child` | 布尔值                                                    | 是否没有子权限组。                  |

### FastJsonDispPermission

权限可展示视图。

| 字段         | 类型                                                      | 说明                                          |
|--------------|-----------------------------------------------------------|-----------------------------------------------|
| `key`        | [FastJsonPermissionKey](#fastjsonpermissionkey)           | 权限键。                                      |
| `group_key`  | [FastJsonPermissionGroupKey](#fastjsonpermissiongroupkey) | 权限组键；未归属权限组时可能为 `null`。       |
| `name`       | 字符串                                                    | 权限名称。                                    |
| `remark`     | 字符串                                                    | 备注。                                        |
| `level`      | 整数                                                      | 权限等级；具体语义由服务端实现决定。          |
| `group_path` | 字符串数组                                                | 权限组路径。                                  |
| `group`      | [FastJsonPermissionGroup](#fastjsonpermissiongroup)       | 权限所属权限组；未归属权限组时可能为 `null`。 |

### FastJsonDispPexp

权限表达式可展示视图。

| 字段      | 类型                                | 说明             |
|-----------|-------------------------------------|------------------|
| `key`     | [FastJsonPexpKey](#fastjsonpexpkey) | 权限表达式键。   |
| `content` | 字符串                              | 权限表达式内容。 |
| `remark`  | 字符串                              | 备注。           |
| `scope`   | [FastJsonScope](#fastjsonscope)     | 所属作用域。     |
| `role`    | [FastJsonRole](#fastjsonrole)       | 所属角色。       |

### FastJsonDispRoleUserRelation

角色用户关系可展示视图。

| 字段      | 类型                                                        | 说明             |
|-----------|-------------------------------------------------------------|------------------|
| `key`     | [FastJsonRoleUserRelationKey](#fastjsonroleuserrelationkey) | 角色用户关系键。 |
| `enabled` | 布尔值                                                      | 关系是否启用。   |
| `remark`  | 字符串                                                      | 备注。           |
| `role`    | [FastJsonRole](#fastjsonrole)                               | 角色信息。       |
| `user`    | [FastJsonUser](#fastjsonuser)                               | 用户信息。       |

### FastJsonPermissionCreateResult

权限创建结果。

| 字段  | 类型                                            | 说明             |
|-------|-------------------------------------------------|------------------|
| `key` | [FastJsonPermissionKey](#fastjsonpermissionkey) | 已创建权限的键。 |

### FastJsonPermissionGroupCreateResult

权限组创建结果。

| 字段  | 类型                                                      | 说明               |
|-------|-----------------------------------------------------------|--------------------|
| `key` | [FastJsonPermissionGroupKey](#fastjsonpermissiongroupkey) | 已创建权限组的键。 |

### FastJsonPexpCreateResult

权限表达式创建结果。

| 字段  | 类型                                | 说明                   |
|-------|-------------------------------------|------------------------|
| `key` | [FastJsonPexpKey](#fastjsonpexpkey) | 已创建权限表达式的键。 |

### FastJsonUserViewOfPermissionInspectResult

从权限查看关联用户的分析结果。

| 字段             | 类型                               | 说明                   |
|------------------|------------------------------------|------------------------|
| `matched_users`  | [FastJsonUser](#fastjsonuser) 数组 | 与查询条件匹配的用户。 |
| `matched_flag`   | 布尔值                             | 是否满足查询条件。     |
| `accepted_roles` | [FastJsonRole](#fastjsonrole) 数组 | 参与接受判定的角色。   |
| `rejected_roles` | [FastJsonRole](#fastjsonrole) 数组 | 参与拒绝判定的角色。   |

### FastJsonPermissionViewOfRoleInspectResult

从作用域角色查看权限的分析结果。

| 字段                          | 类型                                           | 说明                   |
|-------------------------------|------------------------------------------------|------------------------|
| `matched_permissions`         | [FastJsonPermission](#fastjsonpermission) 数组 | 与查询条件匹配的权限。 |
| `matched_flag`                | 布尔值                                         | 是否满足查询条件。     |
| `accepted_permissions`        | [FastJsonPermission](#fastjsonpermission) 数组 | 参与接受判定的权限。   |
| `rejected_permissions`        | [FastJsonPermission](#fastjsonpermission) 数组 | 参与拒绝判定的权限。   |
| `global_rejected_permissions` | [FastJsonPermission](#fastjsonpermission) 数组 | 全局拒绝的权限。       |

### FastJsonPermissionViewOfUserInspectResult

从作用域用户查看权限的分析结果。

| 字段                  | 类型                                           | 说明                       |
|-----------------------|------------------------------------------------|----------------------------|
| `matched_permissions` | [FastJsonPermission](#fastjsonpermission) 数组 | 与查询条件匹配的权限。     |
| `matched_flag`        | 布尔值                                         | 是否满足查询条件。         |
| `role_details`        | [FastJsonRoleDetail](#fastjsonroledetail) 数组 | 各角色对应的权限判定详情。 |

### FastJsonRoleDetail

用户权限查看结果中的角色详情。

| 字段                          | 类型                                           | 说明                       |
|-------------------------------|------------------------------------------------|----------------------------|
| `role`                        | [FastJsonRole](#fastjsonrole)                  | 角色信息。                 |
| `accepted_permissions`        | [FastJsonPermission](#fastjsonpermission) 数组 | 该角色接受判定的权限。     |
| `rejected_permissions`        | [FastJsonPermission](#fastjsonpermission) 数组 | 该角色拒绝判定的权限。     |
| `global_rejected_permissions` | [FastJsonPermission](#fastjsonpermission) 数组 | 该角色涉及的全局拒绝权限。 |

## 请求键模型

### WebInputStringIdKey

字符串 ID 请求键。

| 字段        | 类型   | 必填 | 约束与说明                          |
|-------------|--------|------|-------------------------------------|
| `string_id` | 字符串 | 是   | 非空字符串 ID；未声明最大长度约束。 |

### WebInputPermissionGroupKey

权限组请求键。

| 字段                         | 类型   | 必填 | 约束与说明                                  |
|------------------------------|--------|------|---------------------------------------------|
| `scope_string_id`            | 字符串 | 是   | 作用域字符串 ID；不允许为空，最大长度 150。 |
| `permission_group_string_id` | 字符串 | 是   | 权限组字符串 ID；不允许为空，最大长度 150。 |

### WebInputPermissionKey

权限请求键。

| 字段                   | 类型   | 必填 | 约束与说明                                  |
|------------------------|--------|------|---------------------------------------------|
| `scope_string_id`      | 字符串 | 是   | 作用域字符串 ID；不允许为空，最大长度 150。 |
| `permission_string_id` | 字符串 | 是   | 权限字符串 ID；不允许为空，最大长度 150。   |

### WebInputPexpKey

权限表达式请求键。

| 字段              | 类型   | 必填 | 约束与说明                                      |
|-------------------|--------|------|-------------------------------------------------|
| `scope_string_id` | 字符串 | 是   | 作用域字符串 ID；不允许为空，最大长度 150。     |
| `role_string_id`  | 字符串 | 是   | 角色字符串 ID；不允许为空，最大长度 150。       |
| `pexp_string_id`  | 字符串 | 是   | 权限表达式字符串 ID；不允许为空，最大长度 150。 |

### WebInputRoleUserRelationKey

角色用户关系请求键。

| 字段             | 类型   | 必填 | 约束与说明                                |
|------------------|--------|------|-------------------------------------------|
| `role_string_id` | 字符串 | 是   | 角色字符串 ID；不允许为空，最大长度 150。 |
| `user_string_id` | 字符串 | 是   | 用户字符串 ID；不允许为空，最大长度 150。 |

### WebInputScopedRoleKey

带作用域的角色请求键。

| 字段              | 类型   | 必填 | 约束与说明                                  |
|-------------------|--------|------|---------------------------------------------|
| `scope_string_id` | 字符串 | 是   | 作用域字符串 ID；不允许为空，最大长度 150。 |
| `role_string_id`  | 字符串 | 是   | 角色字符串 ID；不允许为空，最大长度 150。   |

### WebInputScopedUserKey

带作用域的用户请求键。

| 字段              | 类型   | 必填 | 约束与说明                                  |
|-------------------|--------|------|---------------------------------------------|
| `scope_string_id` | 字符串 | 是   | 作用域字符串 ID；不允许为空，最大长度 150。 |
| `user_string_id`  | 字符串 | 是   | 用户字符串 ID；不允许为空，最大长度 150。   |

## 请求体模型

### WebInputScope

作用域新增和更新请求体。

| 字段     | 类型                                        | 必填 | 约束与说明                                            |
|----------|---------------------------------------------|------|-------------------------------------------------------|
| `key`    | [WebInputStringIdKey](#webinputstringidkey) | 是   | 作用域键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `name`   | 字符串                                      | 是   | 作用域名称；不允许为空，最大长度 150。                |
| `remark` | 字符串                                      | 否   | 备注；最大长度 200。                                  |

### WebInputUser

RBAC 用户新增和更新请求体。

| 字段     | 类型                                        | 必填 | 约束与说明                                          |
|----------|---------------------------------------------|------|-----------------------------------------------------|
| `key`    | [WebInputStringIdKey](#webinputstringidkey) | 是   | 用户键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `remark` | 字符串                                      | 否   | 备注；最大长度 200。                                |

### WebInputRole

角色新增和更新请求体。

| 字段      | 类型                                        | 必填 | 约束与说明                                          |
|-----------|---------------------------------------------|------|-----------------------------------------------------|
| `key`     | [WebInputStringIdKey](#webinputstringidkey) | 是   | 角色键；新增时作为目标 ID，更新时用于定位目标记录。 |
| `name`    | 字符串                                      | 是   | 角色名称；不允许为空，最大长度 150。                |
| `enabled` | 布尔值                                      | 否   | 角色是否启用；字段缺省时使用 `false`。              |
| `remark`  | 字符串                                      | 否   | 备注；最大长度 200。                                |

### WebInputRoleUserRelation

角色用户关系新增和更新请求体。

| 字段      | 类型                                                        | 必填 | 约束与说明                             |
|-----------|-------------------------------------------------------------|------|----------------------------------------|
| `key`     | [WebInputRoleUserRelationKey](#webinputroleuserrelationkey) | 是   | 角色用户关系键。                       |
| `enabled` | 布尔值                                                      | 否   | 关系是否启用；字段缺省时使用 `false`。 |
| `remark`  | 字符串                                                      | 否   | 备注；最大长度 200。                   |

### WebInputPermissionCreateInfo

权限新增请求体。

| 字段        | 类型                                                      | 必填 | 约束与说明                                               |
|-------------|-----------------------------------------------------------|------|----------------------------------------------------------|
| `key`       | [WebInputPermissionKey](#webinputpermissionkey)           | 是   | 待新增权限的键。                                         |
| `group_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 权限所属权限组键；未声明非空约束。                       |
| `name`      | 字符串                                                    | 是   | 权限名称；不允许为空，最大长度 150。                     |
| `remark`    | 字符串                                                    | 否   | 备注；最大长度 200。                                     |
| `level`     | 整数                                                      | 否   | 权限等级；字段缺省时使用 `0`，具体语义由服务端实现决定。 |

### WebInputPermissionUpdateInfo

权限更新请求体。

| 字段        | 类型                                                      | 必填 | 约束与说明                                               |
|-------------|-----------------------------------------------------------|------|----------------------------------------------------------|
| `key`       | [WebInputPermissionKey](#webinputpermissionkey)           | 是   | 待更新权限的键。                                         |
| `group_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 权限所属权限组键；未声明非空约束。                       |
| `name`      | 字符串                                                    | 是   | 权限名称；不允许为空，最大长度 150。                     |
| `remark`    | 字符串                                                    | 否   | 备注；最大长度 200。                                     |
| `level`     | 整数                                                      | 否   | 权限等级；字段缺省时使用 `0`，具体语义由服务端实现决定。 |

### WebInputPermissionRemoveInfo

权限删除请求体。

| 字段  | 类型                                            | 必填 | 约束与说明       |
|-------|-------------------------------------------------|------|------------------|
| `key` | [WebInputPermissionKey](#webinputpermissionkey) | 是   | 待删除权限的键。 |

### WebInputPermissionGroupCreateInfo

权限组新增请求体。

| 字段         | 类型                                                      | 必填 | 约束与说明                                     |
|--------------|-----------------------------------------------------------|------|------------------------------------------------|
| `key`        | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 是   | 待新增权限组的键。                             |
| `parent_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 父权限组键；未声明非空约束，根权限组可不提供。 |
| `name`       | 字符串                                                    | 是   | 权限组名称；不允许为空，最大长度 150。         |
| `remark`     | 字符串                                                    | 否   | 备注；最大长度 200。                           |

### WebInputPermissionGroupUpdateInfo

权限组更新请求体。

| 字段         | 类型                                                      | 必填 | 约束与说明                                     |
|--------------|-----------------------------------------------------------|------|------------------------------------------------|
| `key`        | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 是   | 待更新权限组的键。                             |
| `parent_key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 否   | 父权限组键；未声明非空约束，根权限组可不提供。 |
| `name`       | 字符串                                                    | 是   | 权限组名称；不允许为空，最大长度 150。         |
| `remark`     | 字符串                                                    | 否   | 备注；最大长度 200。                           |

### WebInputPermissionGroupRemoveInfo

权限组删除请求体。

| 字段  | 类型                                                      | 必填 | 约束与说明         |
|-------|-----------------------------------------------------------|------|--------------------|
| `key` | [WebInputPermissionGroupKey](#webinputpermissiongroupkey) | 是   | 待删除权限组的键。 |

### WebInputPexpCreateInfo

权限表达式新增请求体。

| 字段      | 类型                                | 必填 | 约束与说明                                 |
|-----------|-------------------------------------|------|--------------------------------------------|
| `key`     | [WebInputPexpKey](#webinputpexpkey) | 是   | 待新增权限表达式的键。                     |
| `content` | 字符串                              | 是   | 权限表达式内容；不允许为空，最大长度 150。 |
| `remark`  | 字符串                              | 否   | 备注；最大长度 200。                       |

### WebInputPexpUpdateInfo

权限表达式更新请求体。

| 字段      | 类型                                | 必填 | 约束与说明                                 |
|-----------|-------------------------------------|------|--------------------------------------------|
| `key`     | [WebInputPexpKey](#webinputpexpkey) | 是   | 待更新权限表达式的键。                     |
| `content` | 字符串                              | 是   | 权限表达式内容；不允许为空，最大长度 150。 |
| `remark`  | 字符串                              | 否   | 备注；最大长度 200。                       |

### WebInputPexpRemoveInfo

权限表达式删除请求体。

| 字段  | 类型                                | 必填 | 约束与说明             |
|-------|-------------------------------------|------|------------------------|
| `key` | [WebInputPexpKey](#webinputpexpkey) | 是   | 待删除权限表达式的键。 |

### WebInputUserViewOfPermissionInspectInfo

从权限查看关联用户的查询条件。

| 字段                       | 类型                                             | 必填 | 约束与说明                                 |
|----------------------------|--------------------------------------------------|------|--------------------------------------------|
| `permission_key`           | [WebInputPermissionKey](#webinputpermissionkey)  | 否   | 目标权限键；未声明非空约束。               |
| `user_key_matched`         | [WebInputStringIdKey](#webinputstringidkey)      | 否   | 要求匹配的用户键；未声明非空约束。         |
| `all_of_user_keys_matched` | [WebInputStringIdKey](#webinputstringidkey) 数组 | 否   | 要求全部匹配的用户键列表；未声明非空约束。 |
| `any_of_user_keys_matched` | [WebInputStringIdKey](#webinputstringidkey) 数组 | 否   | 要求任意匹配的用户键列表；未声明非空约束。 |
| `detail_mode`              | 布尔值                                           | 否   | 是否请求详细信息；字段缺省时使用 `false`。 |

### WebInputPermissionViewOfRoleInspectInfo

从作用域角色查看权限的查询条件。

| 字段                             | 类型                                                 | 必填 | 约束与说明                                 |
|----------------------------------|------------------------------------------------------|------|--------------------------------------------|
| `scoped_role_key`                | [WebInputScopedRoleKey](#webinputscopedrolekey)      | 否   | 作用域角色键；未声明非空约束。             |
| `permission_key_matched`         | [WebInputPermissionKey](#webinputpermissionkey)      | 否   | 要求匹配的权限键；未声明非空约束。         |
| `all_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求全部匹配的权限键列表；未声明非空约束。 |
| `any_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求任意匹配的权限键列表；未声明非空约束。 |
| `detail_mode`                    | 布尔值                                               | 否   | 是否请求详细信息；字段缺省时使用 `false`。 |

### WebInputPermissionViewOfUserInspectInfo

从作用域用户查看权限的查询条件。

| 字段                             | 类型                                                 | 必填 | 约束与说明                                 |
|----------------------------------|------------------------------------------------------|------|--------------------------------------------|
| `scoped_user_key`                | [WebInputScopedUserKey](#webinputscopeduserkey)      | 否   | 作用域用户键；未声明非空约束。             |
| `permission_key_matched`         | [WebInputPermissionKey](#webinputpermissionkey)      | 否   | 要求匹配的权限键；未声明非空约束。         |
| `all_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求全部匹配的权限键列表；未声明非空约束。 |
| `any_of_permission_keys_matched` | [WebInputPermissionKey](#webinputpermissionkey) 数组 | 否   | 要求任意匹配的权限键列表；未声明非空约束。 |
| `detail_mode`                    | 布尔值                                               | 否   | 是否请求详细信息；字段缺省时使用 `false`。 |
