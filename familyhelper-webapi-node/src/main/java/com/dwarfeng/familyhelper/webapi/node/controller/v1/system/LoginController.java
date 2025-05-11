package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicLoginInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputLoginInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticLoginInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.JSFixedFastJsonLoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.LoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
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
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

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
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
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
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * 登录。
     *
     * <p>
     * 该方法已经被废弃，不再推荐使用。<br>
     * 请使用 {@link #dynamicLogin(HttpServletRequest, WebInputDynamicLoginInfo, BindingResult)} 或
     * {@link #staticLogin(HttpServletRequest, WebInputStaticLoginInfo, BindingResult)}。
     *
     * @param request       请求。
     * @param loginInfo     登录信息。
     * @param bindingResult 绑定结果。
     * @return 登录响应。
     * @deprecated 该方法已经被废弃，不再推荐使用。
     */
    @PostMapping("/login/login")
    @BehaviorAnalyse
    @BindingCheck
    @Deprecated
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> login(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLoginInfo loginInfo, BindingResult bindingResult
    ) {
        try {
            LoginResponse loginResponse = loginResponseService.login(WebInputLoginInfo.toStackBean(loginInfo));
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/login/dynamic-login")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> dynamicLogin(
            HttpServletRequest request,
            @RequestBody @Validated WebInputDynamicLoginInfo loginInfo, BindingResult bindingResult
    ) {
        try {
            LoginResponse loginResponse = loginResponseService.dynamicLogin(
                    WebInputDynamicLoginInfo.toStackBean(loginInfo)
            );
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/login/static-login")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> staticLogin(
            HttpServletRequest request,
            @RequestBody @Validated WebInputStaticLoginInfo loginInfo, BindingResult bindingResult
    ) {
        try {
            LoginResponse loginResponse = loginResponseService.staticLogin(
                    WebInputStaticLoginInfo.toStackBean(loginInfo)
            );
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
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
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
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
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
