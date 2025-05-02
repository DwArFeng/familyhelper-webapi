package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataSetCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataSetPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataSetPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputActivityDataSetUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonActivityDataSet;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataSet;
import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.JSFixedFastJsonDispActivityDataSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityDataSet;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataSetResponseService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 活动数据集控制器。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
@RestController
@RequestMapping("/api/v1/life")
public class ActivityDataSetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityDataSetController.class);

    private final ActivityDataSetResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ActivityDataSet, JSFixedFastJsonActivityDataSet> beanTransformer;
    private final BeanTransformer<DispActivityDataSet, JSFixedFastJsonDispActivityDataSet> dispBeanTransformer;

    private final TokenHandler tokenHandler;

    public ActivityDataSetController(
            ActivityDataSetResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ActivityDataSet, JSFixedFastJsonActivityDataSet> beanTransformer,
            BeanTransformer<DispActivityDataSet, JSFixedFastJsonDispActivityDataSet> dispBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/activity-data-set/{id}/exists")
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

    @GetMapping("/activity-data-set/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonActivityDataSet> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            ActivityDataSet activityDataSet = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonActivityDataSet.of(activityDataSet)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonActivityDataSet>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ActivityDataSet> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonActivityDataSet> transform = PagingUtil.transform(
                    all, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispActivityDataSet> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getUserKey(request);
            DispActivityDataSet dispActivityDataSet = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispActivityDataSet.of(dispActivityDataSet))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/all-permitted/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataSet>> allPermittedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispActivityDataSet> allPermittedDisp = service.allPermittedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivityDataSet> transform = PagingUtil.transform(
                    allPermittedDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/activity-data-set/all-owned/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispActivityDataSet>> allOwnedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            PagedData<DispActivityDataSet> allOwnedDisp = service.allOwnedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispActivityDataSet> transform = PagingUtil.transform(
                    allOwnedDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-set/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createActivityDataSet(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataSetCreateInfo activityDataSetCreateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey result = service.createActivityDataSet(
                    accountKey, WebInputActivityDataSetCreateInfo.toStackBean(activityDataSetCreateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-set/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateActivityDataSet(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataSetUpdateInfo webInputActivityDataSetUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.updateActivityDataSet(
                    accountKey, WebInputActivityDataSetUpdateInfo.toStackBean(webInputActivityDataSetUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-set/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeActivityDataSet(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey activityDataSetKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeActivityDataSet(accountKey, WebInputLongIdKey.toStackBean(activityDataSetKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-set/upsert-permission")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> upsertPermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataSetPermissionUpsertInfo webInputPermissionUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.upsertPermission(
                    accountKey, WebInputActivityDataSetPermissionUpsertInfo.toStackBean(webInputPermissionUpsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/activity-data-set/remove-permission")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removePermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputActivityDataSetPermissionRemoveInfo webInputPermissionRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removePermission(
                    accountKey, WebInputActivityDataSetPermissionRemoveInfo.toStackBean(webInputPermissionRemoveInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
