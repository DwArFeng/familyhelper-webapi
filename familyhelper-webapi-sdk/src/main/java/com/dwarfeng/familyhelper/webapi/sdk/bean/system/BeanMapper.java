package com.dwarfeng.familyhelper.webapi.sdk.bean.system;

import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.vo.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.vo.WebInputAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.*;
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

    // region Familyhelper-webapi Disp

    FastJsonDispAccount dispAccountToFastJson(DispAccount dispAccount);

    @InheritInverseConfiguration
    DispAccount dispAccountFromFastJson(FastJsonDispAccount fastJsonDispAccount);

    // endregion

    // region Familyhelper-webapi DTO

    FastJsonLoginResult loginResultToFastJson(LoginResult loginResult);

    @InheritInverseConfiguration
    LoginResult loginResultFromFastJson(FastJsonLoginResult fastJsonLoginResult);

    FastJsonPermissionInspectResult permissionInspectResultToFastJson(PermissionInspectResult permissionInspectResult);

    @InheritInverseConfiguration
    PermissionInspectResult permissionInspectResultFromFastJson(
            FastJsonPermissionInspectResult fastJsonPermissionInspectResult
    );

    FastJsonPostponeResult postponeResultToFastJson(PostponeResult postponeResult);

    @InheritInverseConfiguration
    PostponeResult postponeResultFromFastJson(FastJsonPostponeResult fastJsonPostponeResult);

    WebInputLoginInfo loginInfoToWebInput(LoginInfo loginInfo);

    @InheritInverseConfiguration
    LoginInfo loginInfoFromWebInput(WebInputLoginInfo webInputLoginInfo);

    WebInputLogoutInfo logoutInfoToWebInput(LogoutInfo logoutInfo);

    @InheritInverseConfiguration
    LogoutInfo logoutInfoFromWebInput(WebInputLogoutInfo webInputLogoutInfo);

    WebInputPermissionInspectInfo permissionInspectInfoToWebInput(PermissionInspectInfo permissionInspectInfo);

    @InheritInverseConfiguration
    PermissionInspectInfo permissionInspectInfoFromWebInput(
            WebInputPermissionInspectInfo webInputPermissionInspectInfo
    );

    WebInputPostponeInfo postponeInfoToWebInput(PostponeInfo postponeInfo);

    @InheritInverseConfiguration
    PostponeInfo postponeInfoFromWebInput(WebInputPostponeInfo webInputPostponeInfo);

    // endregion

    // region Familyhelper-webapi VO

    FastJsonAccount accountToFastJson(Account account);

    @InheritInverseConfiguration
    Account accountFromFastJson(FastJsonAccount fastJsonAccount);

    WebInputAccount accountToWebInput(Account account);

    @InheritInverseConfiguration
    Account accountFromWebInput(WebInputAccount webInputAccount);

    // endregion
}
