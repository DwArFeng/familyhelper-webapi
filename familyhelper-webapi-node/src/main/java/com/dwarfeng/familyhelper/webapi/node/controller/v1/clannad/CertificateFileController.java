package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.JSFixedFastJsonCertificateFileInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.CertificateFileInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.CertificateFileResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * 证件文件控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class CertificateFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final CertificateFileResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<CertificateFileInfo, JSFixedFastJsonCertificateFileInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public CertificateFileController(
            CertificateFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<CertificateFileInfo, JSFixedFastJsonCertificateFileInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/certificate-file/{longId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("longId") Long longId) {
        try {
            boolean exists = service.exists(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/certificate-file/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonCertificateFileInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            CertificateFileInfo certificateFileInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonCertificateFileInfo.of(certificateFileInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("certificate/{certificateId}/certificate-file")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonCertificateFileInfo>> childForCertificate(
            HttpServletRequest request,
            @PathVariable("certificateId") long certificateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<CertificateFileInfo> childForCertificate = service.childForCertificate(
                    new LongIdKey(certificateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonCertificateFileInfo> transform = PagingUtil.transform(
                    childForCertificate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/certificate-file/{certificateFileId}/download-file")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadCertificateFile(
            HttpServletRequest request, @PathVariable("certificateFileId") Long certificateFileId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            CertificateFile certificateFile = service.downloadCertificateFile(
                    accountKey, new LongIdKey(certificateFileId)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(certificateFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = certificateFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/certificate-file/{certificateFileId}/request-certificate-file-stream-voucher")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestCertificateFileStreamVoucher(
            HttpServletRequest request, @PathVariable("certificateFileId") Long certificateFileId
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey voucherKey = service.requestCertificateFileStreamVoucher(accountKey, new LongIdKey(certificateFileId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/certificate-file/download-file-by-voucher")
    @BehaviorAnalyse
    public void downloadCertificateFileStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            CertificateFileStream certificateFileStream = service.downloadCertificateFileStreamByVoucher(new LongIdKey(voucherId));

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(certificateFileStream.getOriginName());
            long fileLength = certificateFileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 certificateFileStream 的输入流以及 response 的输出流，传输数据。
            try (InputStream in = certificateFileStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @GetMapping("/certificate-file/{certificateFileId}/download-thumbnail")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadCertificateThumbnail(
            HttpServletRequest request, @PathVariable("certificateFileId") Long certificateFileId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            CertificateThumbnail certificateThumbnail = service.downloadCertificateThumbnail(
                    accountKey, new LongIdKey(certificateFileId)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(certificateThumbnail.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = certificateThumbnail.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/certificate/{certificateId}/certificate-file/upload")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> uploadCertificateFile(
            HttpServletRequest request, @PathVariable("certificateId") Long certificateId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getAccountKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest = commonsMultipartResolver.resolveMultipart(request);
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
            service.uploadCertificateFile(
                    accountKey, new CertificateFileUploadInfo(new LongIdKey(certificateId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/certificate/{certificateId}/certificate-file/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> uploadCertificateFileStream(
            HttpServletRequest request, @PathVariable("certificateId") Long certificateId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getAccountKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 解析文件内容，并上传。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.uploadCertificateFileStream(
                        accountKey,
                        new CertificateFileStreamUploadInfo(new LongIdKey(certificateId), originFileName, contentLength, fin)
                );
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/certificate-file/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeCertificateFile(
            HttpServletRequest request, @RequestBody WebInputLongIdKey certificateFileKey
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeCertificateFile(accountKey, WebInputLongIdKey.toStackBean(certificateFileKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }
}
