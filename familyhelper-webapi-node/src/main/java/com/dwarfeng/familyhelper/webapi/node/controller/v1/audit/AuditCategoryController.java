package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditCategory;
import com.dwarfeng.audit.sdk.bean.entity.WebInputAuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditCategoryResponseService;
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
 * 审计类别控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class AuditCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditCategoryController.class);

    private final AuditCategoryResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<AuditCategory, FastJsonAuditCategory> beanTransformer;

    public AuditCategoryController(
            AuditCategoryResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<AuditCategory, FastJsonAuditCategory> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/audit-category/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_category.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/audit-category/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_category.get")
    public FastJsonResponseData<FastJsonAuditCategory> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            AuditCategory auditCategory = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAuditCategory.of(auditCategory)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/audit-category")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_category.insert")
    @AuditRecord("webapi.audit.audit_category.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.auditCategoryInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "audit.auditCategoryInsertAuditCategoryParamContributor",
                    prefix = "param.audit_category."
            )
            WebInputAuditCategory auditCategory,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey insert = service.insert(WebInputAuditCategory.toStackBean(auditCategory));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/audit-category")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_category.update")
    @AuditRecord("webapi.audit.audit_category.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.auditCategoryUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "audit.auditCategoryUpdateAuditCategoryParamContributor",
                    prefix = "param.audit_category."
            )
            WebInputAuditCategory auditCategory,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputAuditCategory.toStackBean(auditCategory));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/audit-category/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_category.delete")
    @AuditRecord("webapi.audit.audit_category.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.auditCategoryDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("id")
            @AuditParamContribute(
                    contributor = "audit.auditCategoryDeleteIdParamContributor",
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

    @GetMapping("/audit-category/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_category.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonAuditCategory>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<AuditCategory> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonAuditCategory> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
