package com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac;

import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPexp;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPexp;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.PexpResponseService;
import com.dwarfeng.rbacds.sdk.bean.dto.FastJsonPexpCreateResult;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPexpCreateInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPexpRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPexpUpdateInfo;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPexp;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
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
 * 权限表达式控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/rbac")
public class PexpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PexpController.class);

    private final PexpResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Pexp, FastJsonPexp> beanTransformer;
    private final BeanTransformer<DispPexp, FastJsonDispPexp> dispBeanTransformer;

    public PexpController(
            PexpResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("rbac.pexpBeanTransformer") BeanTransformer<Pexp, FastJsonPexp> beanTransformer,
            @Qualifier("rbac.dispPexpBeanTransformer") BeanTransformer<DispPexp, FastJsonDispPexp> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    private PexpKey key(String scopeId, String roleId, String pexpStringId) {
        return new PexpKey(scopeId, roleId, pexpStringId);
    }

    @GetMapping("/pexp/{scopeId}&{roleId}&{pexpStringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("roleId") String roleId,
            @PathVariable("pexpStringId") String pexpStringId
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(service.exists(key(scopeId, roleId, pexpStringId))));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pexp/{scopeId}&{roleId}&{pexpStringId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.get")
    public FastJsonResponseData<FastJsonPexp> get(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("roleId") String roleId,
            @PathVariable("pexpStringId") String pexpStringId
    ) {
        try {
            Pexp entity = service.get(key(scopeId, roleId, pexpStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPexp.of(entity)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/pexp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.child_for_scope")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPexp>> childForScope(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Pexp> data = service.childForScope(new StringIdKey(scopeId), new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role/{roleId}/pexp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.child_for_role")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPexp>> childForRole(
            HttpServletRequest request, @PathVariable("roleId") String roleId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Pexp> data = service.childForRole(new StringIdKey(roleId), new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/role/{roleId}/pexp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.child_for_scope_child_for_role")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPexp>> childForScopeChildForRole(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("roleId") String roleId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Pexp> data = service.childForScopeChildForRole(
                    new StringIdKey(scopeId), new StringIdKey(roleId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/pexp/{scopeId}&{roleId}&{pexpStringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.get_disp")
    public FastJsonResponseData<FastJsonDispPexp> getDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("roleId") String roleId,
            @PathVariable("pexpStringId") String pexpStringId
    ) {
        try {
            DispPexp disp = service.getDisp(key(scopeId, roleId, pexpStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispPexp.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/pexp/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.child_for_scope_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPexp>> childForScopeDisp(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPexp> data = service.childForScopeDisp(new StringIdKey(scopeId), new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role/{roleId}/pexp/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.child_for_role_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPexp>> childForRoleDisp(
            HttpServletRequest request, @PathVariable("roleId") String roleId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPexp> data = service.childForRoleDisp(new StringIdKey(roleId), new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/role/{roleId}/pexp/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.child_for_scope_child_for_role_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPexp>> childForScopeChildForRoleDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("roleId") String roleId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPexp> data = service.childForScopeChildForRoleDisp(
                    new StringIdKey(scopeId), new StringIdKey(roleId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pexp/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.create")
    public FastJsonResponseData<FastJsonPexpCreateResult> create(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPexpCreateInfo webInput, BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonPexpCreateResult.of(service.create(WebInputPexpCreateInfo.toStackBean(webInput)))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/pexp/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPexpUpdateInfo webInput, BindingResult bindingResult
    ) {
        try {
            service.update(WebInputPexpUpdateInfo.toStackBean(webInput));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/pexp/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.pexp.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPexpRemoveInfo webInput, BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputPexpRemoveInfo.toStackBean(webInput));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
