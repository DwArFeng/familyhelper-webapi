package com.dwarfeng.familyhelper.webapi.node.controller.v1.fileio;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportFileResponseService;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputFileDownloadInfo;
import com.dwarfeng.fileio.sdk.bean.entity.JSFixedFastJsonExportFileInfo;
import com.dwarfeng.fileio.stack.bean.dto.File;
import com.dwarfeng.fileio.stack.bean.dto.FileDownloadInfo;
import com.dwarfeng.fileio.stack.bean.dto.FileStream;
import com.dwarfeng.fileio.stack.bean.dto.FileStreamDownloadInfo;
import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 导出文件信息控制器。
 *
 * @author diaocl
 * @author DwArFeng
 * @since 2.1.0
 */
@RestController("fileioExportFileController")
@RequestMapping("/api/v1/fileio")
public class ExportFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ExportFileResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ExportFileInfo, JSFixedFastJsonExportFileInfo> beanTransformer;

    public ExportFileController(
            ExportFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ExportFileInfo, JSFixedFastJsonExportFileInfo> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/export-file/{taskId}&{identifier}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.exists")
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

    @GetMapping("/export-file/{taskId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.get")
    public FastJsonResponseData<JSFixedFastJsonExportFileInfo> get(
            HttpServletRequest request,
            @PathVariable("taskId") long taskId,
            @PathVariable("identifier") String identifier
    ) {
        try {
            ExportFileInfo exportFileInfo = service.get(new TaskItemKey(taskId, identifier));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonExportFileInfo.of(exportFileInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/export-file/{taskId}&{identifier}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.delete")
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

    @GetMapping("/export-file/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportFileInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportFileInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonExportFileInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/task/{taskId}/export-file")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.child_for_task")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonExportFileInfo>> childForTask(
            HttpServletRequest request,
            @PathVariable Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ExportFileInfo> childForTask = service.childForTask(
                    new LongIdKey(taskId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonExportFileInfo> transform = PagingUtil.transform(
                    childForTask, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常，信息如下：", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/export-file/{taskId}&{identifier}/download-file")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.download_file")
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
    @PostMapping("/export-file/download-file-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.download_file_stream")
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
    @PostMapping("/export-file/{taskId}&{identifier}/request-file-stream-voucher")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.fileio.export_file.request_file_stream_voucher")
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
    @GetMapping("/export-file/download-by-voucher")
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

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }
}
