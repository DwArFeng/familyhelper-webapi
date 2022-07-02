package com.dwarfeng.familyhelper.webapi.node.controller.v1.system;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.service.system.PermissionGroupResponseService;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputPermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
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
import java.util.Objects;

/**
 * 权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
public class PermissionGroupController {

    private final PermissionGroupResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<PermissionGroup, FastJsonPermissionGroup> permissionGroupBeanTransformer;
    private final BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispPermissionGroupBeanTransformer;

    public PermissionGroupController(
            PermissionGroupResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<PermissionGroup, FastJsonPermissionGroup> permissionGroupBeanTransformer,
            BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispPermissionGroupBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.permissionGroupBeanTransformer = permissionGroupBeanTransformer;
        this.dispPermissionGroupBeanTransformer = dispPermissionGroupBeanTransformer;
    }

    @GetMapping("/permission-group/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonPermissionGroup> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            PermissionGroup permissionGroup = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPermissionGroup.of(permissionGroup)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/permission-group")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputPermissionGroup webInputPermissionGroup, BindingResult bindingResult) {
        try {
            PermissionGroup permissionGroup = WebInputPermissionGroup.toStackBean(webInputPermissionGroup);
            StringIdKey insert = service.insert(permissionGroup);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/permission-group")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionGroup webInputPermissionGroup, BindingResult bindingResult) {
        try {
            service.update(WebInputPermissionGroup.toStackBean(webInputPermissionGroup));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/permission-group/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request, @PathVariable("id") String id, @RequestParam("nested") boolean nested
    ) {
        try {
            if (nested) {
                service.nestedDelete(new StringIdKey(id));
            } else {
                service.delete(new StringIdKey(id));
            }
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<PermissionGroup> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonPermissionGroup> transform = PagingUtil.transform(all, permissionGroupBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/id-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<PermissionGroup> idLike = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonPermissionGroup> transform = PagingUtil.transform(idLike, permissionGroupBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/permission-group/{parentId}/child", "/permission-group//child"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> childForParent(
            HttpServletRequest request,
            @PathVariable(required = false, value = "parentId") String parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey parentKey = null;
            if (Objects.nonNull(parentId)) {
                parentKey = new StringIdKey(parentId);
            }
            PagedData<PermissionGroup> childForParent = service.childForParent(
                    parentKey, new PagingInfo(page, rows)
            );
            PagedData<FastJsonPermissionGroup> transform = PagingUtil.transform(
                    childForParent, permissionGroupBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonDispPermissionGroup> getDisp(
            HttpServletRequest request, @PathVariable("id") String id) {
        try {
            DispPermissionGroup dispPermissionGroup = service.getDisp(new StringIdKey(id));
            return FastJsonResponseData.of(
                    ResponseDataUtil.good(FastJsonDispPermissionGroup.of(dispPermissionGroup))
            );
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/permission-group/id-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>> idLikeDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<DispPermissionGroup> idLike = service.idLikeDisp(
                    pattern, new PagingInfo(page, rows)
            );
            PagedData<FastJsonDispPermissionGroup> transform = PagingUtil.transform(
                    idLike, dispPermissionGroupBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/permission-group/{parentId}/child/disp", "/permission-group//child/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispPermissionGroup>> childForParentDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "parentId") String parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey parentKey = null;
            if (Objects.nonNull(parentId)) {
                parentKey = new StringIdKey(parentId);
            }
            PagedData<DispPermissionGroup> childForParent = service.childForParentDisp(
                    parentKey, new PagingInfo(page, rows)
            );
            PagedData<FastJsonDispPermissionGroup> transform = PagingUtil.transform(
                    childForParent, dispPermissionGroupBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
