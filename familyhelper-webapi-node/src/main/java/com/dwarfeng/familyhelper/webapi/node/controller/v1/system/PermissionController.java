package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.familyhelper.webapi.stack.service.system.PermissionResponseService;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputPermission;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class PermissionController {

    private final PermissionResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Permission, FastJsonPermission> beanTransformer;

    public PermissionController(
            PermissionResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Permission, FastJsonPermission> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/permission/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/permission/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonPermission> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            Permission permission = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPermission.of(permission)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonPermission.class, e, sem));
        }
    }

    @PostMapping("/permission")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputPermission webInputPermission, BindingResult bindingResult) {
        try {
            Permission permission = WebInputPermission.toStackBean(webInputPermission);
            StringIdKey insert = service.insert(permission);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonStringIdKey.class, e, sem));
        }
    }

    @PatchMapping("/permission")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermission webInputPermission, BindingResult bindingResult) {
        try {
            service.update(WebInputPermission.toStackBean(webInputPermission));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/permission/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/permission/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Permission> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonPermission> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/permission/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> idLike = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonPermission> transform = PagingUtil.transform(idLike, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping(value = {
            "/permission-group/{permissionGroupId}/permission", "/permission-group//permission"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> childForGroup(
            HttpServletRequest request,
            @PathVariable(required = false, value = "permissionGroupId") String permissionGroupId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey permissionGroupKey = null;
            if (Objects.nonNull(permissionGroupId)) {
                permissionGroupKey = new StringIdKey(permissionGroupId);
            }
            PagedData<Permission> childForGroup = service.childForGroup(
                    permissionGroupKey, new PagingInfo(page, rows)
            );
            PagedData<FastJsonPermission> transform = PagingUtil.transform(childForGroup, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @PostMapping("/permission/lookup-for-user")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<List<FastJsonPermission>> lookupForUser(
            HttpServletRequest request,
            @RequestBody @Validated WebInputStringIdKey userKey, BindingResult bindingResult
    ) {
        try {
            List<Permission> permissions = service.lookupForUser(WebInputStringIdKey.toStackBean(userKey));
            List<FastJsonPermission> result = permissions.stream().map(FastJsonPermission::of).collect(Collectors.toList());
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(List.class, e, sem));
        }
    }
}
