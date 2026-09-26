package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspection;
import com.dwarfeng.audit.sdk.bean.entity.WebInputInspection;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
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
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 自动审计控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionController.class);

    private final InspectionResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Inspection, JSFixedFastJsonInspection> beanTransformer;

    public InspectionController(
            InspectionResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Inspection, JSFixedFastJsonInspection> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/inspection/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection.get")
    public FastJsonResponseData<JSFixedFastJsonInspection> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            Inspection inspection = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonInspection.of(inspection)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/inspection")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection.insert")
    @AuditRecord("webapi.audit.inspection.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.inspectionInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "audit.inspectionInsertInspectionParamContributor",
                    prefix = "param.inspection."
            )
            WebInputInspection inspection,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey insert = service.insert(WebInputInspection.toStackBean(inspection));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/inspection")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection.update")
    @AuditRecord("webapi.audit.inspection.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.inspectionUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "audit.inspectionUpdateInspectionParamContributor",
                    prefix = "param.inspection."
            )
            WebInputInspection inspection,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputInspection.toStackBean(inspection));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/inspection/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection.delete")
    @AuditRecord("webapi.audit.inspection.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.inspectionDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("id")
            @AuditParamContribute(
                    contributor = "audit.inspectionDeleteIdParamContributor",
                    prefix = "param.id."
            )
            Long id
    ) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspection>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Inspection> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonInspection> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
