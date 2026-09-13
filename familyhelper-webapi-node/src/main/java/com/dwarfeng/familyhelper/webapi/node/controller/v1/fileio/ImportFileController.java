package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportFileResponseService;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputFileDownloadInfo;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonImportFileInfo;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
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
 * 导入文件信息控制器。
 *
 * @author diaocl
 * @author DwArFeng
 * @since 2.1.0
 */
@RestController("fileioImportFileController")
@RequestMapping("/api/v1/fileio")
public class ImportFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ImportFileResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ImportFileInfo, JSFixedFastJsonImportFileInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    public ImportFileController(
            ImportFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ImportFileInfo, JSFixedFastJsonImportFileInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
    }

    @GetMapping("/import-file/{taskId}&{identifier}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            boolean exists = service.exists(new TaskItemKey(taskId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-file/{taskId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.get")
    public FastJsonResponseData<JSFixedFastJsonImportFileInfo> get(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            ImportFileInfo importFileInfo = service.get(new TaskItemKey(taskId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonImportFileInfo.of(importFileInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/import-file/{taskId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.delete")
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            service.delete(new TaskItemKey(taskId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/import-file/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportFileInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportFileInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonImportFileInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/{taskId}/import-file")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.child_for_task")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonImportFileInfo>> childForTask(
            HttpServletRequest request,
            @PathVariable Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImportFileInfo> childForTask = service.childForTask(
                    new LongIdKey(taskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonImportFileInfo> transform = PagingUtil.transform(
                    childForTask, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * @since 2.1.0
     */
    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/import-file/{taskId}&{identifier}/download-file")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.download_file")
    public ResponseEntity<Object> downloadFile(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            FileDownloadInfo fileDownloadInfo = new FileDownloadInfo(new TaskItemKey(taskId, identifier));
            File file = service.downloadFile(fileDownloadInfo);
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(file.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = file.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    /**
     * @since 2.1.0
     */
    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-file/download-file-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.download_file_stream")
    public void downloadFileStream(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody @Validated(Insert.class) WebInputFileDownloadInfo webInputFileDownloadInfo
    ) throws Exception {
        try {
            FileDownloadInfo fileDownloadInfo = WebInputFileDownloadInfo.toStackBean(webInputFileDownloadInfo);
            FileStreamDownloadInfo fileStreamDownloadInfo = new FileStreamDownloadInfo(fileDownloadInfo.getKey());
            FileStream fileStream = service.downloadFileStream(fileStreamDownloadInfo);

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

    /**
     * @since 2.1.0
     */
    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-file/{taskId}&{identifier}/request-file-stream-voucher")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.request_file_stream_voucher")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestFileStreamVoucher(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            LongIdKey voucherKey = service.requestFileStreamVoucher(
                    new FileStreamDownloadInfo(new TaskItemKey(taskId, identifier))
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
    @GetMapping("/import-file/download-by-voucher")
    @BehaviorAnalyse
    public void downloadFileStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            FileStream fileStream = service.downloadFileStreamByVoucher(new LongIdKey(voucherId));

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

    /**
     * @since 2.1.0
     */
    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-file/{taskId}&{identifier}/upload")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.upload")
    public FastJsonResponseData<Object> upload(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
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
            service.uploadFile(new FileUploadInfo(new TaskItemKey(taskId, identifier), originFileName, content));

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * @since 2.1.0
     */
    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/import-file/{taskId}&{identifier}/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.import_file.upload_stream")
    public FastJsonResponseData<Object> uploadStream(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
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
                service.uploadFileStream(new FileStreamUploadInfo(new TaskItemKey(taskId, identifier),
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
