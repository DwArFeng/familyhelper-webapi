package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonAuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispAuditEntry;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntry;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditEntryResponseService;
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
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 审计条目控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class AuditEntryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditEntryController.class);

    private final AuditEntryResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<AuditEntry, JSFixedFastJsonAuditEntry> beanTransformer;
    private final BeanTransformer<DispAuditEntry, JSFixedFastJsonDispAuditEntry> dispBeanTransformer;

    public AuditEntryController(
            AuditEntryResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<AuditEntry, JSFixedFastJsonAuditEntry> beanTransformer,
            BeanTransformer<DispAuditEntry, JSFixedFastJsonDispAuditEntry> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/audit-entry/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.get")
    public FastJsonResponseData<JSFixedFastJsonAuditEntry> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            AuditEntry auditEntry = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonAuditEntry.of(auditEntry)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAuditEntry>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditEntry> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonAuditEntry> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/created-date-desc")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.created_date_desc")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAuditEntry>> createdDateDesc(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditEntry> createdDateDesc = service.createdDateDesc(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonAuditEntry> transform = PagingUtil.transform(createdDateDesc, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/child-for-audit-category/{auditCategoryId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.child_for_audit_category")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAuditEntry>> childForAuditCategory(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditEntry> childForAuditCategory = service.childForAuditCategory(
                    new StringIdKey(auditCategoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonAuditEntry> transform = PagingUtil.transform(childForAuditCategory, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispAuditEntry> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispAuditEntry dispAuditEntry = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispAuditEntry.of(dispAuditEntry))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAuditEntry>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispAuditEntry> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispAuditEntry> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/created-date-desc/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.created_date_desc_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAuditEntry>> createdDateDescDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispAuditEntry> createdDateDescDisp = service.createdDateDescDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispAuditEntry> transform = PagingUtil.transform(
                    createdDateDescDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-entry/child-for-audit-category/{auditCategoryId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry.child_for_audit_category_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispAuditEntry>>
    childForAuditCategoryDisp(
            HttpServletRequest request,
            @PathVariable("auditCategoryId") String auditCategoryId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispAuditEntry> childForAuditCategoryDisp = service.childForAuditCategoryDisp(
                    new StringIdKey(auditCategoryId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispAuditEntry> transform = PagingUtil.transform(
                    childForAuditCategoryDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
