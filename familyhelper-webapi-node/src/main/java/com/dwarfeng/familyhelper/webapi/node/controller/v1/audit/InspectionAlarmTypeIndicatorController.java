package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionAlarmTypeIndicator;
import com.dwarfeng.audit.sdk.bean.entity.WebInputInspectionAlarmTypeIndicator;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionAlarmTypeIndicatorResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditInvocationContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditParamContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecord;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditResultContribute;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
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
 * 自动审计告警类型指示器控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectionAlarmTypeIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionAlarmTypeIndicatorController.class);

    private final InspectionAlarmTypeIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<InspectionAlarmTypeIndicator, FastJsonInspectionAlarmTypeIndicator> beanTransformer;

    public InspectionAlarmTypeIndicatorController(
            InspectionAlarmTypeIndicatorResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<InspectionAlarmTypeIndicator, FastJsonInspectionAlarmTypeIndicator> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/inspection-alarm-type-indicator/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm_type_indicator.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm_type_indicator.get")
    public FastJsonResponseData<FastJsonInspectionAlarmTypeIndicator> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(FastJsonInspectionAlarmTypeIndicator.of(inspectionAlarmTypeIndicator))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/inspection-alarm-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm_type_indicator.insert")
    @AuditRecord("webapi.audit.inspection_alarm_type_indicator.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(
            contributor = "audit.inspectionAlarmTypeIndicatorInsertResultContributor", prefix = "result."
    )
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "audit.inspectionAlarmTypeIndicatorInsertInspectionAlarmTypeIndicatorParamContributor",
                    prefix = "param.inspection_alarm_type_indicator."
            )
            WebInputInspectionAlarmTypeIndicator inspectionAlarmTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey insert = service.insert(
                    WebInputInspectionAlarmTypeIndicator.toStackBean(inspectionAlarmTypeIndicator)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/inspection-alarm-type-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm_type_indicator.update")
    @AuditRecord("webapi.audit.inspection_alarm_type_indicator.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(
            contributor = "audit.inspectionAlarmTypeIndicatorUpdateResultContributor", prefix = "result."
    )
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "audit.inspectionAlarmTypeIndicatorUpdateInspectionAlarmTypeIndicatorParamContributor",
                    prefix = "param.inspection_alarm_type_indicator."
            )
            WebInputInspectionAlarmTypeIndicator inspectionAlarmTypeIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputInspectionAlarmTypeIndicator.toStackBean(inspectionAlarmTypeIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/inspection-alarm-type-indicator/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm_type_indicator.delete")
    @AuditRecord("webapi.audit.inspection_alarm_type_indicator.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(
            contributor = "audit.inspectionAlarmTypeIndicatorDeleteResultContributor", prefix = "result."
    )
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("id")
            @AuditParamContribute(
                    contributor = "audit.inspectionAlarmTypeIndicatorDeleteIdParamContributor",
                    prefix = "param.id."
            )
            String id
    ) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-alarm-type-indicator/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_alarm_type_indicator.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonInspectionAlarmTypeIndicator>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionAlarmTypeIndicator> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonInspectionAlarmTypeIndicator> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
