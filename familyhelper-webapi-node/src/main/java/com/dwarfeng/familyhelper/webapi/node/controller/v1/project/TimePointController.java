package com.dwarfeng.familyhelper.webapi.node.controller.v1.project;

import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputTimePointCreateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputTimePointUpdateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonTimePoint;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TimePoint;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.project.TimePointResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 任务控制器。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
@RestController
@RequestMapping("/api/v1/project")
public class TimePointController {

    private final TimePointResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<TimePoint, JSFixedFastJsonTimePoint> beanTransformer;

    private final TokenHandler tokenHandler;

    public TimePointController(
            TimePointResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<TimePoint, JSFixedFastJsonTimePoint> beanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/time-point/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/time-point/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonTimePoint> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            TimePoint timePoint = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonTimePoint.of(timePoint)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonTimePoint.class, e, sem));
        }
    }

    @GetMapping("/time-point/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTimePoint>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<TimePoint> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTimePoint> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/task/{taskId}/time-point", "/task//time-point"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTimePoint>> childForTask(
            @PathVariable(required = false, value = "taskId") Long taskId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey taskKey = null;
            if (Objects.nonNull(taskId)) {
                taskKey = new LongIdKey(taskId);
            }
            PagedData<TimePoint> childForTask = service.childForTask(
                    taskKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTimePoint> transform = PagingUtil.transform(
                    childForTask, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @PostMapping("/time-point/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createTimePoint(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTimePointCreateInfo timePointCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createTimePoint(
                    accountKey, WebInputTimePointCreateInfo.toStackBean(timePointCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/time-point/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateTimePoint(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTimePointUpdateInfo webInputTimePointUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateTimePoint(
                    accountKey, WebInputTimePointUpdateInfo.toStackBean(webInputTimePointUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/time-point/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeTimePoint(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey timePointKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeTimePoint(accountKey, WebInputLongIdKey.toStackBean(timePointKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/time-point/finish")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> finishTimePoint(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey timePointKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.finishTimePoint(accountKey, WebInputLongIdKey.toStackBean(timePointKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
