package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonAuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispAuditEntryProperty;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntryProperty;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditEntryPropertyResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 审计条目属性控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class AuditEntryPropertyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditEntryPropertyController.class);

    private final AuditEntryPropertyResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<AuditEntryProperty, JSFixedFastJsonAuditEntryProperty> beanTransformer;
    private final BeanTransformer<DispAuditEntryProperty, JSFixedFastJsonDispAuditEntryProperty>
            dispBeanTransformer;

    public AuditEntryPropertyController(
            AuditEntryPropertyResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<AuditEntryProperty, JSFixedFastJsonAuditEntryProperty> beanTransformer,
            BeanTransformer<DispAuditEntryProperty, JSFixedFastJsonDispAuditEntryProperty> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/audit-entry-property/{auditEntryId}&{propertyId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_property.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("auditEntryId") Long auditEntryId,
            @PathVariable("propertyId") String propertyId
    ) {
        try {
            AuditEntryPropertyKey key = new AuditEntryPropertyKey(auditEntryId, propertyId);
            boolean exists = service.exists(key);
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry-property/{auditEntryId}&{propertyId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_property.get")
    public FastJsonResponseData<JSFixedFastJsonAuditEntryProperty> get(
            HttpServletRequest request,
            @PathVariable("auditEntryId") Long auditEntryId,
            @PathVariable("propertyId") String propertyId
    ) {
        try {
            AuditEntryPropertyKey key = new AuditEntryPropertyKey(auditEntryId, propertyId);
            AuditEntryProperty auditEntryProperty = service.get(key);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonAuditEntryProperty.of(auditEntryProperty))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry-property/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_property.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAuditEntryProperty>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditEntryProperty> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonAuditEntryProperty> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry-property/child-for-audit-entry/{auditEntryId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_property.child_for_audit_entry")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAuditEntryProperty>> childForAuditEntry(
            HttpServletRequest request,
            @PathVariable("auditEntryId") Long auditEntryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditEntryProperty> childForAuditEntry = service.childForAuditEntry(
                    new LongIdKey(auditEntryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAuditEntryProperty> transform = PagingUtil.transform(
                    childForAuditEntry, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry-property/{auditEntryId}&{propertyId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_property.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispAuditEntryProperty> getDisp(
            HttpServletRequest request,
            @PathVariable("auditEntryId") Long auditEntryId,
            @PathVariable("propertyId") String propertyId
    ) {
        try {
            AuditEntryPropertyKey key = new AuditEntryPropertyKey(auditEntryId, propertyId);
            DispAuditEntryProperty dispAuditEntryProperty = service.getDisp(key);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispAuditEntryProperty.of(dispAuditEntryProperty))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry-property/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_property.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAuditEntryProperty>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispAuditEntryProperty> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispAuditEntryProperty> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry-property/child-for-audit-entry/{auditEntryId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_property.child_for_audit_entry_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAuditEntryProperty>>
    childForAuditEntryDisp(
            HttpServletRequest request,
            @PathVariable("auditEntryId") Long auditEntryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispAuditEntryProperty> childForAuditEntryDisp = service.childForAuditEntryDisp(
                    new LongIdKey(auditEntryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispAuditEntryProperty> transform = PagingUtil.transform(
                    childForAuditEntryDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
