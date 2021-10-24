package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputLoginInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system.JSFixedFastJsonLoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.LoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
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
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class LoginController {

    private final LoginResponseService loginResponseService;

    private final ServiceExceptionMapper sem;

    public LoginController(
            LoginResponseService loginResponseService,
            TokenHandler tokenHandler,
            ServiceExceptionMapper sem
    ) {
        this.loginResponseService = loginResponseService;
        this.sem = sem;
    }

    @PostMapping("/login/is-login")
    @BehaviorAnalyse
    @LoginRequired
    @BindingCheck
    public FastJsonResponseData<Boolean> isLogin(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey loginStateKey, BindingResult bindingResult
    ) {
        try {
            boolean result = loginResponseService.isLogin(WebInputLongIdKey.toStackBean(loginStateKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @PostMapping("/login/inspect-login-state")
    @BehaviorAnalyse
    @LoginRequired
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> inspectLoginState(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey loginStateKey, BindingResult bindingResult
    ) {
        try {
            LoginResponse loginResponse = loginResponseService.inspectLoginState(
                    WebInputLongIdKey.toStackBean(loginStateKey)
            );
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLoginResponse.class, e, sem));
        }
    }

    @PostMapping("/login/login")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> login(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLoginInfo loginInfo, BindingResult bindingResult
    ) {
        try {
            LoginResponse loginResponse = loginResponseService.login(WebInputLoginInfo.toStackBean(loginInfo));
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLoginResponse.class, e, sem));
        }
    }

    @PostMapping("/login/postpone")
    @BehaviorAnalyse
    @LoginRequired
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> postpone(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey loginStateKey, BindingResult bindingResult
    ) {
        try {
            LoginResponse loginResponse = loginResponseService.postpone(WebInputLongIdKey.toStackBean(loginStateKey));
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLoginResponse.class, e, sem));
        }
    }

    @PostMapping("/login/logout")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> logout(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey loginStateKey, BindingResult bindingResult
    ) {
        try {
            loginResponseService.logout(WebInputLongIdKey.toStackBean(loginStateKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
