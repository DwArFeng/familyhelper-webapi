# API 参考 - V1 fileio

本文档说明 `familyhelper-webapi-node` 中 `fileio` 业务域提供的 HTTP API。
对应 Java 源码位于 `com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio`，统一路径前缀为 `/api/v1/fileio`。

---

## 概述

`fileio` 业务域提供文件导入与导出能力的完整维护接口，包括任务配置、导出/导入配置、导出器/导入器/读取器/写入器
信息与能力、模板文件、任务、任务文件与任务元数据，以及文件流的上传与下载。源码包含 23 个 Controller、
168 个映射方法；除 6 个按凭证下载的方法外，其余方法均要求有效登录状态和完整权限标识。

| 控制器                        | 功能                                           | 接口数量 |
|-------------------------------|------------------------------------------------|---------:|
| `ExportConfController`        | 导出配置查询、新增、更新和删除                 |        7 |
| `ExportController`            | 导出任务创建、元数据写入、执行和文件下载       |        8 |
| `ExporterInfoController`      | 导出器信息查询、新增、更新和删除               |        7 |
| `ExporterSupportController`   | 导出器能力查询                                 |        4 |
| `ExportFileController`        | 导出文件查询、删除和下载                       |        9 |
| `ExportMetadataController`    | 导出任务元数据查询                             |        4 |
| `ExportTaskController`        | 导出任务查询与可展示视图                       |       13 |
| `ExportTaskSettingController` | 导出任务配置维护和查询                         |        6 |
| `ExportTemplateController`    | 导出模板查询、创建、移除、上传和下载           |       12 |
| `ImportConfController`        | 导入配置查询、新增、更新和删除                 |        7 |
| `ImportController`            | 导入任务创建、元数据写入、执行、文件上传和下载 |       10 |
| `ImporterInfoController`      | 导入器信息查询、新增、更新和删除               |        7 |
| `ImporterSupportController`   | 导入器能力查询                                 |        4 |
| `ImportFileController`        | 导入文件查询、删除、上传和下载                 |       11 |
| `ImportMetadataController`    | 导入任务元数据查询                             |        4 |
| `ImportTaskController`        | 导入任务查询与可展示视图                       |       13 |
| `ImportTaskSettingController` | 导入任务配置维护和查询                         |        6 |
| `ImportTemplateController`    | 导入模板查询、创建、移除、上传和下载           |       12 |
| `ReaderInfoController`        | 读取器信息查询、新增、更新和删除               |        7 |
| `ReaderSupportController`     | 读取器能力查询                                 |        4 |
| `ResetController`             | 导出与导入子系统重置                           |        2 |
| `WriterInfoController`        | 写入器信息查询、新增、更新和删除               |        7 |
| `WriterSupportController`     | 写入器能力查询                                 |        4 |

业务模型可以按以下层次理解：

- 任务配置（`export-task-setting` / `import-task-setting`）是配置聚合根，拥有长整型主键。
- 任务配置项（配置、模板、导出器/导入器/读取器/写入器信息）以“任务配置主键 + 标识符”为复合主键。
- 任务（`export-task` / `import-task`）由任务配置创建，拥有长整型主键，并归属某个用户。
- 任务项（任务文件、任务元数据）以“任务主键 + 标识符”为复合主键。

---

## 公共约定

### 请求与响应格式

普通 JSON 接口使用 `Content-Type: application/json`。成功和失败响应统一使用以下外层结构：

