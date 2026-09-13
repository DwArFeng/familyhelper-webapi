# API 参考 - V1 fileio

本文档提供 `familyhelper-webapi-node` 中 `/api/v1/fileio` 文件导入导出接口的入口说明。

## 接口分类

`fileio` 接口按导入导出流程划分为以下几类：

- 导出配置、导出器、写入器与导出模板维护接口。
- 导出任务创建、元数据维护、执行和文件下载接口。
- 导入配置、导入器、读取器与导入模板维护接口。
- 导入任务创建、元数据维护、文件上传、执行和结果下载接口。
- 文件流下载凭证申请与凭证下载接口。
- fileio 服务重置接口。

涉及文件流上传或下载的接口通过 `familyhelper-plugin-fileio` 提供 Dubbo REST 适配，普通元数据和任务操作通过
`fileio-stack` 服务完成。所有需要登录的接口同时受 `webapi.controller_permitted.fileio.*` 权限配置控制。

具体请求字段和响应结构以 `fileio-sdk` 中的 WebInput/FastJson Bean 为准。
