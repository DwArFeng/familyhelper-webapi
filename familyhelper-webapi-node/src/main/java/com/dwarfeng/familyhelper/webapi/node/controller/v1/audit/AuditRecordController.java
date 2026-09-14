package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputAuditRecordInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditRecordResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
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
 * 审计记录控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class AuditRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditRecordController.class);

    private final AuditRecordResponseService service;
    private final ServiceExceptionMapper sem;

    public AuditRecordController(AuditRecordResponseService service, ServiceExceptionMapper sem) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/audit-record/record")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.audit_record.record")
    public FastJsonResponseData<Object> record(
            HttpServletRequest request,
            @RequestBody @Validated WebInputAuditRecordInfo webInputAuditRecordInfo,
            BindingResult bindingResult
    ) {
        try {
            service.record(WebInputAuditRecordInfo.toStackBean(webInputAuditRecordInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
