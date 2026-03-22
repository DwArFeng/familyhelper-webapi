package com.dwarfeng.familyhelper.webapi.node.controller.v1.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonProtectorVariable;
import com.dwarfeng.acckeeper.sdk.bean.entity.WebInputProtectorVariable;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.FastJsonDispProtectorVariable;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectorVariable;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ProtectorVariableResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 保护器变量控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/acckeeper")
public class ProtectorVariableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectorVariableController.class);

    private final ProtectorVariableResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ProtectorVariable, FastJsonProtectorVariable> beanTransformer;
    private final BeanTransformer<DispProtectorVariable, FastJsonDispProtectorVariable> dispBeanTransformer;

    public ProtectorVariableController(
            ProtectorVariableResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("acckeeper.protectorVariableBeanTransformer")
            BeanTransformer<ProtectorVariable, FastJsonProtectorVariable> beanTransformer,
            @Qualifier("acckeeper.dispProtectorVariableBeanTransformer")
            BeanTransformer<DispProtectorVariable, FastJsonDispProtectorVariable> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/protector-variable/{protectorInfoId}&{variableId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("protectorInfoId") String protectorInfoId,
            @PathVariable("variableId") String variableId
    ) {
        try {
            boolean exists = service.exists(new ProtectorVariableKey(protectorInfoId, variableId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-variable/{protectorInfoId}&{variableId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.get")
    public FastJsonResponseData<FastJsonProtectorVariable> get(
            HttpServletRequest request,
            @PathVariable("protectorInfoId") String protectorInfoId,
            @PathVariable("variableId") String variableId
    ) {
        try {
            ProtectorVariable protectorVariable = service.get(new ProtectorVariableKey(protectorInfoId, variableId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonProtectorVariable.of(protectorVariable)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/protector-variable")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.insert")
    public FastJsonResponseData<FastJsonProtectorVariableKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputProtectorVariable webInputProtectorVariable,
            BindingResult bindingResult
    ) {
        try {
            ProtectorVariable protectorVariable = WebInputProtectorVariable.toStackBean(webInputProtectorVariable);
            ProtectorVariableKey insert = service.insert(protectorVariable);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonProtectorVariableKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/protector-variable")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputProtectorVariable webInputProtectorVariable,
            BindingResult bindingResult
    ) {
        try {
            ProtectorVariable protectorVariable = WebInputProtectorVariable.toStackBean(webInputProtectorVariable);
            service.update(protectorVariable);
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/protector-variable/{protectorInfoId}&{variableId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.delete")
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("protectorInfoId") String protectorInfoId,
            @PathVariable("variableId") String variableId
    ) {
        try {
            service.delete(new ProtectorVariableKey(protectorInfoId, variableId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-variable/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonProtectorVariable>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectorVariable> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonProtectorVariable> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-info/{protectorInfoId}/protector-variable")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.child_for_protector_info")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonProtectorVariable>> childForProtectorInfo(
            HttpServletRequest request,
            @PathVariable("protectorInfoId") String protectorInfoId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ProtectorVariable> child = service.childForProtectorInfo(
                    new StringIdKey(protectorInfoId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonProtectorVariable> transform = PagingUtil.transform(child, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-variable/{protectorInfoId}&{variableId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.get_disp")
    public FastJsonResponseData<FastJsonDispProtectorVariable> getDisp(
            HttpServletRequest request,
            @PathVariable("protectorInfoId") String protectorInfoId,
            @PathVariable("variableId") String variableId
    ) {
        try {
            DispProtectorVariable disp = service.getDisp(new ProtectorVariableKey(protectorInfoId, variableId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispProtectorVariable.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-variable/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispProtectorVariable>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispProtectorVariable> all = service.allDisp(new PagingInfo(page, rows));
            PagedData<FastJsonDispProtectorVariable> transform = PagingUtil.transform(all, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/protector-info/{protectorInfoId}/protector-variable/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.acckeeper.protector_variable.child_for_protector_info_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispProtectorVariable>> childForProtectorInfoDisp(
            HttpServletRequest request,
            @PathVariable("protectorInfoId") String protectorInfoId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispProtectorVariable> child = service.childForProtectorInfoDisp(
                    new StringIdKey(protectorInfoId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonDispProtectorVariable> transform = PagingUtil.transform(child, dispBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
