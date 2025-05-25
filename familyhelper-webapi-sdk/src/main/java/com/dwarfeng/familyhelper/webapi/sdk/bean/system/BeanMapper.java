package com.dwarfeng.familyhelper.webapi.sdk.bean.system;

import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.JSFixedFastJsonDeriveResponse;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.JSFixedFastJsonLoginResponse;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.vo.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.vo.WebInputAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.DeriveResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.LoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.vo.Account;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Familyhelper-webapi Disp-----------------------------------------------------------
    FastJsonDispAccount dispAccountToFastJson(DispAccount dispAccount);

    @InheritInverseConfiguration
    DispAccount dispAccountFromFastJson(FastJsonDispAccount fastJsonDispAccount);

    FastJsonDispPermissionGroup dispPermissionGroupToFastJson(DispPermissionGroup dispPermissionGroup);

    @InheritInverseConfiguration
    DispPermissionGroup dispPermissionGroupFromFastJson(FastJsonDispPermissionGroup fastJsonDispPermissionGroup);

    // -----------------------------------------------------------Familyhelper-webapi DTO-----------------------------------------------------------
    JSFixedFastJsonDeriveResponse deriveResponseToJSFixedFastJson(DeriveResponse deriveResponse);

    @InheritInverseConfiguration
    DeriveResponse deriveResponseFromJSFixedFastJson(JSFixedFastJsonDeriveResponse jSFixedFastJsonDeriveResponse);

    JSFixedFastJsonLoginResponse loginResponseToJSFixedFastJson(LoginResponse loginResponse);

    @InheritInverseConfiguration
    LoginResponse loginResponseFromJSFixedFastJson(JSFixedFastJsonLoginResponse jSFixedFastJsonLoginResponse);

    // -----------------------------------------------------------Familyhelper-webapi VO-----------------------------------------------------------
    FastJsonAccount accountToFastJson(Account account);

    @InheritInverseConfiguration
    Account accountFromFastJson(FastJsonAccount fastJsonAccount);

    WebInputAccount accountToWebInput(Account account);

    @InheritInverseConfiguration
    Account accountFromWebInput(WebInputAccount webInputAccount);
}
