package com.dwarfeng.familyhelper.webapi.node.controller.v1.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.JSFixedFastJsonDispDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.DriverInfoResponseService;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonDriverInfo;
import com.dwarfeng.logicengine.sdk.bean.entity.WebInputDriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditInvocationContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditParamContribute;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecord;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditResultContribute;
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
 * 驱动器信息控制器。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@RestController
@RequestMapping("/api/v1/logicengine")
public class DriverInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverInfoController.class);

    private final DriverInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<DriverInfo, JSFixedFastJsonDriverInfo> beanTransformer;
    private final BeanTransformer<DispDriverInfo, JSFixedFastJsonDispDriverInfo> dispBeanTransformer;

    public DriverInfoController(
            DriverInfoResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<DriverInfo, JSFixedFastJsonDriverInfo> beanTransformer,
            BeanTransformer<DispDriverInfo, JSFixedFastJsonDispDriverInfo> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/driver-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.get")
    public FastJsonResponseData<JSFixedFastJsonDriverInfo> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DriverInfo driverInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDriverInfo.of(driverInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.insert")
    @AuditRecord("webapi.logicengine.driver_info.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.driverInfoInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "logicengine.driverInfoInsertDriverInfoParamContributor",
                    prefix = "param.driver_info."
            )
            WebInputDriverInfo driverInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey insert = service.insert(WebInputDriverInfo.toStackBean(driverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.update")
    @AuditRecord("webapi.logicengine.driver_info.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.driverInfoUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "logicengine.driverInfoUpdateDriverInfoParamContributor",
                    prefix = "param.driver_info."
            )
            WebInputDriverInfo driverInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputDriverInfo.toStackBean(driverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.delete")
    @AuditRecord("webapi.logicengine.driver_info.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.driverInfoDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("id")
            @AuditParamContribute(
                    contributor = "logicengine.driverInfoDeleteIdParamContributor",
                    prefix = "param.id."
            )
            Long id
    ) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDriverInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DriverInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDriverInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/child-for-section/{sectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.child_for_section_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispDriverInfo>> childForSectionDisp(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispDriverInfo> childForSectionDisp = service.childForSectionDisp(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispDriverInfo> transform = PagingUtil.transform(
                    childForSectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/child-for-section-enabled/{sectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.child_for_section_enabled_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispDriverInfo>> childForSectionEnabledDisp(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispDriverInfo> childForSectionEnabledDisp = service.childForSectionEnabledDisp(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispDriverInfo> transform = PagingUtil.transform(
                    childForSectionEnabledDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispDriverInfo> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispDriverInfo dispDriverInfo = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispDriverInfo.of(dispDriverInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispDriverInfo>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispDriverInfo> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispDriverInfo> transform = PagingUtil.transform(allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/child-for-section/{sectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.child_for_section")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDriverInfo>> childForSection(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DriverInfo> childForSection = service.childForSection(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDriverInfo> transform = PagingUtil.transform(childForSection, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/driver-info/child-for-section-enabled/{sectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.driver_info.child_for_section_enabled")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDriverInfo>> childForSectionEnabled(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DriverInfo> childForSectionEnabled = service.childForSectionEnabled(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDriverInfo> transform = PagingUtil.transform(
                    childForSectionEnabled, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
