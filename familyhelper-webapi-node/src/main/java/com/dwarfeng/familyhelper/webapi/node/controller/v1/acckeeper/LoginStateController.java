package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginState;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.JSFixedFastJsonDispLoginState;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginState;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.LoginStateResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录状态控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class LoginStateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginStateController.class);

    private final LoginStateResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<LoginState, FastJsonLoginState> beanTransformer;
    private final BeanTransformer<DispLoginState, JSFixedFastJsonDispLoginState> dispBeanTransformer;

    public LoginStateController(
            LoginStateResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("acckeeper.loginStateBeanTransformer")
            BeanTransformer<LoginState, FastJsonLoginState> beanTransformer,
            @Qualifier("acckeeper.dispLoginStateBeanTransformer")
            BeanTransformer<DispLoginState, JSFixedFastJsonDispLoginState> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/login-state/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-state/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.get")
    public FastJsonResponseData<FastJsonLoginState> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            LoginState loginState = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLoginState.of(loginState)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-state/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonLoginState>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginState> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonLoginState> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{accountId}/login-state")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.child_for_account")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonLoginState>> childForAccount(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginState> child = service.childForAccount(
                    new StringIdKey(accountId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonLoginState> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{accountId}/login-state/type-dynamic")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_dynamic")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonLoginState>> childForAccountTypeEqualsDynamic(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginState> child = service.childForAccountTypeEqualsDynamic(
                    new StringIdKey(accountId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonLoginState> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{accountId}/login-state/type-static")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_static")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonLoginState>> childForAccountTypeEqualsStatic(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginState> child = service.childForAccountTypeEqualsStatic(
                    new StringIdKey(accountId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonLoginState> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-state/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispLoginState> getDisp(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            DispLoginState disp = service.getDisp(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispLoginState.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-state/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginState>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginState> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispLoginState> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{accountId}/login-state/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.child_for_account_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginState>> childForAccountDisp(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginState> child = service.childForAccountDisp(
                    new StringIdKey(accountId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispLoginState> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{accountId}/login-state/type-dynamic/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_dynamic_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginState>>
    childForAccountTypeEqualsDynamicDisp(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginState> child = service.childForAccountTypeEqualsDynamicDisp(
                    new StringIdKey(accountId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispLoginState> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{accountId}/login-state/type-static/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_state.child_for_account_type_equals_static_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginState>>
    childForAccountTypeEqualsStaticDisp(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginState> child = service.childForAccountTypeEqualsStaticDisp(
                    new StringIdKey(accountId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispLoginState> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
