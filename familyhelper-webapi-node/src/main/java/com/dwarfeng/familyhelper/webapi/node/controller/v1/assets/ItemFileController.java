package com.dwarfeng.familyhelper.webapi.node.controller.v1.assets;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.JSFixedFastJsonItemFileInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.*;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemFileInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemFileResponseService;
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
 * 项目文件控制器。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
@RestController
@RequestMapping("/api/v1/assets")
public class ItemFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ItemFileResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ItemFileInfo, JSFixedFastJsonItemFileInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public ItemFileController(
            ItemFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ItemFileInfo, JSFixedFastJsonItemFileInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/item-file/{longId}/exists")
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

    @GetMapping("/item-file/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonItemFileInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            ItemFileInfo itemFileInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonItemFileInfo.of(itemFileInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("item/{itemId}/item-file/default")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItemFileInfo>> childForItem(
            HttpServletRequest request,
            @PathVariable("itemId") long itemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ItemFileInfo> childForItem = service.childForItem(
                    new LongIdKey(itemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItemFileInfo> transform = PagingUtil.transform(
                    childForItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("item/{itemId}/item-file/inspected-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItemFileInfo>> childForItemInspectedDateDesc(
            HttpServletRequest request,
            @PathVariable("itemId") long itemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ItemFileInfo> childForItem = service.childForItemInspectedDateDesc(
                    new LongIdKey(itemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItemFileInfo> transform = PagingUtil.transform(
                    childForItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("item/{itemId}/item-file/modified-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItemFileInfo>> childForItemModifiedDateDesc(
            HttpServletRequest request,
            @PathVariable("itemId") long itemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ItemFileInfo> childForItem = service.childForItemModifiedDateDesc(
                    new LongIdKey(itemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItemFileInfo> transform = PagingUtil.transform(
                    childForItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("item/{itemId}/item-file/origin-name-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItemFileInfo>> childForItemOriginNameAsc(
            HttpServletRequest request,
            @PathVariable("itemId") long itemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ItemFileInfo> childForItem = service.childForItemOriginNameAsc(
                    new LongIdKey(itemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItemFileInfo> transform = PagingUtil.transform(
                    childForItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("item/{itemId}/item-file/created-date-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItemFileInfo>> childForItemCreatedDateAsc(
            HttpServletRequest request,
            @PathVariable("itemId") long itemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ItemFileInfo> childForItem = service.childForItemCreatedDateAsc(
                    new LongIdKey(itemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItemFileInfo> transform = PagingUtil.transform(
                    childForItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item-file/{itemFileId}/download")
    @BehaviorAnalyse
    @LoginRequired
    public ResponseEntity<Object> downloadItemFile(
            HttpServletRequest request, @PathVariable("itemFileId") Long itemFileId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            ItemFile itemFile = service.downloadItemFile(accountKey, new LongIdKey(itemFileId));
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(itemFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = itemFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/item-file/{itemFileId}/request-item-file-stream-voucher")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestItemFileStreamVoucher(
            HttpServletRequest request, @PathVariable("itemFileId") Long itemFileId
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey voucherKey = service.requestItemFileStreamVoucher(accountKey, new LongIdKey(itemFileId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/item-file/download-by-voucher")
    @BehaviorAnalyse
    public void downloadItemFileStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            ItemFileStream itemFileStream = service.downloadItemFileStreamByVoucher(new LongIdKey(voucherId));

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(itemFileStream.getOriginName());
            long fileLength = itemFileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 itemFileStream 的输入流以及 response 的输出流，传输数据。
            try (InputStream in = itemFileStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/item/{itemId}/item-file/upload")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> uploadItemFile(
            HttpServletRequest request, @PathVariable("itemId") Long itemId
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
            service.uploadItemFile(
                    accountKey, new ItemFileUploadInfo(new LongIdKey(itemId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/item/{itemId}/item-file/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> uploadItemFileStream(
            HttpServletRequest request, @PathVariable("itemId") Long itemId
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
                service.uploadItemFileStream(
                        accountKey,
                        new ItemFileStreamUploadInfo(new LongIdKey(itemId), originFileName, contentLength, fin)
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
    @PostMapping("/item-file/{itemFileId}/update")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> updateItemFile(
            HttpServletRequest request, @PathVariable("itemFileId") Long itemFileId
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
            service.updateItemFile(
                    accountKey, new ItemFileUpdateInfo(new LongIdKey(itemFileId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/item-file/{itemFileId}/update-stream")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> updateItemFileStream(
            HttpServletRequest request, @PathVariable("itemFileId") Long itemFileId
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

            // 解析文件内容，并更新。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.updateItemFileStream(
                        accountKey,
                        new ItemFileStreamUpdateInfo(new LongIdKey(itemFileId), originFileName, contentLength, fin)
                );
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/item-file/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeItemFile(
            HttpServletRequest request, @RequestBody WebInputLongIdKey itemFileKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeItemFile(accountKey, WebInputLongIdKey.toStackBean(itemFileKey));
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
