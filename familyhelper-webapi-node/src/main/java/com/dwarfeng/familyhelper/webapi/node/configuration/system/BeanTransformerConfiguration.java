package com.dwarfeng.familyhelper.webapi.node.configuration.system;

import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.vo.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.vo.Account;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.JSFixedFastJsonPexp;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("system.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("system.accountBeanTransformer")
    public BeanTransformer<Account, FastJsonAccount> accountBeanTransformer() {
        return new MapStructBeanTransformer<>(Account.class, FastJsonAccount.class, FastJsonMapper.class);
    }

    @Bean("system.dispAccountBeanTransformer")
    public BeanTransformer<DispAccount, FastJsonDispAccount> dispAccountBeanTransformer() {
        return new MapStructBeanTransformer<>(DispAccount.class, FastJsonDispAccount.class, FastJsonMapper.class);
    }

    @Bean("system.permissionBeanTransformer")
    public BeanTransformer<Permission, FastJsonPermission> permissionBeanTransformer() {
        return new MapStructBeanTransformer<>(Permission.class, FastJsonPermission.class, FastJsonMapper.class);
    }

    @Bean("system.roleBeanTransformer")
    public BeanTransformer<Role, FastJsonRole> roleBeanTransformer() {
        return new MapStructBeanTransformer<>(Role.class, FastJsonRole.class, FastJsonMapper.class);
    }

    @Bean("system.pexpBeanTransformer")
    public BeanTransformer<Pexp, JSFixedFastJsonPexp> pexpBeanTransformer() {
        return new MapStructBeanTransformer<>(Pexp.class, JSFixedFastJsonPexp.class, FastJsonMapper.class);
    }

    @Bean("system.permissionGroupBeanTransformer")
    public BeanTransformer<PermissionGroup, FastJsonPermissionGroup> permissionGroupBeanTransformer() {
        return new MapStructBeanTransformer<>(
                PermissionGroup.class, FastJsonPermissionGroup.class, FastJsonMapper.class
        );
    }

    @Bean("system.dispPermissionGroupBeanTransformer")
    public BeanTransformer<DispPermissionGroup, FastJsonDispPermissionGroup> dispPermissionGroupBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispPermissionGroup.class, FastJsonDispPermissionGroup.class, FastJsonMapper.class
        );
    }

    @Bean("system.loginStateBeanTransformer")
    public BeanTransformer<LoginState, JSFixedFastJsonLoginState> loginStateBeanTransformer() {
        return new MapStructBeanTransformer<>(LoginState.class, JSFixedFastJsonLoginState.class, FastJsonMapper.class);
    }

    @Bean("system.loginHistoryBeanTransformer")
    public BeanTransformer<LoginHistory, JSFixedFastJsonLoginHistory> loginHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                LoginHistory.class, JSFixedFastJsonLoginHistory.class, FastJsonMapper.class
        );
    }

    @Bean("system.loginParamRecordBeanTransformer")
    public BeanTransformer<LoginParamRecord, JSFixedFastJsonLoginParamRecord> loginParamRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                LoginParamRecord.class, JSFixedFastJsonLoginParamRecord.class, FastJsonMapper.class
        );
    }

    @Bean("system.protectDetailRecordBeanTransformer")
    public BeanTransformer<ProtectDetailRecord, JSFixedFastJsonProtectDetailRecord>
    protectDetailRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ProtectDetailRecord.class, JSFixedFastJsonProtectDetailRecord.class, FastJsonMapper.class
        );
    }

    @Bean("system.deriveHistoryBeanTransformer")
    public BeanTransformer<DeriveHistory, JSFixedFastJsonDeriveHistory> deriveHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DeriveHistory.class, JSFixedFastJsonDeriveHistory.class, FastJsonMapper.class
        );
    }
}
