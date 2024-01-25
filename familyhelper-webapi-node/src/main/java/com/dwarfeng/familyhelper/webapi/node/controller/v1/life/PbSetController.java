package com.dwarfeng.familyhelper.webapi.node.controller.v1.life;

import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbSetCreateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbSetPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbSetPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.dto.WebInputPbSetUpdateInfo;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.JSFixedFastJsonPbSet;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbSet;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.JSFixedFastJsonDispPbSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbSet;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbSetResponseService;
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
 * 个人最佳集合控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/life")
public class PbSetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PbSetController.class);

    private final PbSetResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<PbSet, JSFixedFastJsonPbSet> pbSetBeanTransformer;
    private final BeanTransformer<DispPbSet, JSFixedFastJsonDispPbSet> dispPbSetBeanTransformer;

    private final TokenHandler tokenHandler;

    public PbSetController(
            PbSetResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<PbSet, JSFixedFastJsonPbSet> pbSetBeanTransformer,
            BeanTransformer<DispPbSet, JSFixedFastJsonDispPbSet> dispPbSetBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.pbSetBeanTransformer = pbSetBeanTransformer;
        this.dispPbSetBeanTransformer = dispPbSetBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/pb-set/{id}/exists")
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

    @GetMapping("/pb-set/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPbSet> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            PbSet pbSet = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPbSet.of(pbSet)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPbSet>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<PbSet> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPbSet> transform = PagingUtil.transform(all, pbSetBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPbSet> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispPbSet dispPbSet = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPbSet.of(dispPbSet)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/all-permitted/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbSet>> allPermittedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbSet> allPermittedDisp = service.allPermittedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPbSet> transform = PagingUtil.transform(
                    allPermittedDisp, dispPbSetBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pb-set/all-owned/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPbSet>> allOwnedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPbSet> allOwnedDisp = service.allOwnedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPbSet> transform = PagingUtil.transform(
                    allOwnedDisp, dispPbSetBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-set/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createPbSet(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbSetCreateInfo pbSetCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createPbSet(
                    accountKey, WebInputPbSetCreateInfo.toStackBean(pbSetCreateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-set/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updatePbSet(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbSetUpdateInfo webInputPbSetUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updatePbSet(
                    accountKey, WebInputPbSetUpdateInfo.toStackBean(webInputPbSetUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-set/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removePbSet(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey pbSetKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePbSet(accountKey, WebInputLongIdKey.toStackBean(pbSetKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-set/upsert-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> upsertPermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbSetPermissionUpsertInfo webInputPermissionUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.upsertPermission(
                    accountKey, WebInputPbSetPermissionUpsertInfo.toStackBean(webInputPermissionUpsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pb-set/remove-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removePermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPbSetPermissionRemoveInfo webInputPermissionRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePermission(
                    accountKey, WebInputPbSetPermissionRemoveInfo.toStackBean(webInputPermissionRemoveInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
