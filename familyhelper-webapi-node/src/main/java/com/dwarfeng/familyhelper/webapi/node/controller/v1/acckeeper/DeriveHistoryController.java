package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonDeriveHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.JSFixedFastJsonDispDeriveHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispDeriveHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.DeriveHistoryResponseService;
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
 * 派生历史控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class DeriveHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeriveHistoryController.class);

    private final DeriveHistoryResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<DeriveHistory, JSFixedFastJsonDeriveHistory> beanTransformer;
    private final BeanTransformer<DispDeriveHistory, JSFixedFastJsonDispDeriveHistory> dispBeanTransformer;

    public DeriveHistoryController(
            DeriveHistoryResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("acckeeper.deriveHistoryBeanTransformer")
            BeanTransformer<DeriveHistory, JSFixedFastJsonDeriveHistory> beanTransformer,
            @Qualifier("acckeeper.dispDeriveHistoryBeanTransformer")
            BeanTransformer<DispDeriveHistory, JSFixedFastJsonDispDeriveHistory> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/derive-history/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.get")
    public FastJsonResponseData<JSFixedFastJsonDeriveHistory> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DeriveHistory deriveHistory = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDeriveHistory.of(deriveHistory)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDeriveHistory>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DeriveHistory> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDeriveHistory> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/account-id-equals")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.account_id_equals")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDeriveHistory>> accountIdEquals(
            HttpServletRequest request,
            @RequestParam("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DeriveHistory> child = service.accountIdEquals(accountId, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDeriveHistory> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/account-id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.account_id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDeriveHistory>> accountIdLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DeriveHistory> child = service.accountIdLike(pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDeriveHistory> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispDeriveHistory> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispDeriveHistory disp = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispDeriveHistory.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispDeriveHistory>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispDeriveHistory> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispDeriveHistory> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/account-id-equals/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.account_id_equals_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispDeriveHistory>> accountIdEqualsDisp(
            HttpServletRequest request,
            @RequestParam("accountId") String accountId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispDeriveHistory> child = service.accountIdEqualsDisp(
                    accountId, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispDeriveHistory> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/account-id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.derive_history.account_id_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispDeriveHistory>> accountIdLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispDeriveHistory> child = service.accountIdLikeDisp(
                    pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispDeriveHistory> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
