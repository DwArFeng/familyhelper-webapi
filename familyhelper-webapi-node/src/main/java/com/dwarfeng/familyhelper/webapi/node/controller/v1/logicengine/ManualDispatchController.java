package com.dwarfeng.familyhelper.webapi.node.controller.v1.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.ManualDispatchResponseService;
import com.dwarfeng.logicengine.sdk.bean.dto.WebInputManualDispatchInfo;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditInvocationContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditParamContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecord;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditResultContribute;
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
 * 手动调度控制器。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@RestController
@RequestMapping("/api/v1/logicengine")
public class ManualDispatchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManualDispatchController.class);

    private final ManualDispatchResponseService service;
    private final ServiceExceptionMapper sem;

    public ManualDispatchController(ManualDispatchResponseService service, ServiceExceptionMapper sem) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/manual-dispatch/dispatch")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.manual_dispatch.dispatch")
    @AuditRecord("webapi.logicengine.manual_dispatch.dispatch")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.manualDispatchDispatchResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> dispatch(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "logicengine.manualDispatchDispatchManualDispatchInfoParamContributor",
                    prefix = "param.manual_dispatch_info."
            )
            WebInputManualDispatchInfo manualDispatchInfo,
            BindingResult bindingResult
    ) {
        try {
            service.dispatch(WebInputManualDispatchInfo.toStackBean(manualDispatchInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}