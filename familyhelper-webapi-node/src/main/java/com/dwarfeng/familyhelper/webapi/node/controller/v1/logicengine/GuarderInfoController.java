package com.dwarfeng.familyhelper.webapi.node.controller.v1.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.JSFixedFastJsonDispGuarderInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispGuarderInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.GuarderInfoResponseService;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonGuarderInfo;
import com.dwarfeng.logicengine.sdk.bean.entity.WebInputGuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
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
 * 守卫器信息控制器。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@RestController
@RequestMapping("/api/v1/logicengine")
public class GuarderInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuarderInfoController.class);

    private final GuarderInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<GuarderInfo, JSFixedFastJsonGuarderInfo> beanTransformer;
    private final BeanTransformer<DispGuarderInfo, JSFixedFastJsonDispGuarderInfo> dispBeanTransformer;

    public GuarderInfoController(
            GuarderInfoResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<GuarderInfo, JSFixedFastJsonGuarderInfo> beanTransformer,
            BeanTransformer<DispGuarderInfo, JSFixedFastJsonDispGuarderInfo> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/guarder-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.get")
    public FastJsonResponseData<JSFixedFastJsonGuarderInfo> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            GuarderInfo guarderInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonGuarderInfo.of(guarderInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/guarder-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.insert")
    @AuditRecord("webapi.logicengine.guarder_info.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.guarderInfoInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "logicengine.guarderInfoInsertGuarderInfoParamContributor",
                    prefix = "param.guarder_info."
            )
            WebInputGuarderInfo guarderInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey insert = service.insert(WebInputGuarderInfo.toStackBean(guarderInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/guarder-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.update")
    @AuditRecord("webapi.logicengine.guarder_info.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.guarderInfoUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "logicengine.guarderInfoUpdateGuarderInfoParamContributor",
                    prefix = "param.guarder_info."
            )
            WebInputGuarderInfo guarderInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputGuarderInfo.toStackBean(guarderInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/guarder-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.delete")
    @AuditRecord("webapi.logicengine.guarder_info.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.guarderInfoDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("id")
            @AuditParamContribute(
                    contributor = "logicengine.guarderInfoDeleteIdParamContributor",
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

    @GetMapping("/guarder-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonGuarderInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<GuarderInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonGuarderInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/child-for-section/{sectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.child_for_section_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispGuarderInfo>> childForSectionDisp(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispGuarderInfo> childForSectionDisp = service.childForSectionDisp(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispGuarderInfo> transform = PagingUtil.transform(
                    childForSectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/child-for-anchor-state/{sectionLongId}&{stateId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.child_for_anchor_state_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispGuarderInfo>> childForAnchorStateDisp(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispGuarderInfo> childForAnchorStateDisp = service.childForAnchorStateDisp(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispGuarderInfo> transform = PagingUtil.transform(
                    childForAnchorStateDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/child-for-target-state/{sectionLongId}&{stateId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.child_for_target_state_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispGuarderInfo>> childForTargetStateDisp(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispGuarderInfo> childForTargetStateDisp = service.childForTargetStateDisp(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispGuarderInfo> transform = PagingUtil.transform(
                    childForTargetStateDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispGuarderInfo> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispGuarderInfo dispGuarderInfo = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispGuarderInfo.of(dispGuarderInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispGuarderInfo>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispGuarderInfo> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispGuarderInfo> transform = PagingUtil.transform(allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/child-for-section/{sectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.child_for_section")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonGuarderInfo>> childForSection(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<GuarderInfo> childForSection = service.childForSection(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonGuarderInfo> transform = PagingUtil.transform(childForSection, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/child-for-anchor-state/{sectionLongId}&{stateId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.child_for_anchor_state")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonGuarderInfo>> childForAnchorState(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<GuarderInfo> childForAnchorState = service.childForAnchorState(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonGuarderInfo> transform = PagingUtil.transform(
                    childForAnchorState, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/guarder-info/child-for-target-state/{sectionLongId}&{stateId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.guarder_info.child_for_target_state")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonGuarderInfo>> childForTargetState(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<GuarderInfo> childForTargetState = service.childForTargetState(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonGuarderInfo> transform = PagingUtil.transform(
                    childForTargetState, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
