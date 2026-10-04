package com.dwarfeng.familyhelper.webapi.node.controller.v1.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.JSFixedFastJsonDispState;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispState;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.StateResponseService;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonState;
import com.dwarfeng.logicengine.sdk.bean.entity.WebInputState;
import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonStateKey;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
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
 * 状态控制器。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@RestController
@RequestMapping("/api/v1/logicengine")
public class StateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateController.class);

    private final StateResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<State, JSFixedFastJsonState> beanTransformer;
    private final BeanTransformer<DispState, JSFixedFastJsonDispState> dispBeanTransformer;

    public StateController(
            StateResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<State, JSFixedFastJsonState> beanTransformer,
            BeanTransformer<DispState, JSFixedFastJsonDispState> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/state/{sectionLongId}&{stateId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId
    ) {
        try {
            boolean exists = service.exists(new StateKey(sectionLongId, stateId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/state/{sectionLongId}&{stateId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.get")
    public FastJsonResponseData<JSFixedFastJsonState> get(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId
    ) {
        try {
            State state = service.get(new StateKey(sectionLongId, stateId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonState.of(state)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/state")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.insert")
    @AuditRecord("webapi.logicengine.state.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.stateInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<JSFixedFastJsonStateKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "logicengine.stateInsertStateParamContributor",
                    prefix = "param.state."
            )
            WebInputState state,
            BindingResult bindingResult
    ) {
        try {
            StateKey insert = service.insert(WebInputState.toStackBean(state));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonStateKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/state")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.update")
    @AuditRecord("webapi.logicengine.state.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.stateUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "logicengine.stateUpdateStateParamContributor",
                    prefix = "param.state."
            )
            WebInputState state,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputState.toStackBean(state));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/state/{sectionLongId}&{stateId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.delete")
    @AuditRecord("webapi.logicengine.state.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "logicengine.stateDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("sectionLongId")
            @AuditParamContribute(
                    contributor = "logicengine.stateDeleteSectionLongIdParamContributor",
                    prefix = "param.section_long_id."
            )
            Long sectionLongId,
            @PathVariable("stateId")
            @AuditParamContribute(
                    contributor = "logicengine.stateDeleteStateIdParamContributor",
                    prefix = "param.state_id."
            )
            String stateId
    ) {
        try {
            service.delete(new StateKey(sectionLongId, stateId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/state/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonState>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<State> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonState> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/state/child-for-section/{sectionId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.child_for_section_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispState>> childForSectionDisp(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispState> childForSectionDisp = service.childForSectionDisp(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispState> transform = PagingUtil.transform(
                    childForSectionDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/state/{sectionLongId}&{stateId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispState> getDisp(
            HttpServletRequest request,
            @PathVariable("sectionLongId") Long sectionLongId, @PathVariable("stateId") String stateId
    ) {
        try {
            DispState dispState = service.getDisp(new StateKey(sectionLongId, stateId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispState.of(dispState)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/state/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispState>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispState> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispState> transform = PagingUtil.transform(allDisp, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/state/child-for-section/{sectionId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.logicengine.state.child_for_section")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonState>> childForSection(
            HttpServletRequest request,
            @PathVariable("sectionId") Long sectionId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<State> childForSection = service.childForSection(
                    new LongIdKey(sectionId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonState> transform = PagingUtil.transform(childForSection, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
