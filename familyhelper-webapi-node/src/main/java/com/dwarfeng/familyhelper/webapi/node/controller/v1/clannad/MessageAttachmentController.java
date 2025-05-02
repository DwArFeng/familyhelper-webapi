package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.JSFixedFastJsonMessageAttachmentInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageAttachmentInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.MessageAttachmentResponseService;
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
import org.springframework.validation.BindingResult;
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
 * 留言附件控制器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class MessageAttachmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAttachmentController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final MessageAttachmentResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<MessageAttachmentInfo, JSFixedFastJsonMessageAttachmentInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public MessageAttachmentController(
            MessageAttachmentResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<MessageAttachmentInfo, JSFixedFastJsonMessageAttachmentInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/message-attachment/{longId}/exists")
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

    @GetMapping("/message-attachment/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonMessageAttachmentInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            MessageAttachmentInfo messageAttachmentInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonMessageAttachmentInfo.of(messageAttachmentInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("message/{messageId}/message-attachment/default")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessageAttachmentInfo>> childForMessage(
            HttpServletRequest request,
            @PathVariable("messageId") long messageId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<MessageAttachmentInfo> childForMessage = service.childForMessage(
                    new LongIdKey(messageId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMessageAttachmentInfo> transform = PagingUtil.transform(
                    childForMessage, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("message/{messageId}/message-attachment/origin-name-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessageAttachmentInfo>>
    childForMessageOriginNameAsc(
            HttpServletRequest request,
            @PathVariable("messageId") long messageId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<MessageAttachmentInfo> childForMessage = service.childForMessageOriginNameAsc(
                    new LongIdKey(messageId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMessageAttachmentInfo> transform = PagingUtil.transform(
                    childForMessage, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("message/{messageId}/message-attachment/upload-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMessageAttachmentInfo>>
    childForMessageUploadDateDesc(
            HttpServletRequest request,
            @PathVariable("messageId") long messageId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<MessageAttachmentInfo> childForMessage = service.childForMessageUploadDateDesc(
                    new LongIdKey(messageId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMessageAttachmentInfo> transform = PagingUtil.transform(
                    childForMessage, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/message-attachment/{messageAttachmentId}/download")
    @BehaviorAnalyse
    @LoginRequired
    public ResponseEntity<Object> downloadMessageAttachment(
            HttpServletRequest request, @PathVariable("messageAttachmentId") Long messageAttachmentId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            MessageAttachment messageAttachment = service.downloadMessageAttachment(
                    accountKey, new LongIdKey(messageAttachmentId)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(messageAttachment.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = messageAttachment.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/message-attachment/{messageAttachmentId}/request-message-attachment-stream-voucher")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestMessageAttachmentStreamVoucher(
            HttpServletRequest request, @PathVariable("messageAttachmentId") Long messageAttachmentId
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey voucherKey = service.requestMessageAttachmentStreamVoucher(
                    accountKey, new LongIdKey(messageAttachmentId)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/message-attachment/download-by-voucher")
    @BehaviorAnalyse
    public void downloadMessageAttachmentStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            MessageAttachmentStream messageAttachmentStream = service.downloadMessageAttachmentStreamByVoucher(
                    new LongIdKey(voucherId)
            );

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(messageAttachmentStream.getOriginName());
            long fileLength = messageAttachmentStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 messageAttachmentStream 的输入流以及 response 的输出流，传输数据。
            try (InputStream in = messageAttachmentStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/message/{messageId}/message-attachment/upload")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> uploadMessageAttachment(
            HttpServletRequest request, @PathVariable("messageId") Long messageId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
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
            service.uploadMessageAttachment(
                    accountKey, new MessageAttachmentUploadInfo(new LongIdKey(messageId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/message/{messageId}/message-attachment/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> uploadMessageAttachmentStream(
            HttpServletRequest request, @PathVariable("messageId") Long messageId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
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
                service.uploadMessageAttachmentStream(
                        accountKey,
                        new MessageAttachmentStreamUploadInfo(
                                new LongIdKey(messageId), originFileName, contentLength, fin
                        )
                );
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/message-attachment/{messageAttachmentId}/update")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> updateMessageAttachment(
            HttpServletRequest request, @PathVariable("messageAttachmentId") Long messageAttachmentId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
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
            service.updateMessageAttachment(
                    accountKey, new MessageAttachmentUpdateInfo(
                            new LongIdKey(messageAttachmentId), originFileName, content
                    )
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/message-attachment/{messageAttachmentId}/update-stream")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> updateMessageAttachmentStream(
            HttpServletRequest request, @PathVariable("messageAttachmentId") Long messageAttachmentId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest =
                    commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 解析文件内容，并更新。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.updateMessageAttachmentStream(
                        accountKey,
                        new MessageAttachmentStreamUpdateInfo(
                                new LongIdKey(messageAttachmentId), originFileName, contentLength, fin
                        )
                );
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/message-attachment/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeMessageAttachment(
            HttpServletRequest request, @RequestBody WebInputLongIdKey messageAttachmentKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeMessageAttachment(accountKey, WebInputLongIdKey.toStackBean(messageAttachmentKey));
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
