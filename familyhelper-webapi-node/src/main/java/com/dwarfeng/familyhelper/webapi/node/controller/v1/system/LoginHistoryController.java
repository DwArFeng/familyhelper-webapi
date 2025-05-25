package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonLoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginHistoryResponseService;
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
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录历史控制器。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
@RestController
@RequestMapping("/api/v1/system")
public class LoginHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHistoryController.class);

    private final LoginHistoryResponseService loginHistoryResponseService;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<LoginHistory, JSFixedFastJsonLoginHistory> beanTransformer;

    public LoginHistoryController(
            LoginHistoryResponseService loginHistoryResponseService,
            ServiceExceptionMapper sem,
            BeanTransformer<LoginHistory, JSFixedFastJsonLoginHistory> beanTransformer
    ) {
        this.loginHistoryResponseService = loginHistoryResponseService;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/login-history/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_history.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            boolean exists = loginHistoryResponseService.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_history.get")
    public FastJsonResponseData<JSFixedFastJsonLoginHistory> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            LoginHistory loginHistory = loginHistoryResponseService.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLoginHistory.of(loginHistory)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/all")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_history.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginHistory>> all(
            HttpServletRequest request,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginHistory> lookup = loginHistoryResponseService.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonLoginHistory> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/account-id-equals")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.system.login_history.account_id_equals")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginHistory>> accountIdEquals(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginHistory> lookup = loginHistoryResponseService.accountIdEquals(
                    pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonLoginHistory> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
