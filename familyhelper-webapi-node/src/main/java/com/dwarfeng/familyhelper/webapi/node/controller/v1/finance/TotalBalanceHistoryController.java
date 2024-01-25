package com.dwarfeng.familyhelper.webapi.node.controller.v1.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.entity.JSFixedFastJsonTotalBalanceHistory;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.TotalBalanceHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.TotalBalanceHistoryResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
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
import java.util.Objects;

/**
 * 总余额控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/finance")
public class TotalBalanceHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TotalBalanceHistoryController.class);

    private final TotalBalanceHistoryResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<TotalBalanceHistory, JSFixedFastJsonTotalBalanceHistory>
            totalBalanceHistoryBeanTransformer;

    public TotalBalanceHistoryController(
            TotalBalanceHistoryResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<TotalBalanceHistory, JSFixedFastJsonTotalBalanceHistory> totalBalanceHistoryBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.totalBalanceHistoryBeanTransformer = totalBalanceHistoryBeanTransformer;
    }

    @GetMapping("/total-balance-history/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/total-balance-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonTotalBalanceHistory> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            TotalBalanceHistory totalBalanceHistory = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonTotalBalanceHistory.of(totalBalanceHistory))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/total-balance-history/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTotalBalanceHistory>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<TotalBalanceHistory> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTotalBalanceHistory> transform = PagingUtil.transform(
                    all, totalBalanceHistoryBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/total-balance-history", "/account-book//total-balance-history"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTotalBalanceHistory>> childForAccountBook(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<TotalBalanceHistory> childForAccountBook = service.childForAccountBook(
                    accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTotalBalanceHistory> transform = PagingUtil.transform(
                    childForAccountBook, totalBalanceHistoryBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/total-balance-history/desc", "/account-book//total-balance-history/desc"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonTotalBalanceHistory>> childForAccountBookDesc(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<TotalBalanceHistory> childForAccountBook = service.childForAccountBookDesc(
                    accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonTotalBalanceHistory> transform = PagingUtil.transform(
                    childForAccountBook, totalBalanceHistoryBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
