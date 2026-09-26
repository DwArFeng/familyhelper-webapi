package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectionDriverInfo;
import com.dwarfeng.audit.sdk.bean.entity.WebInputInspectionDriverInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispInspectionDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionDriverInfoResponseService;
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
 * 自动审计驱动器信息控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectionDriverInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionDriverInfoController.class);

    private final InspectionDriverInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<InspectionDriverInfo, JSFixedFastJsonInspectionDriverInfo> beanTransformer;
    private final BeanTransformer<DispInspectionDriverInfo, JSFixedFastJsonDispInspectionDriverInfo>
            dispBeanTransformer;

    public InspectionDriverInfoController(
            InspectionDriverInfoResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<InspectionDriverInfo, JSFixedFastJsonInspectionDriverInfo> beanTransformer,
            BeanTransformer<DispInspectionDriverInfo, JSFixedFastJsonDispInspectionDriverInfo> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/inspection-driver-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.get")
    public FastJsonResponseData<JSFixedFastJsonInspectionDriverInfo> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            InspectionDriverInfo inspectionDriverInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonInspectionDriverInfo.of(inspectionDriverInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/inspection-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.insert")
    @AuditRecord("webapi.audit.inspection_driver_info.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.inspectionDriverInfoInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "audit.inspectionDriverInfoInsertInspectionDriverInfoParamContributor",
                    prefix = "param.inspection_driver_info."
            )
            WebInputInspectionDriverInfo inspectionDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey insert = service.insert(WebInputInspectionDriverInfo.toStackBean(inspectionDriverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/inspection-driver-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.update")
    @AuditRecord("webapi.audit.inspection_driver_info.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.inspectionDriverInfoUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "audit.inspectionDriverInfoUpdateInspectionDriverInfoParamContributor",
                    prefix = "param.inspection_driver_info."
            )
            WebInputInspectionDriverInfo inspectionDriverInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputInspectionDriverInfo.toStackBean(inspectionDriverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/inspection-driver-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.delete")
    @AuditRecord("webapi.audit.inspection_driver_info.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "audit.inspectionDriverInfoDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("id")
            @AuditParamContribute(
                    contributor = "audit.inspectionDriverInfoDeleteIdParamContributor",
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

    @GetMapping("/inspection-driver-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionDriverInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionDriverInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonInspectionDriverInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-driver-info/child-for-inspection/{inspectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.child_for_inspection_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionDriverInfo>>
    childForInspectionDisp(
            HttpServletRequest request,
            @PathVariable("inspectionId") Long inspectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionDriverInfo> childForInspectionDisp = service.childForInspectionDisp(
                    new LongIdKey(inspectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispInspectionDriverInfo> transform = PagingUtil.transform(
                    childForInspectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-driver-info/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispInspectionDriverInfo> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispInspectionDriverInfo dispInspectionDriverInfo = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispInspectionDriverInfo.of(dispInspectionDriverInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-driver-info/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectionDriverInfo>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectionDriverInfo> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispInspectionDriverInfo> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspection-driver-info/child-for-inspection/{inspectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspection_driver_info.child_for_inspection")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectionDriverInfo>> childForInspection(
            HttpServletRequest request,
            @PathVariable("inspectionId") Long inspectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectionDriverInfo> childForInspection = service.childForInspection(
                    new LongIdKey(inspectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonInspectionDriverInfo> transform = PagingUtil.transform(
                    childForInspection, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
