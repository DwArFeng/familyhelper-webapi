package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginResult;
import com.dwarfeng.acckeeper.stack.service.AccessService;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.*;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginResponseServiceImpl implements LoginResponseService {

    private static final String LOGIN_REMARK = "通过 familyhelper-webapi 登录";
    private static final Map<String, String> LOGIN_EMPTY_EXTRA_PARAM_MAP = Collections.emptyMap();

    private final AccessService accessService;

    public LoginResponseServiceImpl(@Qualifier("acckeeperAccessService") AccessService accessService) {
        this.accessService = accessService;
    }

    @Override
    public LoginResult login(LoginInfo info) throws ServiceException {
        // 将 LoginInfo 转换为 DynamicLoginInfo。
        DynamicLoginInfo dynamicLoginInfo = toAcckeeperDynamicLoginInfo(
                info, LOGIN_REMARK, LOGIN_EMPTY_EXTRA_PARAM_MAP
        );

        // 调用 AccessService 的 dynamicLogin 方法进行登录。
        DynamicLoginResult dynamicLoginResult = accessService.dynamicLogin(dynamicLoginInfo);

        // 将 DynamicLoginResult 转换为 LoginResult。
        return toWebapiLoginResult(dynamicLoginResult);
    }

    @Override
    public void logout(LogoutInfo info) throws ServiceException {
        // 将 LogoutInfo 转换为 com.dwarfeng.acckeeper.stack.bean.dto.LogoutInfo。
        com.dwarfeng.acckeeper.stack.bean.dto.LogoutInfo acckeeperLogoutInfo = toAcckeeperLogoutInfo(info);

        // 调用 AccessService 的 logout 方法进行登出。
        accessService.logout(acckeeperLogoutInfo);
    }

    @Override
    public PostponeResult postpone(PostponeInfo info) throws ServiceException {
        // 将 PostponeInfo 转换为 com.dwarfeng.acckeeper.stack.bean.dto.PostponeInfo。
        com.dwarfeng.acckeeper.stack.bean.dto.PostponeInfo acckeeperPostponeInfo = toAcckeeperPostponeInfo(info);

        // 调用 AccessService 的 postpone 方法进行延期。
        com.dwarfeng.acckeeper.stack.bean.dto.PostponeResult acckeeperPostponeResult =
                accessService.postpone(acckeeperPostponeInfo);

        // 将 com.dwarfeng.acckeeper.stack.bean.dto.PostponeResult 转换为 PostponeResult。
        return toWebapiPostponeResult(acckeeperPostponeResult);
    }

    // 为了方法的可扩展性，此处不做简化。
    @SuppressWarnings("SameParameterValue")
    private DynamicLoginInfo toAcckeeperDynamicLoginInfo(
            LoginInfo info, String remark, Map<String, String> extraParamMap
    ) {
        if (Objects.isNull(info)) {
            return null;
        } else {
            return new DynamicLoginInfo(
                    info.getAccountKey(),
                    info.getPassword(),
                    remark,
                    extraParamMap
            );
        }
    }

    private LoginResult toWebapiLoginResult(DynamicLoginResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new LoginResult(
                    result.getLoginStateKey(),
                    result.getAccountKey(),
                    result.getExpireDate(),
                    result.getGeneratedDate(),
                    result.getType(),
                    result.getRemark()
            );
        }
    }

    private com.dwarfeng.acckeeper.stack.bean.dto.LogoutInfo toAcckeeperLogoutInfo(LogoutInfo info) {
        if (Objects.isNull(info)) {
            return null;
        } else {
            return new com.dwarfeng.acckeeper.stack.bean.dto.LogoutInfo(
                    info.getLoginStateKey()
            );
        }
    }

    private com.dwarfeng.acckeeper.stack.bean.dto.PostponeInfo toAcckeeperPostponeInfo(PostponeInfo info) {
        if (Objects.isNull(info)) {
            return null;
        } else {
            return new com.dwarfeng.acckeeper.stack.bean.dto.PostponeInfo(
                    info.getLoginStateKey()
            );
        }
    }

    private PostponeResult toWebapiPostponeResult(com.dwarfeng.acckeeper.stack.bean.dto.PostponeResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new PostponeResult(
                    result.getLoginStateKey(),
                    result.getAccountKey(),
                    result.getExpireDate(),
                    result.getGeneratedDate(),
                    result.getType(),
                    result.getRemark()
            );
        }
    }
}
