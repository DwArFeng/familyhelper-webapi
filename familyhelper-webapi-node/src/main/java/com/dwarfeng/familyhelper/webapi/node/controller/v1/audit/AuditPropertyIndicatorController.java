package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonAuditPropertyIndicator;
import com.dwarfeng.audit.sdk.bean.entity.WebInputAuditPropertyIndicator;
import com.dwarfeng.audit.sdk.bean.key.FastJsonAuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispAuditPropertyIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditPropertyIndicator;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditPropertyIndicatorResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
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
 * 审计属性指示器控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class AuditPropertyIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditPropertyIndicatorController.class);

    private final AuditPropertyIndicatorResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<AuditPropertyIndicator, JSFixedFastJsonAuditPropertyIndicator> beanTransformer;
    private final BeanTransformer<DispAuditPropertyIndicator, JSFixedFastJsonDispAuditPropertyIndicator>
            dispBeanTransformer;

    public AuditPropertyIndicatorController(
            AuditPropertyIndicatorResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<AuditPropertyIndicator, JSFixedFastJsonAuditPropertyIndicator> beanTransformer,
            BeanTransformer<DispAuditPropertyIndicator, JSFixedFastJsonDispAuditPropertyIndicator>
                    dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/audit-property-indicator/{auditCategoryId}&{propertyId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @PathVariable("propertyId") String propertyId
    ) {
        try {
            AuditPropertyIndicatorKey key = new AuditPropertyIndicatorKey(auditCategoryId, propertyId);
            boolean exists = service.exists(key);
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-property-indicator/{auditCategoryId}&{propertyId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.get")
    public FastJsonResponseData<JSFixedFastJsonAuditPropertyIndicator> get(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @PathVariable("propertyId") String propertyId
    ) {
        try {
            AuditPropertyIndicatorKey key = new AuditPropertyIndicatorKey(auditCategoryId, propertyId);
            AuditPropertyIndicator auditPropertyIndicator = service.get(key);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonAuditPropertyIndicator.of(auditPropertyIndicator))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/audit-property-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.insert")
    public FastJsonResponseData<FastJsonAuditPropertyIndicatorKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputAuditPropertyIndicator webInputAuditPropertyIndicator,
            BindingResult bindingResult
    ) {
        try {
            AuditPropertyIndicator auditPropertyIndicator =
                    WebInputAuditPropertyIndicator.toStackBean(webInputAuditPropertyIndicator);
            AuditPropertyIndicatorKey insert = service.insert(auditPropertyIndicator);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(FastJsonAuditPropertyIndicatorKey.of(insert))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/audit-property-indicator")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputAuditPropertyIndicator webInputAuditPropertyIndicator,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputAuditPropertyIndicator.toStackBean(webInputAuditPropertyIndicator));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/audit-property-indicator/{auditCategoryId}&{propertyId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.delete")
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @PathVariable("propertyId") String propertyId
    ) {
        try {
            AuditPropertyIndicatorKey key = new AuditPropertyIndicatorKey(auditCategoryId, propertyId);
            service.delete(key);
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-property-indicator/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAuditPropertyIndicator>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditPropertyIndicator> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonAuditPropertyIndicator> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-property-indicator/{auditCategoryId}&{propertyId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispAuditPropertyIndicator> getDisp(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @PathVariable("propertyId") String propertyId
    ) {
        try {
            AuditPropertyIndicatorKey key = new AuditPropertyIndicatorKey(auditCategoryId, propertyId);
            DispAuditPropertyIndicator dispAuditPropertyIndicator = service.getDisp(key);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispAuditPropertyIndicator.of(dispAuditPropertyIndicator))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-property-indicator/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAuditPropertyIndicator>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispAuditPropertyIndicator> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispAuditPropertyIndicator> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-property-indicator/child-for-audit-category/{auditCategoryId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.child_for_audit_category_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAuditPropertyIndicator>>
    childForAuditCategoryDisp(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispAuditPropertyIndicator> childForAuditCategoryDisp = service.childForAuditCategoryDisp(
                    new StringIdKey(auditCategoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispAuditPropertyIndicator> transform = PagingUtil.transform(
                    childForAuditCategoryDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-property-indicator/child-for-audit-category/{auditCategoryId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_property_indicator.child_for_audit_category")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAuditPropertyIndicator>> childForAuditCategory(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditPropertyIndicator> childForAuditCategory = service.childForAuditCategory(
                    new StringIdKey(auditCategoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAuditPropertyIndicator> transform = PagingUtil.transform(
                    childForAuditCategory, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
