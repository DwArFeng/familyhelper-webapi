package com.dwarfeng.familyhelper.webapi.node.controller.v1.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.dto.WebInputBankCardBalanceRecordInfo;
import com.dwarfeng.familyhelper.finance.sdk.bean.dto.WebInputBankCardCreateInfo;
import com.dwarfeng.familyhelper.finance.sdk.bean.dto.WebInputBankCardUpdateInfo;
import com.dwarfeng.familyhelper.finance.sdk.bean.entity.JSFixedFastJsonBankCard;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCard;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispBankCard;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispBankCard;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.BankCardResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 账本控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/finance")
public class BankCardController {

    private final BankCardResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<BankCard, JSFixedFastJsonBankCard> bankCardBeanTransformer;
    private final BeanTransformer<DispBankCard, JSFixedFastJsonDispBankCard> dispBankCardBeanTransformer;

    private final TokenHandler tokenHandler;

    public BankCardController(
            BankCardResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<BankCard, JSFixedFastJsonBankCard> bankCardBeanTransformer,
            BeanTransformer<DispBankCard, JSFixedFastJsonDispBankCard> dispBankCardBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.bankCardBeanTransformer = bankCardBeanTransformer;
        this.dispBankCardBeanTransformer = dispBankCardBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/bank-card/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/bank-card/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonBankCard> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            BankCard bankCard = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonBankCard.of(bankCard)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/bank-card/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBankCard>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<BankCard> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonBankCard> transform = PagingUtil.transform(all, bankCardBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/bank-card", "/account-book//bank-card"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBankCard>> childForAccountBook(
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<BankCard> childForAccountBook = service.childForAccountBook(
                    accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonBankCard> transform = PagingUtil.transform(
                    childForAccountBook, bankCardBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/bank-card/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispBankCard> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispBankCard dispBankCard = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispBankCard.of(dispBankCard)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/bank-card/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispBankCard>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispBankCard> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispBankCard> transform = PagingUtil.transform(
                    allDisp, dispBankCardBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/bank-card/disp", "/account-book//bank-card/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispBankCard>> childForAccountBookDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<DispBankCard> childForAccountBookDisp = service.childForAccountBookDisp(
                    accountKey, accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispBankCard> transform = PagingUtil.transform(
                    childForAccountBookDisp, dispBankCardBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/bank-card/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createBankCard(
            HttpServletRequest request,
            @RequestBody @Validated WebInputBankCardCreateInfo bankCardCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createBankCard(
                    accountKey, WebInputBankCardCreateInfo.toStackBean(bankCardCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/bank-card/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateBankCard(
            HttpServletRequest request,
            @RequestBody @Validated WebInputBankCardUpdateInfo webInputBankCardUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateBankCard(
                    accountKey, WebInputBankCardUpdateInfo.toStackBean(webInputBankCardUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/bank-card/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeBankCard(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey bankCardKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeBankCard(accountKey, WebInputLongIdKey.toStackBean(bankCardKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/bank-card/record-balance")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> recordBalance(
            HttpServletRequest request,
            @RequestBody @Validated WebInputBankCardBalanceRecordInfo bankCardBalanceRecordInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.recordBalance(
                    accountKey, WebInputBankCardBalanceRecordInfo.toStackBean(bankCardBalanceRecordInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/bank-card/rollback-balance")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> rollbackBalance(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey bankCardKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.rollbackBalance(accountKey, WebInputLongIdKey.toStackBean(bankCardKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
