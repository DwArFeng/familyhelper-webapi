package com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.ResetResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 重置控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController("rbacResetController")
@RequestMapping("/api/v1/rbac")
public class ResetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetController.class);

    private final ResetResponseService service;

    private final ServiceExceptionMapper sem;

    public ResetController(
            @Qualifier("rbacResetResponseServiceImpl") ResetResponseService service,
            ServiceExceptionMapper sem
    ) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/reset-filter")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.reset.reset_filter")
    public FastJsonResponseData<Object> resetFilter(HttpServletRequest request) {
        try {
            service.resetFilter();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/reset-analysis")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.reset.reset_analysis")
    public FastJsonResponseData<Object> resetAnalysis(HttpServletRequest request) {
        try {
            service.resetAnalysis();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
