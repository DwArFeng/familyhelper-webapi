package com.dwarfeng.familyhelper.webapi.node.controller.v1.audit;

import com.dwarfeng.audit.sdk.bean.dto.WebInputInspectorVariableRemoveInfo;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectorVariable;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.JSFixedFastJsonDispInspectorVariable;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectorVariable;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectorVariableResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
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
 * 审计器变量控制器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@RestController
@RequestMapping("/api/v1/audit")
public class InspectorVariableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectorVariableController.class);

    private final InspectorVariableResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<InspectorVariable, JSFixedFastJsonInspectorVariable> beanTransformer;
    private final BeanTransformer<DispInspectorVariable, JSFixedFastJsonDispInspectorVariable>
            dispBeanTransformer;

    public InspectorVariableController(
            InspectorVariableResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<InspectorVariable, JSFixedFastJsonInspectorVariable> beanTransformer,
            BeanTransformer<DispInspectorVariable, JSFixedFastJsonDispInspectorVariable> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/inspector-variable/{inspectorInfoId}&{variableId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("inspectorInfoId") Long inspectorInfoId,
            @PathVariable("variableId") String variableId
    ) {
        try {
            InspectorVariableKey key = new InspectorVariableKey(inspectorInfoId, variableId);
            boolean exists = service.exists(key);
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspector-variable/{inspectorInfoId}&{variableId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.get")
    public FastJsonResponseData<JSFixedFastJsonInspectorVariable> get(
            HttpServletRequest request,
            @PathVariable("inspectorInfoId") Long inspectorInfoId,
            @PathVariable("variableId") String variableId
    ) {
        try {
            InspectorVariableKey key = new InspectorVariableKey(inspectorInfoId, variableId);
            InspectorVariable inspectorVariable = service.get(key);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonInspectorVariable.of(inspectorVariable))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspector-variable/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectorVariable>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectorVariable> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonInspectorVariable> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspector-variable/child-for-inspector-info/{inspectorInfoId}")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.child_for_inspector_info")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonInspectorVariable>> childForInspectorInfo(
            HttpServletRequest request,
            @PathVariable("inspectorInfoId") Long inspectorInfoId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<InspectorVariable> childForInspectorInfo = service.childForInspectorInfo(
                    new LongIdKey(inspectorInfoId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonInspectorVariable> transform = PagingUtil.transform(
                    childForInspectorInfo, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspector-variable/{inspectorInfoId}&{variableId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.get_disp")
    public FastJsonResponseData<JSFixedFastJsonDispInspectorVariable> getDisp(
            HttpServletRequest request,
            @PathVariable("inspectorInfoId") Long inspectorInfoId,
            @PathVariable("variableId") String variableId
    ) {
        try {
            InspectorVariableKey key = new InspectorVariableKey(inspectorInfoId, variableId);
            DispInspectorVariable dispInspectorVariable = service.getDisp(key);
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(JSFixedFastJsonDispInspectorVariable.of(dispInspectorVariable))
            );
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspector-variable/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectorVariable>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectorVariable> allDisp = service.allDisp(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispInspectorVariable> transform = PagingUtil.transform(
                    allDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/inspector-variable/child-for-inspector-info/{inspectorInfoId}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.child_for_inspector_info_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispInspectorVariable>>
    childForInspectorInfoDisp(
            HttpServletRequest request,
            @PathVariable("inspectorInfoId") Long inspectorInfoId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispInspectorVariable> childForInspectorInfoDisp = service.childForInspectorInfoDisp(
                    new LongIdKey(inspectorInfoId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispInspectorVariable> transform = PagingUtil.transform(
                    childForInspectorInfoDisp, dispBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/inspector-variable/upsert")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.upsert")
    public FastJsonResponseData<Object> upsert(
            HttpServletRequest request,
            @RequestBody @Validated
            com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputInspectorVariableUpsertInfo
                    webInputInspectorVariableUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            service.upsert(
                    com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputInspectorVariableUpsertInfo
                            .toStackBean(webInputInspectorVariableUpsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/inspector-variable/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.audit.inspector_variable.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputInspectorVariableRemoveInfo webInputInspectorVariableRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputInspectorVariableRemoveInfo.toStackBean(webInputInspectorVariableRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
