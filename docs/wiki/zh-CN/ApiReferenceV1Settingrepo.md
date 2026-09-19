# API 参考 - V1 settingrepo

本文档说明 `familyhelper-webapi-node` 中 `settingrepo` 业务域提供的 HTTP API。对应 Java 源码位于
`com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo`，统一路径前缀为 `/api/v1/settingrepo`。

---

## 概述

`settingrepo` 业务域提供配置类别、配置节点和多种配置内容节点的查询与维护能力。源码包含 14 个 Controller、
154 个映射方法；其中两个方法各声明两个等价 URL。公开接口不要求登录，其余接口均要求有效登录状态和完整权限标识。

| 控制器                         | 功能                                                 | 接口数量 |
|--------------------------------|------------------------------------------------------|---------:|
| `FileListNodeController`       | 文件列表节点查询、上传、更新、排序、删除和下载       |       19 |
| `FileNodeController`           | 单文件节点查询、上传和下载                           |       12 |
| `FormatterSupportController`   | 格式化器能力查询                                     |        4 |
| `IahnNodeController`           | 地区、消息键和国际化消息维护                         |       20 |
| `ImageListNodeController`      | 图片列表节点查询、上传、更新、排序、缩略图和原图下载 |       21 |
| `ImageNodeController`          | 单图片节点查询、上传、缩略图和原图下载               |       15 |
| `KvNodeController`             | 键值对内容查询和条目维护                             |       12 |
| `KvNodeItemController`         | 键值对持久化条目查询                                 |        4 |
| `NavigationNodeController`     | 导航树查询和条目维护                                 |       12 |
| `NavigationNodeItemController` | 导航条目关系、路径和展示信息查询                     |       11 |
| `ResetController`              | 配置格式重置                                         |        1 |
| `SettingCategoryController`    | 配置类别维护和查询                                   |        7 |
| `SettingNodeController`        | 配置节点查询、初始化、移除和公开查看                 |       10 |
| `TextNodeController`           | 文本节点查询、写入和公开查看                         |        6 |

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

所有带 `@LoginRequired` 的接口同时带有 `@PermissionRequired`；接口详情中的权限值均为源码中的完整权限标识。公开接口既没有登录注解，也没有额外权限标识。

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

### Base64 JSON 查询参数

带 `@Base64RequestParam("download-info")` 的 GET 下载接口要求查询参数 `download-info`。其值是请求模型 JSON 的 UTF-8
字节经过标准 Base64 编码后的字符串；该参数缺失时由参数解析器报告错误。

### 文件上传与下载

上传接口使用 `multipart/form-data`。字段 `file` 必填；`category` 是配置类别；`args` 是 JSON 字符串数组文本，例如
`["default"]`。
列表节点上传时 `index` 可为空，更新时 `index` 必填。名称含 `-stream` 的接口把上传流直接交给服务，HTTP 字段格式与对应非流式接口相同。

下载文件接口成功时返回字节响应，并设置 `Content-Disposition`；按凭证下载的接口还设置 `Content-Length` 和
`application/octet-stream`。
申请凭证接口返回 `JSFixedFastJsonLongIdKey`，随后可通过无需登录注解的 `download-file-by-voucher?voucher-id=...`
下载。凭证的有效期和复用规则不在当前 Controller 契约中。

### 日期和长整型

`Date` 序列化为 Unix 毫秒时间戳。名称以 `JSFixed` 开头的长整型响应字段使用字符串序列化；普通 `FastJsonLongIdKey` 仍是 JSON
整数，前端读取时需注意 JavaScript 安全整数范围。

---

## 接口一览

### 文件列表节点

