package com.dwarfeng.familyhelper.webapi.node.controller.v1.rbac;

import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispRoleUserRelation;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispRoleUserRelation;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.RoleUserRelationResponseService;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRoleUserRelation;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputRoleUserRelation;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonRoleUserRelationKey;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
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
 * 角色用户关系控制器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RestController
@RequestMapping("/api/v1/rbac")
public class RoleUserRelationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleUserRelationController.class);

    private final RoleUserRelationResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<RoleUserRelation, FastJsonRoleUserRelation> beanTransformer;
    private final BeanTransformer<DispRoleUserRelation, FastJsonDispRoleUserRelation> dispBeanTransformer;

    public RoleUserRelationController(
            RoleUserRelationResponseService service,
            ServiceExceptionMapper sem,
            @Qualifier("rbac.roleUserRelationBeanTransformer")
            BeanTransformer<RoleUserRelation, FastJsonRoleUserRelation> beanTransformer,
            @Qualifier("rbac.dispRoleUserRelationBeanTransformer")
            BeanTransformer<DispRoleUserRelation, FastJsonDispRoleUserRelation> dispBeanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.dispBeanTransformer = dispBeanTransformer;
    }

    @GetMapping("/role-user-relation/{roleId}&{userId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.exists")
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("roleId") String roleId,
            @PathVariable("userId") String userId
    ) {
        try {
            return FastJsonResponseData.of(ResponseDataUtil.good(service.exists(key(roleId, userId))));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role-user-relation/{roleId}&{userId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.get")
    public FastJsonResponseData<FastJsonRoleUserRelation> get(
            HttpServletRequest request,
            @PathVariable("roleId") String roleId,
            @PathVariable("userId") String userId
    ) {
        try {
            RoleUserRelation entity = service.get(key(roleId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonRoleUserRelation.of(entity)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/role-user-relation")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.insert")
    @AuditRecord("webapi.rbac.role_user_relation.insert")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "rbac.roleUserRelationInsertResultContributor", prefix = "result.")
    public FastJsonResponseData<FastJsonRoleUserRelationKey> insert(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class)
            @AuditParamContribute(
                    contributor = "rbac.roleUserRelationInsertRoleUserRelationParamContributor",
                    prefix = "param.role_user_relation."
            )
            WebInputRoleUserRelation roleUserRelation,
            BindingResult bindingResult
    ) {
        try {
            RoleUserRelation entity = WebInputRoleUserRelation.toStackBean(roleUserRelation);
            RoleUserRelationKey result = service.insert(entity);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonRoleUserRelationKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/role-user-relation")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.update")
    @AuditRecord("webapi.rbac.role_user_relation.update")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "rbac.roleUserRelationUpdateResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> update(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @RequestBody @Validated
            @AuditParamContribute(
                    contributor = "rbac.roleUserRelationUpdateRoleUserRelationParamContributor",
                    prefix = "param.role_user_relation."
            )
            WebInputRoleUserRelation roleUserRelation,
            BindingResult bindingResult
    ) {
        try {
            service.update(WebInputRoleUserRelation.toStackBean(roleUserRelation));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/role-user-relation/{roleId}&{userId}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.delete")
    @AuditRecord("webapi.rbac.role_user_relation.delete")
    @AuditInvocationContribute(contributor = "system.methodContributor", prefix = "method.")
    @AuditResultContribute(contributor = "rbac.roleUserRelationDeleteResultContributor", prefix = "result.")
    public FastJsonResponseData<Object> delete(
            @AuditParamContribute(contributor = "system.requestRequestContributor", prefix = "request.request.")
            @AuditParamContribute(contributor = "system.requestTokenContributor", prefix = "request.token.")
            HttpServletRequest request,
            @PathVariable("roleId")
            @AuditParamContribute(
                    contributor = "rbac.roleUserRelationDeleteRoleIdParamContributor",
                    prefix = "param.role_id."
            )
            String roleId,
            @PathVariable("userId")
            @AuditParamContribute(
                    contributor = "rbac.roleUserRelationDeleteUserIdParamContributor",
                    prefix = "param.user_id."
            )
            String userId
    ) {
        try {
            service.delete(key(roleId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role-user-relation/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonRoleUserRelation>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RoleUserRelation> data = service.all(new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role/{roleId}/role-user-relation")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.child_for_role")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonRoleUserRelation>> childForRole(
            HttpServletRequest request, @PathVariable("roleId") String roleId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RoleUserRelation> data = service.childForRole(
                    new StringIdKey(roleId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/user/{userId}/role-user-relation")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.child_for_user")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonRoleUserRelation>> childForUser(
            HttpServletRequest request, @PathVariable("userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<RoleUserRelation> data = service.childForUser(
                    new StringIdKey(userId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, beanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role-user-relation/{roleId}&{userId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.get_disp")
    public FastJsonResponseData<FastJsonDispRoleUserRelation> getDisp(
            HttpServletRequest request,
            @PathVariable("roleId") String roleId,
            @PathVariable("userId") String userId
    ) {
        try {
            DispRoleUserRelation disp = service.getDisp(key(roleId, userId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispRoleUserRelation.of(disp)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role-user-relation/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.all_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispRoleUserRelation>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispRoleUserRelation> data = service.allDisp(new PagingInfo(page, rows));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/role/{roleId}/role-user-relation/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.child_for_role_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispRoleUserRelation>> childForRoleDisp(
            HttpServletRequest request, @PathVariable("roleId") String roleId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispRoleUserRelation> data = service.childForRoleDisp(
                    new StringIdKey(roleId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/user/{userId}/role-user-relation/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.rbac.role_user_relation.child_for_user_disp")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDispRoleUserRelation>> childForUserDisp(
            HttpServletRequest request, @PathVariable("userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<DispRoleUserRelation> data = service.childForUserDisp(
                    new StringIdKey(userId), new PagingInfo(page, rows)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    JSFixedFastJsonPagedData.of(PagingUtil.transform(data, dispBeanTransformer))
            ));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private RoleUserRelationKey key(String roleId, String userId) {
        return new RoleUserRelationKey(roleId, userId);
    }
}
