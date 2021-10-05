package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.system.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.system.Account;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.JSFixedFastJsonPexp;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemBeanTransformerConfiguration {

    private final Mapper mapper;

    public SystemBeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public BeanTransformer<Account, FastJsonAccount> accountBeanTransformer() {
        return new DozerBeanTransformer<>(Account.class, FastJsonAccount.class, mapper);
    }

    @Bean
    public BeanTransformer<Permission, FastJsonPermission> permissionBeanTransformer() {
        return new DozerBeanTransformer<>(Permission.class, FastJsonPermission.class, mapper);
    }

    @Bean
    public BeanTransformer<Role, FastJsonRole> roleBeanTransformer() {
        return new DozerBeanTransformer<>(Role.class, FastJsonRole.class, mapper);
    }

    @Bean
    public BeanTransformer<Pexp, JSFixedFastJsonPexp> pexpBeanTransformer() {
        return new DozerBeanTransformer<>(Pexp.class, JSFixedFastJsonPexp.class, mapper);
    }

    @Bean
    public BeanTransformer<PermissionGroup, FastJsonPermissionGroup> permissionGroupBeanTransformer() {
        return new DozerBeanTransformer<>(PermissionGroup.class, FastJsonPermissionGroup.class, mapper);
    }

    @Bean
    public BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispPermissionGroupBeanTransformer() {
        return new DozerBeanTransformer<>(DispPermissionGroup.class, FastJsonDispPermissionGroup.class, mapper);
    }
}
