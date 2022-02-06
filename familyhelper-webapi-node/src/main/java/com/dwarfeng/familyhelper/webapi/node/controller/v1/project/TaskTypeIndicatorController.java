package com.dwarfeng.familyhelper.webapi.node.controller.v1.project;

import com.dwarfeng.familyhelper.project.sdk.bean.entity.FastJsonTaskTypeIndicator;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.WebInputTaskTypeIndicator;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TaskTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.service.project.TaskTypeIndicatorResponseService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目类型指示器控制器。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
@RestController
@RequestMapping("/api/v1/project")
public class TaskTypeIndicatorController {

    private final TaskTypeIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<TaskTypeIndicator, FastJsonTaskTypeIndicator> beanTransformer;

    public TaskTypeIndicatorController(
            TaskTypeIndicatorResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<TaskTypeIndicator, FastJsonTaskTypeIndicator> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/task-type-indicator/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/task-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonTaskTypeIndicator> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            TaskTypeIndicator taskTypeIndicator = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonTaskTypeIndicator.of(taskTypeIndicator)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonTaskTypeIndicator.class, e, sem));
        }
    }

    @PostMapping("/task-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputTaskTypeIndicator webInputTaskTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            TaskTypeIndicator taskTypeIndicator = WebInputTaskTypeIndicator.toStackBean(webInputTaskTypeIndicator);
            StringIdKey insert = service.insert(taskTypeIndicator);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonStringIdKey.class, e, sem));
        }
    }

    @PatchMapping("/task-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputTaskTypeIndicator webInputTaskTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputTaskTypeIndicator.toStackBean(webInputTaskTypeIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/task-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/task-type-indicator/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonTaskTypeIndicator>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<TaskTypeIndicator> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonTaskTypeIndicator> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
