package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonDeriveHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.system.DeriveHistoryResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
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
 * 派生历史控制器。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
@RestController
@RequestMapping("/api/v1/system")
public class DeriveHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeriveHistoryController.class);

    private final DeriveHistoryResponseService deriveHistoryResponseService;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<DeriveHistory, JSFixedFastJsonDeriveHistory> beanTransformer;

    public DeriveHistoryController(
            DeriveHistoryResponseService deriveHistoryResponseService,
            ServiceExceptionMapper sem,
            BeanTransformer<DeriveHistory, JSFixedFastJsonDeriveHistory> beanTransformer
    ) {
        this.deriveHistoryResponseService = deriveHistoryResponseService;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/derive-history/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            boolean exists = deriveHistoryResponseService.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDeriveHistory> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DeriveHistory deriveHistory = deriveHistoryResponseService.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDeriveHistory.of(deriveHistory)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/all")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDeriveHistory>> all(
            HttpServletRequest request,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DeriveHistory> lookup = deriveHistoryResponseService.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDeriveHistory> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/derive-history/account-id-equals")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDeriveHistory>> accountIdEquals(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DeriveHistory> lookup = deriveHistoryResponseService.accountIdEquals(
                    pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDeriveHistory> transform = PagingUtil.transform(
                    lookup, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
