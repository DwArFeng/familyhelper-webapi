package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityTemplateCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityTemplateCoverInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCover;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCoverUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateCoverInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateCoverResponseService;
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
 * 活动模板封面控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityTemplateCoverController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTemplateCoverController.class);

    private final ActivityTemplateCoverResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityTemplateCoverInfo, JSFixedFastJsonActivityTemplateCoverInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public ActivityTemplateCoverController(
            ActivityTemplateCoverResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityTemplateCoverInfo, JSFixedFastJsonActivityTemplateCoverInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-template-cover/{longId}/exists")
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

    @GetMapping("/activity-template-cover/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityTemplateCoverInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            ActivityTemplateCoverInfo activityTemplateCoverInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonActivityTemplateCoverInfo.of(activityTemplateCoverInfo)
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template/{activityTemplateId}/activity-template-cover")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityTemplateCoverInfo>>
    childForActivityTemplate(
            HttpServletRequest request,
            @PathVariable("activityTemplateId") Long activityTemplateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateCoverInfo> childForActivityTemplate = service.childForActivityTemplate(
                    new LongIdKey(activityTemplateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonActivityTemplateCoverInfo> transform = PagingUtil.transform(
                    childForActivityTemplate, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));

        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-cover/{activityTemplateCoverId}/download")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> download(
            HttpServletRequest request, @PathVariable("activityTemplateCoverId") Long activityTemplateCoverId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            ActivityTemplateCover activityTemplateCover = service.download(
                    accountKey, new LongIdKey(activityTemplateCoverId)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(activityTemplateCover.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = activityTemplateCover.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/item/{itemId}/activity-template-cover/upload")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> upload(
            HttpServletRequest request, @PathVariable("itemId") Long itemId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

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
                IOUtil.trans(in, bout, 4096);
                bout.flush();
                content = bout.toByteArray();
            }

            // 将文件内容转换为接口需要的格式，并上传。
            service.upload(
                    accountKey, new ActivityTemplateCoverUploadInfo(new LongIdKey(itemId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/activity-template-cover/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request, @RequestBody WebInputLongIdKey activityTemplateCoverKey
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.remove(accountKey, WebInputLongIdKey.toStackBean(activityTemplateCoverKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }

    @PostMapping("/activity-template-cover/update-order")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateOrder(
            HttpServletRequest request, @RequestBody WebInputActivityTemplateCoverOrderUpdateInfo updateInfo
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.updateOrder(
                    accountKey, WebInputActivityTemplateCoverOrderUpdateInfo.toStackBean(updateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
