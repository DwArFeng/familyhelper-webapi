package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.FastJsonActivityTypeIndicator;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.WebInputActivityTypeIndicator;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTypeIndicatorResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 活动类型指示器控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityTypeIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTypeIndicatorController.class);

    private final ActivityTypeIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityTypeIndicator, FastJsonActivityTypeIndicator> beanTransformer;

    public ActivityTypeIndicatorController(
            ActivityTypeIndicatorResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ActivityTypeIndicator, FastJsonActivityTypeIndicator> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/activity-type-indicator/{id}/exists")
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

    @GetMapping("/activity-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonActivityTypeIndicator> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            ActivityTypeIndicator activityTypeIndicator = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(FastJsonActivityTypeIndicator.of(activityTypeIndicator))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputActivityTypeIndicator webInputActivityTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            ActivityTypeIndicator activityTypeIndicator =
                    WebInputActivityTypeIndicator.toStackBean(webInputActivityTypeIndicator);
            StringIdKey insert = service.insert(activityTypeIndicator);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/activity-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityTypeIndicator webInputActivityTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputActivityTypeIndicator.toStackBean(webInputActivityTypeIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/activity-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-type-indicator/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonActivityTypeIndicator>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<ActivityTypeIndicator> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonActivityTypeIndicator> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
