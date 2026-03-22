package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.FastJsonDynamicDeriveResult;
import com.dwarfeng.acckeeper.sdk.bean.dto.FastJsonStaticDeriveResult;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicDeriveInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticDeriveInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.DeriveResponseService;
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
 * 派生控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class DeriveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeriveController.class);

    private final DeriveResponseService service;

    private final ServiceExceptionMapper sem;

    public DeriveController(
            DeriveResponseService service,
            ServiceExceptionMapper sem
    ) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/derive/dynamic-derive")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive.dynamic_derive")
    public FastJsonResponseData<FastJsonDynamicDeriveResult> dynamicDerive(
            HttpServletRequest request,
            @RequestBody @Validated WebInputDynamicDeriveInfo webInput,
            BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonDynamicDeriveResult.of(service.dynamicDerive(WebInputDynamicDeriveInfo.toStackBean(webInput)))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/derive/static-derive")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive.static_derive")
    public FastJsonResponseData<FastJsonStaticDeriveResult> staticDerive(
            HttpServletRequest request,
            @RequestBody @Validated WebInputStaticDeriveInfo webInput,
            BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonStaticDeriveResult.of(service.staticDerive(WebInputStaticDeriveInfo.toStackBean(webInput)))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