```json
{
  "data": null,
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

`meta.code` 为 `0` 表示成功；异常由统一异常映射器写入 `meta.code` 和 `meta.message`，失败时 `data` 通常为 `null`。
FastJson 配置使用 UTF-8，输出空对象字段，将空字符串写为 `""`、空列表写为 `[]`。

### 身份认证

需要身份认证的接口必须携带：

```http
Authentication: <login-state-id>
```

所有带 `@LoginRequired` 的接口同时带有 `@PermissionRequired`；接口详情中的权限值均为源码中的完整权限标识。

仅有下列 6 个方法既没有登录注解，也没有额外权限标识，属于按凭证换取文件流的公开接口：

- `GET /api/v1/fileio/export/download-by-voucher`
- `GET /api/v1/fileio/export-file/download-by-voucher`
- `GET /api/v1/fileio/export-template/download-by-voucher`
- `GET /api/v1/fileio/import/download-by-voucher`
- `GET /api/v1/fileio/import-file/download-by-voucher`
- `GET /api/v1/fileio/import-template/download-by-voucher`

`/me/*` 系列方法在服务端通过 `TokenHandler.getUserId(request)` 解析当前登录用户，不接受用户 ID 参数。

### 分页

| 参数   | 类型 | 必填 | 说明                                                 |
|--------|------|------|------------------------------------------------------|
| `page` | 整数 | 是   | 页码，原样传给后端分页对象；起始值未在当前源码中限定 |
| `rows` | 整数 | 是   | 每页条数                                             |

分页响应：

```json
{
  "data": {
    "current_page": 0,
    "total_pages": 1,
    "rows": 20,
    "count": "1",
    "data": []
  },
  "meta": {
    "code": 0,
    "message": "good"
  }
}
```

`count` 以字符串序列化，避免 JavaScript 长整型精度丢失。

### 复合主键

本业务域大量接口以复合主键定位资源，路径中两个主键分量用 `&` 连接，例如
`/api/v1/fileio/export-conf/1&default`。

| 键类型       | 路径变量                       | 说明                                           |
|--------------|--------------------------------|------------------------------------------------|
| 任务配置项键 | `{taskSettingId}&{identifier}` | 任务配置主键与标识符；用于配置、模板、各类信息 |
| 任务项键     | `{taskId}&{identifier}`        | 任务主键与标识符；用于任务文件和任务元数据     |

`identifier` 的最大长度为 100，控制器不使用 `@Validated` 校验路径变量，长度与格式约束由下游服务判定。

### 文件上传与下载

上传接口使用 `multipart/form-data`，字段 `file` 必填，其余信息由路径变量携带：

- 名称不含 `-stream` 的上传接口把整个文件读入 `byte[]` 后交给服务；
- 名称含 `-stream` 的上传接口把 `InputStream` 与 `Content-Length` 直接交给服务，不在控制器内缓存字节；
- 请求不是标准 multipart 请求、或缺少 `file` 字段时，控制器抛出异常并由统一异常映射器转成错误响应。

下载接口分为四种形态：

| 形态                            | 成功响应                                                                                                                         |
|---------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| `GET .../download-file`         | `ResponseEntity<Object>`，响应体为文件字节，附 `Content-Disposition: attachment;filename=<名称>`，未设置 `Content-Length`        |
| `POST .../download-file-stream` | 直接写 `HttpServletResponse`：`Content-Length`、`Content-Disposition: attachment; filename="<名称>"`、`application/octet-stream` |
| `POST .../request-*-voucher`    | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) 格式的凭证                                                     |
| `GET .../download-by-voucher`   | 直接写 `HttpServletResponse`，响应头与流式下载一致                                                                               |

`GET .../download-file` 在业务异常时仍返回 HTTP 200，响应体改为统一错误对象；流式下载在异常时抛出映射后的异常，
不会返回统一响应外层。
控制器使用 `new String(name.getBytes(), StandardCharsets.ISO_8859_1)` 把文件名转换为 HTTP 头可用编码。

凭证的有效期和复用规则不在当前 Controller 契约中。

### 日期和长整型

`Date` 序列化为 Unix 毫秒时间戳。名称以 `JSFixed` 开头的长整型响应字段使用字符串序列化；
普通 `FastJsonLongIdKey` 仍是 JSON 整数，前端读取时需注意 JavaScript 安全整数范围。

---

## 接口一览

### 导出配置

| 方法   | 路径                                                             | 身份认证 | 请求                 | 成功数据                                                                                                           |
|--------|------------------------------------------------------------------|----------|----------------------|--------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/export-conf/{taskSettingId}&{identifier}/exists` | 需要     | 路径参数             | `data` 为布尔值。                                                                                                  |
| GET    | `/api/v1/fileio/export-conf/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 [`JSFixedFastJsonExportConf`](#response-jsfixedfastjsonexportconf)。                                     |
| POST   | `/api/v1/fileio/export-conf`                                     | 需要     | `WebInputExportConf` | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                              |
| PATCH  | `/api/v1/fileio/export-conf`                                     | 需要     | `WebInputExportConf` | `data` 为 `null`。                                                                                                 |
| DELETE | `/api/v1/fileio/export-conf/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 `null`。                                                                                                 |
| GET    | `/api/v1/fileio/export-conf/all`                                 | 需要     | 查询参数             | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportConf`](#response-jsfixedfastjsonexportconf)。 |
| GET    | `/api/v1/fileio/task-setting/{taskSettingId}/export-conf`        | 需要     | 路径/查询参数        | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportConf`](#response-jsfixedfastjsonexportconf)。 |

### 导出

| 方法 | 路径                                                                      | 身份认证 | 请求                         | 成功数据                                                            |
|------|---------------------------------------------------------------------------|----------|------------------------------|---------------------------------------------------------------------|
| POST | `/api/v1/fileio/export/create-task`                                       | 需要     | `WebInputTaskCreateInfo`     | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。 |
| POST | `/api/v1/fileio/export/upsert-task-metadata`                              | 需要     | `WebInputMetadataUpsertInfo` | `data` 为 `null`。                                                  |
| POST | `/api/v1/fileio/export/execute-task`                                      | 需要     | `WebInputTaskExecuteInfo`    | `data` 为 `null`。                                                  |
| POST | `/api/v1/fileio/export/execute-task-async`                                | 需要     | `WebInputTaskExecuteInfo`    | `data` 为 `null`。                                                  |
| GET  | `/api/v1/fileio/export/{taskId}&{identifier}/download-file`               | 需要     | 路径参数                     | 成功时为文件字节；失败时响应体为统一错误对象。                      |
| POST | `/api/v1/fileio/export/download-file-stream`                              | 需要     | `WebInputFileDownloadInfo`   | 原始文件流（`application/octet-stream`），不使用统一响应外层。      |
| POST | `/api/v1/fileio/export/{taskId}&{identifier}/request-file-stream-voucher` | 需要     | 路径参数                     | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。 |
| GET  | `/api/v1/fileio/export/download-by-voucher`                               | 不需要   | 查询参数                     | 原始文件流（`application/octet-stream`），不使用统一响应外层。      |

### 导出器信息

| 方法   | 路径                                                               | 身份认证 | 请求                   | 成功数据                                                                                                               |
|--------|--------------------------------------------------------------------|----------|------------------------|------------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/exporter-info/{taskSettingId}&{identifier}/exists` | 需要     | 路径参数               | `data` 为布尔值。                                                                                                      |
| GET    | `/api/v1/fileio/exporter-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数               | `data` 为 [`JSFixedFastJsonExporterInfo`](#response-jsfixedfastjsonexporterinfo)。                                     |
| POST   | `/api/v1/fileio/exporter-info`                                     | 需要     | `WebInputExporterInfo` | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                                  |
| PATCH  | `/api/v1/fileio/exporter-info`                                     | 需要     | `WebInputExporterInfo` | `data` 为 `null`。                                                                                                     |
| DELETE | `/api/v1/fileio/exporter-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数               | `data` 为 `null`。                                                                                                     |
| GET    | `/api/v1/fileio/exporter-info/all`                                 | 需要     | 查询参数               | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExporterInfo`](#response-jsfixedfastjsonexporterinfo)。 |
| GET    | `/api/v1/fileio/task-setting/{taskSettingId}/exporter-info`        | 需要     | 路径/查询参数          | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExporterInfo`](#response-jsfixedfastjsonexporterinfo)。 |

### 导出器支持

| 方法 | 路径                                          | 身份认证 | 请求     | 成功数据                                                                                                       |
|------|-----------------------------------------------|----------|----------|----------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/exporter-support/{id}/exists` | 需要     | 路径参数 | `data` 为布尔值。                                                                                              |
| GET  | `/api/v1/fileio/exporter-support/{id}`        | 需要     | 路径参数 | `data` 为 [`FastJsonExporterSupport`](#response-fastjsonexportersupport)。                                     |
| GET  | `/api/v1/fileio/exporter-support/all`         | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonExporterSupport`](#response-fastjsonexportersupport)。 |
| GET  | `/api/v1/fileio/exporter-support/id-like`     | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonExporterSupport`](#response-fastjsonexportersupport)。 |

### 导出文件

| 方法   | 路径                                                                           | 身份认证 | 请求                       | 成功数据                                                                                                                   |
|--------|--------------------------------------------------------------------------------|----------|----------------------------|----------------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/export-file/{taskId}&{identifier}/exists`                      | 需要     | 路径参数                   | `data` 为布尔值。                                                                                                          |
| GET    | `/api/v1/fileio/export-file/{taskId}&{identifier}`                             | 需要     | 路径参数                   | `data` 为 [`JSFixedFastJsonExportFileInfo`](#response-jsfixedfastjsonexportfileinfo)。                                     |
| DELETE | `/api/v1/fileio/export-file/{taskId}&{identifier}`                             | 需要     | 路径参数                   | `data` 为 `null`。                                                                                                         |
| GET    | `/api/v1/fileio/export-file/all`                                               | 需要     | 查询参数                   | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportFileInfo`](#response-jsfixedfastjsonexportfileinfo)。 |
| GET    | `/api/v1/fileio/task/{taskId}/export-file`                                     | 需要     | 路径/查询参数              | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportFileInfo`](#response-jsfixedfastjsonexportfileinfo)。 |
| GET    | `/api/v1/fileio/export-file/{taskId}&{identifier}/download-file`               | 需要     | 路径参数                   | 成功时为文件字节；失败时响应体为统一错误对象。                                                                             |
| POST   | `/api/v1/fileio/export-file/download-file-stream`                              | 需要     | `WebInputFileDownloadInfo` | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                             |
| POST   | `/api/v1/fileio/export-file/{taskId}&{identifier}/request-file-stream-voucher` | 需要     | 路径参数                   | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。                                                        |
| GET    | `/api/v1/fileio/export-file/download-by-voucher`                               | 不需要   | 查询参数                   | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                             |

### 导出元数据

| 方法 | 路径                                                          | 身份认证 | 请求          | 成功数据                                                                                                                   |
|------|---------------------------------------------------------------|----------|---------------|----------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/export-metadata/{taskId}&{identifier}/exists` | 需要     | 路径参数      | `data` 为布尔值。                                                                                                          |
| GET  | `/api/v1/fileio/export-metadata/{taskId}&{identifier}`        | 需要     | 路径参数      | `data` 为 [`JSFixedFastJsonExportMetadata`](#response-jsfixedfastjsonexportmetadata)。                                     |
| GET  | `/api/v1/fileio/export-metadata/all`                          | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportMetadata`](#response-jsfixedfastjsonexportmetadata)。 |
| GET  | `/api/v1/fileio/task/{taskId}/export-metadata`                | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportMetadata`](#response-jsfixedfastjsonexportmetadata)。 |

### 导出任务

| 方法 | 路径                                                           | 身份认证 | 请求          | 成功数据                                                                                                                   |
|------|----------------------------------------------------------------|----------|---------------|----------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/export-task/{id}/exists`                       | 需要     | 路径参数      | `data` 为布尔值。                                                                                                          |
| GET  | `/api/v1/fileio/export-task/{id}`                              | 需要     | 路径参数      | `data` 为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。                                             |
| GET  | `/api/v1/fileio/export-task/all`                               | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。         |
| GET  | `/api/v1/fileio/task-setting/{taskSettingId}/export-task`      | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。         |
| GET  | `/api/v1/fileio/export-task/create-date-desc`                  | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。         |
| GET  | `/api/v1/fileio/user/{userId}/export-task`                     | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。         |
| GET  | `/api/v1/fileio/me/export-task`                                | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。         |
| GET  | `/api/v1/fileio/export-task/{id}/disp`                         | 需要     | 路径参数      | `data` 为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。                                     |
| GET  | `/api/v1/fileio/export-task/all/disp`                          | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。 |
| GET  | `/api/v1/fileio/task-setting/{taskSettingId}/export-task/disp` | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。 |
| GET  | `/api/v1/fileio/export-task/create-date-desc/disp`             | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。 |
| GET  | `/api/v1/fileio/user/{userId}/export-task/disp`                | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。 |
| GET  | `/api/v1/fileio/me/export-task/disp`                           | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。 |

### 导出任务配置

| 方法   | 路径                                             | 身份认证 | 请求                        | 成功数据                                                                                                                         |
|--------|--------------------------------------------------|----------|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/export-task-setting/{id}/exists` | 需要     | 路径参数                    | `data` 为布尔值。                                                                                                                |
| GET    | `/api/v1/fileio/export-task-setting/{id}`        | 需要     | 路径参数                    | `data` 为 [`JSFixedFastJsonExportTaskSetting`](#response-jsfixedfastjsonexporttasksetting)。                                     |
| POST   | `/api/v1/fileio/export-task-setting`             | 需要     | `WebInputExportTaskSetting` | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。                                                              |
| PATCH  | `/api/v1/fileio/export-task-setting`             | 需要     | `WebInputExportTaskSetting` | `data` 为 `null`。                                                                                                               |
| DELETE | `/api/v1/fileio/export-task-setting/{id}`        | 需要     | 路径参数                    | `data` 为 `null`。                                                                                                               |
| GET    | `/api/v1/fileio/export-task-setting/all`         | 需要     | 查询参数                    | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTaskSetting`](#response-jsfixedfastjsonexporttasksetting)。 |

### 导出模板

| 方法 | 路径                                                                                 | 身份认证 | 请求                           | 成功数据                                                                                                                           |
|------|--------------------------------------------------------------------------------------|----------|--------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/export-template/{taskSettingId}&{identifier}/exists`                 | 需要     | 路径参数                       | `data` 为布尔值。                                                                                                                  |
| GET  | `/api/v1/fileio/export-template/{taskSettingId}&{identifier}`                        | 需要     | 路径参数                       | `data` 为 [`JSFixedFastJsonExportTemplateInfo`](#response-jsfixedfastjsonexporttemplateinfo)。                                     |
| GET  | `/api/v1/fileio/export-template/all`                                                 | 需要     | 查询参数                       | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTemplateInfo`](#response-jsfixedfastjsonexporttemplateinfo)。 |
| GET  | `/api/v1/fileio/task-setting/{taskSettingId}/export-template`                        | 需要     | 路径/查询参数                  | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTemplateInfo`](#response-jsfixedfastjsonexporttemplateinfo)。 |
| POST | `/api/v1/fileio/export-template/create`                                              | 需要     | `WebInputTemplateCreateInfo`   | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                                              |
| POST | `/api/v1/fileio/export-template/remove`                                              | 需要     | `WebInputTemplateRemoveInfo`   | `data` 为 `null`。                                                                                                                 |
| GET  | `/api/v1/fileio/export-template/{taskSettingId}&{identifier}/download-file`          | 需要     | 路径参数                       | 成功时为文件字节；失败时响应体为统一错误对象。                                                                                     |
| POST | `/api/v1/fileio/export-template/download-file-stream`                                | 需要     | `WebInputTemplateDownloadInfo` | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                                     |
| POST | `/api/v1/fileio/export-template/{taskSettingId}&{identifier}/request-stream-voucher` | 需要     | 路径参数                       | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。                                                                |
| GET  | `/api/v1/fileio/export-template/download-by-voucher`                                 | 不需要   | 查询参数                       | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                                     |
| POST | `/api/v1/fileio/export-template/{taskSettingId}&{identifier}/upload`                 | 需要     | `multipart/form-data`          | `data` 为 `null`。                                                                                                                 |
| POST | `/api/v1/fileio/export-template/{taskSettingId}&{identifier}/upload-stream`          | 需要     | `multipart/form-data`          | `data` 为 `null`。                                                                                                                 |

### 导入配置

| 方法   | 路径                                                             | 身份认证 | 请求                 | 成功数据                                                                                                           |
|--------|------------------------------------------------------------------|----------|----------------------|--------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/import-conf/{taskSettingId}&{identifier}/exists` | 需要     | 路径参数             | `data` 为布尔值。                                                                                                  |
| GET    | `/api/v1/fileio/import-conf/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 [`JSFixedFastJsonImportConf`](#response-jsfixedfastjsonimportconf)。                                     |
| POST   | `/api/v1/fileio/import-conf`                                     | 需要     | `WebInputImportConf` | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                              |
| PATCH  | `/api/v1/fileio/import-conf`                                     | 需要     | `WebInputImportConf` | `data` 为 `null`。                                                                                                 |
| DELETE | `/api/v1/fileio/import-conf/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 `null`。                                                                                                 |
| GET    | `/api/v1/fileio/import-conf/all`                                 | 需要     | 查询参数             | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportConf`](#response-jsfixedfastjsonimportconf)。 |
| GET    | `/api/v1/fileio/task-setting/{taskSettingId}/import-conf`        | 需要     | 路径/查询参数        | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportConf`](#response-jsfixedfastjsonimportconf)。 |

### 导入

| 方法 | 路径                                                                      | 身份认证 | 请求                         | 成功数据                                                            |
|------|---------------------------------------------------------------------------|----------|------------------------------|---------------------------------------------------------------------|
| POST | `/api/v1/fileio/import/create-task`                                       | 需要     | `WebInputTaskCreateInfo`     | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。 |
| POST | `/api/v1/fileio/import/upsert-task-metadata`                              | 需要     | `WebInputMetadataUpsertInfo` | `data` 为 `null`。                                                  |
| POST | `/api/v1/fileio/import/{taskId}&{identifier}/upload`                      | 需要     | `multipart/form-data`        | `data` 为 `null`。                                                  |
| POST | `/api/v1/fileio/import/{taskId}&{identifier}/upload-stream`               | 需要     | `multipart/form-data`        | `data` 为 `null`。                                                  |
| POST | `/api/v1/fileio/import/execute-task`                                      | 需要     | `WebInputTaskExecuteInfo`    | `data` 为 `null`。                                                  |
| POST | `/api/v1/fileio/import/execute-task-async`                                | 需要     | `WebInputTaskExecuteInfo`    | `data` 为 `null`。                                                  |
| GET  | `/api/v1/fileio/import/{taskId}&{identifier}/download-file`               | 需要     | 路径参数                     | 成功时为文件字节；失败时响应体为统一错误对象。                      |
| POST | `/api/v1/fileio/import/download-file-stream`                              | 需要     | `WebInputFileDownloadInfo`   | 原始文件流（`application/octet-stream`），不使用统一响应外层。      |
| POST | `/api/v1/fileio/import/{taskId}&{identifier}/request-file-stream-voucher` | 需要     | 路径参数                     | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。 |
| GET  | `/api/v1/fileio/import/download-by-voucher`                               | 不需要   | 查询参数                     | 原始文件流（`application/octet-stream`），不使用统一响应外层。      |

### 导入器信息

| 方法   | 路径                                                               | 身份认证 | 请求                   | 成功数据                                                                                                               |
|--------|--------------------------------------------------------------------|----------|------------------------|------------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/importer-info/{taskSettingId}&{identifier}/exists` | 需要     | 路径参数               | `data` 为布尔值。                                                                                                      |
| GET    | `/api/v1/fileio/importer-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数               | `data` 为 [`JSFixedFastJsonImporterInfo`](#response-jsfixedfastjsonimporterinfo)。                                     |
| POST   | `/api/v1/fileio/importer-info`                                     | 需要     | `WebInputImporterInfo` | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                                  |
| PATCH  | `/api/v1/fileio/importer-info`                                     | 需要     | `WebInputImporterInfo` | `data` 为 `null`。                                                                                                     |
| DELETE | `/api/v1/fileio/importer-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数               | `data` 为 `null`。                                                                                                     |
| GET    | `/api/v1/fileio/importer-info/all`                                 | 需要     | 查询参数               | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImporterInfo`](#response-jsfixedfastjsonimporterinfo)。 |
| GET    | `/api/v1/fileio/task-setting/{taskSettingId}/importer-info`        | 需要     | 路径/查询参数          | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImporterInfo`](#response-jsfixedfastjsonimporterinfo)。 |

### 导入器支持

| 方法 | 路径                                          | 身份认证 | 请求     | 成功数据                                                                                                       |
|------|-----------------------------------------------|----------|----------|----------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/importer-support/{id}/exists` | 需要     | 路径参数 | `data` 为布尔值。                                                                                              |
| GET  | `/api/v1/fileio/importer-support/{id}`        | 需要     | 路径参数 | `data` 为 [`FastJsonImporterSupport`](#response-fastjsonimportersupport)。                                     |
| GET  | `/api/v1/fileio/importer-support/all`         | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImporterSupport`](#response-fastjsonimportersupport)。 |
| GET  | `/api/v1/fileio/importer-support/id-like`     | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImporterSupport`](#response-fastjsonimportersupport)。 |

### 导入文件

| 方法   | 路径                                                                           | 身份认证 | 请求                       | 成功数据                                                                                                                   |
|--------|--------------------------------------------------------------------------------|----------|----------------------------|----------------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/import-file/{taskId}&{identifier}/exists`                      | 需要     | 路径参数                   | `data` 为布尔值。                                                                                                          |
| GET    | `/api/v1/fileio/import-file/{taskId}&{identifier}`                             | 需要     | 路径参数                   | `data` 为 [`JSFixedFastJsonImportFileInfo`](#response-jsfixedfastjsonimportfileinfo)。                                     |
| DELETE | `/api/v1/fileio/import-file/{taskId}&{identifier}`                             | 需要     | 路径参数                   | `data` 为 `null`。                                                                                                         |
| GET    | `/api/v1/fileio/import-file/all`                                               | 需要     | 查询参数                   | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportFileInfo`](#response-jsfixedfastjsonimportfileinfo)。 |
| GET    | `/api/v1/fileio/task/{taskId}/import-file`                                     | 需要     | 路径/查询参数              | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportFileInfo`](#response-jsfixedfastjsonimportfileinfo)。 |
| GET    | `/api/v1/fileio/import-file/{taskId}&{identifier}/download-file`               | 需要     | 路径参数                   | 成功时为文件字节；失败时响应体为统一错误对象。                                                                             |
| POST   | `/api/v1/fileio/import-file/download-file-stream`                              | 需要     | `WebInputFileDownloadInfo` | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                             |
| POST   | `/api/v1/fileio/import-file/{taskId}&{identifier}/request-file-stream-voucher` | 需要     | 路径参数                   | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。                                                        |
| GET    | `/api/v1/fileio/import-file/download-by-voucher`                               | 不需要   | 查询参数                   | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                             |
| POST   | `/api/v1/fileio/import-file/{taskId}&{identifier}/upload`                      | 需要     | `multipart/form-data`      | `data` 为 `null`。                                                                                                         |
| POST   | `/api/v1/fileio/import-file/{taskId}&{identifier}/upload-stream`               | 需要     | `multipart/form-data`      | `data` 为 `null`。                                                                                                         |

### 导入元数据

| 方法 | 路径                                                          | 身份认证 | 请求          | 成功数据                                                                                                                   |
|------|---------------------------------------------------------------|----------|---------------|----------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/import-metadata/{taskId}&{identifier}/exists` | 需要     | 路径参数      | `data` 为布尔值。                                                                                                          |
| GET  | `/api/v1/fileio/import-metadata/{taskId}&{identifier}`        | 需要     | 路径参数      | `data` 为 [`JSFixedFastJsonImportMetadata`](#response-jsfixedfastjsonimportmetadata)。                                     |
| GET  | `/api/v1/fileio/import-metadata/all`                          | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportMetadata`](#response-jsfixedfastjsonimportmetadata)。 |
| GET  | `/api/v1/fileio/task/{taskId}/import-metadata`                | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportMetadata`](#response-jsfixedfastjsonimportmetadata)。 |

### 导入任务

| 方法 | 路径                                                           | 身份认证 | 请求          | 成功数据                                                                                                                   |
|------|----------------------------------------------------------------|----------|---------------|----------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/import-task/{id}/exists`                       | 需要     | 路径参数      | `data` 为布尔值。                                                                                                          |
| GET  | `/api/v1/fileio/import-task/{id}`                              | 需要     | 路径参数      | `data` 为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。                                             |
| GET  | `/api/v1/fileio/import-task/all`                               | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。         |
| GET  | `/api/v1/fileio/task-setting/{taskSettingId}/import-task`      | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。         |
| GET  | `/api/v1/fileio/import-task/create-date-desc`                  | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。         |
| GET  | `/api/v1/fileio/import-task/user/{userId}`                     | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。         |
| GET  | `/api/v1/fileio/me/import-task`                                | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。         |
| GET  | `/api/v1/fileio/import-task/{id}/disp`                         | 需要     | 路径参数      | `data` 为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。                                     |
| GET  | `/api/v1/fileio/import-task/all/disp`                          | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。 |
| GET  | `/api/v1/fileio/task-setting/{taskSettingId}/import-task/disp` | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。 |
| GET  | `/api/v1/fileio/import-task/create-date-desc/disp`             | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。 |
| GET  | `/api/v1/fileio/import-task/user/{userId}/disp`                | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。 |
| GET  | `/api/v1/fileio/me/import-task/disp`                           | 需要     | 查询参数      | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。 |

### 导入任务配置

| 方法   | 路径                                             | 身份认证 | 请求                        | 成功数据                                                                                                                         |
|--------|--------------------------------------------------|----------|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/import-task-setting/{id}/exists` | 需要     | 路径参数                    | `data` 为布尔值。                                                                                                                |
| GET    | `/api/v1/fileio/import-task-setting/{id}`        | 需要     | 路径参数                    | `data` 为 [`JSFixedFastJsonImportTaskSetting`](#response-jsfixedfastjsonimporttasksetting)。                                     |
| POST   | `/api/v1/fileio/import-task-setting`             | 需要     | `WebInputImportTaskSetting` | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。                                                              |
| PATCH  | `/api/v1/fileio/import-task-setting`             | 需要     | `WebInputImportTaskSetting` | `data` 为 `null`。                                                                                                               |
| DELETE | `/api/v1/fileio/import-task-setting/{id}`        | 需要     | 路径参数                    | `data` 为 `null`。                                                                                                               |
| GET    | `/api/v1/fileio/import-task-setting/all`         | 需要     | 查询参数                    | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTaskSetting`](#response-jsfixedfastjsonimporttasksetting)。 |

### 导入模板

| 方法 | 路径                                                                                 | 身份认证 | 请求                           | 成功数据                                                                                                                           |
|------|--------------------------------------------------------------------------------------|----------|--------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/import-template/{taskSettingId}&{identifier}/exists`                 | 需要     | 路径参数                       | `data` 为布尔值。                                                                                                                  |
| GET  | `/api/v1/fileio/import-template/{taskSettingId}&{identifier}`                        | 需要     | 路径参数                       | `data` 为 [`JSFixedFastJsonImportTemplateInfo`](#response-jsfixedfastjsonimporttemplateinfo)。                                     |
| GET  | `/api/v1/fileio/import-template/all`                                                 | 需要     | 查询参数                       | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTemplateInfo`](#response-jsfixedfastjsonimporttemplateinfo)。 |
| GET  | `/api/v1/fileio/task-setting/{taskSettingId}/import-template`                        | 需要     | 路径/查询参数                  | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTemplateInfo`](#response-jsfixedfastjsonimporttemplateinfo)。 |
| POST | `/api/v1/fileio/import-template/create`                                              | 需要     | `WebInputTemplateCreateInfo`   | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                                              |
| POST | `/api/v1/fileio/import-template/remove`                                              | 需要     | `WebInputTemplateRemoveInfo`   | `data` 为 `null`。                                                                                                                 |
| GET  | `/api/v1/fileio/import-template/{taskSettingId}&{identifier}/download-file`          | 需要     | 路径参数                       | 成功时为文件字节；失败时响应体为统一错误对象。                                                                                     |
| POST | `/api/v1/fileio/import-template/download-file-stream`                                | 需要     | `WebInputTemplateDownloadInfo` | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                                     |
| POST | `/api/v1/fileio/import-template/{taskSettingId}&{identifier}/request-stream-voucher` | 需要     | 路径参数                       | `data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)。                                                                |
| GET  | `/api/v1/fileio/import-template/download-by-voucher`                                 | 不需要   | 查询参数                       | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                                                     |
| POST | `/api/v1/fileio/import-template/{taskSettingId}&{identifier}/upload`                 | 需要     | `multipart/form-data`          | `data` 为 `null`。                                                                                                                 |
| POST | `/api/v1/fileio/import-template/{taskSettingId}&{identifier}/upload-stream`          | 需要     | `multipart/form-data`          | `data` 为 `null`。                                                                                                                 |

### 读取器信息

| 方法   | 路径                                                             | 身份认证 | 请求                 | 成功数据                                                                                                           |
|--------|------------------------------------------------------------------|----------|----------------------|--------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/reader-info/{taskSettingId}&{identifier}/exists` | 需要     | 路径参数             | `data` 为布尔值。                                                                                                  |
| GET    | `/api/v1/fileio/reader-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 [`JSFixedFastJsonReaderInfo`](#response-jsfixedfastjsonreaderinfo)。                                     |
| POST   | `/api/v1/fileio/reader-info`                                     | 需要     | `WebInputReaderInfo` | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                              |
| PATCH  | `/api/v1/fileio/reader-info`                                     | 需要     | `WebInputReaderInfo` | `data` 为 `null`。                                                                                                 |
| DELETE | `/api/v1/fileio/reader-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 `null`。                                                                                                 |
| GET    | `/api/v1/fileio/reader-info/all`                                 | 需要     | 查询参数             | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonReaderInfo`](#response-jsfixedfastjsonreaderinfo)。 |
| GET    | `/api/v1/fileio/task-setting/{taskSettingId}/reader-info`        | 需要     | 路径/查询参数        | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonReaderInfo`](#response-jsfixedfastjsonreaderinfo)。 |

### 读取器支持

| 方法 | 路径                                        | 身份认证 | 请求     | 成功数据                                                                                                   |
|------|---------------------------------------------|----------|----------|------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/reader-support/{id}/exists` | 需要     | 路径参数 | `data` 为布尔值。                                                                                          |
| GET  | `/api/v1/fileio/reader-support/{id}`        | 需要     | 路径参数 | `data` 为 [`FastJsonReaderSupport`](#response-fastjsonreadersupport)。                                     |
| GET  | `/api/v1/fileio/reader-support/all`         | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonReaderSupport`](#response-fastjsonreadersupport)。 |
| GET  | `/api/v1/fileio/reader-support/id-like`     | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonReaderSupport`](#response-fastjsonreadersupport)。 |

### 重置

| 方法 | 路径                          | 身份认证 | 请求 | 成功数据           |
|------|-------------------------------|----------|------|--------------------|
| POST | `/api/v1/fileio/reset-export` | 需要     | 无   | `data` 为 `null`。 |
| POST | `/api/v1/fileio/reset-import` | 需要     | 无   | `data` 为 `null`。 |

### 写入器信息

| 方法   | 路径                                                             | 身份认证 | 请求                 | 成功数据                                                                                                           |
|--------|------------------------------------------------------------------|----------|----------------------|--------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/fileio/writer-info/{taskSettingId}&{identifier}/exists` | 需要     | 路径参数             | `data` 为布尔值。                                                                                                  |
| GET    | `/api/v1/fileio/writer-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 [`JSFixedFastJsonWriterInfo`](#response-jsfixedfastjsonwriterinfo)。                                     |
| POST   | `/api/v1/fileio/writer-info`                                     | 需要     | `WebInputWriterInfo` | `data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。                              |
| PATCH  | `/api/v1/fileio/writer-info`                                     | 需要     | `WebInputWriterInfo` | `data` 为 `null`。                                                                                                 |
| DELETE | `/api/v1/fileio/writer-info/{taskSettingId}&{identifier}`        | 需要     | 路径参数             | `data` 为 `null`。                                                                                                 |
| GET    | `/api/v1/fileio/writer-info/all`                                 | 需要     | 查询参数             | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonWriterInfo`](#response-jsfixedfastjsonwriterinfo)。 |
| GET    | `/api/v1/fileio/task-setting/{taskSettingId}/writer-info`        | 需要     | 路径/查询参数        | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonWriterInfo`](#response-jsfixedfastjsonwriterinfo)。 |

### 写入器支持

| 方法 | 路径                                        | 身份认证 | 请求     | 成功数据                                                                                                   |
|------|---------------------------------------------|----------|----------|------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/fileio/writer-support/{id}/exists` | 需要     | 路径参数 | `data` 为布尔值。                                                                                          |
| GET  | `/api/v1/fileio/writer-support/{id}`        | 需要     | 路径参数 | `data` 为 [`FastJsonWriterSupport`](#response-fastjsonwritersupport)。                                     |
| GET  | `/api/v1/fileio/writer-support/all`         | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonWriterSupport`](#response-fastjsonwritersupport)。 |
| GET  | `/api/v1/fileio/writer-support/id-like`     | 需要     | 查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonWriterSupport`](#response-fastjsonwritersupport)。 |

---

## 接口详情

## 导出配置

### 判断导出配置是否存在

判断指定任务配置下是否存在指定标识符的导出配置。

```http
GET /api/v1/fileio/export-conf/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_conf.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出配置

获取指定任务配置下指定标识符的导出配置。

```http
GET /api/v1/fileio/export-conf/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_conf.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonExportConf`](#response-jsfixedfastjsonexportconf)。

### 新增导出配置

新增导出配置。

```http
POST /api/v1/fileio/export-conf
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_conf.insert`。
审计记录：`webapi.fileio.export_conf.insert`。

请求体：[`WebInputExportConf`](#request-webinputexportconf)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "content": "{}",
  "remark": "默认导出配置"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 更新导出配置

更新导出配置。

```http
PATCH /api/v1/fileio/export-conf
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_conf.update`。
审计记录：`webapi.fileio.export_conf.update`。

请求体：[`WebInputExportConf`](#request-webinputexportconf)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "content": "{\"sheet\":1}",
  "remark": "更新后的导出配置"
}
```

成功响应：`data` 为 `null`。

### 删除导出配置

删除指定任务配置下指定标识符的导出配置。

```http
DELETE /api/v1/fileio/export-conf/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_conf.delete`。
审计记录：`webapi.fileio.export_conf.delete`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导出配置

分页获取全部导出配置。

```http
GET /api/v1/fileio/export-conf/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_conf.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportConf`](#response-jsfixedfastjsonexportconf)。

### 分页获取任务配置下的导出配置

分页获取指定任务配置下的导出配置。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/export-conf
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_conf.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportConf`](#response-jsfixedfastjsonexportconf)。

## 导出

### 创建导出任务

根据任务配置创建导出任务。

```http
POST /api/v1/fileio/export/create-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export.create_task`。
审计记录：`webapi.fileio.export.create_task`。

请求体：[`WebInputTaskCreateInfo`](#request-webinputtaskcreateinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "setting_key": {
    "long_id": 1
  },
  "user_key": {
    "string_id": "test_user"
  },
  "message": "创建导出任务"
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即新建任务的主键。

### 写入任务元数据

写入（不存在时创建、存在时覆盖）导出任务的元数据。

```http
POST /api/v1/fileio/export/upsert-task-metadata
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export.upsert_task_metadata`。
审计记录：`webapi.fileio.export.upsert_task_metadata`。

请求体：[`WebInputMetadataUpsertInfo`](#request-webinputmetadataupsertinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  },
  "content": "{\"total\":3}"
}
```

成功响应：`data` 为 `null`。

### 同步执行导出任务

同步执行导出任务，方法在导出完成后返回。

```http
POST /api/v1/fileio/export/execute-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export.execute_task`。
审计记录：`webapi.fileio.export.execute_task`。

请求体：[`WebInputTaskExecuteInfo`](#request-webinputtaskexecuteinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "task_key": {
    "long_id": 1
  },
  "message": "开始导出"
}
```

成功响应：`data` 为 `null`；源码中方法声明为 `JSFixedFastJsonLongIdKey`，但实际返回 `null`。

### 异步执行导出任务

异步执行导出任务，方法立即返回，任务在后台推进。

```http
POST /api/v1/fileio/export/execute-task-async
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export.execute_task_async`。
审计记录：`webapi.fileio.export.execute_task_async`。

请求体：[`WebInputTaskExecuteInfo`](#request-webinputtaskexecuteinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "task_key": {
    "long_id": 1
  },
  "message": "异步导出"
}
```

成功响应：`data` 为 `null`；源码中方法声明为 `JSFixedFastJsonLongIdKey`，但实际返回 `null`。

### 下载导出文件（字节响应）

下载导出任务产生的文件，一次性返回全部字节。

```http
GET /api/v1/fileio/export/{taskId}&{identifier}/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export.download_file`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：HTTP 200，响应体为文件字节，响应头包含 `Content-Disposition: attachment;filename=<名称>`。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载导出文件（流式响应）

下载导出任务产生的文件，以流的方式写出响应。

```http
POST /api/v1/fileio/export/download-file-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export.download_file_stream`。

请求体：[`WebInputFileDownloadInfo`](#request-webinputfiledownloadinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  }
}
```

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 申请导出文件流下载凭证

为导出任务文件申请一次性下载凭证。

```http
POST /api/v1/fileio/export/{taskId}&{identifier}/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export.request_file_stream_voucher`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即凭证主键。

### 按凭证下载导出文件

使用凭证下载导出任务文件。

```http
GET /api/v1/fileio/export/download-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明     |
|--------------|------|------|----------|
| `voucher-id` | 整数 | 是   | 凭证主键 |

请求体：无。

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

## 导出器信息

### 判断导出器信息是否存在

判断指定任务配置下是否存在指定标识符的导出器信息。

```http
GET /api/v1/fileio/exporter-info/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_info.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出器信息

获取指定任务配置下指定标识符的导出器信息。

```http
GET /api/v1/fileio/exporter-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_info.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonExporterInfo`](#response-jsfixedfastjsonexporterinfo)。

### 新增导出器信息

新增导出器信息。

```http
POST /api/v1/fileio/exporter-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_info.insert`。
审计记录：`webapi.fileio.exporter_info.insert`。

请求体：[`WebInputExporterInfo`](#request-webinputexporterinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认导出器"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 更新导出器信息

更新导出器信息。

```http
PATCH /api/v1/fileio/exporter-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_info.update`。
审计记录：`webapi.fileio.exporter_info.update`。

请求体：[`WebInputExporterInfo`](#request-webinputexporterinfo)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": false,
  "type": "csv",
  "param": "{\"delimiter\":\",\"}",
  "remark": "停用的导出器"
}
```

成功响应：`data` 为 `null`。

### 删除导出器信息

删除指定任务配置下指定标识符的导出器信息。

```http
DELETE /api/v1/fileio/exporter-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_info.delete`。
审计记录：`webapi.fileio.exporter_info.delete`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导出器信息

分页获取全部导出器信息。

```http
GET /api/v1/fileio/exporter-info/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_info.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExporterInfo`](#response-jsfixedfastjsonexporterinfo)。

### 分页获取任务配置下的导出器信息

分页获取指定任务配置下的导出器信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/exporter-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_info.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExporterInfo`](#response-jsfixedfastjsonexporterinfo)。

## 导出器支持

### 判断导出器类型是否受支持

判断指定类型标识的导出器是否由当前系统支持。

```http
GET /api/v1/fileio/exporter-support/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_support.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 导出器类型ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出器支持信息

获取指定类型标识的导出器能力信息。

```http
GET /api/v1/fileio/exporter-support/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_support.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 导出器类型ID |

请求体：无。

成功响应：`data` 为 [`FastJsonExporterSupport`](#response-fastjsonexportersupport)。

### 分页获取全部导出器支持信息

分页获取全部导出器能力信息。

```http
GET /api/v1/fileio/exporter-support/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_support.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonExporterSupport`](#response-fastjsonexportersupport)。

### 按 ID 模糊查询导出器支持信息

按类型标识模糊匹配分页查询导出器能力信息。

```http
GET /api/v1/fileio/exporter-support/id-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.exporter_support.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 类型标识的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页条数               |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonExporterSupport`](#response-fastjsonexportersupport)。

## 导出文件

### 判断导出文件是否存在

判断指定任务下是否存在指定标识符的导出文件。

```http
GET /api/v1/fileio/export-file/{taskId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.exists`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出文件信息

获取指定任务下指定标识符的导出文件信息。

```http
GET /api/v1/fileio/export-file/{taskId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.get`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonExportFileInfo`](#response-jsfixedfastjsonexportfileinfo)。

### 删除导出文件

删除指定任务下的指定导出文件。

```http
DELETE /api/v1/fileio/export-file/{taskId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.delete`。
审计记录：`webapi.fileio.export_file.delete`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导出文件

分页获取全部导出文件信息。

```http
GET /api/v1/fileio/export-file/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportFileInfo`](#response-jsfixedfastjsonexportfileinfo)。

### 分页获取任务下的导出文件

分页获取指定任务下的导出文件信息。

```http
GET /api/v1/fileio/task/{taskId}/export-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.child_for_task`。

路径参数：

| 参数     | 类型 | 必填 | 说明     |
|----------|------|------|----------|
| `taskId` | 整数 | 是   | 任务主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportFileInfo`](#response-jsfixedfastjsonexportfileinfo)。

### 下载导出文件（字节响应）

下载导出文件，一次性返回全部字节。

```http
GET /api/v1/fileio/export-file/{taskId}&{identifier}/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.download_file`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：HTTP 200，响应体为文件字节，响应头包含 `Content-Disposition: attachment;filename=<名称>`。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载导出文件（流式响应）

下载导出文件，以流的方式写出响应。

```http
POST /api/v1/fileio/export-file/download-file-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.download_file_stream`。

请求体：[`WebInputFileDownloadInfo`](#request-webinputfiledownloadinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  }
}
```

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 申请导出文件流下载凭证

为导出文件申请一次性下载凭证。

```http
POST /api/v1/fileio/export-file/{taskId}&{identifier}/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_file.request_file_stream_voucher`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即凭证主键。

### 按凭证下载导出文件

使用凭证下载导出文件。

```http
GET /api/v1/fileio/export-file/download-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明     |
|--------------|------|------|----------|
| `voucher-id` | 整数 | 是   | 凭证主键 |

请求体：无。

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

## 导出元数据

### 判断导出元数据是否存在

判断指定任务下是否存在指定标识符的导出元数据。

```http
GET /api/v1/fileio/export-metadata/{taskId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_metadata.exists`。

路径参数：

| 参数         | 类型   | 必填 | 说明         |
|--------------|--------|------|--------------|
| `taskId`     | 整数   | 是   | 任务主键     |
| `identifier` | 字符串 | 是   | 元数据标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出元数据

获取指定任务下指定标识符的导出元数据。

```http
GET /api/v1/fileio/export-metadata/{taskId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_metadata.get`。

路径参数：

| 参数         | 类型   | 必填 | 说明         |
|--------------|--------|------|--------------|
| `taskId`     | 整数   | 是   | 任务主键     |
| `identifier` | 字符串 | 是   | 元数据标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonExportMetadata`](#response-jsfixedfastjsonexportmetadata)。

### 分页获取全部导出元数据

分页获取全部导出元数据。

```http
GET /api/v1/fileio/export-metadata/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_metadata.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportMetadata`](#response-jsfixedfastjsonexportmetadata)。

### 分页获取任务下的导出元数据

分页获取指定任务下的导出元数据。

```http
GET /api/v1/fileio/task/{taskId}/export-metadata
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_metadata.child_for_task`。

路径参数：

| 参数     | 类型 | 必填 | 说明     |
|----------|------|------|----------|
| `taskId` | 整数 | 是   | 任务主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportMetadata`](#response-jsfixedfastjsonexportmetadata)。

## 导出任务

### 判断导出任务是否存在

判断指定主键的导出任务是否存在。

```http
GET /api/v1/fileio/export-task/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.exists`。

路径参数：

| 参数 | 类型 | 必填 | 说明     |
|------|------|------|----------|
| `id` | 整数 | 是   | 任务主键 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出任务

获取指定主键的导出任务。

```http
GET /api/v1/fileio/export-task/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.get`。

路径参数：

| 参数 | 类型 | 必填 | 说明     |
|------|------|------|----------|
| `id` | 整数 | 是   | 任务主键 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。

### 分页获取全部导出任务

分页获取全部导出任务。

```http
GET /api/v1/fileio/export-task/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。

### 分页获取任务配置下的导出任务

分页获取指定任务配置下的导出任务。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/export-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。

### 按创建时间倒序分页获取导出任务

按创建时间降序分页获取导出任务。

```http
GET /api/v1/fileio/export-task/create-date-desc
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.create_date_desc`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。

### 分页获取指定用户的导出任务

分页获取指定用户拥有的导出任务。

```http
GET /api/v1/fileio/user/{userId}/export-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.child_for_user`。

路径参数：

| 参数     | 类型   | 必填 | 说明         |
|----------|--------|------|--------------|
| `userId` | 字符串 | 是   | 用户字符串ID |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。

### 分页获取当前登录用户的导出任务

分页获取当前登录用户拥有的导出任务；用户身份由登录令牌解析。

```http
GET /api/v1/fileio/me/export-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.child_for_me`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTask`](#response-jsfixedfastjsonexporttask)。

### 获取可展示的导出任务

获取指定主键的导出任务，并附带任务归属账户的展示信息。

```http
GET /api/v1/fileio/export-task/{id}/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.get_disp`。

路径参数：

| 参数 | 类型 | 必填 | 说明     |
|------|------|------|----------|
| `id` | 整数 | 是   | 任务主键 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。

### 分页获取全部可展示的导出任务

分页获取全部导出任务，并附带任务归属账户的展示信息。

```http
GET /api/v1/fileio/export-task/all/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.all_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。

### 分页获取任务配置下可展示的导出任务

分页获取指定任务配置下的导出任务，并附带账户展示信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/export-task/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.child_for_task_setting_disp`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。

### 按创建时间倒序分页获取可展示的导出任务

按创建时间降序分页获取导出任务，并附带账户展示信息。

```http
GET /api/v1/fileio/export-task/create-date-desc/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.create_date_desc_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。

### 分页获取指定用户可展示的导出任务

分页获取指定用户拥有的导出任务，并附带账户展示信息。

```http
GET /api/v1/fileio/user/{userId}/export-task/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.child_for_user_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明         |
|----------|--------|------|--------------|
| `userId` | 字符串 | 是   | 用户字符串ID |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。

### 分页获取当前登录用户可展示的导出任务

分页获取当前登录用户拥有的导出任务，并附带账户展示信息。

```http
GET /api/v1/fileio/me/export-task/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task.child_for_me_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispExportTask`](#response-jsfixedfastjsondispexporttask)。

## 导出任务配置

### 判断导出任务配置是否存在

判断指定主键的导出任务配置是否存在。

```http
GET /api/v1/fileio/export-task-setting/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task_setting.exists`。

路径参数：

| 参数 | 类型 | 必填 | 说明         |
|------|------|------|--------------|
| `id` | 整数 | 是   | 任务配置主键 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出任务配置

获取指定主键的导出任务配置。

```http
GET /api/v1/fileio/export-task-setting/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task_setting.get`。

路径参数：

| 参数 | 类型 | 必填 | 说明         |
|------|------|------|--------------|
| `id` | 整数 | 是   | 任务配置主键 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonExportTaskSetting`](#response-jsfixedfastjsonexporttasksetting)。

### 新增导出任务配置

新增导出任务配置。

```http
POST /api/v1/fileio/export-task-setting
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task_setting.insert`。
审计记录：`webapi.fileio.export_task_setting.insert`。

请求体：[`WebInputExportTaskSetting`](#request-webinputexporttasksetting)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "enabled": true,
  "name": "默认导出配置",
  "remark": "用于日常导出"
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即新建任务配置的主键。

### 更新导出任务配置

更新导出任务配置。

```http
PATCH /api/v1/fileio/export-task-setting
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task_setting.update`。
审计记录：`webapi.fileio.export_task_setting.update`。

请求体：[`WebInputExportTaskSetting`](#request-webinputexporttasksetting)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。
更新时 `key` 必填。

请求示例：

```json
{
  "key": {
    "long_id": 1
  },
  "enabled": false,
  "name": "默认导出配置",
  "remark": "暂停使用"
}
```

成功响应：`data` 为 `null`。

### 删除导出任务配置

删除指定主键的导出任务配置。

```http
DELETE /api/v1/fileio/export-task-setting/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task_setting.delete`。
审计记录：`webapi.fileio.export_task_setting.delete`。

路径参数：

| 参数 | 类型 | 必填 | 说明         |
|------|------|------|--------------|
| `id` | 整数 | 是   | 任务配置主键 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导出任务配置

分页获取全部导出任务配置。

```http
GET /api/v1/fileio/export-task-setting/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_task_setting.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTaskSetting`](#response-jsfixedfastjsonexporttasksetting)。

## 导出模板

### 判断导出模板是否存在

判断指定任务配置下是否存在指定标识符的导出模板。

```http
GET /api/v1/fileio/export-template/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导出模板信息

获取指定任务配置下指定标识符的导出模板信息。

```http
GET /api/v1/fileio/export-template/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonExportTemplateInfo`](#response-jsfixedfastjsonexporttemplateinfo)。

### 分页获取全部导出模板

分页获取全部导出模板信息。

```http
GET /api/v1/fileio/export-template/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTemplateInfo`](#response-jsfixedfastjsonexporttemplateinfo)。

### 分页获取任务配置下的导出模板

分页获取指定任务配置下的导出模板信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/export-template
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonExportTemplateInfo`](#response-jsfixedfastjsonexporttemplateinfo)。

### 创建导出模板

创建导出模板条目（此时不含文件内容）。

```http
POST /api/v1/fileio/export-template/create
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.create`。
审计记录：`webapi.fileio.export_template.create`。

请求体：[`WebInputTemplateCreateInfo`](#request-webinputtemplatecreateinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "task_setting_key": {
    "long_id": 1
  },
  "identifier": "default",
  "origin_name": "template.xlsx"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 移除导出模板

移除指定任务配置下指定标识符的导出模板。

```http
POST /api/v1/fileio/export-template/remove
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.remove`。
审计记录：`webapi.fileio.export_template.remove`。

请求体：[`WebInputTemplateRemoveInfo`](#request-webinputtemplateremoveinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  }
}
```

成功响应：`data` 为 `null`。

### 下载导出模板（字节响应）

下载导出模板文件，一次性返回全部字节。

```http
GET /api/v1/fileio/export-template/{taskSettingId}&{identifier}/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.download_file`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：HTTP 200，响应体为文件字节，响应头包含 `Content-Disposition: attachment;filename=<名称>`。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载导出模板（流式响应）

下载导出模板文件，以流的方式写出响应。

```http
POST /api/v1/fileio/export-template/download-file-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.download_file_stream`。

请求体：[`WebInputTemplateDownloadInfo`](#request-webinputtemplatedownloadinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  }
}
```

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 申请导出模板流下载凭证

为导出模板申请一次性下载凭证。

```http
POST /api/v1/fileio/export-template/{taskSettingId}&{identifier}/request-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.request_stream_voucher`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即凭证主键。

### 按凭证下载导出模板

使用凭证下载导出模板文件。

```http
GET /api/v1/fileio/export-template/download-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明     |
|--------------|------|------|----------|
| `voucher-id` | 整数 | 是   | 凭证主键 |

请求体：无。

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 上传导出模板

以 `multipart/form-data` 上传导出模板文件，控制器在内存中读取全部字节。

```http
POST /api/v1/fileio/export-template/{taskSettingId}&{identifier}/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.upload`。
审计记录：`webapi.fileio.export_template.upload`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

表单字段：

| 字段   | 类型 | 必填 | 说明                         |
|--------|------|------|------------------------------|
| `file` | 文件 | 是   | 模板文件，原始文件名一并提交 |

成功响应：`data` 为 `null`。

### 流式上传导出模板

以 `multipart/form-data` 流式上传导出模板文件，控制器直接把输入流交给服务。

```http
POST /api/v1/fileio/export-template/{taskSettingId}&{identifier}/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.export_template.upload_stream`。
审计记录：`webapi.fileio.export_template.upload_stream`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

表单字段：

| 字段   | 类型 | 必填 | 说明                                           |
|--------|------|------|------------------------------------------------|
| `file` | 文件 | 是   | 模板文件，原始文件名与字节数一并提交给下游服务 |

成功响应：`data` 为 `null`。

## 导入配置

### 判断导入配置是否存在

判断指定任务配置下是否存在指定标识符的导入配置。

```http
GET /api/v1/fileio/import-conf/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_conf.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入配置

获取指定任务配置下指定标识符的导入配置。

```http
GET /api/v1/fileio/import-conf/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_conf.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonImportConf`](#response-jsfixedfastjsonimportconf)。

### 新增导入配置

新增导入配置。

```http
POST /api/v1/fileio/import-conf
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_conf.insert`。
审计记录：`webapi.fileio.import_conf.insert`。

请求体：[`WebInputImportConf`](#request-webinputimportconf)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "content": "{}",
  "remark": "默认导入配置"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 更新导入配置

更新导入配置。

```http
PATCH /api/v1/fileio/import-conf
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_conf.update`。
审计记录：`webapi.fileio.import_conf.update`。

请求体：[`WebInputImportConf`](#request-webinputimportconf)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "content": "{\"sheet\":1}",
  "remark": "更新后的导入配置"
}
```

成功响应：`data` 为 `null`。

### 删除导入配置

删除指定任务配置下指定标识符的导入配置。

```http
DELETE /api/v1/fileio/import-conf/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_conf.delete`。
审计记录：`webapi.fileio.import_conf.delete`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导入配置

分页获取全部导入配置。

```http
GET /api/v1/fileio/import-conf/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_conf.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportConf`](#response-jsfixedfastjsonimportconf)。

### 分页获取任务配置下的导入配置

分页获取指定任务配置下的导入配置。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/import-conf
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_conf.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportConf`](#response-jsfixedfastjsonimportconf)。

## 导入

### 创建导入任务

根据任务配置创建导入任务。

```http
POST /api/v1/fileio/import/create-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.create_task`。
审计记录：`webapi.fileio.import.create_task`。

请求体：[`WebInputTaskCreateInfo`](#request-webinputtaskcreateinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "setting_key": {
    "long_id": 1
  },
  "user_key": {
    "string_id": "test_user"
  },
  "message": "创建导入任务"
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即新建任务的主键。

### 写入任务元数据

写入（不存在时创建、存在时覆盖）导入任务的元数据。

```http
POST /api/v1/fileio/import/upsert-task-metadata
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.upsert_task_metadata`。
审计记录：`webapi.fileio.import.upsert_task_metadata`。

请求体：[`WebInputMetadataUpsertInfo`](#request-webinputmetadataupsertinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  },
  "content": "{\"total\":3}"
}
```

成功响应：`data` 为 `null`。

### 上传导入文件

以 `multipart/form-data` 上传待导入文件，控制器在内存中读取全部字节。

```http
POST /api/v1/fileio/import/{taskId}&{identifier}/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.upload`。
审计记录：`webapi.fileio.import.upload`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

表单字段：

| 字段   | 类型 | 必填 | 说明                           |
|--------|------|------|--------------------------------|
| `file` | 文件 | 是   | 待导入文件，原始文件名一并提交 |

方法上声明了 `@BindingCheck`，但请求参数由 `HttpServletRequest` 手工解析，不涉及数据绑定校验。

成功响应：`data` 为 `null`。

### 流式上传导入文件

以 `multipart/form-data` 流式上传待导入文件，控制器直接把输入流交给服务。

```http
POST /api/v1/fileio/import/{taskId}&{identifier}/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.upload_stream`。
审计记录：`webapi.fileio.import.upload_stream`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

表单字段：

| 字段   | 类型 | 必填 | 说明                                             |
|--------|------|------|--------------------------------------------------|
| `file` | 文件 | 是   | 待导入文件，原始文件名与字节数一并提交给下游服务 |

成功响应：`data` 为 `null`。

### 同步执行导入任务

同步执行导入任务，方法在导入完成后返回。

```http
POST /api/v1/fileio/import/execute-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.execute_task`。
审计记录：`webapi.fileio.import.execute_task`。

请求体：[`WebInputTaskExecuteInfo`](#request-webinputtaskexecuteinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "task_key": {
    "long_id": 1
  },
  "message": "开始导入"
}
```

成功响应：`data` 为 `null`；源码中方法声明为 `JSFixedFastJsonLongIdKey`，但实际返回 `null`。

### 异步执行导入任务

异步执行导入任务，方法立即返回，任务在后台推进。

```http
POST /api/v1/fileio/import/execute-task-async
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.execute_task_async`。
审计记录：`webapi.fileio.import.execute_task_async`。

请求体：[`WebInputTaskExecuteInfo`](#request-webinputtaskexecuteinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "task_key": {
    "long_id": 1
  },
  "message": "异步导入"
}
```

成功响应：`data` 为 `null`；源码中方法声明为 `JSFixedFastJsonLongIdKey`，但实际返回 `null`。

### 下载导入文件（字节响应）

下载导入任务关联的文件，一次性返回全部字节。

```http
GET /api/v1/fileio/import/{taskId}&{identifier}/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.download_file`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：HTTP 200，响应体为文件字节，响应头包含 `Content-Disposition: attachment;filename=<名称>`。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载导入文件（流式响应）

下载导入任务关联的文件，以流的方式写出响应。

```http
POST /api/v1/fileio/import/download-file-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.download_file_stream`。

请求体：[`WebInputFileDownloadInfo`](#request-webinputfiledownloadinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  }
}
```

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 申请导入文件流下载凭证

为导入任务文件申请一次性下载凭证。

```http
POST /api/v1/fileio/import/{taskId}&{identifier}/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import.request_file_stream_voucher`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即凭证主键。

### 按凭证下载导入文件

使用凭证下载导入任务文件。

```http
GET /api/v1/fileio/import/download-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明     |
|--------------|------|------|----------|
| `voucher-id` | 整数 | 是   | 凭证主键 |

请求体：无。

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

## 导入器信息

### 判断导入器信息是否存在

判断指定任务配置下是否存在指定标识符的导入器信息。

```http
GET /api/v1/fileio/importer-info/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_info.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入器信息

获取指定任务配置下指定标识符的导入器信息。

```http
GET /api/v1/fileio/importer-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_info.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonImporterInfo`](#response-jsfixedfastjsonimporterinfo)。

### 新增导入器信息

新增导入器信息。

```http
POST /api/v1/fileio/importer-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_info.insert`。
审计记录：`webapi.fileio.importer_info.insert`。

请求体：[`WebInputImporterInfo`](#request-webinputimporterinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认导入器"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 更新导入器信息

更新导入器信息。

```http
PATCH /api/v1/fileio/importer-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_info.update`。
审计记录：`webapi.fileio.importer_info.update`。

请求体：[`WebInputImporterInfo`](#request-webinputimporterinfo)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": false,
  "type": "csv",
  "param": "{\"delimiter\":\",\"}",
  "remark": "停用的导入器"
}
```

成功响应：`data` 为 `null`。

### 删除导入器信息

删除指定任务配置下指定标识符的导入器信息。

```http
DELETE /api/v1/fileio/importer-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_info.delete`。
审计记录：`webapi.fileio.importer_info.delete`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导入器信息

分页获取全部导入器信息。

```http
GET /api/v1/fileio/importer-info/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_info.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImporterInfo`](#response-jsfixedfastjsonimporterinfo)。

### 分页获取任务配置下的导入器信息

分页获取指定任务配置下的导入器信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/importer-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_info.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImporterInfo`](#response-jsfixedfastjsonimporterinfo)。

## 导入器支持

### 判断导入器类型是否受支持

判断指定类型标识的导入器是否由当前系统支持。

```http
GET /api/v1/fileio/importer-support/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_support.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 导入器类型ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入器支持信息

获取指定类型标识的导入器能力信息。

```http
GET /api/v1/fileio/importer-support/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_support.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 导入器类型ID |

请求体：无。

成功响应：`data` 为 [`FastJsonImporterSupport`](#response-fastjsonimportersupport)。

### 分页获取全部导入器支持信息

分页获取全部导入器能力信息。

```http
GET /api/v1/fileio/importer-support/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_support.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImporterSupport`](#response-fastjsonimportersupport)。

### 按 ID 模糊查询导入器支持信息

按类型标识模糊匹配分页查询导入器能力信息。

```http
GET /api/v1/fileio/importer-support/id-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.importer_support.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 类型标识的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页条数               |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImporterSupport`](#response-fastjsonimportersupport)。

## 导入文件

### 判断导入文件是否存在

判断指定任务下是否存在指定标识符的导入文件。

```http
GET /api/v1/fileio/import-file/{taskId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.exists`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入文件信息

获取指定任务下指定标识符的导入文件信息。

```http
GET /api/v1/fileio/import-file/{taskId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.get`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonImportFileInfo`](#response-jsfixedfastjsonimportfileinfo)。

### 删除导入文件

删除指定任务下的指定导入文件。

```http
DELETE /api/v1/fileio/import-file/{taskId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.delete`。
审计记录：`webapi.fileio.import_file.delete`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导入文件

分页获取全部导入文件信息。

```http
GET /api/v1/fileio/import-file/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportFileInfo`](#response-jsfixedfastjsonimportfileinfo)。

### 分页获取任务下的导入文件

分页获取指定任务下的导入文件信息。

```http
GET /api/v1/fileio/task/{taskId}/import-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.child_for_task`。

路径参数：

| 参数     | 类型 | 必填 | 说明     |
|----------|------|------|----------|
| `taskId` | 整数 | 是   | 任务主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportFileInfo`](#response-jsfixedfastjsonimportfileinfo)。

### 下载导入文件（字节响应，按文件键）

下载导入文件，一次性返回全部字节。

```http
GET /api/v1/fileio/import-file/{taskId}&{identifier}/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.download_file`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：HTTP 200，响应体为文件字节，响应头包含 `Content-Disposition: attachment;filename=<名称>`。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载导入文件（流式响应）

下载导入文件，以流的方式写出响应。

```http
POST /api/v1/fileio/import-file/download-file-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.download_file_stream`。

请求体：[`WebInputFileDownloadInfo`](#request-webinputfiledownloadinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  }
}
```

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 申请导入文件流下载凭证

为导入文件申请一次性下载凭证。

```http
POST /api/v1/fileio/import-file/{taskId}&{identifier}/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.request_file_stream_voucher`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即凭证主键。

### 按凭证下载导入文件

使用凭证下载导入文件。

```http
GET /api/v1/fileio/import-file/download-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明     |
|--------------|------|------|----------|
| `voucher-id` | 整数 | 是   | 凭证主键 |

请求体：无。

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 上传导入文件（按文件键）

以 `multipart/form-data` 上传导入文件，控制器在内存中读取全部字节。

```http
POST /api/v1/fileio/import-file/{taskId}&{identifier}/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.upload`。
审计记录：`webapi.fileio.import_file.upload`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

表单字段：

| 字段   | 类型 | 必填 | 说明                 |
|--------|------|------|----------------------|
| `file` | 文件 | 是   | 文件内容与原始文件名 |

成功响应：`data` 为 `null`。

### 流式上传导入文件（按文件键）

以 `multipart/form-data` 流式上传导入文件，控制器直接把输入流交给服务。

```http
POST /api/v1/fileio/import-file/{taskId}&{identifier}/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_file.upload_stream`。
审计记录：`webapi.fileio.import_file.upload_stream`。

路径参数：

| 参数         | 类型   | 必填 | 说明       |
|--------------|--------|------|------------|
| `taskId`     | 整数   | 是   | 任务主键   |
| `identifier` | 字符串 | 是   | 文件标识符 |

表单字段：

| 字段   | 类型 | 必填 | 说明                                     |
|--------|------|------|------------------------------------------|
| `file` | 文件 | 是   | 文件内容、原始文件名与字节数一并提交下游 |

成功响应：`data` 为 `null`。

## 导入元数据

### 判断导入元数据是否存在

判断指定任务下是否存在指定标识符的导入元数据。

```http
GET /api/v1/fileio/import-metadata/{taskId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_metadata.exists`。

路径参数：

| 参数         | 类型   | 必填 | 说明         |
|--------------|--------|------|--------------|
| `taskId`     | 整数   | 是   | 任务主键     |
| `identifier` | 字符串 | 是   | 元数据标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入元数据

获取指定任务下指定标识符的导入元数据。

```http
GET /api/v1/fileio/import-metadata/{taskId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_metadata.get`。

路径参数：

| 参数         | 类型   | 必填 | 说明         |
|--------------|--------|------|--------------|
| `taskId`     | 整数   | 是   | 任务主键     |
| `identifier` | 字符串 | 是   | 元数据标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonImportMetadata`](#response-jsfixedfastjsonimportmetadata)。

### 分页获取全部导入元数据

分页获取全部导入元数据。

```http
GET /api/v1/fileio/import-metadata/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_metadata.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportMetadata`](#response-jsfixedfastjsonimportmetadata)。

### 分页获取任务下的导入元数据

分页获取指定任务下的导入元数据。

```http
GET /api/v1/fileio/task/{taskId}/import-metadata
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_metadata.child_for_task`。

路径参数：

| 参数     | 类型 | 必填 | 说明     |
|----------|------|------|----------|
| `taskId` | 整数 | 是   | 任务主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportMetadata`](#response-jsfixedfastjsonimportmetadata)。

## 导入任务

### 判断导入任务是否存在

判断指定主键的导入任务是否存在。

```http
GET /api/v1/fileio/import-task/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.exists`。

路径参数：

| 参数 | 类型 | 必填 | 说明     |
|------|------|------|----------|
| `id` | 整数 | 是   | 任务主键 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入任务

获取指定主键的导入任务。

```http
GET /api/v1/fileio/import-task/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.get`。

路径参数：

| 参数 | 类型 | 必填 | 说明     |
|------|------|------|----------|
| `id` | 整数 | 是   | 任务主键 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。

### 分页获取全部导入任务

分页获取全部导入任务。

```http
GET /api/v1/fileio/import-task/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。

### 分页获取任务配置下的导入任务

分页获取指定任务配置下的导入任务。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/import-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。

### 按创建时间倒序分页获取导入任务

按创建时间降序分页获取导入任务。

```http
GET /api/v1/fileio/import-task/create-date-desc
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.create_date_desc`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。

### 分页获取指定用户的导入任务

分页获取指定用户拥有的导入任务。

```http
GET /api/v1/fileio/import-task/user/{userId}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.child_for_user`。

路径参数：

| 参数     | 类型   | 必填 | 说明         |
|----------|--------|------|--------------|
| `userId` | 字符串 | 是   | 用户字符串ID |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。

### 分页获取当前登录用户的导入任务

分页获取当前登录用户拥有的导入任务；用户身份由登录令牌解析。

```http
GET /api/v1/fileio/me/import-task
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.child_for_me`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTask`](#response-jsfixedfastjsonimporttask)。

### 获取可展示的导入任务

获取指定主键的导入任务，并附带任务归属账户的展示信息。

```http
GET /api/v1/fileio/import-task/{id}/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.get_disp`。

路径参数：

| 参数 | 类型 | 必填 | 说明     |
|------|------|------|----------|
| `id` | 整数 | 是   | 任务主键 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。

### 分页获取全部可展示的导入任务

分页获取全部导入任务，并附带任务归属账户的展示信息。

```http
GET /api/v1/fileio/import-task/all/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.all_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。

### 分页获取任务配置下可展示的导入任务

分页获取指定任务配置下的导入任务，并附带账户展示信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/import-task/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.child_for_task_setting_disp`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。

### 按创建时间倒序分页获取可展示的导入任务

按创建时间降序分页获取导入任务，并附带账户展示信息。

```http
GET /api/v1/fileio/import-task/create-date-desc/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.create_date_desc_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。

### 分页获取指定用户可展示的导入任务

分页获取指定用户拥有的导入任务，并附带账户展示信息。

```http
GET /api/v1/fileio/import-task/user/{userId}/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.child_for_user_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明         |
|----------|--------|------|--------------|
| `userId` | 字符串 | 是   | 用户字符串ID |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。

### 分页获取当前登录用户可展示的导入任务

分页获取当前登录用户拥有的导入任务，并附带账户展示信息。

```http
GET /api/v1/fileio/me/import-task/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task.child_for_me_disp`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispImportTask`](#response-jsfixedfastjsondispimporttask)。

## 导入任务配置

### 判断导入任务配置是否存在

判断指定主键的导入任务配置是否存在。

```http
GET /api/v1/fileio/import-task-setting/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task_setting.exists`。

路径参数：

| 参数 | 类型 | 必填 | 说明         |
|------|------|------|--------------|
| `id` | 整数 | 是   | 任务配置主键 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入任务配置

获取指定主键的导入任务配置。

```http
GET /api/v1/fileio/import-task-setting/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task_setting.get`。

路径参数：

| 参数 | 类型 | 必填 | 说明         |
|------|------|------|--------------|
| `id` | 整数 | 是   | 任务配置主键 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonImportTaskSetting`](#response-jsfixedfastjsonimporttasksetting)。

### 新增导入任务配置

新增导入任务配置。

```http
POST /api/v1/fileio/import-task-setting
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task_setting.insert`。
审计记录：`webapi.fileio.import_task_setting.insert`。

请求体：[`WebInputImportTaskSetting`](#request-webinputimporttasksetting)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "enabled": true,
  "name": "默认导入配置",
  "remark": "用于日常导入"
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即新建任务配置的主键。

### 更新导入任务配置

更新导入任务配置。

```http
PATCH /api/v1/fileio/import-task-setting
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task_setting.update`。
审计记录：`webapi.fileio.import_task_setting.update`。

请求体：[`WebInputImportTaskSetting`](#request-webinputimporttasksetting)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。
更新时 `key` 必填。

请求示例：

```json
{
  "key": {
    "long_id": 1
  },
  "enabled": false,
  "name": "默认导入配置",
  "remark": "暂停使用"
}
```

成功响应：`data` 为 `null`。

### 删除导入任务配置

删除指定主键的导入任务配置。

```http
DELETE /api/v1/fileio/import-task-setting/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task_setting.delete`。
审计记录：`webapi.fileio.import_task_setting.delete`。

路径参数：

| 参数 | 类型 | 必填 | 说明         |
|------|------|------|--------------|
| `id` | 整数 | 是   | 任务配置主键 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部导入任务配置

分页获取全部导入任务配置。

```http
GET /api/v1/fileio/import-task-setting/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_task_setting.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTaskSetting`](#response-jsfixedfastjsonimporttasksetting)。

## 导入模板

### 判断导入模板是否存在

判断指定任务配置下是否存在指定标识符的导入模板。

```http
GET /api/v1/fileio/import-template/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：`data` 为布尔值。

### 获取导入模板信息

获取指定任务配置下指定标识符的导入模板信息。

```http
GET /api/v1/fileio/import-template/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonImportTemplateInfo`](#response-jsfixedfastjsonimporttemplateinfo)。

### 分页获取全部导入模板

分页获取全部导入模板信息。

```http
GET /api/v1/fileio/import-template/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTemplateInfo`](#response-jsfixedfastjsonimporttemplateinfo)。

### 分页获取任务配置下的导入模板

分页获取指定任务配置下的导入模板信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/import-template
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonImportTemplateInfo`](#response-jsfixedfastjsonimporttemplateinfo)。

### 创建导入模板

创建导入模板条目（此时不含文件内容）。

```http
POST /api/v1/fileio/import-template/create
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.create`。
审计记录：`webapi.fileio.import_template.create`。

请求体：[`WebInputTemplateCreateInfo`](#request-webinputtemplatecreateinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "task_setting_key": {
    "long_id": 1
  },
  "identifier": "default",
  "origin_name": "template.xlsx"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 移除导入模板

移除指定任务配置下指定标识符的导入模板。

```http
POST /api/v1/fileio/import-template/remove
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.remove`。
审计记录：`webapi.fileio.import_template.remove`。

请求体：[`WebInputTemplateRemoveInfo`](#request-webinputtemplateremoveinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  }
}
```

成功响应：`data` 为 `null`。

### 下载导入模板（字节响应）

下载导入模板文件，一次性返回全部字节。

```http
GET /api/v1/fileio/import-template/{taskSettingId}&{identifier}/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.download_file`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：HTTP 200，响应体为文件字节，响应头包含 `Content-Disposition: attachment;filename=<名称>`。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载导入模板（流式响应）

下载导入模板文件，以流的方式写出响应。

```http
POST /api/v1/fileio/import-template/download-file-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.download_file_stream`。

请求体：[`WebInputTemplateDownloadInfo`](#request-webinputtemplatedownloadinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  }
}
```

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 申请导入模板流下载凭证

为导入模板申请一次性下载凭证。

```http
POST /api/v1/fileio/import-template/{taskSettingId}&{identifier}/request-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.request_stream_voucher`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)，即凭证主键。

### 按凭证下载导入模板

使用凭证下载导入模板文件。

```http
GET /api/v1/fileio/import-template/download-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明     |
|--------------|------|------|----------|
| `voucher-id` | 整数 | 是   | 凭证主键 |

请求体：无。

成功响应：直接写响应流，响应头包含 `Content-Length`、`Content-Disposition: attachment; filename="<名称>"`，
内容类型为 `application/octet-stream`；不使用统一响应外层。

### 上传导入模板

以 `multipart/form-data` 上传导入模板文件，控制器在内存中读取全部字节。

```http
POST /api/v1/fileio/import-template/{taskSettingId}&{identifier}/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.upload`。
审计记录：`webapi.fileio.import_template.upload`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

表单字段：

| 字段   | 类型 | 必填 | 说明                         |
|--------|------|------|------------------------------|
| `file` | 文件 | 是   | 模板文件，原始文件名一并提交 |

成功响应：`data` 为 `null`。

### 流式上传导入模板

以 `multipart/form-data` 流式上传导入模板文件，控制器直接把输入流交给服务。

```http
POST /api/v1/fileio/import-template/{taskSettingId}&{identifier}/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.import_template.upload_stream`。
审计记录：`webapi.fileio.import_template.upload_stream`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 模板标识符   |

表单字段：

| 字段   | 类型 | 必填 | 说明                                           |
|--------|------|------|------------------------------------------------|
| `file` | 文件 | 是   | 模板文件，原始文件名与字节数一并提交给下游服务 |

成功响应：`data` 为 `null`。

## 读取器信息

### 判断读取器信息是否存在

判断指定任务配置下是否存在指定标识符的读取器信息。

```http
GET /api/v1/fileio/reader-info/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_info.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取读取器信息

获取指定任务配置下指定标识符的读取器信息。

```http
GET /api/v1/fileio/reader-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_info.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonReaderInfo`](#response-jsfixedfastjsonreaderinfo)。

### 新增读取器信息

新增读取器信息。

```http
POST /api/v1/fileio/reader-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_info.insert`。
审计记录：`webapi.fileio.reader_info.insert`。

请求体：[`WebInputReaderInfo`](#request-webinputreaderinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认读取器"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 更新读取器信息

更新读取器信息。

```http
PATCH /api/v1/fileio/reader-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_info.update`。
审计记录：`webapi.fileio.reader_info.update`。

请求体：[`WebInputReaderInfo`](#request-webinputreaderinfo)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": false,
  "type": "csv",
  "param": "{\"delimiter\":\",\"}",
  "remark": "停用的读取器"
}
```

成功响应：`data` 为 `null`。

### 删除读取器信息

删除指定任务配置下指定标识符的读取器信息。

```http
DELETE /api/v1/fileio/reader-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_info.delete`。
审计记录：`webapi.fileio.reader_info.delete`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部读取器信息

分页获取全部读取器信息。

```http
GET /api/v1/fileio/reader-info/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_info.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonReaderInfo`](#response-jsfixedfastjsonreaderinfo)。

### 分页获取任务配置下的读取器信息

分页获取指定任务配置下的读取器信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/reader-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_info.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonReaderInfo`](#response-jsfixedfastjsonreaderinfo)。

## 读取器支持

### 判断读取器类型是否受支持

判断指定类型标识的读取器是否由当前系统支持。

```http
GET /api/v1/fileio/reader-support/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_support.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 读取器类型ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取读取器支持信息

获取指定类型标识的读取器能力信息。

```http
GET /api/v1/fileio/reader-support/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_support.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 读取器类型ID |

请求体：无。

成功响应：`data` 为 [`FastJsonReaderSupport`](#response-fastjsonreadersupport)。

### 分页获取全部读取器支持信息

分页获取全部读取器能力信息。

```http
GET /api/v1/fileio/reader-support/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_support.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonReaderSupport`](#response-fastjsonreadersupport)。

### 按 ID 模糊查询读取器支持信息

按类型标识模糊匹配分页查询读取器能力信息。

```http
GET /api/v1/fileio/reader-support/id-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reader_support.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 类型标识的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页条数               |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonReaderSupport`](#response-fastjsonreadersupport)。

## 重置

### 重置导出子系统

重置导出子系统的运行时状态。

```http
POST /api/v1/fileio/reset-export
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reset.reset_export`。

请求体：无。

成功响应：`data` 为 `null`。

### 重置导入子系统

重置导入子系统的运行时状态。

```http
POST /api/v1/fileio/reset-import
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.reset.reset_import`。

请求体：无。

成功响应：`data` 为 `null`。

## 写入器信息

### 判断写入器信息是否存在

判断指定任务配置下是否存在指定标识符的写入器信息。

```http
GET /api/v1/fileio/writer-info/{taskSettingId}&{identifier}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_info.exists`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为布尔值。

### 获取写入器信息

获取指定任务配置下指定标识符的写入器信息。

```http
GET /api/v1/fileio/writer-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_info.get`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonWriterInfo`](#response-jsfixedfastjsonwriterinfo)。

### 新增写入器信息

新增写入器信息。

```http
POST /api/v1/fileio/writer-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_info.insert`。
审计记录：`webapi.fileio.writer_info.insert`。

请求体：[`WebInputWriterInfo`](#request-webinputwriterinfo)，使用 `Insert` 校验分组。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认写入器"
}
```

成功响应：`data` 为 [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey)。

### 更新写入器信息

更新写入器信息。

```http
PATCH /api/v1/fileio/writer-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_info.update`。
审计记录：`webapi.fileio.writer_info.update`。

请求体：[`WebInputWriterInfo`](#request-webinputwriterinfo)，使用默认校验分组；校验结果由 `@BindingCheck` 处理。

请求示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": false,
  "type": "csv",
  "param": "{\"delimiter\":\",\"}",
  "remark": "停用的写入器"
}
```

成功响应：`data` 为 `null`。

### 删除写入器信息

删除指定任务配置下指定标识符的写入器信息。

```http
DELETE /api/v1/fileio/writer-info/{taskSettingId}&{identifier}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_info.delete`。
审计记录：`webapi.fileio.writer_info.delete`。

路径参数：

| 参数            | 类型   | 必填 | 说明         |
|-----------------|--------|------|--------------|
| `taskSettingId` | 整数   | 是   | 任务配置主键 |
| `identifier`    | 字符串 | 是   | 配置项标识符 |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部写入器信息

分页获取全部写入器信息。

```http
GET /api/v1/fileio/writer-info/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_info.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonWriterInfo`](#response-jsfixedfastjsonwriterinfo)。

### 分页获取任务配置下的写入器信息

分页获取指定任务配置下的写入器信息。

```http
GET /api/v1/fileio/task-setting/{taskSettingId}/writer-info
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_info.child_for_task_setting`。

路径参数：

| 参数            | 类型 | 必填 | 说明         |
|-----------------|------|------|--------------|
| `taskSettingId` | 整数 | 是   | 任务配置主键 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonWriterInfo`](#response-jsfixedfastjsonwriterinfo)。

## 写入器支持

### 判断写入器类型是否受支持

判断指定类型标识的写入器是否由当前系统支持。

```http
GET /api/v1/fileio/writer-support/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_support.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 写入器类型ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取写入器支持信息

获取指定类型标识的写入器能力信息。

```http
GET /api/v1/fileio/writer-support/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_support.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明         |
|------|--------|------|--------------|
| `id` | 字符串 | 是   | 写入器类型ID |

请求体：无。

成功响应：`data` 为 [`FastJsonWriterSupport`](#response-fastjsonwritersupport)。

### 分页获取全部写入器支持信息

分页获取全部写入器能力信息。

```http
GET /api/v1/fileio/writer-support/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_support.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonWriterSupport`](#response-fastjsonwritersupport)。

### 按 ID 模糊查询写入器支持信息

按类型标识模糊匹配分页查询写入器能力信息。

```http
GET /api/v1/fileio/writer-support/id-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.fileio.writer_support.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明                   |
|-----------|--------|------|------------------------|
| `pattern` | 字符串 | 是   | 类型标识的模糊匹配模式 |
| `page`    | 整数   | 是   | 页码                   |
| `rows`    | 整数   | 是   | 每页条数               |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonWriterSupport`](#response-fastjsonwritersupport)。

---

## 数据结构

### 通用键和分页

<a id="webinputstringidkey"></a>

#### WebInputStringIdKey

| 字段        | 类型   | 必填 | 说明          |
|-------------|--------|------|---------------|
| `string_id` | 字符串 | 是   | 非空字符串 ID |

<a id="webinputlongidkey"></a>

#### WebInputLongIdKey

| 字段      | 类型 | 必填 | 说明                            |
|-----------|------|------|---------------------------------|
| `long_id` | 整数 | 否   | 长整型 ID；源码没有非空校验注解 |

<a id="fastjsonstringidkey"></a>

#### FastJsonStringIdKey

| 字段        | 类型   | 说明      |
|-------------|--------|-----------|
| `string_id` | 字符串 | 字符串 ID |

<a id="fastjsonlongidkey"></a>

#### FastJsonLongIdKey

| 字段      | 类型 | 说明                                            |
|-----------|------|-------------------------------------------------|
| `long_id` | 整数 | 普通长整型 ID，可能超出 JavaScript 安全整数范围 |

<a id="jsfixedfastjsonlongidkey"></a>

#### JSFixedFastJsonLongIdKey

| 字段      | 类型   | 说明                                      |
|-----------|--------|-------------------------------------------|
| `long_id` | 字符串 | 通过 `ToStringSerializer` 输出的长整型 ID |

<a id="webinputtasksettingitemkey"></a>

#### WebInputTaskSettingItemKey

| 字段              | 类型   | 必填 | 说明                 |
|-------------------|--------|------|----------------------|
| `task_setting_id` | 整数   | 是   | 任务配置主键         |
| `identifier`      | 字符串 | 是   | 标识符，最大长度 100 |

<a id="webinputtaskitemkey"></a>

#### WebInputTaskItemKey

| 字段         | 类型   | 必填 | 说明                 |
|--------------|--------|------|----------------------|
| `task_id`    | 整数   | 是   | 任务主键             |
| `identifier` | 字符串 | 是   | 标识符，最大长度 100 |

<a id="jsfixedfastjsontasksettingitemkey"></a>

#### JSFixedFastJsonTaskSettingItemKey

| 字段              | 类型   | 说明                       |
|-------------------|--------|----------------------------|
| `task_setting_id` | 字符串 | 任务配置主键，字符串序列化 |
| `identifier`      | 字符串 | 标识符                     |

<a id="jsfixedfastjsontaskitemkey"></a>

#### JSFixedFastJsonTaskItemKey

| 字段         | 类型   | 说明                   |
|--------------|--------|------------------------|
| `task_id`    | 字符串 | 任务主键，字符串序列化 |
| `identifier` | 字符串 | 标识符                 |

<a id="pageddata"></a>

#### PagedData

| 字段           | 类型   | 说明       |
|----------------|--------|------------|
| `current_page` | 整数   | 当前页码   |
| `total_pages`  | 整数   | 总页数     |
| `rows`         | 整数   | 每页条数   |
| `count`        | 字符串 | 数据总数   |
| `data`         | 数组   | 当前页数据 |

### 请求模型

以下“必填”只依据 Bean Validation 注解；未声明 `@NotNull`、`@NotEmpty` 或 `@NotBlank` 的字段不标为必填。
`@Length` 上限来自 `com.dwarfeng.fileio.sdk.util.Constraints`。

<a id="request-webinputexportconf"></a>

#### WebInputExportConf

| 字段      | 类型                                                        | 必填 | 说明                 |
|-----------|-------------------------------------------------------------|------|----------------------|
| `key`     | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键         |
| `content` | 字符串                                                      | 否   | 配置内容，无长度校验 |
| `remark`  | 字符串                                                      | 否   | 备注，最大长度 200   |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "content": "{}",
  "remark": "默认导出配置"
}
```

<a id="request-webinputimportconf"></a>

#### WebInputImportConf

| 字段      | 类型                                                        | 必填 | 说明                 |
|-----------|-------------------------------------------------------------|------|----------------------|
| `key`     | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键         |
| `content` | 字符串                                                      | 否   | 配置内容，无长度校验 |
| `remark`  | 字符串                                                      | 否   | 备注，最大长度 200   |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "content": "{}",
  "remark": "默认导入配置"
}
```

<a id="request-webinputexporterinfo"></a>

#### WebInputExporterInfo

| 字段      | 类型                                                        | 必填 | 说明                         |
|-----------|-------------------------------------------------------------|------|------------------------------|
| `key`     | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键                 |
| `enabled` | 布尔值                                                      | 否   | 是否启用，原始类型默认 false |
| `type`    | 字符串                                                      | 是   | 导出器类型，最大长度 50      |
| `param`   | 字符串                                                      | 否   | 参数，无长度校验             |
| `remark`  | 字符串                                                      | 否   | 备注，最大长度 200           |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认导出器"
}
```

<a id="request-webinputimporterinfo"></a>

#### WebInputImporterInfo

| 字段      | 类型                                                        | 必填 | 说明                         |
|-----------|-------------------------------------------------------------|------|------------------------------|
| `key`     | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键                 |
| `enabled` | 布尔值                                                      | 否   | 是否启用，原始类型默认 false |
| `type`    | 字符串                                                      | 是   | 导入器类型，最大长度 50      |
| `param`   | 字符串                                                      | 否   | 参数，无长度校验             |
| `remark`  | 字符串                                                      | 否   | 备注，最大长度 200           |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认导入器"
}
```

<a id="request-webinputreaderinfo"></a>

#### WebInputReaderInfo

| 字段      | 类型                                                        | 必填 | 说明                         |
|-----------|-------------------------------------------------------------|------|------------------------------|
| `key`     | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键                 |
| `enabled` | 布尔值                                                      | 否   | 是否启用，原始类型默认 false |
| `type`    | 字符串                                                      | 是   | 读取器类型，最大长度 50      |
| `param`   | 字符串                                                      | 否   | 参数，无长度校验             |
| `remark`  | 字符串                                                      | 否   | 备注，最大长度 200           |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认读取器"
}
```

<a id="request-webinputwriterinfo"></a>

#### WebInputWriterInfo

| 字段      | 类型                                                        | 必填 | 说明                         |
|-----------|-------------------------------------------------------------|------|------------------------------|
| `key`     | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键                 |
| `enabled` | 布尔值                                                      | 否   | 是否启用，原始类型默认 false |
| `type`    | 字符串                                                      | 是   | 写入器类型，最大长度 50      |
| `param`   | 字符串                                                      | 否   | 参数，无长度校验             |
| `remark`  | 字符串                                                      | 否   | 备注，最大长度 200           |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  },
  "enabled": true,
  "type": "excel",
  "param": "{}",
  "remark": "默认写入器"
}
```

<a id="request-webinputexporttasksetting"></a>

#### WebInputExportTaskSetting

| 字段      | 类型                                      | 必填 | 说明                         |
|-----------|-------------------------------------------|------|------------------------------|
| `key`     | [`WebInputLongIdKey`](#webinputlongidkey) | 否   | 任务配置主键；更新时必填     |
| `enabled` | 布尔值                                    | 否   | 是否启用，原始类型默认 false |
| `name`    | 字符串                                    | 是   | 名称，最大长度 50            |
| `remark`  | 字符串                                    | 否   | 备注，最大长度 200           |

示例：

```json
{
  "enabled": true,
  "name": "默认导出配置",
  "remark": "用于日常导出"
}
```

<a id="request-webinputimporttasksetting"></a>

#### WebInputImportTaskSetting

| 字段      | 类型                                      | 必填 | 说明                         |
|-----------|-------------------------------------------|------|------------------------------|
| `key`     | [`WebInputLongIdKey`](#webinputlongidkey) | 否   | 任务配置主键；更新时必填     |
| `enabled` | 布尔值                                    | 否   | 是否启用，原始类型默认 false |
| `name`    | 字符串                                    | 是   | 名称，最大长度 50            |
| `remark`  | 字符串                                    | 否   | 备注，最大长度 200           |

示例：

```json
{
  "enabled": true,
  "name": "默认导入配置",
  "remark": "用于日常导入"
}
```

<a id="request-webinputtaskcreateinfo"></a>

#### WebInputTaskCreateInfo

| 字段          | 类型                                          | 必填 | 说明                   |
|---------------|-----------------------------------------------|------|------------------------|
| `setting_key` | [`WebInputLongIdKey`](#webinputlongidkey)     | 是   | 任务配置主键           |
| `user_key`    | [`WebInputStringIdKey`](#webinputstringidkey) | 否   | 任务归属用户           |
| `message`     | 字符串                                        | 否   | 任务消息，最大长度 200 |

示例：

```json
{
  "setting_key": {
    "long_id": 1
  },
  "user_key": {
    "string_id": "test_user"
  },
  "message": "创建导出任务"
}
```

<a id="request-webinputtaskexecuteinfo"></a>

#### WebInputTaskExecuteInfo

| 字段       | 类型                                      | 必填 | 说明                 |
|------------|-------------------------------------------|------|----------------------|
| `task_key` | [`WebInputLongIdKey`](#webinputlongidkey) | 是   | 任务主键             |
| `message`  | 字符串                                    | 否   | 执行消息，无长度校验 |

示例：

```json
{
  "task_key": {
    "long_id": 1
  },
  "message": "开始执行"
}
```

<a id="request-webinputmetadataupsertinfo"></a>

#### WebInputMetadataUpsertInfo

| 字段      | 类型                                          | 必填 | 说明                   |
|-----------|-----------------------------------------------|------|------------------------|
| `key`     | [`WebInputTaskItemKey`](#webinputtaskitemkey) | 是   | 任务项键               |
| `content` | 字符串                                        | 否   | 元数据内容，无长度校验 |

示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  },
  "content": "{\"total\":3}"
}
```

<a id="request-webinputfiledownloadinfo"></a>

#### WebInputFileDownloadInfo

| 字段  | 类型                                          | 必填 | 说明     |
|-------|-----------------------------------------------|------|----------|
| `key` | [`WebInputTaskItemKey`](#webinputtaskitemkey) | 是   | 任务项键 |

示例：

```json
{
  "key": {
    "task_id": 1,
    "identifier": "default"
  }
}
```

<a id="request-webinputtemplatecreateinfo"></a>

#### WebInputTemplateCreateInfo

| 字段               | 类型                                      | 必填 | 说明                     |
|--------------------|-------------------------------------------|------|--------------------------|
| `task_setting_key` | [`WebInputLongIdKey`](#webinputlongidkey) | 否   | 任务配置主键             |
| `identifier`       | 字符串                                    | 是   | 模板标识符，最大长度 100 |
| `origin_name`      | 字符串                                    | 否   | 原始文件名，无长度校验   |

示例：

```json
{
  "task_setting_key": {
    "long_id": 1
  },
  "identifier": "default",
  "origin_name": "template.xlsx"
}
```

<a id="request-webinputtemplateremoveinfo"></a>

#### WebInputTemplateRemoveInfo

| 字段  | 类型                                                        | 必填 | 说明         |
|-------|-------------------------------------------------------------|------|--------------|
| `key` | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键 |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  }
}
```

<a id="request-webinputtemplatedownloadinfo"></a>

#### WebInputTemplateDownloadInfo

| 字段  | 类型                                                        | 必填 | 说明         |
|-------|-------------------------------------------------------------|------|--------------|
| `key` | [`WebInputTaskSettingItemKey`](#webinputtasksettingitemkey) | 是   | 任务配置项键 |

示例：

```json
{
  "key": {
    "task_setting_id": 1,
    "identifier": "default"
  }
}
```

### 响应模型

<a id="response-jsfixedfastjsonexportconf"></a>

#### JSFixedFastJsonExportConf

| 字段      | 类型                                                                      | 说明         |
|-----------|---------------------------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `content` | 字符串                                                                    | 配置内容     |
| `remark`  | 字符串                                                                    | 备注         |

<a id="response-jsfixedfastjsonimportconf"></a>

#### JSFixedFastJsonImportConf

| 字段      | 类型                                                                      | 说明         |
|-----------|---------------------------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `content` | 字符串                                                                    | 配置内容     |
| `remark`  | 字符串                                                                    | 备注         |

<a id="response-jsfixedfastjsonexporterinfo"></a>

#### JSFixedFastJsonExporterInfo

| 字段      | 类型                                                                      | 说明         |
|-----------|---------------------------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `enabled` | 布尔值                                                                    | 是否启用     |
| `type`    | 字符串                                                                    | 导出器类型   |
| `param`   | 字符串                                                                    | 参数         |
| `remark`  | 字符串                                                                    | 备注         |

<a id="response-jsfixedfastjsonimporterinfo"></a>

#### JSFixedFastJsonImporterInfo

| 字段      | 类型                                                                      | 说明         |
|-----------|---------------------------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `enabled` | 布尔值                                                                    | 是否启用     |
| `type`    | 字符串                                                                    | 导入器类型   |
| `param`   | 字符串                                                                    | 参数         |
| `remark`  | 字符串                                                                    | 备注         |

<a id="response-jsfixedfastjsonreaderinfo"></a>

#### JSFixedFastJsonReaderInfo

| 字段      | 类型                                                                      | 说明         |
|-----------|---------------------------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `enabled` | 布尔值                                                                    | 是否启用     |
| `type`    | 字符串                                                                    | 读取器类型   |
| `param`   | 字符串                                                                    | 参数         |
| `remark`  | 字符串                                                                    | 备注         |

<a id="response-jsfixedfastjsonwriterinfo"></a>

#### JSFixedFastJsonWriterInfo

| 字段      | 类型                                                                      | 说明         |
|-----------|---------------------------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `enabled` | 布尔值                                                                    | 是否启用     |
| `type`    | 字符串                                                                    | 写入器类型   |
| `param`   | 字符串                                                                    | 参数         |
| `remark`  | 字符串                                                                    | 备注         |

<a id="response-fastjsonexportersupport"></a>

#### FastJsonExporterSupport

| 字段               | 类型                                          | 说明       |
|--------------------|-----------------------------------------------|------------|
| `key`              | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 类型标识键 |
| `label`            | 字符串                                        | 显示标签   |
| `description`      | 字符串                                        | 描述       |
| `example_param`    | 字符串                                        | 参数示例   |
| `example_metadata` | 字符串                                        | 元数据示例 |

<a id="response-fastjsonimportersupport"></a>

#### FastJsonImporterSupport

| 字段               | 类型                                          | 说明       |
|--------------------|-----------------------------------------------|------------|
| `key`              | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 类型标识键 |
| `label`            | 字符串                                        | 显示标签   |
| `description`      | 字符串                                        | 描述       |
| `example_param`    | 字符串                                        | 参数示例   |
| `example_metadata` | 字符串                                        | 元数据示例 |

<a id="response-fastjsonreadersupport"></a>

#### FastJsonReaderSupport

| 字段               | 类型                                          | 说明       |
|--------------------|-----------------------------------------------|------------|
| `key`              | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 类型标识键 |
| `label`            | 字符串                                        | 显示标签   |
| `description`      | 字符串                                        | 描述       |
| `example_param`    | 字符串                                        | 参数示例   |
| `example_metadata` | 字符串                                        | 元数据示例 |

<a id="response-fastjsonwritersupport"></a>

#### FastJsonWriterSupport

| 字段               | 类型                                          | 说明       |
|--------------------|-----------------------------------------------|------------|
| `key`              | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 类型标识键 |
| `label`            | 字符串                                        | 显示标签   |
| `description`      | 字符串                                        | 描述       |
| `example_param`    | 字符串                                        | 参数示例   |
| `example_metadata` | 字符串                                        | 元数据示例 |

<a id="response-jsfixedfastjsonexportfileinfo"></a>

#### JSFixedFastJsonExportFileInfo

| 字段             | 类型                                                        | 说明         |
|------------------|-------------------------------------------------------------|--------------|
| `key`            | [`JSFixedFastJsonTaskItemKey`](#jsfixedfastjsontaskitemkey) | 任务项键     |
| `origin_name`    | 字符串                                                      | 原始文件名   |
| `length`         | 整数                                                        | 文件字节数   |
| `generated_date` | 日期                                                        | 文件生成时间 |
| `remark`         | 字符串                                                      | 备注         |

<a id="response-jsfixedfastjsonimportfileinfo"></a>

#### JSFixedFastJsonImportFileInfo

| 字段            | 类型                                                        | 说明         |
|-----------------|-------------------------------------------------------------|--------------|
| `key`           | [`JSFixedFastJsonTaskItemKey`](#jsfixedfastjsontaskitemkey) | 任务项键     |
| `origin_name`   | 字符串                                                      | 原始文件名   |
| `length`        | 整数                                                        | 文件字节数   |
| `uploaded_date` | 日期                                                        | 文件上传时间 |
| `remark`        | 字符串                                                      | 备注         |

<a id="response-jsfixedfastjsonexportmetadata"></a>

#### JSFixedFastJsonExportMetadata

| 字段      | 类型                                                        | 说明       |
|-----------|-------------------------------------------------------------|------------|
| `key`     | [`JSFixedFastJsonTaskItemKey`](#jsfixedfastjsontaskitemkey) | 任务项键   |
| `content` | 字符串                                                      | 元数据内容 |
| `remark`  | 字符串                                                      | 备注       |

<a id="response-jsfixedfastjsonimportmetadata"></a>

#### JSFixedFastJsonImportMetadata

| 字段      | 类型                                                        | 说明       |
|-----------|-------------------------------------------------------------|------------|
| `key`     | [`JSFixedFastJsonTaskItemKey`](#jsfixedfastjsontaskitemkey) | 任务项键   |
| `content` | 字符串                                                      | 元数据内容 |
| `remark`  | 字符串                                                      | 备注       |

<a id="response-jsfixedfastjsonexporttemplateinfo"></a>

#### JSFixedFastJsonExportTemplateInfo

| 字段            | 类型                                                                      | 说明         |
|-----------------|---------------------------------------------------------------------------|--------------|
| `key`           | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `origin_name`   | 字符串                                                                    | 原始文件名   |
| `length`        | 整数                                                                      | 文件字节数   |
| `uploaded_date` | 日期                                                                      | 上传时间     |
| `remark`        | 字符串                                                                    | 备注         |

<a id="response-jsfixedfastjsonimporttemplateinfo"></a>

#### JSFixedFastJsonImportTemplateInfo

| 字段            | 类型                                                                      | 说明         |
|-----------------|---------------------------------------------------------------------------|--------------|
| `key`           | [`JSFixedFastJsonTaskSettingItemKey`](#jsfixedfastjsontasksettingitemkey) | 任务配置项键 |
| `origin_name`   | 字符串                                                                    | 原始文件名   |
| `length`        | 整数                                                                      | 文件字节数   |
| `uploaded_date` | 日期                                                                      | 上传时间     |
| `remark`        | 字符串                                                                    | 备注         |

<a id="response-jsfixedfastjsonexporttasksetting"></a>

#### JSFixedFastJsonExportTaskSetting

| 字段      | 类型                                                    | 说明         |
|-----------|---------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务配置主键 |
| `enabled` | 布尔值                                                  | 是否启用     |
| `name`    | 字符串                                                  | 名称         |
| `remark`  | 字符串                                                  | 备注         |

<a id="response-jsfixedfastjsonimporttasksetting"></a>

#### JSFixedFastJsonImportTaskSetting

| 字段      | 类型                                                    | 说明         |
|-----------|---------------------------------------------------------|--------------|
| `key`     | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务配置主键 |
| `enabled` | 布尔值                                                  | 是否启用     |
| `name`    | 字符串                                                  | 名称         |
| `remark`  | 字符串                                                  | 备注         |

<a id="response-jsfixedfastjsonexporttask"></a>

#### JSFixedFastJsonExportTask

| 字段               | 类型                                                    | 说明                                                                 |
|--------------------|---------------------------------------------------------|----------------------------------------------------------------------|
| `key`              | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务主键                                                             |
| `setting_key`      | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务配置主键                                                         |
| `userKey`          | [`FastJsonStringIdKey`](#fastjsonstringidkey)           | 任务归属用户；注意该字段 JSON 名为 `userKey`，与其它字段命名风格不同 |
| `status`           | 整数                                                    | 任务状态                                                             |
| `create_date`      | 日期                                                    | 创建时间                                                             |
| `start_date`       | 日期                                                    | 开始时间                                                             |
| `end_date`         | 日期                                                    | 结束时间                                                             |
| `duration`         | 整数                                                    | 持续时长，可为 `null`                                                |
| `data_count`       | 整数                                                    | 数据条数                                                             |
| `message`          | 字符串                                                  | 任务消息                                                             |
| `should_dead_date` | 日期                                                    | 应为到期时间；JSON 名沿用源码中的 `should_dead_date`                 |
| `last_beat_date`   | 日期                                                    | 最后心跳时间；JSON 名沿用源码中的 `last_beat_date`                   |

说明：源码中 `shouldExpireDate` 与 `shouldDeadDate` 两个属性的 `@JSONField` 名称分别是 `should_dead_date` 与
`last_beat_date`，因此响应中的字段名与属性语义存在偏差，调用方应以 JSON 名称为准。

<a id="response-jsfixedfastjsonimporttask"></a>

#### JSFixedFastJsonImportTask

| 字段                 | 类型                                                    | 说明                              |
|----------------------|---------------------------------------------------------|-----------------------------------|
| `key`                | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务主键                          |
| `setting_key`        | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务配置主键                      |
| `userKey`            | [`FastJsonStringIdKey`](#fastjsonstringidkey)           | 任务归属用户；JSON 名为 `userKey` |
| `status`             | 整数                                                    | 任务状态                          |
| `create_date`        | 日期                                                    | 创建时间                          |
| `start_date`         | 日期                                                    | 开始时间                          |
| `end_date`           | 日期                                                    | 结束时间                          |
| `duration`           | 整数                                                    | 持续时长，可为 `null`             |
| `data_count`         | 整数                                                    | 数据条数                          |
| `message`            | 字符串                                                  | 任务消息                          |
| `should_expire_date` | 日期                                                    | 应为到期时间                      |
| `should_dead_date`   | 日期                                                    | 应为死亡时间                      |

<a id="response-jsfixedfastjsondispexporttask"></a>

#### JSFixedFastJsonDispExportTask

| 字段               | 类型                                                    | 说明                                                 |
|--------------------|---------------------------------------------------------|------------------------------------------------------|
| `key`              | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务主键                                             |
| `setting_key`      | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务配置主键                                         |
| `user_key`         | [`FastJsonStringIdKey`](#fastjsonstringidkey)           | 任务归属用户；注意该 JSON 名为 `user_key`            |
| `status`           | 整数                                                    | 任务状态                                             |
| `create_date`      | 日期                                                    | 创建时间                                             |
| `start_date`       | 日期                                                    | 开始时间                                             |
| `end_date`         | 日期                                                    | 结束时间                                             |
| `duration`         | 整数                                                    | 持续时长，可为 `null`                                |
| `data_count`       | 整数                                                    | 数据条数                                             |
| `message`          | 字符串                                                  | 任务消息                                             |
| `should_dead_date` | 日期                                                    | 应为到期时间；JSON 名沿用源码中的 `should_dead_date` |
| `last_beat_date`   | 日期                                                    | 最后心跳时间；JSON 名沿用源码中的 `last_beat_date`   |
| `account`          | [`FastJsonDispAccount`](#response-fastjsondispaccount)  | 任务归属账户展示信息                                 |

<a id="response-jsfixedfastjsondispimporttask"></a>

#### JSFixedFastJsonDispImportTask

| 字段                 | 类型                                                    | 说明                  |
|----------------------|---------------------------------------------------------|-----------------------|
| `key`                | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务主键              |
| `setting_key`        | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 任务配置主键          |
| `user_key`           | [`FastJsonStringIdKey`](#fastjsonstringidkey)           | 任务归属用户          |
| `status`             | 整数                                                    | 任务状态              |
| `create_date`        | 日期                                                    | 创建时间              |
| `start_date`         | 日期                                                    | 开始时间              |
| `end_date`           | 日期                                                    | 结束时间              |
| `duration`           | 整数                                                    | 持续时长，可为 `null` |
| `data_count`         | 整数                                                    | 数据条数              |
| `message`            | 字符串                                                  | 任务消息              |
| `should_expire_date` | 日期                                                    | 应为到期时间          |
| `should_dead_date`   | 日期                                                    | 应为死亡时间          |
| `account`            | [`FastJsonDispAccount`](#response-fastjsondispaccount)  | 任务归属账户展示信息  |

<a id="response-fastjsondispaccount"></a>

#### FastJsonDispAccount

| 字段           | 类型                                          | 说明     |
|----------------|-----------------------------------------------|----------|
| `key`          | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 账户主键 |
| `name`         | 字符串                                        | 账户名称 |
| `enabled`      | 布尔值                                        | 是否启用 |
| `remark`       | 字符串                                        | 备注     |
| `display_name` | 字符串                                        | 显示名称 |
