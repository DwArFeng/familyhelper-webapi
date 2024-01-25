package com.dwarfeng.familyhelper.webapi.node.controller.v1.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.entity.JSFixedFastJsonBankCardBalanceHistory;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCardBalanceHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.BankCardBalanceHistoryResponseService;
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
 * 银行卡余额控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/finance")
public class BankCardBalanceHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankCardBalanceHistoryController.class);

    private final BankCardBalanceHistoryResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<BankCardBalanceHistory, JSFixedFastJsonBankCardBalanceHistory>
            bankCardBalanceHistoryBeanTransformer;

    public BankCardBalanceHistoryController(
            BankCardBalanceHistoryResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<BankCardBalanceHistory, JSFixedFastJsonBankCardBalanceHistory>
                    bankCardBalanceHistoryBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.bankCardBalanceHistoryBeanTransformer = bankCardBalanceHistoryBeanTransformer;
    }

    @GetMapping("/bank-card-balance-history/{id}/exists")
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

    @GetMapping("/bank-card-balance-history/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonBankCardBalanceHistory> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            BankCardBalanceHistory bankCardBalanceHistory = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonBankCardBalanceHistory.of(bankCardBalanceHistory))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/bank-card-balance-history/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBankCardBalanceHistory>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<BankCardBalanceHistory> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonBankCardBalanceHistory> transform = PagingUtil.transform(
                    all, bankCardBalanceHistoryBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/bank-card/{bankCardId}/bank-card-balance-history", "/bank-card//bank-card-balance-history"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBankCardBalanceHistory>> childForBankCard(
            HttpServletRequest request,
            @PathVariable(required = false, value = "bankCardId") Long bankCardId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey bankCardKey = null;
            if (Objects.nonNull(bankCardId)) {
                bankCardKey = new LongIdKey(bankCardId);
            }
            PagedData<BankCardBalanceHistory> childForBankCard = service.childForBankCard(
                    bankCardKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonBankCardBalanceHistory> transform = PagingUtil.transform(
                    childForBankCard, bankCardBalanceHistoryBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/bank-card/{bankCardId}/bank-card-balance-history/desc", "/bank-card//bank-card-balance-history/desc"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBankCardBalanceHistory>> childForBankCardDesc(
            HttpServletRequest request,
            @PathVariable(required = false, value = "bankCardId") Long bankCardId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey bankCardKey = null;
            if (Objects.nonNull(bankCardId)) {
                bankCardKey = new LongIdKey(bankCardId);
            }
            PagedData<BankCardBalanceHistory> childForBankCard = service.childForBankCardDesc(
                    bankCardKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonBankCardBalanceHistory> transform = PagingUtil.transform(
                    childForBankCard, bankCardBalanceHistoryBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
