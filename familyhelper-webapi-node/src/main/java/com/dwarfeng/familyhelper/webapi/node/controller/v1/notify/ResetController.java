package com.dwarfeng.familyhelper.webapi.node.controller.v1.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.ResetResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 重置控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController("notifyResetController")
@RequestMapping("/api/v1/notify")
public class ResetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetController.class);

    private final ResetResponseService service;

    private final ServiceExceptionMapper sem;

    public ResetController(ResetResponseService service, ServiceExceptionMapper sem) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/reset-route")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.reset.reset_route")
    public FastJsonResponseData<Object> resetRoute(HttpServletRequest request) {
        try {
            service.resetRoute();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/reset-dispatch")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.reset.reset_dispatch")
    public FastJsonResponseData<Object> resetDispatch(HttpServletRequest request) {
        try {
            service.resetDispatch();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/reset-send")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.notify.reset.reset_send")
    public FastJsonResponseData<Object> resetSend(HttpServletRequest request) {
        try {
            service.resetSend();
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
