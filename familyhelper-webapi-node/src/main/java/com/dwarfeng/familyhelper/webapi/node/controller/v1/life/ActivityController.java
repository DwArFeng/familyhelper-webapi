package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivity;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Activity;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispActivity;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivity;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityResponseService;
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

/**
 * 活动模板控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityController {

    private final ActivityResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Activity, JSFixedFastJsonActivity> beanTransformer;
    private final BeanTransformer<DispActivity, JSFixedFastJsonDispActivity> dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityController(
            ActivityResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Activity, JSFixedFastJsonActivity> beanTransformer,
            BeanTransformer<DispActivity, JSFixedFastJsonDispActivity> dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity/{id}/exists")
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

    @GetMapping("/activity/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivity> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            Activity activity = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonActivity.of(activity)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivity>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Activity> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivity> transform = PagingUtil.transform(
                    all, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispActivity> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispActivity dispActivity = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispActivity.of(dispActivity))
            );
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/all-permitted/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivity>> allPermittedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivity> allPermittedDisp = service.allPermittedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivity> transform = PagingUtil.transform(
                    allPermittedDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity/all-owned/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivity>> allOwnedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispActivity> allOwnedDisp = service.allOwnedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivity> transform = PagingUtil.transform(
                    allOwnedDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createActivity(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityCreateInfo activityCreateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createActivity(
                    accountKey, WebInputActivityCreateInfo.toStackBean(activityCreateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateActivity(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityUpdateInfo webInputActivityUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateActivity(
                    accountKey, WebInputActivityUpdateInfo.toStackBean(webInputActivityUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeActivity(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey activityKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeActivity(accountKey, WebInputLongIdKey.toStackBean(activityKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity/upsert-permission")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> upsertPermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityPermissionUpsertInfo webInputPermissionUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.upsertPermission(
                    accountKey, WebInputActivityPermissionUpsertInfo.toStackBean(webInputPermissionUpsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity/remove-permission")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removePermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityPermissionRemoveInfo webInputPermissionRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePermission(
                    accountKey, WebInputActivityPermissionRemoveInfo.toStackBean(webInputPermissionRemoveInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity/lock")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> lockActivity(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey activityKey,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.lockActivity(accountKey, WebInputLongIdKey.toStackBean(activityKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
