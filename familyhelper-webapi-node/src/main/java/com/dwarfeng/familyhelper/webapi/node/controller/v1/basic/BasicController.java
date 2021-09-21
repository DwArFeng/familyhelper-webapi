package com.dwarfeng.familyhelper.webapi.node.controller.v1.basic;

import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.basic.JSFixedFastJsonLoginResponse;
import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.basic.WebInputLoginRequest;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.basic.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic.LoginRequest;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic.LoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.basic.Account;
import com.dwarfeng.familyhelper.webapi.stack.handler.basic.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.basic.BasicResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.dozer.Mapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public class BasicController {

    private final BasicResponseService basicResponseService;
    private final TokenHandler tokenHandler;
    private final Mapper mapper;
    private final ServiceExceptionMapper sem;

    public BasicController(
            BasicResponseService basicResponseService, TokenHandler tokenHandler, Mapper mapper,
            ServiceExceptionMapper sem
    ) {
        this.basicResponseService = basicResponseService;
        this.tokenHandler = tokenHandler;
        this.mapper = mapper;
        this.sem = sem;
    }

    @PostMapping("/login")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> login(
            @RequestBody @Validated WebInputLoginRequest loginRequest, BindingResult bindingResult) {
        try {
            LoginResponse loginResponse = basicResponseService.login(mapper.map(loginRequest, LoginRequest.class));
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLoginResponse.class, e, sem));
        }
    }

    @PostMapping("/postpone")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> postpone(HttpServletRequest request) {
        try {
            long tokenId = tokenHandler.getTokenId(request);
            basicResponseService.postpone(new LongIdKey(tokenId));
            LoginResponse loginResponse = basicResponseService.getLoginResponse(new LongIdKey(tokenId));
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLoginResponse.class, e, sem));
        }
    }

    @GetMapping("/my-login-state")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLoginResponse> myLoginState(HttpServletRequest request) {
        try {
            long tokenId = tokenHandler.getTokenId(request);
            LoginResponse loginResponse = basicResponseService.getLoginResponse(new LongIdKey(tokenId));
            JSFixedFastJsonLoginResponse of = JSFixedFastJsonLoginResponse.of(loginResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLoginResponse.class, e, sem));
        }
    }

    @GetMapping("/my-permissions")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<List<String>> myPermission(HttpServletRequest request) {
        try {
            long tokenId = tokenHandler.getTokenId(request);
            List<String> permissions = basicResponseService.getPermissions(new LongIdKey(tokenId));
            return FastJsonResponseData.of(ResponseDataUtil.good(permissions));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(List.class, e, sem));
        }
    }

    @PostMapping("/logout")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> logout(HttpServletRequest request) {
        try {
            long tokenId = tokenHandler.getTokenId(request);
            basicResponseService.logout(new LongIdKey(tokenId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/who-am-i")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonAccount> whoAmI(HttpServletRequest request) {
        try {
            long tokenId = tokenHandler.getTokenId(request);
            Account uiAccount = basicResponseService.whoAmI(new LongIdKey(tokenId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAccount.of(uiAccount)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
