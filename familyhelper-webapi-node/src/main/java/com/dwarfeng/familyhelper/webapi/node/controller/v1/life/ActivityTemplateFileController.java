package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityTemplateFileInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFile;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFileUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFileUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateFileInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateFileResponseService;
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
 * 活动模板文件控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityTemplateFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTemplateFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ActivityTemplateFileResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityTemplateFileInfo, JSFixedFastJsonActivityTemplateFileInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public ActivityTemplateFileController(
            ActivityTemplateFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityTemplateFileInfo, JSFixedFastJsonActivityTemplateFileInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-template-file/{longId}/exists")
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

    @GetMapping("/activity-template-file/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityTemplateFileInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            ActivityTemplateFileInfo activityTemplateFileInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonActivityTemplateFileInfo.of(activityTemplateFileInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity-template/{activityTemplateId}/activity-template-file/default")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateFileInfo>>
    childForActivityTemplate(
            HttpServletRequest request,
            @PathVariable("activityTemplateId") long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateFileInfo> childForActivityTemplate = service.childForActivityTemplate(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateFileInfo> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity-template/{activityTemplateId}/activity-template-file/inspected-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateFileInfo>>
    childForActivityTemplateInspectedDateDesc(
            HttpServletRequest request,
            @PathVariable("activityTemplateId") long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateFileInfo> childForActivityTemplate = service.childForActivityTemplateInspectedDateDesc(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateFileInfo> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity-template/{activityTemplateId}/activity-template-file/modified-date-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateFileInfo>>
    childForActivityTemplateModifiedDateDesc(
            HttpServletRequest request,
            @PathVariable("activityTemplateId") long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateFileInfo> childForActivityTemplate = service.childForActivityTemplateModifiedDateDesc(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateFileInfo> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity-template/{activityTemplateId}/activity-template-file/origin-name-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateFileInfo>>
    childForActivityTemplateOriginNameAsc(
            HttpServletRequest request,
            @PathVariable("activityTemplateId") long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateFileInfo> childForActivityTemplate = service.childForActivityTemplateOriginNameAsc(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateFileInfo> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("activity-template/{activityTemplateId}/activity-template-file/created-date-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateFileInfo>>
    childForActivityTemplateCreatedDateAsc(
            HttpServletRequest request,
            @PathVariable("activityTemplateId") long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateFileInfo> childForActivityTemplate = service.childForActivityTemplateCreatedDateAsc(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateFileInfo> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-file/{activityTemplateFileId}/download")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadActivityTemplateFile(
            HttpServletRequest request, @PathVariable("activityTemplateFileId") Long activityTemplateFileId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            ActivityTemplateFile activityTemplateFile = service.downloadActivityTemplateFile(
                    accountKey, new LongIdKey(activityTemplateFileId)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(activityTemplateFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = activityTemplateFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/activity-template/{activityTemplateId}/activity-template-file/upload")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> uploadActivityTemplateFile(
            HttpServletRequest request, @PathVariable("activityTemplateId") Long activityTemplateId
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
            service.uploadActivityTemplateFile(
                    accountKey,
                    new ActivityTemplateFileUploadInfo(new LongIdKey(activityTemplateId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/activity-template-file/{activityTemplateFileId}/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateActivityTemplateFile(
            HttpServletRequest request, @PathVariable("activityTemplateFileId") Long activityTemplateFileId
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
            service.updateActivityTemplateFile(
                    accountKey,
                    new ActivityTemplateFileUpdateInfo(new LongIdKey(activityTemplateFileId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-template-file/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeActivityTemplateFile(
            HttpServletRequest request, @RequestBody WebInputLongIdKey activityTemplateFileKey
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeActivityTemplateFile(accountKey, WebInputLongIdKey.toStackBean(activityTemplateFileKey));
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
