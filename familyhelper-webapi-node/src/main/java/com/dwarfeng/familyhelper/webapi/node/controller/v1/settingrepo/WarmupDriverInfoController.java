package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.disp.JSFixedFastJsonDispWarmupDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispWarmupDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.WarmupDriverInfoResponseService;
import com.dwarfeng.settingrepo.sdk.bean.entity.JSFixedFastJsonWarmupDriverInfo;
import com.dwarfeng.settingrepo.sdk.bean.entity.WebInputWarmupDriverInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.WarmupDriverInfo;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 预热驱动信息控制器。
 *
 * @author DwArFeng
 * @since 2.4.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class WarmupDriverInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarmupDriverInfoController.class);

    private final WarmupDriverInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<WarmupDriverInfo, JSFixedFastJsonWarmupDriverInfo> beanTransformer;
    private final BeanTransformer<DispWarmupDriverInfo, JSFixedFastJsonDispWarmupDriverInfo> dispBeanTransformer;

    public WarmupDriverInfoController(
            WarmupDriverInfoResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<WarmupDriverInfo, JSFixedFastJsonWarmupDriverInfo> beanTransformer,
            BeanTransformer<DispWarmupDriverInfo, JSFixedFastJsonDispWarmupDriverInfo> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/warmup-driver-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.get")
    public FastJsonResponseData<JSFixedFastJsonWarmupDriverInfo> get(
            HttpServletRequest request, @PathVariable("id") long id
    ) {
        try {
            WarmupDriverInfo warmupDriverInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonWarmupDriverInfo.of(warmupDriverInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/warmup-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.insert")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputWarmupDriverInfo warmupDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey insert = service.insert(WebInputWarmupDriverInfo.toStackBean(warmupDriverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/warmup-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputWarmupDriverInfo warmupDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputWarmupDriverInfo.toStackBean(warmupDriverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/warmup-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.delete")
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-driver-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonWarmupDriverInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<WarmupDriverInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonWarmupDriverInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-setting/{warmupSettingId}/warmup-driver-info")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.child_for_warmup_setting")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonWarmupDriverInfo>> childForWarmupSetting(
            HttpServletRequest request,
            @PathVariable("warmupSettingId") long warmupSettingId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<WarmupDriverInfo> childForWarmupSetting = service.childForWarmupSetting(
                    new LongIdKey(warmupSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonWarmupDriverInfo> transform = PagingUtil.transform(
                    childForWarmupSetting, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-setting/{warmupSettingId}/warmup-driver-info/enabled")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.child_for_warmup_setting_enabled")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonWarmupDriverInfo>>
    childForWarmupSettingEnabled(
            HttpServletRequest request,
            @PathVariable("warmupSettingId") long warmupSettingId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<WarmupDriverInfo> childForWarmupSettingEnabled = service.childForWarmupSettingEnabled(
                    new LongIdKey(warmupSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonWarmupDriverInfo> transform = PagingUtil.transform(
                    childForWarmupSettingEnabled, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-driver-info/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispWarmupDriverInfo> getDisp(
            HttpServletRequest request, @PathVariable("id") long id
    ) {
        try {
            DispWarmupDriverInfo dispWarmupDriverInfo = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispWarmupDriverInfo.of(dispWarmupDriverInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-driver-info/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispWarmupDriverInfo>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispWarmupDriverInfo> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispWarmupDriverInfo> transform = PagingUtil.transform(allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-setting/{warmupSettingId}/warmup-driver-info/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.warmup_driver_info.child_for_warmup_setting_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispWarmupDriverInfo>>
    childForWarmupSettingDisp(
            HttpServletRequest request,
            @PathVariable("warmupSettingId") long warmupSettingId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispWarmupDriverInfo> childForWarmupSettingDisp = service.childForWarmupSettingDisp(
                    new LongIdKey(warmupSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispWarmupDriverInfo> transform = PagingUtil.transform(
                    childForWarmupSettingDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/warmup-setting/{warmupSettingId}/warmup-driver-info/enabled/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired(
            "webapi.controller_permitted.settingrepo.warmup_driver_info.child_for_warmup_setting_enabled_disp"
    )
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispWarmupDriverInfo>>
    childForWarmupSettingEnabledDisp(
            HttpServletRequest request,
            @PathVariable("warmupSettingId") long warmupSettingId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispWarmupDriverInfo> childForWarmupSettingEnabledDisp = service.childForWarmupSettingEnabledDisp(
                    new LongIdKey(warmupSettingId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispWarmupDriverInfo> transform = PagingUtil.transform(
                    childForWarmupSettingEnabledDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
