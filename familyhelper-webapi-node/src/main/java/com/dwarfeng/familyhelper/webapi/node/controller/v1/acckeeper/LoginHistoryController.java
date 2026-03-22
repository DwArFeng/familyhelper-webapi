package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonLoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.JSFixedFastJsonDispLoginHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.LoginHistoryResponseService;
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
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录历史控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class LoginHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHistoryController.class);

    private final LoginHistoryResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<LoginHistory, JSFixedFastJsonLoginHistory> beanTransformer;
    private final BeanTransformer<DispLoginHistory, JSFixedFastJsonDispLoginHistory> dispBeanTransformer;

    public LoginHistoryController(
            LoginHistoryResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("acckeeper.loginHistoryBeanTransformer")
            BeanTransformer<LoginHistory, JSFixedFastJsonLoginHistory> beanTransformer,
            @Qualifier("acckeeper.dispLoginHistoryBeanTransformer")
            BeanTransformer<DispLoginHistory, JSFixedFastJsonDispLoginHistory> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/login-history/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.get")
    public FastJsonResponseData<JSFixedFastJsonLoginHistory> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            LoginHistory loginHistory = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLoginHistory.of(loginHistory)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginHistory>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginHistory> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonLoginHistory> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/account-id-equals")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.account_id_equals")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginHistory>> accountIdEquals(
            HttpServletRequest request,
            @RequestParam("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginHistory> child = service.accountIdEquals(accountId, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonLoginHistory> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/account-id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.account_id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonLoginHistory>> accountIdLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LoginHistory> child = service.accountIdLike(pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonLoginHistory> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispLoginHistory> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispLoginHistory disp = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispLoginHistory.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginHistory>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginHistory> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispLoginHistory> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/account-id-equals/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.account_id_equals_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginHistory>> accountIdEqualsDisp(
            HttpServletRequest request,
            @RequestParam("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginHistory> child = service.accountIdEqualsDisp(
                    accountId, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispLoginHistory> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/login-history/account-id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.login_history.account_id_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispLoginHistory>> accountIdLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispLoginHistory> child = service.accountIdLikeDisp(
                    pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispLoginHistory> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
