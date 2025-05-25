package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonLoginState;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginStateResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 账号控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class LoginStateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginStateController.class);

    private final LoginStateResponseService loginStateResponseService;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<LoginState, JSFixedFastJsonLoginState> beanTransformer;

    public LoginStateController(
            LoginStateResponseService loginStateResponseService,
            ServiceExceptionMapper sem,
            BeanTransformer<LoginState, JSFixedFastJsonLoginState> beanTransformer
    ) {
        this.loginStateResponseService = loginStateResponseService;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/login-state/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_state.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            boolean exists = loginStateResponseService.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-state/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_state.get")
    public FastJsonResponseData<JSFixedFastJsonLoginState> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            LoginState loginState = loginStateResponseService.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLoginState.of(loginState)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-state/all")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_state.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginState>> all(
            HttpServletRequest request,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginState> lookup = loginStateResponseService.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonLoginState> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/account/{accountId}/login-state", "/account//login-state"
    })
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_state.child_for_account")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginState>> childForAccount(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = null;
            if (Objects.nonNull(accountId)) {
                accountKey = new StringIdKey(accountId);
            }
            PagedData<LoginState> lookup = loginStateResponseService.childForAccount(
                    accountKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonLoginState> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/account/{accountId}/login-state/type-equals-dynamic", "/account//login-state/type-equals-dynamic"
    })
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_state.child_for_account_type_equals_dynamic")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginState>> childForAccountTypeEqualsDynamic(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = null;
            if (Objects.nonNull(accountId)) {
                accountKey = new StringIdKey(accountId);
            }
            PagedData<LoginState> lookup = loginStateResponseService.childForAccountTypeEqualsDynamic(
                    accountKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonLoginState> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/account/{accountId}/login-state/type-equals-static", "/account//login-state/type-equals-static"
    })
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_state.child_for_account_type_equals_static")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginState>> childForAccountTypeEqualsStatic(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = null;
            if (Objects.nonNull(accountId)) {
                accountKey = new StringIdKey(accountId);
            }
            PagedData<LoginState> lookup = loginStateResponseService.childForAccountTypeEqualsStatic(
                    accountKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonLoginState> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
