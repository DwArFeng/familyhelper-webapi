package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityFileInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.*;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityFileInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityFileResponseService;
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
 * 活动文件控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ActivityFileResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityFileInfo, JSFixedFastJsonActivityFileInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public ActivityFileController(
            ActivityFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityFileInfo, JSFixedFastJsonActivityFileInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-file/{longId}/exists")
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

    @GetMapping("/activity-file/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityFileInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            ActivityFileInfo activityFileInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonActivityFileInfo.of(activityFileInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity/{activityId}/activity-file/default")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityFileInfo>>
    childForActivity(
            HttpServletRequest request,
            @PathVariable("activityId") long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityFileInfo> childForActivity = service.childForActivity(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityFileInfo> transform = PagingUtil.transform(
                    childForActivity, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity/{activityId}/activity-file/inspected-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityFileInfo>>
    childForActivityInspectedDateDesc(
            HttpServletRequest request,
            @PathVariable("activityId") long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityFileInfo> childForActivity = service.childForActivityInspectedDateDesc(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityFileInfo> transform = PagingUtil.transform(
                    childForActivity, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity/{activityId}/activity-file/modified-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityFileInfo>>
    childForActivityModifiedDateDesc(
            HttpServletRequest request,
            @PathVariable("activityId") long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityFileInfo> childForActivity = service.childForActivityModifiedDateDesc(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityFileInfo> transform = PagingUtil.transform(
                    childForActivity, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity/{activityId}/activity-file/origin-name-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityFileInfo>>
    childForActivityOriginNameAsc(
            HttpServletRequest request,
            @PathVariable("activityId") long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityFileInfo> childForActivity = service.childForActivityOriginNameAsc(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityFileInfo> transform = PagingUtil.transform(
                    childForActivity, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity/{activityId}/activity-file/created-date-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityFileInfo>>
    childForActivityCreatedDateAsc(
            HttpServletRequest request,
            @PathVariable("activityId") long activityId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityFileInfo> childForActivity = service.childForActivityCreatedDateAsc(
                    new LongIdKey(activityId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityFileInfo> transform = PagingUtil.transform(
                    childForActivity, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-file/{activityFileId}/download")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadActivityFile(
            HttpServletRequest request, @PathVariable("activityFileId") Long activityFileId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            ActivityFile activityFile = service.downloadActivityFile(
                    accountKey, new LongIdKey(activityFileId)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(activityFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = activityFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/activity-file/{activityFileId}/request-activity-file-stream-voucher")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestActivityFileStreamVoucher(
            HttpServletRequest request, @PathVariable("activityFileId") Long activityFileId
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey voucherKey = service.requestActivityFileStreamVoucher(accountKey, new LongIdKey(activityFileId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/activity-file/download-by-voucher")
    @BehaviorAnalyse
    public void downloadActivityFileStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            ActivityFileStream activityFileStream = service.downloadActivityFileStreamByVoucher(
                    new LongIdKey(voucherId)
            );

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(activityFileStream.getOriginName());
            long fileLength = activityFileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 activityFileStream 的输入流以及 response 的输出流，传输数据。
            try (InputStream in = activityFileStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/activity/{activityId}/activity-file/upload")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> uploadActivityFile(
            HttpServletRequest request, @PathVariable("activityId") Long activityId
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
            service.uploadActivityFile(
                    accountKey,
                    new ActivityFileUploadInfo(new LongIdKey(activityId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/activity/{activityId}/activity-file/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> uploadActivityFileStream(
            HttpServletRequest request, @PathVariable("activityId") Long activityId
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

            // 解析文件内容，并上传。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.uploadActivityFileStream(
                        accountKey,
                        new ActivityFileStreamUploadInfo(new LongIdKey(activityId), originFileName, contentLength, fin)
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
    @PostMapping("/activity-file/{activityFileId}/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateActivityFile(
            HttpServletRequest request, @PathVariable("activityFileId") Long activityFileId
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
            service.updateActivityFile(
                    accountKey,
                    new ActivityFileUpdateInfo(new LongIdKey(activityFileId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/activity-file/{activityFileId}/update-stream")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> updateActivityFileStream(
            HttpServletRequest request, @PathVariable("activityFileId") Long activityFileId
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

            // 解析文件内容，并更新。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.updateActivityFileStream(
                        accountKey,
                        new ActivityFileStreamUpdateInfo(
                                new LongIdKey(activityFileId), originFileName, contentLength, fin
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

    @PostMapping("/activity-file/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeActivityFile(
            HttpServletRequest request, @RequestBody WebInputLongIdKey activityFileKey
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeActivityFile(accountKey, WebInputLongIdKey.toStackBean(activityFileKey));
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
