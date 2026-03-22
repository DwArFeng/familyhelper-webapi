package com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac;

import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.PermissionGroupResponseService;
import com.dwarfeng.rbacds.sdk.bean.dto.FastJsonPermissionGroupCreateResult;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupCreateInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
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
 * 权限组控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/rbac")
public class PermissionGroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionGroupController.class);

    private final PermissionGroupResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<PermissionGroup, FastJsonPermissionGroup> beanTransformer;
    private final BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispBeanTransformer;

    public PermissionGroupController(
            PermissionGroupResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("rbac.permissionGroupBeanTransformer")
            BeanTransformer<PermissionGroup, FastJsonPermissionGroup> beanTransformer,
            @Qualifier("rbac.dispPermissionGroupBeanTransformer")
            BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/permission-group/{scopeId}&{permissionGroupId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("permissionGroupId") String permissionGroupId
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(service.exists(key(scopeId, permissionGroupId))));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{permissionGroupId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.get")
    public FastJsonResponseData<FastJsonPermissionGroup> get(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("permissionGroupId") String permissionGroupId
    ) {
        try {
            PermissionGroup entity = service.get(key(scopeId, permissionGroupId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPermissionGroup.of(entity)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> all = service.all(new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(all, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/permission-group")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_scope")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> childForScope(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> data = service.childForScope(
                    new StringIdKey(scopeId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{parentId}/children")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_parent")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> childForParent(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("parentId") String parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> data = service.childForParent(
                    key(scopeId, parentId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/permission-group-string-id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.permission_group_string_id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> permissionGroupStringIdLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> data = service.permissionGroupStringIdLike(
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

    @GetMapping("/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_parent_permission_group_string_id_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>>
    childForParentPermissionGroupStringIdLike(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("parentId") String parentId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> data = service.childForParentPermissionGroupStringIdLike(
                    key(scopeId, parentId), pattern, new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.name_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> nameLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> data = service.nameLike(pattern, new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{parentId}/children/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_parent_name_like")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> childForParentNameLike(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("parentId") String parentId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> data = service.childForParentNameLike(
                    key(scopeId, parentId), pattern, new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/permission-group/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_scope_root")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> childForScopeRoot(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> data = service.childForScopeRoot(
                    new StringIdKey(scopeId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{permissionGroupId}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.path_from_root")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> pathFromRoot(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionGroupId") String permissionGroupId
    ) {
        try {
            PagedData<PermissionGroup> data = service.pathFromRoot(key(scopeId, permissionGroupId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{permissionGroupId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.get_disp")
    public FastJsonResponseData<FastJsonDispPermissionGroup> getDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionGroupId") String permissionGroupId
    ) {
        try {
            DispPermissionGroup disp = service.getDisp(key(scopeId, permissionGroupId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispPermissionGroup.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.allDisp(new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/permission-group/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_scope_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>> childForScopeDisp(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.childForScopeDisp(
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

    @GetMapping("/permission-group/{scopeId}&{parentId}/children/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_parent_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>> childForParentDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("parentId") String parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.childForParentDisp(
                    key(scopeId, parentId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/permission-group-string-id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.permission_group_string_id_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>>
    permissionGroupStringIdLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.permissionGroupStringIdLikeDisp(
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

    @GetMapping("/permission-group/{scopeId}&{parentId}/children/permission-group-string-id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_parent_permission_group_string_id_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>>
    childForParentPermissionGroupStringIdLikeDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("parentId") String parentId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.childForParentPermissionGroupStringIdLikeDisp(
                    key(scopeId, parentId), pattern, new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.name_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>>
    nameLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.nameLikeDisp(pattern, new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{scopeId}&{parentId}/children/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_parent_name_like_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>>
    childForParentNameLikeDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId, @PathVariable("parentId") String parentId,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.childForParentNameLikeDisp(
                    key(scopeId, parentId), pattern, new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/scope/{scopeId}/permission-group/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.child_for_scope_root_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>> childForScopeRootDisp(
            HttpServletRequest request, @PathVariable("scopeId") String scopeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.childForScopeRootDisp(
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

    @GetMapping("/permission-group/{scopeId}&{permissionGroupId}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.path_from_root_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>> pathFromRootDisp(
            HttpServletRequest request,
            @PathVariable("scopeId") String scopeId,
            @PathVariable("permissionGroupId") String permissionGroupId
    ) {
        try {
            PagedData<DispPermissionGroup> data = service.pathFromRootDisp(key(scopeId, permissionGroupId));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/permission-group/create")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.create")
    public FastJsonResponseData<FastJsonPermissionGroupCreateResult> create(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionGroupCreateInfo webInput, BindingResult bindingResult
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    FastJsonPermissionGroupCreateResult.of(
                            service.create(WebInputPermissionGroupCreateInfo.toStackBean(webInput))
                    )
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/permission-group/update")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.update")
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionGroupUpdateInfo webInput, BindingResult bindingResult
    ) {
        try {
            service.update(WebInputPermissionGroupUpdateInfo.toStackBean(webInput));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/permission-group/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.permission_group.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionGroupRemoveInfo webInput, BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputPermissionGroupRemoveInfo.toStackBean(webInput));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private PermissionGroupKey key(String scopeId, String permissionGroupId) {
        return new PermissionGroupKey(scopeId, permissionGroupId);
    }
}
