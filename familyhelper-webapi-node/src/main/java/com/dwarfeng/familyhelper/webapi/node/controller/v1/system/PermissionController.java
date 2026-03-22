package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.FastJsonPermissionInspectResult;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectResult;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.system.PermissionResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 权限控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController("systemPermissionController")
@RequestMapping("/api/v1/system")
public class PermissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionResponseService service;

    private final ServiceExceptionMapper sem;

    private final TokenHandler tokenHandler;

    public PermissionController(
            PermissionResponseService service,
            ServiceExceptionMapper sem,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.tokenHandler = tokenHandler;
    }

    @PostMapping("/inspect-permission-of-me")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonPermissionInspectResult> inspectPermissionOfMe(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionOfMeInspectInfo webInput,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = new StringIdKey(tokenHandler.getUserId(request));
            PermissionInspectResult result = service.inspectPermission(
                    WebInputPermissionOfMeInspectInfo.toStackBean(webInput, accountKey)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPermissionInspectResult.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * 关于我的权限查看信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class WebInputPermissionOfMeInspectInfo implements Dto {

        private static final long serialVersionUID = -7829604820332831329L;

        public static PermissionInspectInfo toStackBean(
                WebInputPermissionOfMeInspectInfo webInput, StringIdKey accountKey
        ) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new PermissionInspectInfo(
                        WebInputStringIdKey.toStackBean(webInput.getScopeKey()),
                        accountKey
                );
            }
        }

        @JSONField(name = "scope_key")
        @NotNull
        @Valid
        private WebInputStringIdKey scopeKey;

        public WebInputPermissionOfMeInspectInfo() {
        }

        public WebInputStringIdKey getScopeKey() {
            return scopeKey;
        }

        public void setScopeKey(WebInputStringIdKey scopeKey) {
            this.scopeKey = scopeKey;
        }

        @Override
        public String toString() {
            return "WebInputPermissionOfMeInspectInfo{" +
                    "scopeKey=" + scopeKey +
                    '}';
        }
    }
}
