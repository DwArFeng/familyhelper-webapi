package com.dwarfeng.familyhelper.webapi.node.configuration.system;

import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
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
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Mapper
public interface FastJsonMapper {

    FastJsonAccount accountToFastJson(Account account);

    @InheritInverseConfiguration
    Account accountFromFastJson(FastJsonAccount fastJsonAccount);

    FastJsonDispAccount dispAccountToFastJson(DispAccount dispAccount);

    @InheritInverseConfiguration
    DispAccount dispAccountFromFastJson(FastJsonDispAccount fastJsonDispAccount);

    FastJsonPermission permissionToFastJson(Permission permission);

    @InheritInverseConfiguration
    Permission permissionFromFastJson(FastJsonPermission fastJsonPermission);

    FastJsonRole roleToFastJson(Role role);

    @InheritInverseConfiguration
    Role roleFromFastJson(FastJsonRole fastJsonRole);

    JSFixedFastJsonPexp pexpToJsFixedFastJson(Pexp pexp);

    @InheritInverseConfiguration
    Pexp pexpFromJsFixedFastJson(JSFixedFastJsonPexp jsFixedFastJsonPexp);

    FastJsonPermissionGroup permissionGroupToFastJson(PermissionGroup permissionGroup);

    @InheritInverseConfiguration
    PermissionGroup permissionGroupFromFastJson(FastJsonPermissionGroup fastJsonPermissionGroup);

    FastJsonDispPermissionGroup dispPermissionGroupToFastJson(DispPermissionGroup dispPermissionGroup);

    @InheritInverseConfiguration
    DispPermissionGroup dispPermissionGroupFromFastJson(FastJsonDispPermissionGroup fastJsonDispPermissionGroup);

    JSFixedFastJsonLoginState loginStateToJsFixedFastJson(LoginState loginState);

    @InheritInverseConfiguration
    LoginState loginStateFromJsFixedFastJson(JSFixedFastJsonLoginState jsFixedFastJsonLoginState);

    JSFixedFastJsonLoginHistory loginHistoryToJsFixedFastJson(LoginHistory loginHistory);

    @InheritInverseConfiguration
    LoginHistory loginHistoryFromJsFixedFastJson(JSFixedFastJsonLoginHistory jsFixedFastJsonLoginHistory);

    JSFixedFastJsonLoginParamRecord loginParamRecordToJsFixedFastJson(LoginParamRecord loginParamRecord);

    @InheritInverseConfiguration
    LoginParamRecord loginParamRecordFromJsFixedFastJson(
            JSFixedFastJsonLoginParamRecord jsFixedFastJsonLoginParamRecord
    );

    JSFixedFastJsonProtectDetailRecord protectDetailRecordToJsFixedFastJson(ProtectDetailRecord protectDetailRecord);

    @InheritInverseConfiguration
    ProtectDetailRecord protectDetailRecordFromJsFixedFastJson(
            JSFixedFastJsonProtectDetailRecord jsFixedFastJsonProtectDetailRecord
    );

    JSFixedFastJsonDeriveHistory deriveHistoryToJsFixedFastJson(DeriveHistory deriveHistory);

    @InheritInverseConfiguration
    DeriveHistory deriveHistoryFromJsFixedFastJson(JSFixedFastJsonDeriveHistory jsFixedFastJsonDeriveHistory);
}
