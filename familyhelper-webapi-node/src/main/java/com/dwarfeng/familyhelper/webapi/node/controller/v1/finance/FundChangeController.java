package com.dwarfeng.familyhelper.webapi.node.controller.v1.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.dto.WebInputFundChangeRecordInfo;
import com.dwarfeng.familyhelper.finance.sdk.bean.dto.WebInputFundChangeUpdateInfo;
import com.dwarfeng.familyhelper.finance.sdk.bean.entity.JSFixedFastJsonFundChange;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChange;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispFundChange;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispFundChange;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.FundChangeResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
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
 * 资金变更控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/finance")
public class FundChangeController {

    private final FundChangeResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<FundChange, JSFixedFastJsonFundChange> fundChangeBeanTransformer;
    private final BeanTransformer<DispFundChange, JSFixedFastJsonDispFundChange> dispFundChangeBeanTransformer;

    private final TokenHandler tokenHandler;

    public FundChangeController(
            FundChangeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<FundChange, JSFixedFastJsonFundChange> fundChangeBeanTransformer,
            BeanTransformer<DispFundChange, JSFixedFastJsonDispFundChange> dispFundChangeBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.fundChangeBeanTransformer = fundChangeBeanTransformer;
        this.dispFundChangeBeanTransformer = dispFundChangeBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/fund-change/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/fund-change/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonFundChange> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            FundChange fundChange = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonFundChange.of(fundChange)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonFundChange.class, e, sem));
        }
    }

    @GetMapping("/fund-change/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonFundChange>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<FundChange> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonFundChange> transform = PagingUtil.transform(all, fundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change", "/account-book//fund-change"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonFundChange>> childForAccountBook(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<FundChange> childForAccountBook = service.childForAccountBook(
                    accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonFundChange> transform = PagingUtil.transform(
                    childForAccountBook, fundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change/desc", "/account-book//fund-change/desc"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonFundChange>> childForAccountBookDesc(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<FundChange> childForAccountBook = service.childForAccountBookDesc(
                    accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonFundChange> transform = PagingUtil.transform(
                    childForAccountBook, fundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change/type-equals", "/account-book//fund-change/type-equals"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonFundChange>> childForAccountBookTypeEquals(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<FundChange> childForAccountBook = service.childForAccountBookTypeEquals(
                    accountBookKey, pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonFundChange> transform = PagingUtil.transform(
                    childForAccountBook, fundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change/type-equals-desc",
            "/account-book//fund-change/type-equals-desc"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonFundChange>> childForAccountBookTypeEqualsDesc(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<FundChange> childForAccountBook = service.childForAccountBookTypeEqualsDesc(
                    accountBookKey, pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonFundChange> transform = PagingUtil.transform(
                    childForAccountBook, fundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/fund-change/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispFundChange> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispFundChange dispFundChange = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispFundChange.of(dispFundChange)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonDispFundChange.class, e, sem));
        }
    }

    @GetMapping("/fund-change/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispFundChange>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispFundChange> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispFundChange> transform = PagingUtil.transform(
                    allDisp, dispFundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change/disp", "/account-book//fund-change/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispFundChange>> childForAccountBookDisp(
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
            PagedData<DispFundChange> childForAccountBookDisp = service.childForAccountBookDisp(
                    accountKey, accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispFundChange> transform = PagingUtil.transform(
                    childForAccountBookDisp, dispFundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change/desc/disp", "/account-book//fund-change/desc/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispFundChange>> childForAccountBookDescDisp(
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
            PagedData<DispFundChange> childForAccountBookDisp = service.childForAccountBookDescDisp(
                    accountKey, accountBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispFundChange> transform = PagingUtil.transform(
                    childForAccountBookDisp, dispFundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change/type-equals/disp",
            "/account-book//fund-change/type-equals/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispFundChange>>
    childForAccountBookTypeEqualsDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<DispFundChange> childForAccountBookDisp = service.childForAccountBookTypeEqualsDisp(
                    accountKey, accountBookKey, pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispFundChange> transform = PagingUtil.transform(
                    childForAccountBookDisp, dispFundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/account-book/{accountBookId}/fund-change/type-equals-desc/disp",
            "/account-book//fund-change/type-equals-desc/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispFundChange>>
    childForAccountBookTypeEqualsDescDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            PagedData<DispFundChange> childForAccountBookDisp = service.childForAccountBookTypeEqualsDescDisp(
                    accountKey, accountBookKey, pattern, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispFundChange> transform = PagingUtil.transform(
                    childForAccountBookDisp, dispFundChangeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @PostMapping(value = {
            "/account-book/{accountBookId}/fund-change/record", "/account-book//fund-change/record"
    })
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> recordFundChange(
            HttpServletRequest request,
            @PathVariable(required = false, value = "accountBookId") Long accountBookId,
            @RequestBody @Validated WebInputFundChangeRecordInfo fundChangeRecordInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey accountBookKey = null;
            if (Objects.nonNull(accountBookId)) {
                accountBookKey = new LongIdKey(accountBookId);
            }
            LongIdKey result = service.recordFundChange(
                    accountKey, accountBookKey, WebInputFundChangeRecordInfo.toStackBean(fundChangeRecordInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/fund-change/{id}/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateFundChange(
            HttpServletRequest request, @PathVariable("id") Long id,
            @RequestBody @Validated WebInputFundChangeUpdateInfo webInputFundChangeUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateFundChange(
                    accountKey, new LongIdKey(id),
                    WebInputFundChangeUpdateInfo.toStackBean(webInputFundChangeUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/fund-change/{id}/remove")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> removeFundChange(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeFundChange(accountKey, new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
