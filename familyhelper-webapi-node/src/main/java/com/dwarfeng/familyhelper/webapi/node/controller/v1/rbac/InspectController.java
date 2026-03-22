package com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.InspectResponseService;
import com.dwarfeng.rbacds.sdk.bean.dto.*;
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
 * 查看控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/rbac")
public class InspectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectController.class);

    private final InspectResponseService service;

    private final ServiceExceptionMapper sem;

    public InspectController(
            InspectResponseService service,
            ServiceExceptionMapper sem
    ) {
        this.service = service;
        this.sem = sem;
    }

    @PostMapping("/inspect/user-view-of-permission")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.inspect.inspect_user_view_of_permission")
    public FastJsonResponseData<FastJsonUserViewOfPermissionInspectResult> inspectUserViewOfPermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputUserViewOfPermissionInspectInfo webInput, BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonUserViewOfPermissionInspectResult.of(
                            service.inspectUserViewOfPermission(
                                    WebInputUserViewOfPermissionInspectInfo.toStackBean(webInput)
                            )
                    )
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/inspect/permission-view-of-role")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.inspect.inspect_permission_view_of_role")
    public FastJsonResponseData<FastJsonPermissionViewOfRoleInspectResult> inspectPermissionViewOfRole(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionViewOfRoleInspectInfo webInput, BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonPermissionViewOfRoleInspectResult.of(
                            service.inspectPermissionViewOfRole(
                                    WebInputPermissionViewOfRoleInspectInfo.toStackBean(webInput)
                            )
                    )
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/inspect/permission-view-of-user")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.inspect.inspect_permission_view_of_user")
    public FastJsonResponseData<FastJsonPermissionViewOfUserInspectResult> inspectPermissionViewOfUser(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionViewOfUserInspectInfo webInput, BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonPermissionViewOfUserInspectResult.of(
                            service.inspectPermissionViewOfUser(
                                    WebInputPermissionViewOfUserInspectInfo.toStackBean(webInput)
                            )
                    )
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
