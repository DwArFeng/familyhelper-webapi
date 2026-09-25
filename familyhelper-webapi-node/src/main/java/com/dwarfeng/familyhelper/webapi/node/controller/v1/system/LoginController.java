package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.FastJsonLoginResult;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.FastJsonPostponeResult;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.WebInputLoginInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.LogoutInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PostponeInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PostponeResult;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditInvocationContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditParamContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecord;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditResultContribute;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
 * 登录控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final LoginResponseService service;

    private final ServiceExceptionMapper sem;

    private final TokenHandler tokenHandler;

    public LoginController(
            LoginResponseService service,
            ServiceExceptionMapper sem,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.tokenHandler = tokenHandler;
    }

    @PostMapping("/login")
    @BehaviorAnalyse
    @BindingCheck
    @AuditRecord("webapi.system.login.login")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "system.loginLoginResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonLoginResult> login(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "system.loginLoginParamContributor", prefix = "param.login_info."
            )
            WebInputLoginInfo loginInfo,
            BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonLoginResult.of(service.login(WebInputLoginInfo.toStackBean(loginInfo)))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/logout-me")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> logoutMe(HttpServletRequest request) {
        try {
            StringIdKey loginStateKey = new StringIdKey(tokenHandler.getLoginId(request));
            service.logout(new LogoutInfo(loginStateKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/postpone-me")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonPostponeResult> postponeMe(HttpServletRequest request) {
        try {
            StringIdKey loginStateKey = new StringIdKey(tokenHandler.getLoginId(request));
            PostponeResult result = service.postpone(new PostponeInfo(loginStateKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPostponeResult.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
