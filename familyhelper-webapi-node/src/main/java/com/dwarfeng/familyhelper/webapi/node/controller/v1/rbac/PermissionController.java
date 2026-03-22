package com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac;

import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPermission;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermission;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.PermissionResponseService;
import com.dwarfeng.rbacds.sdk.bean.dto.FastJsonPermissionCreateResult;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionCreateInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionUpdateInfo;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
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
 * 权限控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController("rbacPermissionController")
@RequestMapping("/api/v1/rbac")
public class PermissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Permission, FastJsonPermission> beanTransformer;
    private final BeanTransformer<DispPermission, FastJsonDispPermission> dispBeanTransformer;

    public PermissionController(
            PermissionResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("rbac.permissionBeanTransformer")
            BeanTransformer<Permission, FastJsonPermission> beanTransformer,
            @Qualifier("rbac.dispPermissionBeanTransformer")
            BeanTransformer<DispPermission, FastJsonDispPermission> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    private PermissionKey key(String scopeId, String permissionStringId) {
        return new PermissionKey(scopeId, permissionStringId);
    }

    private PermissionGroupKey groupKey(String scopeId, String permissionGroupStringId) {
        return new PermissionGroupKey(scopeId, permissionGroupStringId);
    }

    @GetMapping("/permission/{scopeId}&{permissionStringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionStringId") String permissionStringId
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(service.exists(key(scopeId, permissionStringId))));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/{scopeId}&{permissionStringId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.get")
    public FastJsonResponseData<FastJsonPermission> get(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionStringId") String permissionStringId
    ) {
        try {
            Permission entity = service.get(key(scopeId, permissionStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPermission.of(entity)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> data = service.all(new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/permission")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.child_for_scope")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> childForScope(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> data = service.childForScope(new StringIdKey(scopeId), new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{permissionGroupStringId}/permission")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.child_for_group")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> childForGroup(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionGroupStringId") String permissionGroupStringId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> data = service.childForGroup(
                    groupKey(scopeId, permissionGroupStringId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/permission-string-id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.permission_string_id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> permissionStringIdLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> data = service.permissionStringIdLike(
                    pattern, new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.name_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> nameLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> data = service.nameLike(pattern, new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/permission/permission-string-id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.child_for_scope_permission_string_id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> childForScopePermissionStringIdLike(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> data = service.childForScopePermissionStringIdLike(
                    new StringIdKey(scopeId), pattern, new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/permission/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.create")
    public FastJsonResponseData<FastJsonPermissionCreateResult> create(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionCreateInfo webInput, BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonPermissionCreateResult.of(
                            service.create(WebInputPermissionCreateInfo.toStackBean(webInput))
                    )
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/permission/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionUpdateInfo webInput, BindingResult bindingResult
    ) {
        try {
            service.update(WebInputPermissionUpdateInfo.toStackBean(webInput));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/permission/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionRemoveInfo webInput, BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputPermissionRemoveInfo.toStackBean(webInput));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/{scopeId}&{permissionStringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.get_disp")
    public FastJsonResponseData<FastJsonDispPermission> getDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionStringId") String permissionStringId
    ) {
        try {
            DispPermission disp = service.getDisp(key(scopeId, permissionStringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispPermission.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermission>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermission> data = service.allDisp(new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/permission/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.child_for_scope_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermission>> childForScopeDisp(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermission> data = service.childForScopeDisp(
                    new StringIdKey(scopeId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{permissionGroupStringId}/permission/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.child_for_group_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermission>> childForGroupDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionGroupStringId") String permissionGroupStringId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermission> data = service.childForGroupDisp(
                    groupKey(scopeId, permissionGroupStringId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/permission-string-id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.permission_string_id_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermission>> permissionStringIdLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermission> data = service.permissionStringIdLikeDisp(
                    pattern, new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission.name_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermission>> nameLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermission> data = service.nameLikeDisp(pattern, new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
