package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputAccountRegisterInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputAccountUpdateInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputPasswordResetInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputPasswordUpdateInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.vo.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.sdk.cna.ValidateList;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.vo.Account;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 账号控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountResponseService accountResponseService;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Account, FastJsonAccount> accountBeanTransformer;
    private final BeanTransformer<DispAccount, FastJsonDispAccount> dispAccountBeanTransformer;

    private final TokenHandler tokenHandler;

    public AccountController(
            AccountResponseService accountResponseService,
            ServiceExceptionMapper sem,
            BeanTransformer<Account, FastJsonAccount> accountBeanTransformer,
            BeanTransformer<DispAccount, FastJsonDispAccount> dispAccountBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.accountResponseService = accountResponseService;
        this.sem = sem;
        this.accountBeanTransformer = accountBeanTransformer;
        this.dispAccountBeanTransformer = dispAccountBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/account/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = accountResponseService.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonAccount> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            Account account = accountResponseService.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAccount.of(account)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/account/{accountId}/role/{roleId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> addRoleRelation(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId, @PathVariable("roleId") String roleId
    ) {
        try {
            accountResponseService.addRoleRelation(new StringIdKey(accountId), new StringIdKey(roleId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/account/{accountId}/role/{roleId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> deleteRoleRelation(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId, @PathVariable("roleId") String roleId
    ) {
        try {
            accountResponseService.deleteRoleRelation(new StringIdKey(accountId), new StringIdKey(roleId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/account/{accountId}/reset-role-relation")
    @BehaviorAnalyse
    @LoginRequired
    @BindingCheck
    public FastJsonResponseData<Object> resetRoleRelation(
            HttpServletRequest request,
            @PathVariable("accountId") String accountId,
            @RequestBody @Validated ValidateList<WebInputStringIdKey> roleKeys, BindingResult bindingResult
    ) {
        try {
            accountResponseService.resetRoleRelation(
                    new StringIdKey(accountId),
                    roleKeys.stream().map(WebInputStringIdKey::toStackBean).collect(Collectors.toList())
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonAccount>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Account> all = accountResponseService.all(new PagingInfo(page, rows));
            PagedData<FastJsonAccount> transform = PagingUtil.transform(all, accountBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role/{roleId}/account")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonAccount>> childForRole(
            HttpServletRequest request,
            @PathVariable("roleId") String roleId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Account> childForRole = accountResponseService.childForRole(
                    new StringIdKey(roleId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonAccount> transform = PagingUtil.transform(childForRole, accountBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/profile/{profileId}/account/guest")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonAccount>> childForProfileGuest(
            HttpServletRequest request,
            @PathVariable("profileId") String profileId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Account> childForProfileGuest = accountResponseService.childForProfileGuest(
                    new StringIdKey(profileId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonAccount> transform = PagingUtil.transform(childForProfileGuest, accountBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonAccount>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Account> idLike = accountResponseService.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonAccount> transform = PagingUtil.transform(idLike, accountBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonDispAccount> getDisp(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispAccount dispAccount = accountResponseService.getDisp(new StringIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispAccount.of(dispAccount)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/account/id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispAccount>> idLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            PagedData<DispAccount> idLike = accountResponseService.idLikeDisp(
                    pattern, new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<FastJsonDispAccount> transform = PagingUtil.transform(idLike, dispAccountBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/account/register")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> register(
            HttpServletRequest request,
            @RequestBody @Validated WebInputAccountRegisterInfo accountRegisterInfo, BindingResult bindingResult) {
        try {
            accountResponseService.register(WebInputAccountRegisterInfo.toStackBean(accountRegisterInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/account/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputAccountUpdateInfo accountUpdateInfo, BindingResult bindingResult) {
        try {
            accountResponseService.update(WebInputAccountUpdateInfo.toStackBean(accountUpdateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/account/remove")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputStringIdKey accountKey, BindingResult bindingResult) {
        try {
            accountResponseService.delete(WebInputStringIdKey.toStackBean(accountKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
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
            accountResponseService.updatePassword(WebInputPasswordUpdateInfo.toStackBean(passwordUpdateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAccount.of(null)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/account/reset-password")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> resetPassword(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPasswordResetInfo passwordResetInfo, BindingResult bindingResult
    ) {
        try {
            accountResponseService.resetPassword(WebInputPasswordResetInfo.toStackBean(passwordResetInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonAccount.of(null)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/account/invalid")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> invalid(
            HttpServletRequest request,
            @RequestBody @Validated WebInputStringIdKey accountKey, BindingResult bindingResult) {
        try {
            accountResponseService.invalid(WebInputStringIdKey.toStackBean(accountKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