| 方法 | 路径                                                                        | 身份认证 | 请求                                                 | 成功数据                                                                                                 |
|------|-----------------------------------------------------------------------------|----------|------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/file-list-node/{id}/exists`                            | 需要     | 路径/查询参数                                        | `data` 为布尔值。                                                                                        |
| GET  | `/api/v1/settingrepo/file-list-node/{id}`                                   | 需要     | 路径/查询参数                                        | `data` 为 [`FastJsonFileListNode`](#response-fastjsonfilelistnode)。                                     |
| GET  | `/api/v1/settingrepo/file-list-node/all`                                    | 需要     | 路径/查询参数                                        | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFileListNode`](#response-fastjsonfilelistnode)。 |
| POST | `/api/v1/settingrepo/file-list-node/size`                                   | 需要     | `WebInputFileListNodeSizeInfo`                       | `data` 为 [`FastJsonFileListNodeSizeResult`](#response-fastjsonfilelistnodesizeresult)。                 |
| POST | `/api/v1/settingrepo/file-list-node/inspect`                                | 需要     | `WebInputFileListNodeInspectInfo`                    | `data` 为 [`FastJsonFileListNodeInspectResult`](#response-fastjsonfilelistnodeinspectresult)。           |
| POST | `/api/v1/settingrepo/file-list-node/download-file`                          | 需要     | `WebInputFileListNodeFileDownloadInfo`               | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                   |
| GET  | `/api/v1/settingrepo/file-list-node/download-file`                          | 需要     | Base64(`WebInputFileListNodeFileDownloadInfo`)       | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                   |
| POST | `/api/v1/settingrepo/file-list-node/request-file-stream-voucher`            | 需要     | `WebInputFileListNodeFileDownloadInfo`               | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                             |
| GET  | `/api/v1/settingrepo/file-list-node/download-file-by-voucher`               | 不需要   | 路径/查询参数                                        | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                           |
| POST | `/api/v1/settingrepo/file-list-node/upload`                                 | 需要     | `multipart/form-data`                                | `data` 为 `null`。                                                                                       |
| POST | `/api/v1/settingrepo/file-list-node/upload-stream`                          | 需要     | `multipart/form-data`                                | `data` 为 `null`。                                                                                       |
| POST | `/api/v1/settingrepo/file-list-node/update`                                 | 需要     | `multipart/form-data`                                | `data` 为 `null`。                                                                                       |
| POST | `/api/v1/settingrepo/file-list-node/update-stream`                          | 需要     | `multipart/form-data`                                | `data` 为 `null`。                                                                                       |
| POST | `/api/v1/settingrepo/file-list-node/change-order`                           | 需要     | `WebInputFileListNodeChangeOrderInfo`                | `data` 为 `null`。                                                                                       |
| POST | `/api/v1/settingrepo/file-list-node/remove`                                 | 需要     | `WebInputFileListNodeRemoveInfo`                     | `data` 为 `null`。                                                                                       |
| POST | `/api/v1/settingrepo/file-list-node/size-for-public`                        | 不需要   | `WebInputPublicFileListNodeSizeInfo`                 | `data` 为 [`FastJsonFileListNodeSizeResult`](#response-fastjsonfilelistnodesizeresult)。                 |
| POST | `/api/v1/settingrepo/file-list-node/inspect-for-public`                     | 不需要   | `WebInputPublicFileListNodeInspectInfo`              | `data` 为 [`FastJsonFileListNodeInspectResult`](#response-fastjsonfilelistnodeinspectresult)。           |
| GET  | `/api/v1/settingrepo/file-list-node/download-file-for-public`               | 不需要   | Base64(`WebInputPublicFileListNodeFileDownloadInfo`) | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                   |
| POST | `/api/v1/settingrepo/file-list-node/request-file-stream-voucher-for-public` | 不需要   | `WebInputPublicFileListNodeFileDownloadInfo`         | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                             |

### 文件节点

| 方法 | 路径                                                                   | 身份认证 | 请求                                             | 成功数据                                                                                         |
|------|------------------------------------------------------------------------|----------|--------------------------------------------------|--------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/file-node/{id}/exists`                            | 需要     | 路径/查询参数                                    | `data` 为布尔值。                                                                                |
| GET  | `/api/v1/settingrepo/file-node/{id}`                                   | 需要     | 路径/查询参数                                    | `data` 为 [`FastJsonFileNode`](#response-fastjsonfilenode)。                                     |
| GET  | `/api/v1/settingrepo/file-node/all`                                    | 需要     | 路径/查询参数                                    | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFileNode`](#response-fastjsonfilenode)。 |
| POST | `/api/v1/settingrepo/file-node/inspect`                                | 需要     | `WebInputFileNodeInspectInfo`                    | `data` 为 [`FastJsonFileNodeInspectResult`](#response-fastjsonfilenodeinspectresult)。           |
| GET  | `/api/v1/settingrepo/file-node/download-file`                          | 需要     | Base64(`WebInputFileNodeFileDownloadInfo`)       | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                           |
| POST | `/api/v1/settingrepo/file-node/request-file-stream-voucher`            | 需要     | `WebInputFileNodeFileDownloadInfo`               | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                     |
| GET  | `/api/v1/settingrepo/file-node/download-file-by-voucher`               | 不需要   | 路径/查询参数                                    | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                   |
| POST | `/api/v1/settingrepo/file-node/upload`                                 | 需要     | `multipart/form-data`                            | `data` 为 `null`。                                                                               |
| POST | `/api/v1/settingrepo/file-node/upload-stream`                          | 需要     | `multipart/form-data`                            | `data` 为 `null`。                                                                               |
| POST | `/api/v1/settingrepo/file-node/inspect-for-public`                     | 不需要   | `WebInputPublicFileNodeInspectInfo`              | `data` 为 [`FastJsonFileNodeInspectResult`](#response-fastjsonfilenodeinspectresult)。           |
| GET  | `/api/v1/settingrepo/file-node/download-file-for-public`               | 不需要   | Base64(`WebInputPublicFileNodeFileDownloadInfo`) | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                           |
| POST | `/api/v1/settingrepo/file-node/request-file-stream-voucher-for-public` | 不需要   | `WebInputPublicFileNodeFileDownloadInfo`         | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                     |

### 格式化器支持

| 方法 | 路径                                                | 身份认证 | 请求          | 成功数据                                                                                                         |
|------|-----------------------------------------------------|----------|---------------|------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/formatter-support/{id}/exists` | 需要     | 路径/查询参数 | `data` 为布尔值。                                                                                                |
| GET  | `/api/v1/settingrepo/formatter-support/{id}`        | 需要     | 路径/查询参数 | `data` 为 [`FastJsonFormatterSupport`](#response-fastjsonformattersupport)。                                     |
| GET  | `/api/v1/settingrepo/formatter-support/all`         | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFormatterSupport`](#response-fastjsonformattersupport)。 |
| GET  | `/api/v1/settingrepo/formatter-support/id-like`     | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFormatterSupport`](#response-fastjsonformattersupport)。 |

### 国际化节点

| 方法 | 路径                                                                       | 身份认证 | 请求                                         | 成功数据                                                                                                             |
|------|----------------------------------------------------------------------------|----------|----------------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/iahn-node/{id}/exists`                                | 需要     | 路径/查询参数                                | `data` 为布尔值。                                                                                                    |
| GET  | `/api/v1/settingrepo/iahn-node/{id}`                                       | 需要     | 路径/查询参数                                | `data` 为 [`FastJsonIahnNode`](#response-fastjsoniahnnode)。                                                         |
| GET  | `/api/v1/settingrepo/iahn-node/all`                                        | 需要     | 路径/查询参数                                | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonIahnNode`](#response-fastjsoniahnnode)。                     |
| POST | `/api/v1/settingrepo/iahn-node/inspect-message`                            | 需要     | `WebInputIahnNodeMessageInspectInfo`         | `data` 为 [`FastJsonIahnNodeMessageInspectResult`](#response-fastjsoniahnnodemessageinspectresult)。                 |
| POST | `/api/v1/settingrepo/iahn-node/batch-inspect-message-by-locale`            | 需要     | `WebInputIahnNodeMessageInspectByLocaleInfo` | `data` 为 [`FastJsonIahnNodeMessageInspectByLocaleResult`](#response-fastjsoniahnnodemessageinspectbylocaleresult)。 |
| POST | `/api/v1/settingrepo/iahn-node/inspect-locale-list`                        | 需要     | `WebInputIahnNodeLocaleListInspectInfo`      | `data` 为 [`FastJsonIahnNodeLocaleListInspectResult`](#response-fastjsoniahnnodelocalelistinspectresult)。           |
| POST | `/api/v1/settingrepo/iahn-node/inspect-mek-list`                           | 需要     | `WebInputIahnNodeMekListInspectInfo`         | `data` 为 [`FastJsonIahnNodeMekListInspectResult`](#response-fastjsoniahnnodemeklistinspectresult)。                 |
| POST | `/api/v1/settingrepo/iahn-node/inspect-message-table`                      | 需要     | `WebInputIahnNodeMessageTableInspectInfo`    | `data` 为 [`FastJsonIahnNodeMessageTableInspectResult`](#response-fastjsoniahnnodemessagetableinspectresult)。       |
| POST | `/api/v1/settingrepo/iahn-node/put-locale`                                 | 需要     | `WebInputIahnNodeLocalePutInfo`              | `data` 为 `null`。                                                                                                   |
| POST | `/api/v1/settingrepo/iahn-node/remove-locale`                              | 需要     | `WebInputIahnNodeLocaleRemoveInfo`           | `data` 为 `null`。                                                                                                   |
| POST | `/api/v1/settingrepo/iahn-node/put-mek`                                    | 需要     | `WebInputIahnNodeMekPutInfo`                 | `data` 为 `null`。                                                                                                   |
| POST | `/api/v1/settingrepo/iahn-node/remove-mek`                                 | 需要     | `WebInputIahnNodeMekRemoveInfo`              | `data` 为 `null`。                                                                                                   |
| POST | `/api/v1/settingrepo/iahn-node/upsert-message`                             | 需要     | `WebInputIahnNodeMessageUpsertInfo`          | `data` 为 `null`。                                                                                                   |
| POST | `/api/v1/settingrepo/iahn-node/batch-upsert-message-by-locale`             | 需要     | `WebInputIahnNodeMessageUpsertByLocaleInfo`  | `data` 为 `null`。                                                                                                   |
| POST | `/api/v1/settingrepo/iahn-node/batch-upsert-message-by-mek`                | 需要     | `WebInputIahnNodeMessageUpsertByMekInfo`     | `data` 为 `null`。                                                                                                   |
| POST | `/api/v1/settingrepo/iahn-node/inspect-message-for-public`                 | 不需要   | `PublicIahnNodeMessageInspectInfo`           | `data` 为 [`FastJsonIahnNodeMessageInspectResult`](#response-fastjsoniahnnodemessageinspectresult)。                 |
| POST | `/api/v1/settingrepo/iahn-node/batch-inspect-message-by-locale-for-public` | 不需要   | `PublicIahnNodeMessageInspectByLocaleInfo`   | `data` 为 [`FastJsonIahnNodeMessageInspectByLocaleResult`](#response-fastjsoniahnnodemessageinspectbylocaleresult)。 |
| POST | `/api/v1/settingrepo/iahn-node/inspect-locale-list-for-public`             | 不需要   | `PublicIahnNodeLocaleListInspectInfo`        | `data` 为 [`FastJsonIahnNodeLocaleListInspectResult`](#response-fastjsoniahnnodelocalelistinspectresult)。           |
| POST | `/api/v1/settingrepo/iahn-node/inspect-mek-list-for-public`                | 不需要   | `PublicIahnNodeMekListInspectInfo`           | `data` 为 [`FastJsonIahnNodeMekListInspectResult`](#response-fastjsoniahnnodemeklistinspectresult)。                 |
| POST | `/api/v1/settingrepo/iahn-node/inspect-message-table-for-public`           | 不需要   | `PublicIahnNodeMessageTableInspectInfo`      | `data` 为 [`FastJsonIahnNodeMessageTableInspectResult`](#response-fastjsoniahnnodemessagetableinspectresult)。       |

### 图片列表节点

| 方法 | 路径                                                                         | 身份认证 | 请求                                                       | 成功数据                                                                                                   |
|------|------------------------------------------------------------------------------|----------|------------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/image-list-node/{id}/exists`                            | 需要     | 路径/查询参数                                              | `data` 为布尔值。                                                                                          |
| GET  | `/api/v1/settingrepo/image-list-node/{id}`                                   | 需要     | 路径/查询参数                                              | `data` 为 [`FastJsonImageListNode`](#response-fastjsonimagelistnode)。                                     |
| GET  | `/api/v1/settingrepo/image-list-node/all`                                    | 需要     | 路径/查询参数                                              | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImageListNode`](#response-fastjsonimagelistnode)。 |
| POST | `/api/v1/settingrepo/image-list-node/size`                                   | 需要     | `WebInputImageListNodeSizeInfo`                            | `data` 为 [`FastJsonImageListNodeSizeResult`](#response-fastjsonimagelistnodesizeresult)。                 |
| POST | `/api/v1/settingrepo/image-list-node/inspect`                                | 需要     | `WebInputImageListNodeInspectInfo`                         | `data` 为 [`FastJsonImageListNodeInspectResult`](#response-fastjsonimagelistnodeinspectresult)。           |
| POST | `/api/v1/settingrepo/image-list-node/download-file`                          | 需要     | `WebInputImageListNodeFileDownloadInfo`                    | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                     |
| GET  | `/api/v1/settingrepo/image-list-node/download-file`                          | 需要     | Base64(`WebInputImageListNodeFileDownloadInfo`)            | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                     |
| POST | `/api/v1/settingrepo/image-list-node/request-file-stream-voucher`            | 需要     | `WebInputImageListNodeFileDownloadInfo`                    | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                               |
| GET  | `/api/v1/settingrepo/image-list-node/download-file-by-voucher`               | 不需要   | 路径/查询参数                                              | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                             |
| GET  | `/api/v1/settingrepo/image-list-node/download-thumbnail`                     | 需要     | Base64(`WebInputImageListNodeThumbnailDownloadInfo`)       | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                     |
| POST | `/api/v1/settingrepo/image-list-node/upload`                                 | 需要     | `multipart/form-data`                                      | `data` 为 `null`。                                                                                         |
| POST | `/api/v1/settingrepo/image-list-node/upload-stream`                          | 需要     | `multipart/form-data`                                      | `data` 为 `null`。                                                                                         |
| POST | `/api/v1/settingrepo/image-list-node/update`                                 | 需要     | `multipart/form-data`                                      | `data` 为 `null`。                                                                                         |
| POST | `/api/v1/settingrepo/image-list-node/update-stream`                          | 需要     | `multipart/form-data`                                      | `data` 为 `null`。                                                                                         |
| POST | `/api/v1/settingrepo/image-list-node/change-order`                           | 需要     | `WebInputImageListNodeChangeOrderInfo`                     | `data` 为 `null`。                                                                                         |
| POST | `/api/v1/settingrepo/image-list-node/remove`                                 | 需要     | `WebInputImageListNodeRemoveInfo`                          | `data` 为 `null`。                                                                                         |
| POST | `/api/v1/settingrepo/image-list-node/size-for-public`                        | 不需要   | `WebInputPublicImageListNodeSizeInfo`                      | `data` 为 [`FastJsonImageListNodeSizeResult`](#response-fastjsonimagelistnodesizeresult)。                 |
| POST | `/api/v1/settingrepo/image-list-node/inspect-for-public`                     | 不需要   | `WebInputPublicImageListNodeInspectInfo`                   | `data` 为 [`FastJsonImageListNodeInspectResult`](#response-fastjsonimagelistnodeinspectresult)。           |
| GET  | `/api/v1/settingrepo/image-list-node/download-file-for-public`               | 不需要   | Base64(`WebInputPublicImageListNodeFileDownloadInfo`)      | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                     |
| POST | `/api/v1/settingrepo/image-list-node/request-file-stream-voucher-for-public` | 不需要   | `WebInputPublicImageListNodeFileDownloadInfo`              | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                               |
| GET  | `/api/v1/settingrepo/image-list-node/download-thumbnail-for-public`          | 不需要   | Base64(`WebInputPublicImageListNodeThumbnailDownloadInfo`) | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                                     |

### 图片节点

| 方法 | 路径                                                                    | 身份认证 | 请求                                                   | 成功数据                                                                                           |
|------|-------------------------------------------------------------------------|----------|--------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/image-node/{id}/exists`                            | 需要     | 路径/查询参数                                          | `data` 为布尔值。                                                                                  |
| GET  | `/api/v1/settingrepo/image-node/{id}`                                   | 需要     | 路径/查询参数                                          | `data` 为 [`FastJsonImageNode`](#response-fastjsonimagenode)。                                     |
| GET  | `/api/v1/settingrepo/image-node/all`                                    | 需要     | 路径/查询参数                                          | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImageNode`](#response-fastjsonimagenode)。 |
| POST | `/api/v1/settingrepo/image-node/inspect`                                | 需要     | `WebInputImageNodeInspectInfo`                         | `data` 为 [`FastJsonImageNodeInspectResult`](#response-fastjsonimagenodeinspectresult)。           |
| POST | `/api/v1/settingrepo/image-node/download-file`                          | 需要     | `WebInputImageNodeFileDownloadInfo`                    | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                             |
| GET  | `/api/v1/settingrepo/image-node/download-file`                          | 需要     | Base64(`WebInputImageNodeFileDownloadInfo`)            | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                             |
| POST | `/api/v1/settingrepo/image-node/request-file-stream-voucher`            | 需要     | `WebInputImageNodeFileDownloadInfo`                    | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                       |
| GET  | `/api/v1/settingrepo/image-node/download-file-by-voucher`               | 不需要   | 路径/查询参数                                          | 原始文件流（`application/octet-stream`），不使用统一响应外层。                                     |
| GET  | `/api/v1/settingrepo/image-node/download-thumbnail`                     | 需要     | Base64(`WebInputImageNodeThumbnailDownloadInfo`)       | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                             |
| POST | `/api/v1/settingrepo/image-node/upload`                                 | 需要     | `multipart/form-data`                                  | `data` 为 `null`。                                                                                 |
| POST | `/api/v1/settingrepo/image-node/upload-stream`                          | 需要     | `multipart/form-data`                                  | `data` 为 `null`。                                                                                 |
| POST | `/api/v1/settingrepo/image-node/inspect-for-public`                     | 不需要   | `WebInputPublicImageNodeInspectInfo`                   | `data` 为 [`FastJsonImageNodeInspectResult`](#response-fastjsonimagenodeinspectresult)。           |
| GET  | `/api/v1/settingrepo/image-node/download-file-for-public`               | 不需要   | Base64(`WebInputPublicImageNodeFileDownloadInfo`)      | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                             |
| POST | `/api/v1/settingrepo/image-node/request-file-stream-voucher-for-public` | 不需要   | `WebInputPublicImageNodeFileDownloadInfo`              | `data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。                       |
| GET  | `/api/v1/settingrepo/image-node/download-thumbnail-for-public`          | 不需要   | Base64(`WebInputPublicImageNodeThumbnailDownloadInfo`) | 成功时为文件或缩略图字节；失败时响应体为统一错误对象。                                             |

### 键值对节点

| 方法 | 路径                                                  | 身份认证 | 请求                                  | 成功数据                                                                                     |
|------|-------------------------------------------------------|----------|---------------------------------------|----------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/kv-node/{id}/exists`             | 需要     | 路径/查询参数                         | `data` 为布尔值。                                                                            |
| GET  | `/api/v1/settingrepo/kv-node/{id}`                    | 需要     | 路径/查询参数                         | `data` 为 [`FastJsonKvNode`](#response-fastjsonkvnode)。                                     |
| GET  | `/api/v1/settingrepo/kv-node/all`                     | 需要     | 路径/查询参数                         | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonKvNode`](#response-fastjsonkvnode)。 |
| POST | `/api/v1/settingrepo/kv-node/count`                   | 需要     | `WebInputKvNodeCountInfo`             | `data` 为 [`FastJsonKvNodeCountResult`](#response-fastjsonkvnodecountresult)。               |
| POST | `/api/v1/settingrepo/kv-node/inspect`                 | 需要     | `WebInputKvNodeInspectInfo`           | `data` 为 [`FastJsonKvNodeInspectResult`](#response-fastjsonkvnodeinspectresult)。           |
| POST | `/api/v1/settingrepo/kv-node/inspect-item`            | 需要     | `WebInputKvNodeItemInspectInfo`       | `data` 为 [`FastJsonKvNodeItemInspectResult`](#response-fastjsonkvnodeiteminspectresult)。   |
| POST | `/api/v1/settingrepo/kv-node/put-item`                | 需要     | `WebInputKvNodeItemPutInfo`           | `data` 为 `null`。                                                                           |
| POST | `/api/v1/settingrepo/kv-node/remove-item`             | 需要     | `WebInputKvNodeItemRemoveInfo`        | `data` 为 `null`。                                                                           |
| POST | `/api/v1/settingrepo/kv-node/clear`                   | 需要     | `WebInputKvNodeClearInfo`             | `data` 为 `null`。                                                                           |
| POST | `/api/v1/settingrepo/kv-node/count-for-public`        | 不需要   | `WebInputPublicKvNodeCountInfo`       | `data` 为 [`FastJsonKvNodeCountResult`](#response-fastjsonkvnodecountresult)。               |
| POST | `/api/v1/settingrepo/kv-node/inspect-for-public`      | 不需要   | `WebInputPublicKvNodeInspectInfo`     | `data` 为 [`FastJsonKvNodeInspectResult`](#response-fastjsonkvnodeinspectresult)。           |
| POST | `/api/v1/settingrepo/kv-node/inspect-item-for-public` | 不需要   | `WebInputPublicKvNodeItemInspectInfo` | `data` 为 [`FastJsonKvNodeItemInspectResult`](#response-fastjsonkvnodeiteminspectresult)。   |

### 键值对节点条目

| 方法 | 路径                                                              | 身份认证 | 请求          | 成功数据                                                                                             |
|------|-------------------------------------------------------------------|----------|---------------|------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/kv-node-item/{nodeId}/{itemStringId}/exists` | 需要     | 路径/查询参数 | `data` 为布尔值。                                                                                    |
| GET  | `/api/v1/settingrepo/kv-node-item/{nodeId}/{itemStringId}`        | 需要     | 路径/查询参数 | `data` 为 [`FastJsonKvNodeItem`](#response-fastjsonkvnodeitem)。                                     |
| GET  | `/api/v1/settingrepo/kv-node-item/all`                            | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonKvNodeItem`](#response-fastjsonkvnodeitem)。 |
| GET  | `/api/v1/settingrepo/kv-node/{nodeId}/kv-node-item`               | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonKvNodeItem`](#response-fastjsonkvnodeitem)。 |

### 导航节点

| 方法 | 路径                                                     | 身份认证 | 请求                                      | 成功数据                                                                                                               |
|------|----------------------------------------------------------|----------|-------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/navigation-node/{id}/exists`        | 需要     | 路径/查询参数                             | `data` 为布尔值。                                                                                                      |
| GET  | `/api/v1/settingrepo/navigation-node/{id}`               | 需要     | 路径/查询参数                             | `data` 为 [`FastJsonNavigationNode`](#response-fastjsonnavigationnode)。                                               |
| GET  | `/api/v1/settingrepo/navigation-node/all`                | 需要     | 路径/查询参数                             | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonNavigationNode`](#response-fastjsonnavigationnode)。           |
| POST | `/api/v1/settingrepo/navigation-node/size`               | 需要     | `WebInputNavigationNodeSizeInfo`          | `data` 为 [`FastJsonNavigationNodeSizeResult`](#response-fastjsonnavigationnodesizeresult)。                           |
| POST | `/api/v1/settingrepo/navigation-node/inspect`            | 需要     | `WebInputNavigationNodeInspectInfo`       | `data` 为 [`JSFixedFastJsonNavigationNodeInspectResult`](#response-jsfixedfastjsonnavigationnodeinspectresult)。       |
| POST | `/api/v1/settingrepo/navigation-node/update-node`        | 需要     | `WebInputNavigationNodeUpdateInfo`        | `data` 为 `null`。                                                                                                     |
| POST | `/api/v1/settingrepo/navigation-node/insert-item`        | 需要     | `WebInputNavigationNodeItemInsertInfo`    | `data` 为 [`JSFixedFastJsonNavigationNodeItemInsertResult`](#response-jsfixedfastjsonnavigationnodeiteminsertresult)。 |
| POST | `/api/v1/settingrepo/navigation-node/update-item`        | 需要     | `WebInputNavigationNodeItemUpdateInfo`    | `data` 为 `null`。                                                                                                     |
| POST | `/api/v1/settingrepo/navigation-node/remove-item`        | 需要     | `WebInputNavigationNodeItemRemoveInfo`    | `data` 为 `null`。                                                                                                     |
| POST | `/api/v1/settingrepo/navigation-node/format-index`       | 需要     | `WebInputNavigationNodeFormatIndexInfo`   | `data` 为 `null`。                                                                                                     |
| POST | `/api/v1/settingrepo/navigation-node/size-for-public`    | 不需要   | `WebInputPublicNavigationNodeSizeInfo`    | `data` 为 [`FastJsonNavigationNodeSizeResult`](#response-fastjsonnavigationnodesizeresult)。                           |
| POST | `/api/v1/settingrepo/navigation-node/inspect-for-public` | 不需要   | `WebInputPublicNavigationNodeInspectInfo` | `data` 为 [`JSFixedFastJsonNavigationNodeInspectResult`](#response-jsfixedfastjsonnavigationnodeinspectresult)。       |

### 导航节点条目

| 方法 | 路径                                                                                                                       | 身份认证 | 请求          | 成功数据                                                                                                                                   |
|------|----------------------------------------------------------------------------------------------------------------------------|----------|---------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/exists`                                                            | 需要     | 路径/查询参数 | `data` 为布尔值。                                                                                                                          |
| GET  | `/api/v1/settingrepo/navigation-node-item/{nodeId}/{id}`                                                                   | 需要     | 路径/查询参数 | `data` 为 [`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。                                                           |
| GET  | `/api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/name-like`                                              | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。                       |
| GET  | `/api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/root/`                                                  | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。                       |
| GET  | `/api/v1/settingrepo/navigation-node-item/{parentId}/child`<br>`/api/v1/settingrepo/navigation-node-item//child`           | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。                       |
| GET  | `/api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/path-from-root`                                                    | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。                       |
| GET  | `/api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/disp`                                                              | 需要     | 路径/查询参数 | `data` 为 [`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。                                     |
| GET  | `/api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/name-like/disp`                                         | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。 |
| GET  | `/api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/root/disp`                                              | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。 |
| GET  | `/api/v1/settingrepo/navigation-node-item/{parentId}/child/disp`<br>`/api/v1/settingrepo/navigation-node-item//child/disp` | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。 |
| GET  | `/api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/path-from-root/disp`                                               | 需要     | 路径/查询参数 | `data` 为 [PagedData](#pageddata)，其中元素为 [`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。 |

### 重置

| 方法 | 路径                               | 身份认证 | 请求 | 成功数据           |
|------|------------------------------------|----------|------|--------------------|
| POST | `/api/v1/settingrepo/reset-format` | 需要     | 无   | `data` 为 `null`。 |

### 配置类别

| 方法   | 路径                                               | 身份认证 | 请求                      | 成功数据                                                                                                       |
|--------|----------------------------------------------------|----------|---------------------------|----------------------------------------------------------------------------------------------------------------|
| GET    | `/api/v1/settingrepo/setting-category/{id}/exists` | 需要     | 路径/查询参数             | `data` 为布尔值。                                                                                              |
| GET    | `/api/v1/settingrepo/setting-category/{id}`        | 需要     | 路径/查询参数             | `data` 为 [`FastJsonSettingCategory`](#response-fastjsonsettingcategory)。                                     |
| POST   | `/api/v1/settingrepo/setting-category`             | 需要     | `WebInputSettingCategory` | `data` 为 [`FastJsonStringIdKey`](#response-fastjsonstringidkey)。                                             |
| PATCH  | `/api/v1/settingrepo/setting-category`             | 需要     | `WebInputSettingCategory` | `data` 为 `null`。                                                                                             |
| DELETE | `/api/v1/settingrepo/setting-category/{id}`        | 需要     | 路径/查询参数             | `data` 为 `null`。                                                                                             |
| GET    | `/api/v1/settingrepo/setting-category/all`         | 需要     | 路径/查询参数             | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingCategory`](#response-fastjsonsettingcategory)。 |
| GET    | `/api/v1/settingrepo/setting-category/id-like`     | 需要     | 路径/查询参数             | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingCategory`](#response-fastjsonsettingcategory)。 |

### 配置节点

| 方法 | 路径                                                  | 身份认证 | 请求                                   | 成功数据                                                                                               |
|------|-------------------------------------------------------|----------|----------------------------------------|--------------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/setting-node/{id}/exists`        | 需要     | 路径/查询参数                          | `data` 为布尔值。                                                                                      |
| GET  | `/api/v1/settingrepo/setting-node/{id}`               | 需要     | 路径/查询参数                          | `data` 为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。                                     |
| GET  | `/api/v1/settingrepo/setting-node/all`                | 需要     | 路径/查询参数                          | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。 |
| GET  | `/api/v1/settingrepo/setting-node/id-like`            | 需要     | 路径/查询参数                          | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。 |
| GET  | `/api/v1/settingrepo/setting-node/reachable`          | 需要     | 路径/查询参数                          | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。 |
| GET  | `/api/v1/settingrepo/setting-node/id-like-reachable`  | 需要     | 路径/查询参数                          | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。 |
| POST | `/api/v1/settingrepo/setting-node/inspect`            | 需要     | `WebInputSettingNodeInspectInfo`       | `data` 为 [`FastJsonSettingNodeInspectResult`](#response-fastjsonsettingnodeinspectresult)。           |
| POST | `/api/v1/settingrepo/setting-node/init`               | 需要     | `WebInputSettingNodeInitInfo`          | `data` 为 `null`。                                                                                     |
| POST | `/api/v1/settingrepo/setting-node/remove`             | 需要     | `WebInputSettingNodeRemoveInfo`        | `data` 为 `null`。                                                                                     |
| POST | `/api/v1/settingrepo/setting-node/inspect-for-public` | 不需要   | `WebInputPublicSettingNodeInspectInfo` | `data` 为 [`FastJsonSettingNodeInspectResult`](#response-fastjsonsettingnodeinspectresult)。           |

### 文本节点

| 方法 | 路径                                               | 身份认证 | 请求                                | 成功数据                                                                                         |
|------|----------------------------------------------------|----------|-------------------------------------|--------------------------------------------------------------------------------------------------|
| GET  | `/api/v1/settingrepo/text-node/{id}/exists`        | 需要     | 路径/查询参数                       | `data` 为布尔值。                                                                                |
| GET  | `/api/v1/settingrepo/text-node/{id}`               | 需要     | 路径/查询参数                       | `data` 为 [`FastJsonTextNode`](#response-fastjsontextnode)。                                     |
| GET  | `/api/v1/settingrepo/text-node/all`                | 需要     | 路径/查询参数                       | `data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonTextNode`](#response-fastjsontextnode)。 |
| POST | `/api/v1/settingrepo/text-node/inspect`            | 需要     | `WebInputTextNodeInspectInfo`       | `data` 为 [`FastJsonTextNodeInspectResult`](#response-fastjsontextnodeinspectresult)。           |
| POST | `/api/v1/settingrepo/text-node/put`                | 需要     | `WebInputTextNodePutInfo`           | `data` 为 `null`。                                                                               |
| POST | `/api/v1/settingrepo/text-node/inspect-for-public` | 不需要   | `WebInputPublicTextNodeInspectInfo` | `data` 为 [`FastJsonTextNodeInspectResult`](#response-fastjsontextnodeinspectresult)。           |

---

## 接口详情

## 文件列表节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/file-list-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/file-list-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonFileListNode`](#response-fastjsonfilelistnode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/file-list-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFileListNode`](#response-fastjsonfilelistnode)。

### 获取条目数量

获取条目数量。

```http
POST /api/v1/settingrepo/file-list-node/size
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.size`。

请求体：[`WebInputFileListNodeSizeInfo`](#request-webinputfilelistnodesizeinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonFileListNodeSizeResult`](#response-fastjsonfilelistnodesizeresult)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/file-list-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.inspect`。

请求体：[`WebInputFileListNodeInspectInfo`](#request-webinputfilelistnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonFileListNodeInspectResult`](#response-fastjsonfilelistnodeinspectresult)。

### 下载文件（兼容接口）

下载文件（兼容接口）。

```http
POST /api/v1/settingrepo/file-list-node/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.download_file`。

兼容性：该 POST 下载接口已由源码标记为废弃，调用方应改用同路径的 GET 接口。

请求体：[`WebInputFileListNodeFileDownloadInfo`](#request-webinputfilelistnodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载文件

下载文件。

```http
GET /api/v1/settingrepo/file-list-node/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.download_file`。

查询参数：`download-info`，必填；内容为 [
`WebInputFileListNodeFileDownloadInfo`](#request-webinputfilelistnodefiledownloadinfo) JSON 的 UTF-8 标准 Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 申请文件流下载凭证

申请文件流下载凭证。

```http
POST /api/v1/settingrepo/file-list-node/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.request_file_stream_voucher`。

请求体：[`WebInputFileListNodeFileDownloadInfo`](#request-webinputfilelistnodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

### 使用凭证下载文件流

使用凭证下载文件流。

```http
GET /api/v1/settingrepo/file-list-node/download-file-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明          |
|--------------|------|------|---------------|
| `voucher-id` | 整数 | 是   | 文件流凭证 ID |

请求体：无。

成功响应：原始文件流（`application/octet-stream`），不使用统一响应外层。

该接口直接写入 `HttpServletResponse`；服务异常会映射后抛出，不返回普通 `FastJsonResponseData`。

### 上传文件

上传文件。

```http
POST /api/v1/settingrepo/file-list-node/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.upload_file`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 否   | 插入位置；空值会传为 null               |

成功响应：`data` 为 `null`。

### 流式上传文件

流式上传文件。

```http
POST /api/v1/settingrepo/file-list-node/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.upload_file_stream`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 否   | 插入位置；空值会传为 null               |

成功响应：`data` 为 `null`。

### 更新文件

更新文件。

```http
POST /api/v1/settingrepo/file-list-node/update
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.update_file`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 是   | 待更新条目的索引                        |

成功响应：`data` 为 `null`。

### 流式更新文件

流式更新文件。

```http
POST /api/v1/settingrepo/file-list-node/update-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.update_file_stream`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 是   | 待更新条目的索引                        |

成功响应：`data` 为 `null`。

### 调整条目顺序

调整条目顺序。

```http
POST /api/v1/settingrepo/file-list-node/change-order
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.change_order`。

请求体：[`WebInputFileListNodeChangeOrderInfo`](#request-webinputfilelistnodechangeorderinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "old_index": 0,
  "neo_index": 1
}
```

成功响应：`data` 为 `null`。

### 删除条目

删除条目。

```http
POST /api/v1/settingrepo/file-list-node/remove
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_list_node.remove`。

请求体：[`WebInputFileListNodeRemoveInfo`](#request-webinputfilelistnoderemoveinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：`data` 为 `null`。

### 公开获取条目数量

公开获取条目数量。

```http
POST /api/v1/settingrepo/file-list-node/size-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicFileListNodeSizeInfo`](#request-webinputpublicfilelistnodesizeinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonFileListNodeSizeResult`](#response-fastjsonfilelistnodesizeresult)。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/file-list-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicFileListNodeInspectInfo`](#request-webinputpublicfilelistnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonFileListNodeInspectResult`](#response-fastjsonfilelistnodeinspectresult)。

### 公开下载文件

公开下载文件。

```http
GET /api/v1/settingrepo/file-list-node/download-file-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：`download-info`，必填；内容为 [
`WebInputPublicFileListNodeFileDownloadInfo`](#request-webinputpublicfilelistnodefiledownloadinfo) JSON 的 UTF-8 标准
Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 公开申请文件流下载凭证

公开申请文件流下载凭证。

```http
POST /api/v1/settingrepo/file-list-node/request-file-stream-voucher-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicFileListNodeFileDownloadInfo`](#request-webinputpublicfilelistnodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

## 文件节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/file-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/file-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonFileNode`](#response-fastjsonfilenode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/file-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFileNode`](#response-fastjsonfilenode)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/file-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.inspect`。

请求体：[`WebInputFileNodeInspectInfo`](#request-webinputfilenodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonFileNodeInspectResult`](#response-fastjsonfilenodeinspectresult)。

### 下载文件

下载文件。

```http
GET /api/v1/settingrepo/file-node/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.download_file`。

查询参数：`download-info`，必填；内容为 [`WebInputFileNodeFileDownloadInfo`](#request-webinputfilenodefiledownloadinfo)
JSON 的 UTF-8 标准 Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 申请文件流下载凭证

申请文件流下载凭证。

```http
POST /api/v1/settingrepo/file-node/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.request_file_stream_voucher`。

请求体：[`WebInputFileNodeFileDownloadInfo`](#request-webinputfilenodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

### 使用凭证下载文件流

使用凭证下载文件流。

```http
GET /api/v1/settingrepo/file-node/download-file-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明          |
|--------------|------|------|---------------|
| `voucher-id` | 整数 | 是   | 文件流凭证 ID |

请求体：无。

成功响应：原始文件流（`application/octet-stream`），不使用统一响应外层。

该接口直接写入 `HttpServletResponse`；服务异常会映射后抛出，不返回普通 `FastJsonResponseData`。

### 上传文件

上传文件。

```http
POST /api/v1/settingrepo/file-node/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.upload_file`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |

成功响应：`data` 为 `null`。

### 流式上传文件

流式上传文件。

```http
POST /api/v1/settingrepo/file-node/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.file_node.upload_file_stream`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |

成功响应：`data` 为 `null`。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/file-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicFileNodeInspectInfo`](#request-webinputpublicfilenodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonFileNodeInspectResult`](#response-fastjsonfilenodeinspectresult)。

### 公开下载文件

公开下载文件。

```http
GET /api/v1/settingrepo/file-node/download-file-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：`download-info`，必填；内容为 [
`WebInputPublicFileNodeFileDownloadInfo`](#request-webinputpublicfilenodefiledownloadinfo) JSON 的 UTF-8 标准 Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 公开申请文件流下载凭证

公开申请文件流下载凭证。

```http
POST /api/v1/settingrepo/file-node/request-file-stream-voucher-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicFileNodeFileDownloadInfo`](#request-webinputpublicfilenodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

## 格式化器支持

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/formatter-support/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.formatter_support.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/formatter-support/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.formatter_support.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonFormatterSupport`](#response-fastjsonformattersupport)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/formatter-support/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.formatter_support.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFormatterSupport`](#response-fastjsonformattersupport)。

### 按 ID 模糊查询

按 ID 模糊查询。

```http
GET /api/v1/settingrepo/formatter-support/id-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.formatter_support.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明         |
|-----------|--------|------|--------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式 |
| `page`    | 整数   | 是   | 页码         |
| `rows`    | 整数   | 是   | 每页条数     |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonFormatterSupport`](#response-fastjsonformattersupport)。

## 国际化节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/iahn-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/iahn-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonIahnNode`](#response-fastjsoniahnnode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/iahn-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonIahnNode`](#response-fastjsoniahnnode)。

### 查看国际化消息

查看国际化消息。

```http
POST /api/v1/settingrepo/iahn-node/inspect-message
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.inspect_message`。

请求体：[`WebInputIahnNodeMessageInspectInfo`](#request-webinputiahnnodemessageinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "mek_id": "welcome"
}
```

成功响应：`data` 为 [`FastJsonIahnNodeMessageInspectResult`](#response-fastjsoniahnnodemessageinspectresult)。

### 按地区批量查看国际化消息

按地区批量查看国际化消息。

```http
POST /api/v1/settingrepo/iahn-node/batch-inspect-message-by-locale
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.batch_inspect_message_by_locale`。

请求体：[`WebInputIahnNodeMessageInspectByLocaleInfo`](#request-webinputiahnnodemessageinspectbylocaleinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": ""
}
```

成功响应：`data` 为 [
`FastJsonIahnNodeMessageInspectByLocaleResult`](#response-fastjsoniahnnodemessageinspectbylocaleresult)。

### 查看地区列表

查看地区列表。

```http
POST /api/v1/settingrepo/iahn-node/inspect-locale-list
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.inspect_locale_list`。

请求体：[`WebInputIahnNodeLocaleListInspectInfo`](#request-webinputiahnnodelocalelistinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonIahnNodeLocaleListInspectResult`](#response-fastjsoniahnnodelocalelistinspectresult)。

### 查看消息键列表

查看消息键列表。

```http
POST /api/v1/settingrepo/iahn-node/inspect-mek-list
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.inspect_mek_list`。

请求体：[`WebInputIahnNodeMekListInspectInfo`](#request-webinputiahnnodemeklistinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonIahnNodeMekListInspectResult`](#response-fastjsoniahnnodemeklistinspectresult)。

### 查看消息表

查看消息表。

```http
POST /api/v1/settingrepo/iahn-node/inspect-message-table
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.inspect_message_table`。

请求体：[`WebInputIahnNodeMessageTableInspectInfo`](#request-webinputiahnnodemessagetableinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonIahnNodeMessageTableInspectResult`](#response-fastjsoniahnnodemessagetableinspectresult)。

### 写入地区

写入地区。

```http
POST /api/v1/settingrepo/iahn-node/put-locale
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.put_locale`。

请求体：[`WebInputIahnNodeLocalePutInfo`](#request-webinputiahnnodelocaleputinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "label": "示例标签",
  "remark": ""
}
```

成功响应：`data` 为 `null`。

### 移除地区

移除地区。

```http
POST /api/v1/settingrepo/iahn-node/remove-locale
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.remove_locale`。

请求体：[`WebInputIahnNodeLocaleRemoveInfo`](#request-webinputiahnnodelocaleremoveinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": ""
}
```

成功响应：`data` 为 `null`。

### 写入消息键

写入消息键。

```http
POST /api/v1/settingrepo/iahn-node/put-mek
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.put_mek`。

请求体：[`WebInputIahnNodeMekPutInfo`](#request-webinputiahnnodemekputinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "mek_id": "welcome",
  "label": "示例标签",
  "default_message": "默认消息",
  "remark": ""
}
```

成功响应：`data` 为 `null`。

### 移除消息键

移除消息键。

```http
POST /api/v1/settingrepo/iahn-node/remove-mek
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.remove_mek`。

请求体：[`WebInputIahnNodeMekRemoveInfo`](#request-webinputiahnnodemekremoveinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "mek_id": "welcome"
}
```

成功响应：`data` 为 `null`。

### 写入或更新消息

写入或更新消息。

```http
POST /api/v1/settingrepo/iahn-node/upsert-message
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.upsert_message`。

请求体：[`WebInputIahnNodeMessageUpsertInfo`](#request-webinputiahnnodemessageupsertinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "mek_id": "welcome",
  "message": "示例消息"
}
```

成功响应：`data` 为 `null`。

### 按地区批量写入或更新消息

按地区批量写入或更新消息。

```http
POST /api/v1/settingrepo/iahn-node/batch-upsert-message-by-locale
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.batch_upsert_message_by_locale`。

请求体：[`WebInputIahnNodeMessageUpsertByLocaleInfo`](#request-webinputiahnnodemessageupsertbylocaleinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "items": [
    {
      "mek_id": "welcome",
      "message": "示例消息"
    }
  ]
}
```

成功响应：`data` 为 `null`。

### 按消息键批量写入或更新消息

按消息键批量写入或更新消息。

```http
POST /api/v1/settingrepo/iahn-node/batch-upsert-message-by-mek
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.iahn_node.batch_upsert_message_by_mek`。

请求体：[`WebInputIahnNodeMessageUpsertByMekInfo`](#request-webinputiahnnodemessageupsertbymekinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "mek_id": "welcome",
  "items": [
    {
      "language": "zh",
      "country": "CN",
      "variant": "",
      "message": "示例消息"
    }
  ]
}
```

成功响应：`data` 为 `null`。

### 公开查看国际化消息

公开查看国际化消息。

```http
POST /api/v1/settingrepo/iahn-node/inspect-message-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`PublicIahnNodeMessageInspectInfo`](#request-publiciahnnodemessageinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "mekId": "welcome"
}
```

成功响应：`data` 为 [`FastJsonIahnNodeMessageInspectResult`](#response-fastjsoniahnnodemessageinspectresult)。

### 公开按地区批量查看国际化消息

公开按地区批量查看国际化消息。

```http
POST /api/v1/settingrepo/iahn-node/batch-inspect-message-by-locale-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`PublicIahnNodeMessageInspectByLocaleInfo`](#request-publiciahnnodemessageinspectbylocaleinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": ""
}
```

成功响应：`data` 为 [
`FastJsonIahnNodeMessageInspectByLocaleResult`](#response-fastjsoniahnnodemessageinspectbylocaleresult)。

### 公开查看地区列表

公开查看地区列表。

```http
POST /api/v1/settingrepo/iahn-node/inspect-locale-list-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`PublicIahnNodeLocaleListInspectInfo`](#request-publiciahnnodelocalelistinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonIahnNodeLocaleListInspectResult`](#response-fastjsoniahnnodelocalelistinspectresult)。

### 公开查看消息键列表

公开查看消息键列表。

```http
POST /api/v1/settingrepo/iahn-node/inspect-mek-list-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`PublicIahnNodeMekListInspectInfo`](#request-publiciahnnodemeklistinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonIahnNodeMekListInspectResult`](#response-fastjsoniahnnodemeklistinspectresult)。

### 公开查看消息表

公开查看消息表。

```http
POST /api/v1/settingrepo/iahn-node/inspect-message-table-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`PublicIahnNodeMessageTableInspectInfo`](#request-publiciahnnodemessagetableinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonIahnNodeMessageTableInspectResult`](#response-fastjsoniahnnodemessagetableinspectresult)。

## 图片列表节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/image-list-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/image-list-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonImageListNode`](#response-fastjsonimagelistnode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/image-list-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImageListNode`](#response-fastjsonimagelistnode)。

### 获取条目数量

获取条目数量。

```http
POST /api/v1/settingrepo/image-list-node/size
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.size`。

请求体：[`WebInputImageListNodeSizeInfo`](#request-webinputimagelistnodesizeinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonImageListNodeSizeResult`](#response-fastjsonimagelistnodesizeresult)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/image-list-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.inspect`。

请求体：[`WebInputImageListNodeInspectInfo`](#request-webinputimagelistnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonImageListNodeInspectResult`](#response-fastjsonimagelistnodeinspectresult)。

### 下载文件（兼容接口）

下载文件（兼容接口）。

```http
POST /api/v1/settingrepo/image-list-node/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.download_file`。

兼容性：该 POST 下载接口已由源码标记为废弃，调用方应改用同路径的 GET 接口。

请求体：[`WebInputImageListNodeFileDownloadInfo`](#request-webinputimagelistnodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载文件

下载文件。

```http
GET /api/v1/settingrepo/image-list-node/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.download_file`。

查询参数：`download-info`，必填；内容为 [
`WebInputImageListNodeFileDownloadInfo`](#request-webinputimagelistnodefiledownloadinfo) JSON 的 UTF-8 标准 Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 申请文件流下载凭证

申请文件流下载凭证。

```http
POST /api/v1/settingrepo/image-list-node/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.request_file_stream_voucher`。

请求体：[`WebInputImageListNodeFileDownloadInfo`](#request-webinputimagelistnodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

### 使用凭证下载文件流

使用凭证下载文件流。

```http
GET /api/v1/settingrepo/image-list-node/download-file-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明          |
|--------------|------|------|---------------|
| `voucher-id` | 整数 | 是   | 文件流凭证 ID |

请求体：无。

成功响应：原始文件流（`application/octet-stream`），不使用统一响应外层。

该接口直接写入 `HttpServletResponse`；服务异常会映射后抛出，不返回普通 `FastJsonResponseData`。

### 下载缩略图

下载缩略图。

```http
GET /api/v1/settingrepo/image-list-node/download-thumbnail
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.download_thumbnail`。

查询参数：`download-info`，必填；内容为 [
`WebInputImageListNodeThumbnailDownloadInfo`](#request-webinputimagelistnodethumbnaildownloadinfo) JSON 的 UTF-8 标准
Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 上传文件

上传文件。

```http
POST /api/v1/settingrepo/image-list-node/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.upload_file`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 否   | 插入位置；空值会传为 null               |

成功响应：`data` 为 `null`。

### 流式上传文件

流式上传文件。

```http
POST /api/v1/settingrepo/image-list-node/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.upload_file_stream`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 否   | 插入位置；空值会传为 null               |

成功响应：`data` 为 `null`。

### 更新文件

更新文件。

```http
POST /api/v1/settingrepo/image-list-node/update
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.update_file`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 是   | 待更新条目的索引                        |

成功响应：`data` 为 `null`。

### 流式更新文件

流式更新文件。

```http
POST /api/v1/settingrepo/image-list-node/update-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.update_file_stream`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |
| `index`    | 整数   | 是   | 待更新条目的索引                        |

成功响应：`data` 为 `null`。

### 调整条目顺序

调整条目顺序。

```http
POST /api/v1/settingrepo/image-list-node/change-order
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.change_order`。

请求体：[`WebInputImageListNodeChangeOrderInfo`](#request-webinputimagelistnodechangeorderinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "old_index": 0,
  "neo_index": 1
}
```

成功响应：`data` 为 `null`。

### 删除条目

删除条目。

```http
POST /api/v1/settingrepo/image-list-node/remove
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_list_node.remove`。

请求体：[`WebInputImageListNodeRemoveInfo`](#request-webinputimagelistnoderemoveinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：`data` 为 `null`。

### 公开获取条目数量

公开获取条目数量。

```http
POST /api/v1/settingrepo/image-list-node/size-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicImageListNodeSizeInfo`](#request-webinputpublicimagelistnodesizeinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonImageListNodeSizeResult`](#response-fastjsonimagelistnodesizeresult)。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/image-list-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicImageListNodeInspectInfo`](#request-webinputpublicimagelistnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonImageListNodeInspectResult`](#response-fastjsonimagelistnodeinspectresult)。

### 公开下载文件

公开下载文件。

```http
GET /api/v1/settingrepo/image-list-node/download-file-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：`download-info`，必填；内容为 [
`WebInputPublicImageListNodeFileDownloadInfo`](#request-webinputpublicimagelistnodefiledownloadinfo) JSON 的 UTF-8 标准
Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 公开申请文件流下载凭证

公开申请文件流下载凭证。

```http
POST /api/v1/settingrepo/image-list-node/request-file-stream-voucher-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicImageListNodeFileDownloadInfo`](#request-webinputpublicimagelistnodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

### 公开下载缩略图

公开下载缩略图。

```http
GET /api/v1/settingrepo/image-list-node/download-thumbnail-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：`download-info`，必填；内容为 [
`WebInputPublicImageListNodeThumbnailDownloadInfo`](#request-webinputpublicimagelistnodethumbnaildownloadinfo) JSON 的
UTF-8 标准 Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

## 图片节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/image-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/image-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonImageNode`](#response-fastjsonimagenode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/image-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonImageNode`](#response-fastjsonimagenode)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/image-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.inspect`。

请求体：[`WebInputImageNodeInspectInfo`](#request-webinputimagenodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonImageNodeInspectResult`](#response-fastjsonimagenodeinspectresult)。

### 下载文件（兼容接口）

下载文件（兼容接口）。

```http
POST /api/v1/settingrepo/image-node/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.download_file`。

兼容性：该 POST 下载接口已由源码标记为废弃，调用方应改用同路径的 GET 接口。

请求体：[`WebInputImageNodeFileDownloadInfo`](#request-webinputimagenodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 下载文件

下载文件。

```http
GET /api/v1/settingrepo/image-node/download-file
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.download_file`。

查询参数：`download-info`，必填；内容为 [`WebInputImageNodeFileDownloadInfo`](#request-webinputimagenodefiledownloadinfo)
JSON 的 UTF-8 标准 Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 申请文件流下载凭证

申请文件流下载凭证。

```http
POST /api/v1/settingrepo/image-node/request-file-stream-voucher
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.request_file_stream_voucher`。

请求体：[`WebInputImageNodeFileDownloadInfo`](#request-webinputimagenodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

### 使用凭证下载文件流

使用凭证下载文件流。

```http
GET /api/v1/settingrepo/image-node/download-file-by-voucher
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：

| 参数         | 类型 | 必填 | 说明          |
|--------------|------|------|---------------|
| `voucher-id` | 整数 | 是   | 文件流凭证 ID |

请求体：无。

成功响应：原始文件流（`application/octet-stream`），不使用统一响应外层。

该接口直接写入 `HttpServletResponse`；服务异常会映射后抛出，不返回普通 `FastJsonResponseData`。

### 下载缩略图

下载缩略图。

```http
GET /api/v1/settingrepo/image-node/download-thumbnail
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.download_thumbnail`。

查询参数：`download-info`，必填；内容为 [
`WebInputImageNodeThumbnailDownloadInfo`](#request-webinputimagenodethumbnaildownloadinfo) JSON 的 UTF-8 标准 Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 上传文件

上传文件。

```http
POST /api/v1/settingrepo/image-node/upload
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.upload_file`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |

成功响应：`data` 为 `null`。

### 流式上传文件

流式上传文件。

```http
POST /api/v1/settingrepo/image-node/upload-stream
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.image_node.upload_file_stream`。

请求体：`multipart/form-data`。

| 字段       | 类型   | 必填 | 说明                                    |
|------------|--------|------|-----------------------------------------|
| `file`     | 文件   | 是   | 上传文件；原始文件名随文件提交          |
| `category` | 字符串 | 是   | 配置类别                                |
| `args`     | 字符串 | 是   | JSON 字符串数组文本，例如 `["default"]` |

成功响应：`data` 为 `null`。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/image-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicImageNodeInspectInfo`](#request-webinputpublicimagenodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonImageNodeInspectResult`](#response-fastjsonimagenodeinspectresult)。

### 公开下载文件

公开下载文件。

```http
GET /api/v1/settingrepo/image-node/download-file-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：`download-info`，必填；内容为 [
`WebInputPublicImageNodeFileDownloadInfo`](#request-webinputpublicimagenodefiledownloadinfo) JSON 的 UTF-8 标准 Base64
编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

### 公开申请文件流下载凭证

公开申请文件流下载凭证。

```http
POST /api/v1/settingrepo/image-node/request-file-stream-voucher-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicImageNodeFileDownloadInfo`](#request-webinputpublicimagenodefiledownloadinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`JSFixedFastJsonLongIdKey`](#response-jsfixedfastjsonlongidkey)。

### 公开下载缩略图

公开下载缩略图。

```http
GET /api/v1/settingrepo/image-node/download-thumbnail-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

查询参数：`download-info`，必填；内容为 [
`WebInputPublicImageNodeThumbnailDownloadInfo`](#request-webinputpublicimagenodethumbnaildownloadinfo) JSON 的 UTF-8 标准
Base64 编码。

解码后的 JSON 示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：成功时为文件或缩略图字节；失败时响应体为统一错误对象。

异常时控制器仍构造 HTTP 200 响应，响应体改为统一错误对象。

## 键值对节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/kv-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/kv-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonKvNode`](#response-fastjsonkvnode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/kv-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonKvNode`](#response-fastjsonkvnode)。

### 获取条目数量

获取条目数量。

```http
POST /api/v1/settingrepo/kv-node/count
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.count`。

请求体：[`WebInputKvNodeCountInfo`](#request-webinputkvnodecountinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonKvNodeCountResult`](#response-fastjsonkvnodecountresult)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/kv-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.inspect`。

请求体：[`WebInputKvNodeInspectInfo`](#request-webinputkvnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonKvNodeInspectResult`](#response-fastjsonkvnodeinspectresult)。

### 查看指定条目

查看指定条目。

```http
POST /api/v1/settingrepo/kv-node/inspect-item
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.inspect_item`。

请求体：[`WebInputKvNodeItemInspectInfo`](#request-webinputkvnodeiteminspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item"
}
```

成功响应：`data` 为 [`FastJsonKvNodeItemInspectResult`](#response-fastjsonkvnodeiteminspectresult)。

### 写入条目

写入条目。

```http
POST /api/v1/settingrepo/kv-node/put-item
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.put_item`。

请求体：[`WebInputKvNodeItemPutInfo`](#request-webinputkvnodeitemputinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item",
  "value": "示例值"
}
```

成功响应：`data` 为 `null`。

### 移除条目

移除条目。

```http
POST /api/v1/settingrepo/kv-node/remove-item
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.remove_item`。

请求体：[`WebInputKvNodeItemRemoveInfo`](#request-webinputkvnodeitemremoveinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item"
}
```

成功响应：`data` 为 `null`。

### 清空条目

清空条目。

```http
POST /api/v1/settingrepo/kv-node/clear
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node.clear`。

请求体：[`WebInputKvNodeClearInfo`](#request-webinputkvnodeclearinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 `null`。

### 公开获取条目数量

公开获取条目数量。

```http
POST /api/v1/settingrepo/kv-node/count-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicKvNodeCountInfo`](#request-webinputpublickvnodecountinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonKvNodeCountResult`](#response-fastjsonkvnodecountresult)。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/kv-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicKvNodeInspectInfo`](#request-webinputpublickvnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonKvNodeInspectResult`](#response-fastjsonkvnodeinspectresult)。

### 公开查看指定条目

公开查看指定条目。

```http
POST /api/v1/settingrepo/kv-node/inspect-item-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicKvNodeItemInspectInfo`](#request-webinputpublickvnodeiteminspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item"
}
```

成功响应：`data` 为 [`FastJsonKvNodeItemInspectResult`](#response-fastjsonkvnodeiteminspectresult)。

## 键值对节点条目

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/kv-node-item/{nodeId}/{itemStringId}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node_item.exists`。

路径参数：

| 参数           | 类型   | 必填 | 说明          |
|----------------|--------|------|---------------|
| `nodeId`       | 字符串 | 是   | 节点 ID       |
| `itemStringId` | 字符串 | 是   | 条目字符串 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/kv-node-item/{nodeId}/{itemStringId}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node_item.get`。

路径参数：

| 参数           | 类型   | 必填 | 说明          |
|----------------|--------|------|---------------|
| `nodeId`       | 字符串 | 是   | 节点 ID       |
| `itemStringId` | 字符串 | 是   | 条目字符串 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonKvNodeItem`](#response-fastjsonkvnodeitem)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/kv-node-item/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node_item.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonKvNodeItem`](#response-fastjsonkvnodeitem)。

### 分页获取节点条目

分页获取节点条目。

```http
GET /api/v1/settingrepo/kv-node/{nodeId}/kv-node-item
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.kv_node_item.child_for_node`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonKvNodeItem`](#response-fastjsonkvnodeitem)。

## 导航节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/navigation-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/navigation-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonNavigationNode`](#response-fastjsonnavigationnode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/navigation-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonNavigationNode`](#response-fastjsonnavigationnode)。

### 获取条目数量

获取条目数量。

```http
POST /api/v1/settingrepo/navigation-node/size
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.size`。

请求体：[`WebInputNavigationNodeSizeInfo`](#request-webinputnavigationnodesizeinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonNavigationNodeSizeResult`](#response-fastjsonnavigationnodesizeresult)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/navigation-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.inspect`。

请求体：[`WebInputNavigationNodeInspectInfo`](#request-webinputnavigationnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`JSFixedFastJsonNavigationNodeInspectResult`](#response-jsfixedfastjsonnavigationnodeinspectresult)。

### 更新导航节点

更新导航节点。

```http
POST /api/v1/settingrepo/navigation-node/update-node
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.update_node`。

请求体：[`WebInputNavigationNodeUpdateInfo`](#request-webinputnavigationnodeupdateinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "content": "示例内容"
}
```

成功响应：`data` 为 `null`。

### 插入导航条目

插入导航条目。

```http
POST /api/v1/settingrepo/navigation-node/insert-item
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.insert_item`。

请求体：[`WebInputNavigationNodeItemInsertInfo`](#request-webinputnavigationnodeiteminsertinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "parent_item_key": {
    "long_id": 1
  },
  "index": 0,
  "name": "示例条目",
  "content": "示例内容",
  "remark": ""
}
```

成功响应：`data` 为 [
`JSFixedFastJsonNavigationNodeItemInsertResult`](#response-jsfixedfastjsonnavigationnodeiteminsertresult)。

### 更新导航条目

更新导航条目。

```http
POST /api/v1/settingrepo/navigation-node/update-item
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.update_item`。

请求体：[`WebInputNavigationNodeItemUpdateInfo`](#request-webinputnavigationnodeitemupdateinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_key": {
    "long_id": 1
  },
  "parent_item_key": {
    "long_id": 1
  },
  "index": 0,
  "name": "示例条目",
  "content": "示例内容",
  "remark": ""
}
```

成功响应：`data` 为 `null`。

### 移除条目

移除条目。

```http
POST /api/v1/settingrepo/navigation-node/remove-item
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.remove_item`。

请求体：[`WebInputNavigationNodeItemRemoveInfo`](#request-webinputnavigationnodeitemremoveinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_key": {
    "long_id": 1
  }
}
```

成功响应：`data` 为 `null`。

### 格式化子条目索引

格式化子条目索引。

```http
POST /api/v1/settingrepo/navigation-node/format-index
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node.format_index`。

请求体：[`WebInputNavigationNodeFormatIndexInfo`](#request-webinputnavigationnodeformatindexinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "parent_item_key": {
    "long_id": 1
  }
}
```

成功响应：`data` 为 `null`。

### 公开获取条目数量

公开获取条目数量。

```http
POST /api/v1/settingrepo/navigation-node/size-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicNavigationNodeSizeInfo`](#request-webinputpublicnavigationnodesizeinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonNavigationNodeSizeResult`](#response-fastjsonnavigationnodesizeresult)。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/navigation-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicNavigationNodeInspectInfo`](#request-webinputpublicnavigationnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`JSFixedFastJsonNavigationNodeInspectResult`](#response-jsfixedfastjsonnavigationnodeinspectresult)。

## 导航节点条目

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.exists`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |
| `id`     | 整数   | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/navigation-node-item/{nodeId}/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.get`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |
| `id`     | 整数   | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。

### 按名称模糊查询节点条目

按名称模糊查询节点条目。

```http
GET /api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/name-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_name_like`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |

查询参数：

| 参数      | 类型   | 必填 | 说明         |
|-----------|--------|------|--------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式 |
| `page`    | 整数   | 是   | 页码         |
| `rows`    | 整数   | 是   | 每页条数     |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。

### 分页获取根条目

分页获取根条目。

```http
GET /api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/root/
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_child_for_root`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。

### 分页获取子条目

分页获取子条目。

```http
GET /api/v1/settingrepo/navigation-node-item/{parentId}/child
GET /api/v1/settingrepo/navigation-node-item//child
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.child_for_parent`。

路径参数：

| 参数       | 类型 | 必填 | 说明                              |
|------------|------|------|-----------------------------------|
| `parentId` | 整数 | 否   | 父条目 ID；省略时使用无父条目路由 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。

### 获取从根到指定条目的路径

获取从根到指定条目的路径。

```http
GET /api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/path-from-root
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.path_from_root`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |
| `id`     | 整数   | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`FastJsonNavigationNodeItem`](#response-fastjsonnavigationnodeitem)。

### 获取展示信息

获取展示信息。

```http
GET /api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.get_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |
| `id`     | 整数   | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。

### 按名称模糊查询展示条目

按名称模糊查询展示条目。

```http
GET /api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/name-like/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_name_like_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |

查询参数：

| 参数      | 类型   | 必填 | 说明         |
|-----------|--------|------|--------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式 |
| `page`    | 整数   | 是   | 页码         |
| `rows`    | 整数   | 是   | 每页条数     |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。

### 分页获取根展示条目

分页获取根展示条目。

```http
GET /api/v1/settingrepo/navigation-node/{nodeId}/navigation-node-item/root/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.child_for_node_child_for_root_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。

### 分页获取子展示条目

分页获取子展示条目。

```http
GET /api/v1/settingrepo/navigation-node-item/{parentId}/child/disp
GET /api/v1/settingrepo/navigation-node-item//child/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.child_for_parent_disp`。

路径参数：

| 参数       | 类型 | 必填 | 说明                              |
|------------|------|------|-----------------------------------|
| `parentId` | 整数 | 否   | 父条目 ID；省略时使用无父条目路由 |

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。

### 获取展示路径

获取展示路径。

```http
GET /api/v1/settingrepo/navigation-node-item/{nodeId}/{id}/path-from-root/disp
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.navigation_node_item.path_from_root_disp`。

路径参数：

| 参数     | 类型   | 必填 | 说明    |
|----------|--------|------|---------|
| `nodeId` | 字符串 | 是   | 节点 ID |
| `id`     | 整数   | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [
`JSFixedFastJsonDispNavigationNodeItem`](#response-jsfixedfastjsondispnavigationnodeitem)。

## 重置

### 重置配置格式

重置配置格式。

```http
POST /api/v1/settingrepo/reset-format
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.reset.reset_format`。

请求体：无。

成功响应：`data` 为 `null`。

## 配置类别

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/setting-category/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_category.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/setting-category/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_category.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonSettingCategory`](#response-fastjsonsettingcategory)。

### 新增配置类别

新增配置类别。

```http
POST /api/v1/settingrepo/setting-category
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_category.insert`。

请求体：[`WebInputSettingCategory`](#request-webinputsettingcategory)。

请求示例：

```json
{
  "key": {
    "string_id": "example"
  },
  "formatter_type": "string",
  "formatter_param": "",
  "remark": ""
}
```

成功响应：`data` 为 [`FastJsonStringIdKey`](#response-fastjsonstringidkey)。

### 更新配置类别

更新配置类别。

```http
PATCH /api/v1/settingrepo/setting-category
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_category.update`。

请求体：[`WebInputSettingCategory`](#request-webinputsettingcategory)。

请求示例：

```json
{
  "key": {
    "string_id": "example"
  },
  "formatter_type": "string",
  "formatter_param": "",
  "remark": ""
}
```

成功响应：`data` 为 `null`。

### 删除配置类别

删除配置类别。

```http
DELETE /api/v1/settingrepo/setting-category/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_category.delete`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 `null`。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/setting-category/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_category.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingCategory`](#response-fastjsonsettingcategory)。

### 按 ID 模糊查询

按 ID 模糊查询。

```http
GET /api/v1/settingrepo/setting-category/id-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_category.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明         |
|-----------|--------|------|--------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式 |
| `page`    | 整数   | 是   | 页码         |
| `rows`    | 整数   | 是   | 每页条数     |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingCategory`](#response-fastjsonsettingcategory)。

## 配置节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/setting-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/setting-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/setting-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。

### 按 ID 模糊查询

按 ID 模糊查询。

```http
GET /api/v1/settingrepo/setting-node/id-like
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.id_like`。

查询参数：

| 参数      | 类型   | 必填 | 说明         |
|-----------|--------|------|--------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式 |
| `page`    | 整数   | 是   | 页码         |
| `rows`    | 整数   | 是   | 每页条数     |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。

### 分页获取可达配置节点

分页获取可达配置节点。

```http
GET /api/v1/settingrepo/setting-node/reachable
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.reachable`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。

### 按 ID 模糊查询可达配置节点

按 ID 模糊查询可达配置节点。

```http
GET /api/v1/settingrepo/setting-node/id-like-reachable
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.id_like_reachable`。

查询参数：

| 参数      | 类型   | 必填 | 说明         |
|-----------|--------|------|--------------|
| `pattern` | 字符串 | 是   | 模糊匹配模式 |
| `page`    | 整数   | 是   | 页码         |
| `rows`    | 整数   | 是   | 每页条数     |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonSettingNode`](#response-fastjsonsettingnode)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/setting-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.inspect`。

请求体：[`WebInputSettingNodeInspectInfo`](#request-webinputsettingnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonSettingNodeInspectResult`](#response-fastjsonsettingnodeinspectresult)。

### 初始化配置节点

初始化配置节点。

```http
POST /api/v1/settingrepo/setting-node/init
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.init`。

请求体：[`WebInputSettingNodeInitInfo`](#request-webinputsettingnodeinitinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "type": 1,
  "remark": ""
}
```

成功响应：`data` 为 `null`。

### 删除条目

删除条目。

```http
POST /api/v1/settingrepo/setting-node/remove
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.setting_node.remove`。

请求体：[`WebInputSettingNodeRemoveInfo`](#request-webinputsettingnoderemoveinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 `null`。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/setting-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicSettingNodeInspectInfo`](#request-webinputpublicsettingnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonSettingNodeInspectResult`](#response-fastjsonsettingnodeinspectresult)。

## 文本节点

### 判断是否存在

判断是否存在。

```http
GET /api/v1/settingrepo/text-node/{id}/exists
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.text_node.exists`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为布尔值。

### 获取详情

获取详情。

```http
GET /api/v1/settingrepo/text-node/{id}
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.text_node.get`。

路径参数：

| 参数 | 类型   | 必填 | 说明    |
|------|--------|------|---------|
| `id` | 字符串 | 是   | 资源 ID |

请求体：无。

成功响应：`data` 为 [`FastJsonTextNode`](#response-fastjsontextnode)。

### 分页获取全部

分页获取全部。

```http
GET /api/v1/settingrepo/text-node/all
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.text_node.all`。

查询参数：

| 参数   | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| `page` | 整数 | 是   | 页码     |
| `rows` | 整数 | 是   | 每页条数 |

请求体：无。

成功响应：`data` 为 [PagedData](#pageddata)，其中元素为 [`FastJsonTextNode`](#response-fastjsontextnode)。

### 查看内容

查看内容。

```http
POST /api/v1/settingrepo/text-node/inspect
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.text_node.inspect`。

请求体：[`WebInputTextNodeInspectInfo`](#request-webinputtextnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonTextNodeInspectResult`](#response-fastjsontextnodeinspectresult)。

### 写入文本

写入文本。

```http
POST /api/v1/settingrepo/text-node/put
```

身份认证：需要。
所需权限：`webapi.controller_permitted.settingrepo.text_node.put`。

请求体：[`WebInputTextNodePutInfo`](#request-webinputtextnodeputinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "value": "示例值"
}
```

成功响应：`data` 为 `null`。

### 公开查看内容

公开查看内容。

```http
POST /api/v1/settingrepo/text-node/inspect-for-public
```

身份认证：不需要。
所需权限：无额外权限标识。

请求体：[`WebInputPublicTextNodeInspectInfo`](#request-webinputpublictextnodeinspectinfo)。

请求示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

成功响应：`data` 为 [`FastJsonTextNodeInspectResult`](#response-fastjsontextnodeinspectresult)。

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

<a id="fastjsonkvnodeitemkey"></a>

#### FastJsonKvNodeItemKey

| 字段             | 类型   | 说明          |
|------------------|--------|---------------|
| `node_string_id` | 字符串 | 节点字符串 ID |
| `item_string_id` | 字符串 | 条目字符串 ID |

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

<a id="request-publiciahnnodelocalelistinspectinfo"></a>

#### PublicIahnNodeLocaleListInspectInfo

| 字段       | 类型       | 必填 | 说明           |
|------------|------------|------|----------------|
| `category` | 字符串     | 否   | 配置类别       |
| `args`     | 字符串数组 | 否   | 格式化参数数组 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-publiciahnnodemeklistinspectinfo"></a>

#### PublicIahnNodeMekListInspectInfo

| 字段       | 类型       | 必填 | 说明           |
|------------|------------|------|----------------|
| `category` | 字符串     | 否   | 配置类别       |
| `args`     | 字符串数组 | 否   | 格式化参数数组 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-publiciahnnodemessageinspectbylocaleinfo"></a>

#### PublicIahnNodeMessageInspectByLocaleInfo

| 字段       | 类型       | 必填 | 说明           |
|------------|------------|------|----------------|
| `category` | 字符串     | 否   | 配置类别       |
| `args`     | 字符串数组 | 否   | 格式化参数数组 |
| `language` | 字符串     | 否   | 语言代码       |
| `country`  | 字符串     | 否   | 国家或地区代码 |
| `variant`  | 字符串     | 否   | 变体代码       |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": ""
}
```

<a id="request-publiciahnnodemessageinspectinfo"></a>

#### PublicIahnNodeMessageInspectInfo

| 字段       | 类型       | 必填 | 说明           |
|------------|------------|------|----------------|
| `category` | 字符串     | 否   | 配置类别       |
| `args`     | 字符串数组 | 否   | 格式化参数数组 |
| `language` | 字符串     | 否   | 语言代码       |
| `country`  | 字符串     | 否   | 国家或地区代码 |
| `variant`  | 字符串     | 否   | 变体代码       |
| `mekId`    | 字符串     | 否   | 消息键 ID      |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "mekId": "welcome"
}
```

<a id="request-publiciahnnodemessagetableinspectinfo"></a>

#### PublicIahnNodeMessageTableInspectInfo

| 字段       | 类型       | 必填 | 说明           |
|------------|------------|------|----------------|
| `category` | 字符串     | 否   | 配置类别       |
| `args`     | 字符串数组 | 否   | 格式化参数数组 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputfilelistnodechangeorderinfo"></a>

#### WebInputFileListNodeChangeOrderInfo

| 字段        | 类型       | 必填 | 说明                     |
|-------------|------------|------|--------------------------|
| `category`  | 字符串     | 是   | 配置类别；不能为空       |
| `args`      | 字符串数组 | 是   | 格式化参数数组           |
| `old_index` | 整数       | 否   | 原索引；必须大于等于 0   |
| `neo_index` | 整数       | 否   | 目标索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "old_index": 0,
  "neo_index": 1
}
```

<a id="request-webinputfilelistnodefiledownloadinfo"></a>

#### WebInputFileListNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputfilelistnodeinspectinfo"></a>

#### WebInputFileListNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputfilelistnoderemoveinfo"></a>

#### WebInputFileListNodeRemoveInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputfilelistnodesizeinfo"></a>

#### WebInputFileListNodeSizeInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputfilenodefiledownloadinfo"></a>

#### WebInputFileNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputfilenodeinspectinfo"></a>

#### WebInputFileNodeInspectInfo

| 字段       | 类型       | 必填 | 说明                             |
|------------|------------|------|----------------------------------|
| `category` | 字符串     | 是   | 配置类别；最大长度 100；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组                   |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputiahnnodelocalelistinspectinfo"></a>

#### WebInputIahnNodeLocaleListInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputiahnnodelocaleputinfo"></a>

#### WebInputIahnNodeLocalePutInfo

| 字段       | 类型       | 必填 | 说明                                                        |
|------------|------------|------|-------------------------------------------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空                                          |
| `args`     | 字符串数组 | 是   | 格式化参数数组                                              |
| `language` | 字符串     | 否   | 语言代码；为空或 2-3 位字母；递归校验嵌套字段               |
| `country`  | 字符串     | 否   | 国家或地区代码；为空、2 位字母或 3 位数字；递归校验嵌套字段 |
| `variant`  | 字符串     | 否   | 变体代码；为空或 5-8 位字母/数字；递归校验嵌套字段          |
| `label`    | 字符串     | 否   | 显示标签；最大长度 100                                      |
| `remark`   | 字符串     | 否   | 备注；最大长度 100                                          |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "label": "示例标签",
  "remark": ""
}
```

<a id="request-webinputiahnnodelocaleremoveinfo"></a>

#### WebInputIahnNodeLocaleRemoveInfo

| 字段       | 类型       | 必填 | 说明                                                        |
|------------|------------|------|-------------------------------------------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空                                          |
| `args`     | 字符串数组 | 是   | 格式化参数数组                                              |
| `language` | 字符串     | 否   | 语言代码；为空或 2-3 位字母；递归校验嵌套字段               |
| `country`  | 字符串     | 否   | 国家或地区代码；为空、2 位字母或 3 位数字；递归校验嵌套字段 |
| `variant`  | 字符串     | 否   | 变体代码；为空或 5-8 位字母/数字；递归校验嵌套字段          |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": ""
}
```

<a id="request-webinputiahnnodemeklistinspectinfo"></a>

#### WebInputIahnNodeMekListInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputiahnnodemekputinfo"></a>

#### WebInputIahnNodeMekPutInfo

| 字段              | 类型       | 必填 | 说明                              |
|-------------------|------------|------|-----------------------------------|
| `category`        | 字符串     | 是   | 配置类别；不能为空                |
| `args`            | 字符串数组 | 是   | 格式化参数数组                    |
| `mek_id`          | 字符串     | 是   | 消息键 ID；最大长度 100；不能为空 |
| `label`           | 字符串     | 否   | 显示标签；最大长度 100            |
| `default_message` | 字符串     | 是   | 默认消息；最大长度 250            |
| `remark`          | 字符串     | 否   | 备注；最大长度 100                |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "mek_id": "welcome",
  "label": "示例标签",
  "default_message": "默认消息",
  "remark": ""
}
```

<a id="request-webinputiahnnodemekremoveinfo"></a>

#### WebInputIahnNodeMekRemoveInfo

| 字段       | 类型       | 必填 | 说明                              |
|------------|------------|------|-----------------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空                |
| `args`     | 字符串数组 | 是   | 格式化参数数组                    |
| `mek_id`   | 字符串     | 是   | 消息键 ID；最大长度 100；不能为空 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "mek_id": "welcome"
}
```

<a id="request-webinputiahnnodemessageinspectbylocaleinfo"></a>

#### WebInputIahnNodeMessageInspectByLocaleInfo

| 字段       | 类型       | 必填 | 说明                                                        |
|------------|------------|------|-------------------------------------------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空                                          |
| `args`     | 字符串数组 | 是   | 格式化参数数组                                              |
| `language` | 字符串     | 否   | 语言代码；为空或 2-3 位字母；递归校验嵌套字段               |
| `country`  | 字符串     | 否   | 国家或地区代码；为空、2 位字母或 3 位数字；递归校验嵌套字段 |
| `variant`  | 字符串     | 否   | 变体代码；为空或 5-8 位字母/数字；递归校验嵌套字段          |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": ""
}
```

<a id="request-webinputiahnnodemessageinspectinfo"></a>

#### WebInputIahnNodeMessageInspectInfo

| 字段       | 类型       | 必填 | 说明                                                        |
|------------|------------|------|-------------------------------------------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空                                          |
| `args`     | 字符串数组 | 是   | 格式化参数数组                                              |
| `language` | 字符串     | 否   | 语言代码；为空或 2-3 位字母；递归校验嵌套字段               |
| `country`  | 字符串     | 否   | 国家或地区代码；为空、2 位字母或 3 位数字；递归校验嵌套字段 |
| `variant`  | 字符串     | 否   | 变体代码；为空或 5-8 位字母/数字；递归校验嵌套字段          |
| `mek_id`   | 字符串     | 是   | 消息键 ID；最大长度 100；不能为空                           |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "mek_id": "welcome"
}
```

<a id="request-webinputiahnnodemessagetableinspectinfo"></a>

#### WebInputIahnNodeMessageTableInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputiahnnodemessageupsertbylocaleinfo"></a>

#### WebInputIahnNodeMessageUpsertByLocaleInfo

| 字段       | 类型                | 必填 | 说明                                                        |
|------------|---------------------|------|-------------------------------------------------------------|
| `category` | 字符串              | 是   | 配置类别；不能为空                                          |
| `args`     | 字符串数组          | 是   | 格式化参数数组                                              |
| `language` | 字符串              | 否   | 语言代码；为空或 2-3 位字母；递归校验嵌套字段               |
| `country`  | 字符串              | 否   | 国家或地区代码；为空、2 位字母或 3 位数字；递归校验嵌套字段 |
| `variant`  | 字符串              | 否   | 变体代码；为空或 5-8 位字母/数字；递归校验嵌套字段          |
| `items`    | `WebInputItem` 数组 | 是   | 条目列表；递归校验嵌套字段                                  |

##### WebInputIahnNodeMessageUpsertByLocaleInfo.WebInputItem

| 字段      | 类型   | 必填 | 说明                              |
|-----------|--------|------|-----------------------------------|
| `mek_id`  | 字符串 | 是   | 消息键 ID；最大长度 100；不能为空 |
| `message` | 字符串 | 是   | 消息内容；最大长度 250            |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "items": [
    {
      "mek_id": "welcome",
      "message": "示例消息"
    }
  ]
}
```

<a id="request-webinputiahnnodemessageupsertbymekinfo"></a>

#### WebInputIahnNodeMessageUpsertByMekInfo

| 字段       | 类型                | 必填 | 说明                              |
|------------|---------------------|------|-----------------------------------|
| `category` | 字符串              | 是   | 配置类别；不能为空                |
| `args`     | 字符串数组          | 是   | 格式化参数数组                    |
| `mek_id`   | 字符串              | 是   | 消息键 ID；最大长度 100；不能为空 |
| `items`    | `WebInputItem` 数组 | 是   | 条目列表；递归校验嵌套字段        |

##### WebInputIahnNodeMessageUpsertByMekInfo.WebInputItem

| 字段       | 类型   | 必填 | 说明                                                        |
|------------|--------|------|-------------------------------------------------------------|
| `language` | 字符串 | 否   | 语言代码；为空或 2-3 位字母；递归校验嵌套字段               |
| `country`  | 字符串 | 否   | 国家或地区代码；为空、2 位字母或 3 位数字；递归校验嵌套字段 |
| `variant`  | 字符串 | 否   | 变体代码；为空或 5-8 位字母/数字；递归校验嵌套字段          |
| `message`  | 字符串 | 是   | 消息内容；最大长度 250                                      |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "mek_id": "welcome",
  "items": [
    {
      "language": "zh",
      "country": "CN",
      "variant": "",
      "message": "示例消息"
    }
  ]
}
```

<a id="request-webinputiahnnodemessageupsertinfo"></a>

#### WebInputIahnNodeMessageUpsertInfo

| 字段       | 类型       | 必填 | 说明                                                        |
|------------|------------|------|-------------------------------------------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空                                          |
| `args`     | 字符串数组 | 是   | 格式化参数数组                                              |
| `language` | 字符串     | 否   | 语言代码；为空或 2-3 位字母；递归校验嵌套字段               |
| `country`  | 字符串     | 否   | 国家或地区代码；为空、2 位字母或 3 位数字；递归校验嵌套字段 |
| `variant`  | 字符串     | 否   | 变体代码；为空或 5-8 位字母/数字；递归校验嵌套字段          |
| `mek_id`   | 字符串     | 是   | 消息键 ID；最大长度 100；不能为空                           |
| `message`  | 字符串     | 是   | 消息内容；最大长度 250                                      |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "language": "zh",
  "country": "CN",
  "variant": "",
  "mek_id": "welcome",
  "message": "示例消息"
}
```

<a id="request-webinputimagelistnodechangeorderinfo"></a>

#### WebInputImageListNodeChangeOrderInfo

| 字段        | 类型       | 必填 | 说明                     |
|-------------|------------|------|--------------------------|
| `category`  | 字符串     | 是   | 配置类别；不能为空       |
| `args`      | 字符串数组 | 是   | 格式化参数数组           |
| `old_index` | 整数       | 否   | 原索引；必须大于等于 0   |
| `neo_index` | 整数       | 否   | 目标索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "old_index": 0,
  "neo_index": 1
}
```

<a id="request-webinputimagelistnodefiledownloadinfo"></a>

#### WebInputImageListNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputimagelistnodeinspectinfo"></a>

#### WebInputImageListNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputimagelistnoderemoveinfo"></a>

#### WebInputImageListNodeRemoveInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputimagelistnodesizeinfo"></a>

#### WebInputImageListNodeSizeInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputimagelistnodethumbnaildownloadinfo"></a>

#### WebInputImageListNodeThumbnailDownloadInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputimagenodefiledownloadinfo"></a>

#### WebInputImageNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputimagenodeinspectinfo"></a>

#### WebInputImageNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputimagenodethumbnaildownloadinfo"></a>

#### WebInputImageNodeThumbnailDownloadInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputkvnodeclearinfo"></a>

#### WebInputKvNodeClearInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputkvnodecountinfo"></a>

#### WebInputKvNodeCountInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputkvnodeinspectinfo"></a>

#### WebInputKvNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputkvnodeiteminspectinfo"></a>

#### WebInputKvNodeItemInspectInfo

| 字段             | 类型       | 必填 | 说明                    |
|------------------|------------|------|-------------------------|
| `category`       | 字符串     | 是   | 配置类别；不能为空      |
| `args`           | 字符串数组 | 是   | 格式化参数数组          |
| `item_string_id` | 字符串     | 是   | 条目字符串 ID；不能为空 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item"
}
```

<a id="request-webinputkvnodeitemputinfo"></a>

#### WebInputKvNodeItemPutInfo

| 字段             | 类型       | 必填 | 说明                                  |
|------------------|------------|------|---------------------------------------|
| `category`       | 字符串     | 是   | 配置类别；不能为空                    |
| `args`           | 字符串数组 | 是   | 格式化参数数组                        |
| `item_string_id` | 字符串     | 是   | 条目字符串 ID；最大长度 100；不能为空 |
| `value`          | 字符串     | 否   | 值                                    |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item",
  "value": "示例值"
}
```

<a id="request-webinputkvnodeitemremoveinfo"></a>

#### WebInputKvNodeItemRemoveInfo

| 字段             | 类型       | 必填 | 说明                    |
|------------------|------------|------|-------------------------|
| `category`       | 字符串     | 是   | 配置类别；不能为空      |
| `args`           | 字符串数组 | 是   | 格式化参数数组          |
| `item_string_id` | 字符串     | 是   | 条目字符串 ID；不能为空 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item"
}
```

<a id="request-webinputnavigationnodeformatindexinfo"></a>

#### WebInputNavigationNodeFormatIndexInfo

| 字段              | 类型                                      | 必填 | 说明                                        |
|-------------------|-------------------------------------------|------|---------------------------------------------|
| `category`        | 字符串                                    | 是   | 配置类别；不能为空                          |
| `args`            | 字符串数组                                | 是   | 格式化参数数组                              |
| `parent_item_key` | [`WebInputLongIdKey`](#webinputlongidkey) | 否   | 父条目键；根条目可为 null；递归校验嵌套字段 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "parent_item_key": {
    "long_id": 1
  }
}
```

<a id="request-webinputnavigationnodeinspectinfo"></a>

#### WebInputNavigationNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputnavigationnodeiteminsertinfo"></a>

#### WebInputNavigationNodeItemInsertInfo

| 字段              | 类型                                      | 必填 | 说明                                        |
|-------------------|-------------------------------------------|------|---------------------------------------------|
| `category`        | 字符串                                    | 是   | 配置类别；不能为空                          |
| `args`            | 字符串数组                                | 是   | 格式化参数数组                              |
| `parent_item_key` | [`WebInputLongIdKey`](#webinputlongidkey) | 否   | 父条目键；根条目可为 null；递归校验嵌套字段 |
| `index`           | 整数                                      | 否   | 条目索引；必须大于等于 0                    |
| `name`            | 字符串                                    | 是   | 名称；最大长度 50；不能为空                 |
| `content`         | 字符串                                    | 是   | 内容                                        |
| `remark`          | 字符串                                    | 否   | 备注；最大长度 100                          |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "parent_item_key": {
    "long_id": 1
  },
  "index": 0,
  "name": "示例条目",
  "content": "示例内容",
  "remark": ""
}
```

<a id="request-webinputnavigationnodeitemremoveinfo"></a>

#### WebInputNavigationNodeItemRemoveInfo

| 字段       | 类型                                      | 必填 | 说明                     |
|------------|-------------------------------------------|------|--------------------------|
| `category` | 字符串                                    | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组                                | 是   | 格式化参数数组           |
| `item_key` | [`WebInputLongIdKey`](#webinputlongidkey) | 是   | 条目键；递归校验嵌套字段 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_key": {
    "long_id": 1
  }
}
```

<a id="request-webinputnavigationnodeitemupdateinfo"></a>

#### WebInputNavigationNodeItemUpdateInfo

| 字段              | 类型                                      | 必填 | 说明                                        |
|-------------------|-------------------------------------------|------|---------------------------------------------|
| `category`        | 字符串                                    | 是   | 配置类别；不能为空                          |
| `args`            | 字符串数组                                | 是   | 格式化参数数组                              |
| `item_key`        | [`WebInputLongIdKey`](#webinputlongidkey) | 是   | 条目键；递归校验嵌套字段                    |
| `parent_item_key` | [`WebInputLongIdKey`](#webinputlongidkey) | 否   | 父条目键；根条目可为 null；递归校验嵌套字段 |
| `index`           | 整数                                      | 否   | 条目索引；必须大于等于 0                    |
| `name`            | 字符串                                    | 是   | 名称；最大长度 50；不能为空                 |
| `content`         | 字符串                                    | 是   | 内容                                        |
| `remark`          | 字符串                                    | 否   | 备注；最大长度 100                          |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_key": {
    "long_id": 1
  },
  "parent_item_key": {
    "long_id": 1
  },
  "index": 0,
  "name": "示例条目",
  "content": "示例内容",
  "remark": ""
}
```

<a id="request-webinputnavigationnodesizeinfo"></a>

#### WebInputNavigationNodeSizeInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputnavigationnodeupdateinfo"></a>

#### WebInputNavigationNodeUpdateInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |
| `content`  | 字符串     | 是   | 内容               |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "content": "示例内容"
}
```

<a id="request-webinputpublicfilelistnodefiledownloadinfo"></a>

#### WebInputPublicFileListNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputpublicfilelistnodeinspectinfo"></a>

#### WebInputPublicFileListNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicfilelistnodesizeinfo"></a>

#### WebInputPublicFileListNodeSizeInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicfilenodefiledownloadinfo"></a>

#### WebInputPublicFileNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicfilenodeinspectinfo"></a>

#### WebInputPublicFileNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicimagelistnodefiledownloadinfo"></a>

#### WebInputPublicImageListNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputpublicimagelistnodeinspectinfo"></a>

#### WebInputPublicImageListNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicimagelistnodesizeinfo"></a>

#### WebInputPublicImageListNodeSizeInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicimagelistnodethumbnaildownloadinfo"></a>

#### WebInputPublicImageListNodeThumbnailDownloadInfo

| 字段       | 类型       | 必填 | 说明                     |
|------------|------------|------|--------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空       |
| `args`     | 字符串数组 | 是   | 格式化参数数组           |
| `index`    | 整数       | 否   | 条目索引；必须大于等于 0 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "index": 0
}
```

<a id="request-webinputpublicimagenodefiledownloadinfo"></a>

#### WebInputPublicImageNodeFileDownloadInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicimagenodeinspectinfo"></a>

#### WebInputPublicImageNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicimagenodethumbnaildownloadinfo"></a>

#### WebInputPublicImageNodeThumbnailDownloadInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublickvnodecountinfo"></a>

#### WebInputPublicKvNodeCountInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublickvnodeinspectinfo"></a>

#### WebInputPublicKvNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublickvnodeiteminspectinfo"></a>

#### WebInputPublicKvNodeItemInspectInfo

| 字段             | 类型       | 必填 | 说明                    |
|------------------|------------|------|-------------------------|
| `category`       | 字符串     | 是   | 配置类别；不能为空      |
| `args`           | 字符串数组 | 是   | 格式化参数数组          |
| `item_string_id` | 字符串     | 是   | 条目字符串 ID；不能为空 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "item_string_id": "example-item"
}
```

<a id="request-webinputpublicnavigationnodeinspectinfo"></a>

#### WebInputPublicNavigationNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicnavigationnodesizeinfo"></a>

#### WebInputPublicNavigationNodeSizeInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublicsettingnodeinspectinfo"></a>

#### WebInputPublicSettingNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputpublictextnodeinspectinfo"></a>

#### WebInputPublicTextNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputsettingcategory"></a>

#### WebInputSettingCategory

| 字段              | 类型                                          | 必填 | 说明                                |
|-------------------|-----------------------------------------------|------|-------------------------------------|
| `key`             | [`WebInputStringIdKey`](#webinputstringidkey) | 否   | 键                                  |
| `formatter_type`  | 字符串                                        | 是   | 格式化器类型；最大长度 50；不能为空 |
| `formatter_param` | 字符串                                        | 否   | 格式化器参数                        |
| `remark`          | 字符串                                        | 否   | 备注；最大长度 100                  |

示例：

```json
{
  "key": {
    "string_id": "example"
  },
  "formatter_type": "string",
  "formatter_param": "",
  "remark": ""
}
```

<a id="request-webinputsettingnodeinitinfo"></a>

#### WebInputSettingNodeInitInfo

| 字段       | 类型       | 必填 | 说明                       |
|------------|------------|------|----------------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空         |
| `args`     | 字符串数组 | 是   | 格式化参数数组             |
| `type`     | 整数       | 否   | 节点类型；递归校验嵌套字段 |
| `remark`   | 字符串     | 否   | 备注；最大长度 100         |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "type": 1,
  "remark": ""
}
```

<a id="request-webinputsettingnodeinspectinfo"></a>

#### WebInputSettingNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputsettingnoderemoveinfo"></a>

#### WebInputSettingNodeRemoveInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputtextnodeinspectinfo"></a>

#### WebInputTextNodeInspectInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ]
}
```

<a id="request-webinputtextnodeputinfo"></a>

#### WebInputTextNodePutInfo

| 字段       | 类型       | 必填 | 说明               |
|------------|------------|------|--------------------|
| `category` | 字符串     | 是   | 配置类别；不能为空 |
| `args`     | 字符串数组 | 是   | 格式化参数数组     |
| `value`    | 字符串     | 否   | 值                 |

示例：

```json
{
  "category": "example",
  "args": [
    "default"
  ],
  "value": "示例值"
}
```

### 响应模型

<a id="response-fastjsonfilelistnode"></a>

#### FastJsonFileListNode

| 字段   | 类型                                          | 说明     |
|--------|-----------------------------------------------|----------|
| `key`  | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键       |
| `size` | 整数                                          | 条目数量 |

<a id="response-fastjsonfilelistnodeinspectresult"></a>

#### FastJsonFileListNodeInspectResult

| 字段    | 类型                | 说明     |
|---------|---------------------|----------|
| `items` | `FastJsonItem` 数组 | 条目列表 |

##### FastJsonFileListNodeInspectResult.FastJsonItem

| 字段          | 类型   | 说明           |
|---------------|--------|----------------|
| `null_flag`   | 布尔值 | 该位置是否为空 |
| `origin_name` | 字符串 | 原始文件名     |
| `length`      | 整数   | 文件字节数     |

<a id="response-fastjsonfilelistnodesizeresult"></a>

#### FastJsonFileListNodeSizeResult

| 字段   | 类型 | 说明     |
|--------|------|----------|
| `size` | 整数 | 条目数量 |

<a id="response-fastjsonfilenode"></a>

#### FastJsonFileNode

| 字段          | 类型                                          | 说明       |
|---------------|-----------------------------------------------|------------|
| `key`         | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键         |
| `origin_name` | 字符串                                        | 原始文件名 |
| `store_name`  | 字符串                                        | 存储文件名 |
| `length`      | 整数                                          | 文件字节数 |

<a id="response-fastjsonfilenodeinspectresult"></a>

#### FastJsonFileNodeInspectResult

| 字段          | 类型   | 说明       |
|---------------|--------|------------|
| `origin_name` | 字符串 | 原始文件名 |
| `length`      | 整数   | 文件字节数 |

<a id="response-fastjsonformattersupport"></a>

#### FastJsonFormatterSupport

| 字段            | 类型                                          | 说明     |
|-----------------|-----------------------------------------------|----------|
| `key`           | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键       |
| `label`         | 字符串                                        | 显示标签 |
| `description`   | 字符串                                        | 字段值   |
| `example_param` | 字符串                                        | 字段值   |

<a id="response-fastjsoniahnnode"></a>

#### FastJsonIahnNode

| 字段  | 类型                                          | 说明 |
|-------|-----------------------------------------------|------|
| `key` | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键   |

<a id="response-fastjsoniahnnodelocalelistinspectresult"></a>

#### FastJsonIahnNodeLocaleListInspectResult

| 字段    | 类型                | 说明     |
|---------|---------------------|----------|
| `items` | `FastJsonItem` 数组 | 条目列表 |

##### FastJsonIahnNodeLocaleListInspectResult.FastJsonItem

| 字段       | 类型   | 说明           |
|------------|--------|----------------|
| `language` | 字符串 | 语言代码       |
| `country`  | 字符串 | 国家或地区代码 |
| `variant`  | 字符串 | 变体代码       |
| `label`    | 字符串 | 显示标签       |
| `remark`   | 字符串 | 备注           |

<a id="response-fastjsoniahnnodemeklistinspectresult"></a>

#### FastJsonIahnNodeMekListInspectResult

| 字段    | 类型                | 说明     |
|---------|---------------------|----------|
| `items` | `FastJsonItem` 数组 | 条目列表 |

##### FastJsonIahnNodeMekListInspectResult.FastJsonItem

| 字段              | 类型   | 说明      |
|-------------------|--------|-----------|
| `mek_id`          | 字符串 | 消息键 ID |
| `label`           | 字符串 | 显示标签  |
| `default_message` | 字符串 | 默认消息  |
| `remark`          | 字符串 | 备注      |

<a id="response-fastjsoniahnnodemessageinspectbylocaleresult"></a>

#### FastJsonIahnNodeMessageInspectByLocaleResult

| 字段    | 类型                | 说明     |
|---------|---------------------|----------|
| `items` | `FastJsonItem` 数组 | 条目列表 |

##### FastJsonIahnNodeMessageInspectByLocaleResult.FastJsonItem

| 字段      | 类型   | 说明      |
|-----------|--------|-----------|
| `mek_id`  | 字符串 | 消息键 ID |
| `message` | 字符串 | 消息内容  |

<a id="response-fastjsoniahnnodemessageinspectresult"></a>

#### FastJsonIahnNodeMessageInspectResult

| 字段      | 类型   | 说明     |
|-----------|--------|----------|
| `message` | 字符串 | 消息内容 |

<a id="response-fastjsoniahnnodemessagetableinspectresult"></a>

#### FastJsonIahnNodeMessageTableInspectResult

| 字段      | 类型                  | 说明     |
|-----------|-----------------------|----------|
| `columns` | `FastJsonColumn` 数组 | 消息表列 |
| `rows`    | `FastJsonRow` 数组    | 消息表行 |

##### FastJsonIahnNodeMessageTableInspectResult.FastJsonColumn

| 字段       | 类型   | 说明           |
|------------|--------|----------------|
| `language` | 字符串 | 语言代码       |
| `country`  | 字符串 | 国家或地区代码 |
| `variant`  | 字符串 | 变体代码       |
| `label`    | 字符串 | 显示标签       |
| `remark`   | 字符串 | 备注           |

##### FastJsonIahnNodeMessageTableInspectResult.FastJsonRow

| 字段              | 类型                   | 说明         |
|-------------------|------------------------|--------------|
| `mek_id`          | 字符串                 | 消息键 ID    |
| `label`           | 字符串                 | 显示标签     |
| `default_message` | 字符串                 | 默认消息     |
| `remark`          | 字符串                 | 备注         |
| `row_datas`       | `FastJsonRowData` 数组 | 消息表单元格 |

##### FastJsonIahnNodeMessageTableInspectResult.FastJsonRowData

| 字段      | 类型   | 说明     |
|-----------|--------|----------|
| `message` | 字符串 | 消息内容 |

<a id="response-fastjsonimagelistnode"></a>

#### FastJsonImageListNode

| 字段   | 类型                                          | 说明     |
|--------|-----------------------------------------------|----------|
| `key`  | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键       |
| `size` | 整数                                          | 条目数量 |

<a id="response-fastjsonimagelistnodeinspectresult"></a>

#### FastJsonImageListNodeInspectResult

| 字段    | 类型                | 说明     |
|---------|---------------------|----------|
| `items` | `FastJsonItem` 数组 | 条目列表 |

##### FastJsonImageListNodeInspectResult.FastJsonItem

| 字段          | 类型   | 说明           |
|---------------|--------|----------------|
| `null_flag`   | 布尔值 | 该位置是否为空 |
| `origin_name` | 字符串 | 原始文件名     |
| `length`      | 整数   | 文件字节数     |

<a id="response-fastjsonimagelistnodesizeresult"></a>

#### FastJsonImageListNodeSizeResult

| 字段   | 类型 | 说明     |
|--------|------|----------|
| `size` | 整数 | 条目数量 |

<a id="response-fastjsonimagenode"></a>

#### FastJsonImageNode

| 字段          | 类型                                          | 说明       |
|---------------|-----------------------------------------------|------------|
| `key`         | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键         |
| `origin_name` | 字符串                                        | 原始文件名 |
| `store_name`  | 字符串                                        | 存储文件名 |
| `length`      | 整数                                          | 文件字节数 |

<a id="response-fastjsonimagenodeinspectresult"></a>

#### FastJsonImageNodeInspectResult

| 字段          | 类型   | 说明       |
|---------------|--------|------------|
| `origin_name` | 字符串 | 原始文件名 |
| `length`      | 整数   | 文件字节数 |

<a id="response-fastjsonkvnode"></a>

#### FastJsonKvNode

| 字段    | 类型                                          | 说明     |
|---------|-----------------------------------------------|----------|
| `key`   | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键       |
| `count` | 整数                                          | 条目数量 |

<a id="response-fastjsonkvnodecountresult"></a>

#### FastJsonKvNodeCountResult

| 字段    | 类型 | 说明     |
|---------|------|----------|
| `count` | 整数 | 条目数量 |

<a id="response-fastjsonkvnodeinspectresult"></a>

#### FastJsonKvNodeInspectResult

| 字段    | 类型                | 说明     |
|---------|---------------------|----------|
| `items` | `FastJsonItem` 数组 | 条目列表 |

##### FastJsonKvNodeInspectResult.FastJsonItem

| 字段             | 类型   | 说明          |
|------------------|--------|---------------|
| `item_string_id` | 字符串 | 条目字符串 ID |
| `value`          | 字符串 | 值            |

<a id="response-fastjsonkvnodeitem"></a>

#### FastJsonKvNodeItem

| 字段    | 类型                                              | 说明 |
|---------|---------------------------------------------------|------|
| `key`   | [`FastJsonKvNodeItemKey`](#fastjsonkvnodeitemkey) | 键   |
| `value` | 字符串                                            | 值   |

<a id="response-fastjsonkvnodeiteminspectresult"></a>

#### FastJsonKvNodeItemInspectResult

| 字段    | 类型   | 说明 |
|---------|--------|------|
| `value` | 字符串 | 值   |

<a id="response-fastjsonnavigationnode"></a>

#### FastJsonNavigationNode

| 字段      | 类型                                          | 说明     |
|-----------|-----------------------------------------------|----------|
| `key`     | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键       |
| `size`    | 整数                                          | 条目数量 |
| `content` | 字符串                                        | 内容     |

<a id="response-fastjsonnavigationnodeitem"></a>

#### FastJsonNavigationNodeItem

| 字段         | 类型                                          | 说明     |
|--------------|-----------------------------------------------|----------|
| `key`        | [`FastJsonLongIdKey`](#fastjsonlongidkey)     | 键       |
| `node_key`   | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 节点键   |
| `parent_key` | [`FastJsonLongIdKey`](#fastjsonlongidkey)     | 父条目键 |
| `index`      | 整数                                          | 条目索引 |
| `name`       | 字符串                                        | 名称     |
| `content`    | 字符串                                        | 内容     |
| `remark`     | 字符串                                        | 备注     |

<a id="response-fastjsonnavigationnodesizeresult"></a>

#### FastJsonNavigationNodeSizeResult

| 字段   | 类型 | 说明     |
|--------|------|----------|
| `size` | 整数 | 条目数量 |

<a id="response-fastjsonsettingcategory"></a>

#### FastJsonSettingCategory

| 字段              | 类型                                          | 说明         |
|-------------------|-----------------------------------------------|--------------|
| `key`             | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键           |
| `formatter_type`  | 字符串                                        | 格式化器类型 |
| `formatter_param` | 字符串                                        | 格式化器参数 |
| `remark`          | 字符串                                        | 备注         |

<a id="response-fastjsonsettingnode"></a>

#### FastJsonSettingNode

| 字段                 | 类型                                          | 说明           |
|----------------------|-----------------------------------------------|----------------|
| `key`                | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键             |
| `type`               | 整数                                          | 节点类型       |
| `last_modified_date` | 整数（Unix 毫秒时间戳）                       | 最后修改时间   |
| `remark`             | 字符串                                        | 备注           |
| `reachable`          | 布尔值                                        | 是否可达       |
| `category`           | 字符串                                        | 配置类别       |
| `args`               | 字符串数组                                    | 格式化参数数组 |

<a id="response-fastjsonsettingnodeinspectresult"></a>

#### FastJsonSettingNodeInspectResult

| 字段                 | 类型                    | 说明         |
|----------------------|-------------------------|--------------|
| `type`               | 整数                    | 节点类型     |
| `last_modified_date` | 整数（Unix 毫秒时间戳） | 最后修改时间 |
| `remark`             | 字符串                  | 备注         |

<a id="response-fastjsonstringidkey"></a>

#### FastJsonStringIdKey

| 字段        | 类型   | 说明      |
|-------------|--------|-----------|
| `string_id` | 字符串 | 字符串 ID |

<a id="response-fastjsontextnode"></a>

#### FastJsonTextNode

| 字段    | 类型                                          | 说明 |
|---------|-----------------------------------------------|------|
| `key`   | [`FastJsonStringIdKey`](#fastjsonstringidkey) | 键   |
| `value` | 字符串                                        | 值   |

<a id="response-fastjsontextnodeinspectresult"></a>

#### FastJsonTextNodeInspectResult

| 字段    | 类型   | 说明 |
|---------|--------|------|
| `value` | 字符串 | 值   |

<a id="response-jsfixedfastjsondispnavigationnodeitem"></a>

#### JSFixedFastJsonDispNavigationNodeItem

| 字段                          | 类型                                                                      | 说明           |
|-------------------------------|---------------------------------------------------------------------------|----------------|
| `key`                         | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)                   | 键             |
| `node_key`                    | [`FastJsonStringIdKey`](#fastjsonstringidkey)                             | 节点键         |
| `parent_key`                  | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey)                   | 父条目键       |
| `index`                       | 整数                                                                      | 条目索引       |
| `name`                        | 字符串                                                                    | 名称           |
| `content`                     | 字符串                                                                    | 内容           |
| `remark`                      | 字符串                                                                    | 备注           |
| `parent_navigation_node_item` | [`JSFixedFastJsonNavigationNodeItem`](#jsfixedfastjsonnavigationnodeitem) | 父导航条目     |
| `has_no_child`                | 布尔值                                                                    | 是否没有子条目 |

<a id="response-jsfixedfastjsonlongidkey"></a>

#### JSFixedFastJsonLongIdKey

| 字段      | 类型   | 说明                                      |
|-----------|--------|-------------------------------------------|
| `long_id` | 字符串 | 通过 `ToStringSerializer` 输出的长整型 ID |

<a id="response-jsfixedfastjsonnavigationnodeinspectresult"></a>

#### JSFixedFastJsonNavigationNodeInspectResult

| 字段       | 类型                       | 说明     |
|------------|----------------------------|----------|
| `count`    | 整数                       | 条目数量 |
| `content`  | 字符串                     | 内容     |
| `children` | `JSFixedFastJsonItem` 数组 | 子条目   |

##### JSFixedFastJsonNavigationNodeInspectResult.JSFixedFastJsonItem

| 字段              | 类型                                                    | 说明                      |
|-------------------|---------------------------------------------------------|---------------------------|
| `key`             | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 键                        |
| `parent_item_key` | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 父条目键；根条目可为 null |
| `index`           | 整数                                                    | 条目索引                  |
| `name`            | 字符串                                                  | 名称                      |
| `content`         | 字符串                                                  | 内容                      |
| `remark`          | 字符串                                                  | 备注                      |
| `children`        | `JSFixedFastJsonItem` 数组                              | 子条目                    |

<a id="response-jsfixedfastjsonnavigationnodeiteminsertresult"></a>

#### JSFixedFastJsonNavigationNodeItemInsertResult

| 字段       | 类型                                                    | 说明           |
|------------|---------------------------------------------------------|----------------|
| `category` | 字符串                                                  | 配置类别       |
| `args`     | 字符串数组                                              | 格式化参数数组 |
| `item_key` | [`JSFixedFastJsonLongIdKey`](#jsfixedfastjsonlongidkey) | 条目键         |

<a id="jsfixedfastjsonnavigationnodeitem"></a>

#### JSFixedFastJsonNavigationNodeItem

| 字段         | 类型                                                  | 说明                      |
|--------------|-------------------------------------------------------|---------------------------|
| `key`        | [JSFixedFastJsonLongIdKey](#jsfixedfastjsonlongidkey) | 条目键                    |
| `node_key`   | [FastJsonStringIdKey](#fastjsonstringidkey)           | 导航节点键                |
| `parent_key` | [JSFixedFastJsonLongIdKey](#jsfixedfastjsonlongidkey) | 父条目键；根条目为 `null` |
| `index`      | 整数                                                  | 同级顺序索引              |
| `name`       | 字符串                                                | 名称                      |
| `content`    | 字符串                                                | 内容                      |
| `remark`     | 字符串                                                | 备注                      |
