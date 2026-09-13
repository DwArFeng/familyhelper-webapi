package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportTemplateResponseService;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputTemplateCreateInfo;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputTemplateDownloadInfo;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputTemplateRemoveInfo;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonImportTemplateInfo;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 导入模板控制器。
 *
 * @author diaocl
 * @author DwArFeng
 * @since 2.1.0
 */
@RestController("fileioImportTemplateController")
@RequestMapping("/api/v1/fileio")
public class ImportTemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportTemplateController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ImportTemplateResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ImportTemplateInfo, JSFixedFastJsonImportTemplateInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    public ImportTemplateController(
            ImportTemplateResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ImportTemplateInfo, JSFixedFastJsonImportTemplateInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
    }

    @GetMapping("/import-template/{taskSettingId}&{identifier}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            boolean exists = service.exists(new TaskSettingItemKey(taskSettingId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-template/{taskSettingId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.get")
    public FastJsonResponseData<JSFixedFastJsonImportTemplateInfo> get(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            ImportTemplateInfo importTemplateInfo = service.get(new TaskSettingItemKey(taskSettingId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonImportTemplateInfo.of(importTemplateInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-template/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTemplateInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportTemplateInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImportTemplateInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task-setting/{taskSettingId}/import-template")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.child_for_task_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportTemplateInfo>> childForTaskSetting(
            HttpServletRequest request,
            @PathVariable Long taskSettingId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportTemplateInfo> childForTaskSetting = service.childForTaskSetting(
                    new LongIdKey(taskSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonImportTemplateInfo> transform = PagingUtil.transform(
                    childForTaskSetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-template/create")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.create")
    public FastJsonResponseData<JSFixedFastJsonTaskSettingItemKey> create(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputTemplateCreateInfo webInputTemplateCreateInfo
    ) {
        try {
            TemplateCreateInfo templateCreateInfo = WebInputTemplateCreateInfo.toStackBean(webInputTemplateCreateInfo);
            TaskSettingItemKey taskSettingItemKey = service.create(templateCreateInfo);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonTaskSettingItemKey.of(taskSettingItemKey))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-template/remove")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputTemplateRemoveInfo webInputTemplateRemoveInfo
    ) {
        try {
            TemplateRemoveInfo templateRemoveInfo = WebInputTemplateRemoveInfo.toStackBean(webInputTemplateRemoveInfo);
            service.remove(templateRemoveInfo);
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/import-template/{taskSettingId}&{identifier}/download-file")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.download_file")
    public ResponseEntity<Object> downloadFile(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            TemplateDownloadInfo templateDownloadInfo = new TemplateDownloadInfo(
                    new TaskSettingItemKey(taskSettingId, identifier)
            );
            Template file = service.download(templateDownloadInfo);
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(file.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = file.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-template/download-file-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.download_file_stream")
    public void downloadFileStream(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody @Validated(Insert.class) WebInputTemplateDownloadInfo webInputTemplateDownloadInfo
    ) throws Exception {
        try {
            TemplateDownloadInfo templateDownloadInfo = WebInputTemplateDownloadInfo.toStackBean(
                    webInputTemplateDownloadInfo
            );
            TemplateStreamDownloadInfo templateStreamDownloadInfo = new TemplateStreamDownloadInfo(
                    templateDownloadInfo.getKey()
            );
            TemplateStream fileStream = service.downloadStream(templateStreamDownloadInfo);

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(fileStream.getOriginName());
            long fileLength = fileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 输入流以及 response 的输出流，传输数据。
            try (InputStream in = fileStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            throw sem.map(e);
        }
    }

    /**
     * @since 2.1.0
     */
    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-template/{taskSettingId}&{identifier}/request-stream-voucher")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.request_stream_voucher")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestStreamVoucher(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            LongIdKey voucherKey = service.requestStreamVoucher(
                    new TemplateStreamDownloadInfo(new TaskSettingItemKey(taskSettingId, identifier))
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * @since 2.1.0
     */
    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/import-template/download-by-voucher")
    @BehaviorAnalyse
    public void downloadStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            TemplateStream fileStream = service.downloadStreamByVoucher(new LongIdKey(voucherId));

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(fileStream.getOriginName());
            long fileLength = fileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取输入流以及 response 的输出流，传输数据。
            try (InputStream in = fileStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            throw sem.map(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-template/{taskSettingId}&{identifier}/upload")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.upload")
    public FastJsonResponseData<Object> upload(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            // 获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest =
                    commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 解析文件内容。
            String originFileName = file.getOriginalFilename();
            byte[] content;
            try (InputStream in = file.getInputStream(); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                IOUtil.trans(in, bout, IO_TRANS_BUFFER_SIZE);
                bout.flush();
                content = bout.toByteArray();
            }

            // 将文件内容转换为接口需要的格式，并上传。
            service.upload(
                    new TemplateUploadInfo(new TaskSettingItemKey(taskSettingId, identifier), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-template/{taskSettingId}&{identifier}/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_template.upload_stream")
    public FastJsonResponseData<Object> uploadStream(
            HttpServletRequest request,
            @PathVariable("taskSettingId") long taskSettingId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            // 获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest =
                    commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 解析文件内容，并上传。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.uploadStream(new TemplateStreamUploadInfo(new TaskSettingItemKey(taskSettingId, identifier),
                        originFileName, contentLength, fin));
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }
}
