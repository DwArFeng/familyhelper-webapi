package com.dwarfeng.familyhelper.webapi.node.controller.v1.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.JSFixedFastJsonDispPerformerInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispPerformerInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.PerformerInfoResponseService;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonPerformerInfo;
import com.dwarfeng.logicengine.sdk.bean.entity.WebInputPerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
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
 * 执行器信息控制器。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@RestController
@RequestMapping("/api/v1/logicengine")
public class PerformerInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformerInfoController.class);

    private final PerformerInfoResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<PerformerInfo, JSFixedFastJsonPerformerInfo> beanTransformer;
    private final BeanTransformer<DispPerformerInfo, JSFixedFastJsonDispPerformerInfo> dispBeanTransformer;

    public PerformerInfoController(
            PerformerInfoResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<PerformerInfo, JSFixedFastJsonPerformerInfo> beanTransformer,
            BeanTransformer<DispPerformerInfo, JSFixedFastJsonDispPerformerInfo> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/performer-info/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.get")
    public FastJsonResponseData<JSFixedFastJsonPerformerInfo> get(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            PerformerInfo performerInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPerformerInfo.of(performerInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/performer-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.insert")
    @AuditRecord("webapi.logicengine.performer_info.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.performerInfoInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonLongIdKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "logicengine.performerInfoInsertPerformerInfoParamContributor",
                    prefix = "param.performer_info."
            )
            WebInputPerformerInfo performerInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey insert = service.insert(WebInputPerformerInfo.toStackBean(performerInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/performer-info")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.update")
    @AuditRecord("webapi.logicengine.performer_info.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.performerInfoUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "logicengine.performerInfoUpdatePerformerInfoParamContributor",
                    prefix = "param.performer_info."
            )
            WebInputPerformerInfo performerInfo,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputPerformerInfo.toStackBean(performerInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/performer-info/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.delete")
    @AuditRecord("webapi.logicengine.performer_info.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.performerInfoDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("id")
            @AuditParamContribute(
                    contributor = "logicengine.performerInfoDeleteIdParamContributor",
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

    @GetMapping("/performer-info/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPerformerInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PerformerInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonPerformerInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/child-for-section/{sectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.child_for_section_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPerformerInfo>> childForSectionDisp(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPerformerInfo> childForSectionDisp = service.childForSectionDisp(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPerformerInfo> transform = PagingUtil.transform(
                    childForSectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/child-for-anchor-state/{sectionLongId}&{stateId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.child_for_anchor_state_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPerformerInfo>> childForAnchorStateDisp(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPerformerInfo> childForAnchorStateDisp = service.childForAnchorStateDisp(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPerformerInfo> transform = PagingUtil.transform(
                    childForAnchorStateDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/child-for-target-state/{sectionLongId}&{stateId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.child_for_target_state_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPerformerInfo>> childForTargetStateDisp(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPerformerInfo> childForTargetStateDisp = service.childForTargetStateDisp(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispPerformerInfo> transform = PagingUtil.transform(
                    childForTargetStateDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispPerformerInfo> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            DispPerformerInfo dispPerformerInfo = service.getDisp(new LongIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispPerformerInfo.of(dispPerformerInfo))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPerformerInfo>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPerformerInfo> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispPerformerInfo> transform = PagingUtil.transform(allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/child-for-section/{sectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.child_for_section")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPerformerInfo>> childForSection(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PerformerInfo> childForSection = service.childForSection(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPerformerInfo> transform = PagingUtil.transform(childForSection, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/child-for-anchor-state/{sectionLongId}&{stateId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.child_for_anchor_state")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPerformerInfo>> childForAnchorState(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PerformerInfo> childForAnchorState = service.childForAnchorState(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPerformerInfo> transform = PagingUtil.transform(
                    childForAnchorState, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/performer-info/child-for-target-state/{sectionLongId}&{stateId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.performer_info.child_for_target_state")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPerformerInfo>> childForTargetState(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PerformerInfo> childForTargetState = service.childForTargetState(
                    new StateKey(sectionLongId, stateId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPerformerInfo> transform = PagingUtil.transform(
                    childForTargetState, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
