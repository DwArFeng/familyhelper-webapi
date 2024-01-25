package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.FastJsonActivityTemplateDriverSupport;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDriverSupport;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateDriverSupportResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 活动模板驱动器支持控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityTemplateDriverSupportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTemplateDriverSupportController.class);

    private final ActivityTemplateDriverSupportResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityTemplateDriverSupport, FastJsonActivityTemplateDriverSupport> beanTransformer;

    public ActivityTemplateDriverSupportController(
            ActivityTemplateDriverSupportResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ActivityTemplateDriverSupport, FastJsonActivityTemplateDriverSupport> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/activity-template-driver-support/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-driver-support/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonActivityTemplateDriverSupport> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            ActivityTemplateDriverSupport activityTemplateDriverSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(FastJsonActivityTemplateDriverSupport.of(activityTemplateDriverSupport))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-driver-support/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonActivityTemplateDriverSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateDriverSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonActivityTemplateDriverSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-template-driver-support/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonActivityTemplateDriverSupport>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityTemplateDriverSupport> all = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonActivityTemplateDriverSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
