package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.*;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.AccessResponseService;
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
 * 访问控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class AccessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessController.class);

    private final AccessResponseService service;

    private final ServiceExceptionMapper sem;

    public AccessController(
            AccessResponseService service,
            ServiceExceptionMapper sem
    ) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/access/dynamic-login")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonDynamicLoginResult> dynamicLogin(
            HttpServletRequest request,
            @RequestBody @Validated WebInputDynamicLoginInfo webInput,
            BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonDynamicLoginResult.of(service.dynamicLogin(WebInputDynamicLoginInfo.toStackBean(webInput)))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/access/static-login")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonStaticLoginResult> staticLogin(
            HttpServletRequest request,
            @RequestBody @Validated WebInputStaticLoginInfo webInput,
            BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonStaticLoginResult.of(service.staticLogin(WebInputStaticLoginInfo.toStackBean(webInput)))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/access/logout")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.access.logout")
    public FastJsonResponseData<Object> logout(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLogoutInfo webInput,
            BindingResult bindingResult
    ) {
        try {
            service.logout(WebInputLogoutInfo.toStackBean(webInput));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/access/postpone")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.access.postpone")
    public FastJsonResponseData<FastJsonPostponeResult> postpone(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPostponeInfo webInput,
            BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonPostponeResult.of(service.postpone(WebInputPostponeInfo.toStackBean(webInput)))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
