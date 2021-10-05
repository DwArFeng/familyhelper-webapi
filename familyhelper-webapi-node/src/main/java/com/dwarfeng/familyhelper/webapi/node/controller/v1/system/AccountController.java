package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system.WebInputPasswordResetInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system.WebInputPasswordUpdateInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system.WebInputRegisterInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.system.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.system.WebInputAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.system.Account;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 账号控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class AccountController {

    private final AccountResponseService accountResponseService;

    private final TokenHandler tokenHandler;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Account, FastJsonAccount> beanTransformer;

    public AccountController(
            AccountResponseService accountResponseService, TokenHandler tokenHandler, ServiceExceptionMapper sem,
            BeanTransformer<Account, FastJsonAccount> beanTransformer
    ) {
        this.accountResponseService = accountResponseService;
        this.tokenHandler = tokenHandler;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/account/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = accountResponseService.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/account/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonAccount> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            Account Account = accountResponseService.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAccount.of(Account)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonAccount.class, e, sem));
        }
    }

    @PatchMapping("/account")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputAccount Account, BindingResult bindingResult) {
        try {
            accountResponseService.update(WebInputAccount.toStackBean(Account));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/account/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            accountResponseService.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/account/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonAccount>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Account> all = accountResponseService.all(new PagingInfo(page, rows));
            PagedData<FastJsonAccount> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/account/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonAccount>> nameLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Account> all = accountResponseService.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonAccount> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @PostMapping("/account/register")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> register(
            HttpServletRequest request,
            @RequestBody @Validated WebInputRegisterInfo registerInfo, BindingResult bindingResult) {
        try {
            accountResponseService.register(WebInputRegisterInfo.toStackBean(registerInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/account/update-password")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updatePassword(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPasswordUpdateInfo passwordUpdateInfo, BindingResult bindingResult) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            accountResponseService.updatePassword(
                    accountKey, WebInputPasswordUpdateInfo.toStackBean(passwordUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAccount.of(null)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/account/reset-password")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> resetPassword(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPasswordResetInfo passwordResetInfo, BindingResult bindingResult) {
        try {
            accountResponseService.resetPassword(WebInputPasswordResetInfo.toStackBean(passwordResetInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAccount.of(null)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
