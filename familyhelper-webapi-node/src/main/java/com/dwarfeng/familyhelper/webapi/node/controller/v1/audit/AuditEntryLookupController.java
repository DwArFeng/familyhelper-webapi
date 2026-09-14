package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.dto.JSFixedFastJsonAuditEntryLookupResult;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputAuditEntryCompositeLookupInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputAuditEntryGroupedLookupInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditEntryLookupResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 审计条目查询控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class AuditEntryLookupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditEntryLookupController.class);

    private final AuditEntryLookupResponseService service;
    private final ServiceExceptionMapper sem;

    public AuditEntryLookupController(AuditEntryLookupResponseService service, ServiceExceptionMapper sem) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/audit-entry-lookup/composite")
    @BehaviorAnalyse
    @SkipRecord
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_lookup.composite")
    public FastJsonResponseData<JSFixedFastJsonAuditEntryLookupResult> composite(
            HttpServletRequest request,
            @RequestBody @Validated
            WebInputAuditEntryCompositeLookupInfo webInputAuditEntryCompositeLookupInfo,
            BindingResult bindingResult
    ) {
        try {
            AuditEntryLookupResult lookupResult = service.lookupComposite(
                    WebInputAuditEntryCompositeLookupInfo.toStackBean(webInputAuditEntryCompositeLookupInfo)
            );
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonAuditEntryLookupResult.of(lookupResult))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/audit-entry-lookup/grouped")
    @BehaviorAnalyse
    @SkipRecord
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_entry_lookup.grouped")
    public FastJsonResponseData<JSFixedFastJsonAuditEntryLookupResult> grouped(
            HttpServletRequest request,
            @RequestBody @Validated
            WebInputAuditEntryGroupedLookupInfo webInputAuditEntryGroupedLookupInfo,
            BindingResult bindingResult
    ) {
        try {
            AuditEntryLookupResult lookupResult = service.lookupGrouped(
                    WebInputAuditEntryGroupedLookupInfo.toStackBean(webInputAuditEntryGroupedLookupInfo)
            );
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonAuditEntryLookupResult.of(lookupResult))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
