package com.dwarfeng.familyhelper.webapi.node.configuration.system;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.system.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
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
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonSettingNode;
import com.dwarfeng.settingrepo.stack.bean.entity.SettingNode;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("system.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("system.accountBeanTransformer")
    public BeanTransformer<Account, FastJsonAccount> accountBeanTransformer() {
        return new DozerBeanTransformer<>(Account.class, FastJsonAccount.class, mapper);
    }

    @Bean("system.dispAccountBeanTransformer")
    public BeanTransformer<DispAccount, FastJsonDispAccount> dispAccountBeanTransformer() {
        return new DozerBeanTransformer<>(DispAccount.class, FastJsonDispAccount.class, mapper);
    }

    @Bean("system.permissionBeanTransformer")
    public BeanTransformer<Permission, FastJsonPermission> permissionBeanTransformer() {
        return new DozerBeanTransformer<>(Permission.class, FastJsonPermission.class, mapper);
    }

    @Bean("system.roleBeanTransformer")
    public BeanTransformer<Role, FastJsonRole> roleBeanTransformer() {
        return new DozerBeanTransformer<>(Role.class, FastJsonRole.class, mapper);
    }

    @Bean("system.pexpBeanTransformer")
    public BeanTransformer<Pexp, JSFixedFastJsonPexp> pexpBeanTransformer() {
        return new DozerBeanTransformer<>(Pexp.class, JSFixedFastJsonPexp.class, mapper);
    }

    @Bean("system.permissionGroupBeanTransformer")
    public BeanTransformer<PermissionGroup, FastJsonPermissionGroup> permissionGroupBeanTransformer() {
        return new DozerBeanTransformer<>(PermissionGroup.class, FastJsonPermissionGroup.class, mapper);
    }

    @Bean("system.dispPermissionGroupBeanTransformer")
    public BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispPermissionGroupBeanTransformer() {
        return new DozerBeanTransformer<>(DispPermissionGroup.class, FastJsonDispPermissionGroup.class, mapper);
    }

    @Bean("system.settingNodeBeanTransformer")
    public BeanTransformer<SettingNode, FastJsonSettingNode> settingNodeBeanTransformer() {
        return new DozerBeanTransformer<>(SettingNode.class, FastJsonSettingNode.class, mapper);
    }
}
