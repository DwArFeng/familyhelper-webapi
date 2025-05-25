package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicDeriveInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticDeriveInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system.JSFixedFastJsonDeriveResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.DeriveResponse;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.system.DeriveResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
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
 * 派生控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class DeriveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeriveController.class);

    private final DeriveResponseService deriveResponseService;

    private final ServiceExceptionMapper sem;

    public DeriveController(
            DeriveResponseService deriveResponseService,
            TokenHandler tokenHandler,
            ServiceExceptionMapper sem
    ) {
        this.deriveResponseService = deriveResponseService;
        this.sem = sem;
    }

    @PostMapping("/derive/dynamic-derive")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDeriveResponse> dynamicDerive(
            HttpServletRequest request,
            @RequestBody @Validated WebInputDynamicDeriveInfo deriveInfo, BindingResult bindingResult
    ) {
        try {
            DeriveResponse deriveResponse = deriveResponseService.dynamicDerive(
                    WebInputDynamicDeriveInfo.toStackBean(deriveInfo)
            );
            JSFixedFastJsonDeriveResponse of = JSFixedFastJsonDeriveResponse.of(deriveResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/derive/static-derive")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDeriveResponse> staticDerive(
            HttpServletRequest request,
            @RequestBody @Validated WebInputStaticDeriveInfo deriveInfo, BindingResult bindingResult
    ) {
        try {
            DeriveResponse deriveResponse = deriveResponseService.staticDerive(
                    WebInputStaticDeriveInfo.toStackBean(deriveInfo)
            );
            JSFixedFastJsonDeriveResponse of = JSFixedFastJsonDeriveResponse.of(deriveResponse);
            return FastJsonResponseData.of(ResponseDataUtil.good(of));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
