package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.dto.WebInputInspectionJobExecuteInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionJobResponseService;
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
 * 自动审计作业控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectionJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionJobController.class);

    private final InspectionJobResponseService service;
    private final ServiceExceptionMapper sem;

    public InspectionJobController(InspectionJobResponseService service, ServiceExceptionMapper sem) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/inspection-job/execute")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_job.execute")
    @AuditRecord("webapi.audit.inspection_job.execute")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.inspectionJobExecuteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> execute(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "audit.inspectionJobExecuteInspectionJobExecuteInfoParamContributor",
                    prefix = "param.inspection_job_execute_info."
            )
            WebInputInspectionJobExecuteInfo inspectionJobExecuteInfo,
            BindingResult bindingResult
    ) {
        try {
            service.execute(WebInputInspectionJobExecuteInfo.toStackBean(inspectionJobExecuteInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
