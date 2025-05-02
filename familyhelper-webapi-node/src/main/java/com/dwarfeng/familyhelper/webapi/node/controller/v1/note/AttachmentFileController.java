package com.dwarfeng.familyhelper.webapi.node.controller.v1.note;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.note.sdk.bean.entity.JSFixedFastJsonAttachmentFileInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFile;
import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFileUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.AttachmentFileUploadInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.AttachmentFileInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.note.AttachmentFileResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 附件文件控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/note")
public class AttachmentFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final AttachmentFileResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<AttachmentFileInfo, JSFixedFastJsonAttachmentFileInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public AttachmentFileController(
            AttachmentFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<AttachmentFileInfo, JSFixedFastJsonAttachmentFileInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/attachment-file/{longId}/exists")
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

    @GetMapping("/attachment-file/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonAttachmentFileInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            AttachmentFileInfo attachmentFileInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonAttachmentFileInfo.of(attachmentFileInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("note-item/{noteItemId}/attachment-file/default")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAttachmentFileInfo>> childForNoteItem(
            HttpServletRequest request,
            @PathVariable("noteItemId") long noteItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AttachmentFileInfo> childForNoteItem = service.childForNoteItem(
                    new LongIdKey(noteItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAttachmentFileInfo> transform = PagingUtil.transform(
                    childForNoteItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("note-item/{noteItemId}/attachment-file/inspected-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAttachmentFileInfo>>
    childForNoteItemInspectedDateDesc(
            HttpServletRequest request,
            @PathVariable("noteItemId") long noteItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AttachmentFileInfo> childForNoteItem = service.childForNoteItemInspectedDateDesc(
                    new LongIdKey(noteItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAttachmentFileInfo> transform = PagingUtil.transform(
                    childForNoteItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("note-item/{noteItemId}/attachment-file/modified-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAttachmentFileInfo>>
    childForNoteItemModifiedDateDesc(
            HttpServletRequest request,
            @PathVariable("noteItemId") long noteItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AttachmentFileInfo> childForNoteItem = service.childForNoteItemModifiedDateDesc(
                    new LongIdKey(noteItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAttachmentFileInfo> transform = PagingUtil.transform(
                    childForNoteItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("note-item/{noteItemId}/attachment-file/origin-name-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAttachmentFileInfo>>
    childForNoteItemOriginNameAsc(
            HttpServletRequest request,
            @PathVariable("noteItemId") long noteItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AttachmentFileInfo> childForNoteItem = service.childForNoteItemOriginNameAsc(
                    new LongIdKey(noteItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAttachmentFileInfo> transform = PagingUtil.transform(
                    childForNoteItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("note-item/{noteItemId}/attachment-file/created-date-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAttachmentFileInfo>>
    childForNoteItemCreatedDateAsc(
            HttpServletRequest request,
            @PathVariable("noteItemId") long noteItemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AttachmentFileInfo> childForNoteItem = service.childForNoteItemCreatedDateAsc(
                    new LongIdKey(noteItemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAttachmentFileInfo> transform = PagingUtil.transform(
                    childForNoteItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/attachment-file/{attachmentFileId}/download")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadAttachmentFile(
            HttpServletRequest request, @PathVariable("attachmentFileId") Long attachmentFileId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            AttachmentFile attachmentFile = service.downloadAttachmentFile(
                    accountKey, new LongIdKey(attachmentFileId)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(attachmentFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = attachmentFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/note-item/{noteItemId}/attachment-file/upload")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> uploadAttachmentFile(
            HttpServletRequest request, @PathVariable("noteItemId") Long noteItemId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest
                    = commonsMultipartResolver.resolveMultipart(request);
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
            service.uploadAttachmentFile(
                    accountKey, new AttachmentFileUploadInfo(new LongIdKey(noteItemId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/attachment-file/{attachmentFileId}/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateAttachmentFile(
            HttpServletRequest request, @PathVariable("attachmentFileId") Long attachmentFileId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest
                    = commonsMultipartResolver.resolveMultipart(request);
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
            service.updateAttachmentFile(
                    accountKey, new AttachmentFileUpdateInfo(new LongIdKey(attachmentFileId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/attachment-file/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeAttachmentFile(
            HttpServletRequest request, @RequestBody WebInputLongIdKey attachmentFileKey
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeAttachmentFile(accountKey, WebInputLongIdKey.toStackBean(attachmentFileKey));
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
