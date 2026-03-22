package com.dwarfeng.familyhelper.webapi.node.configuration.rbac;

import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPermission;
import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPexp;
import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispRoleUserRelation;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermission;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPexp;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispRoleUserRelation;
import com.dwarfeng.rbacds.sdk.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("rbac.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("rbac.scopeBeanTransformer")
    public BeanTransformer<Scope, FastJsonScope> scopeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Scope.class, FastJsonScope.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.roleBeanTransformer")
    public BeanTransformer<Role, FastJsonRole> roleBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Role.class, FastJsonRole.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.userBeanTransformer")
    public BeanTransformer<User, FastJsonUser> userBeanTransformer() {
        return new MapStructBeanTransformer<>(
                User.class, FastJsonUser.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.filterSupportBeanTransformer")
    public BeanTransformer<FilterSupport, FastJsonFilterSupport> filterSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                FilterSupport.class, FastJsonFilterSupport.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.pexpBeanTransformer")
    public BeanTransformer<Pexp, FastJsonPexp> pexpBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Pexp.class, FastJsonPexp.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.permissionBeanTransformer")
    public BeanTransformer<Permission, FastJsonPermission> permissionBeanTransformer() {
        return new MapStructBeanTransformer<>(
                Permission.class, FastJsonPermission.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.permissionGroupBeanTransformer")
    public BeanTransformer<PermissionGroup, FastJsonPermissionGroup> permissionGroupBeanTransformer() {
        return new MapStructBeanTransformer<>(
                PermissionGroup.class, FastJsonPermissionGroup.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.roleUserRelationBeanTransformer")
    public BeanTransformer<RoleUserRelation, FastJsonRoleUserRelation> roleUserRelationBeanTransformer() {
        return new MapStructBeanTransformer<>(
                RoleUserRelation.class, FastJsonRoleUserRelation.class,
                com.dwarfeng.rbacds.sdk.bean.BeanMapper.class
        );
    }

    @Bean("rbac.dispPermissionBeanTransformer")
    public BeanTransformer<DispPermission, FastJsonDispPermission> dispPermissionBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispPermission.class, FastJsonDispPermission.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.BeanMapper.class
        );
    }

    @Bean("rbac.dispPermissionGroupBeanTransformer")
    public BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispPermissionGroupBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispPermissionGroup.class, FastJsonDispPermissionGroup.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.BeanMapper.class
        );
    }

    @Bean("rbac.dispPexpBeanTransformer")
    public BeanTransformer<DispPexp, FastJsonDispPexp> dispPexpBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispPexp.class, FastJsonDispPexp.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.BeanMapper.class
        );
    }

    @Bean("rbac.dispRoleUserRelationBeanTransformer")
    public BeanTransformer<DispRoleUserRelation, FastJsonDispRoleUserRelation> dispRoleUserRelationBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispRoleUserRelation.class, FastJsonDispRoleUserRelation.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.BeanMapper.class
        );
    }
}
