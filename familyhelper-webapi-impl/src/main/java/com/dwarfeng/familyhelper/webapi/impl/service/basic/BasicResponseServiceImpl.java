package com.dwarfeng.familyhelper.webapi.impl.service.basic;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic.LoginRequest;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic.LoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.basic.Account;
import com.dwarfeng.familyhelper.webapi.stack.service.basic.AccountResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.basic.BasicResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionLookupService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BasicResponseServiceImpl implements BasicResponseService {

    private final LoginService loginService;
    private final PermissionLookupService permissionLookupService;
    private final AccountResponseService accountResponseService;

    private final ServiceExceptionMapper sem;

    public BasicResponseServiceImpl(
            @Qualifier("acckeeperLoginService") LoginService loginService,
            @Qualifier("rbacPermissionLookupService") PermissionLookupService permissionLookupService,
            AccountResponseService accountResponseService,
            ServiceExceptionMapper sem
    ) {
        this.loginService = loginService;
        this.permissionLookupService = permissionLookupService;
        this.accountResponseService = accountResponseService;
        this.sem = sem;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws ServiceException {
        try {
            LoginState loginState = loginService.login(
                    new StringIdKey(loginRequest.getAccountId()), loginRequest.getPassword()
            );
            return new LoginResponse(
                    loginRequest.getAccountId(),
                    loginState.getKey().getLongId(),
                    new Date(loginState.getExpireDate())
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("登录时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public LoginResponse getLoginResponse(LongIdKey longIdKey) throws ServiceException {
        try {
            LoginState loginState = loginService.getLoginState(longIdKey);
            return new LoginResponse(
                    loginState.getAccountKey().getStringId(),
                    loginState.getKey().getLongId(),
                    new Date(loginState.getExpireDate())
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取登录状态时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public List<String> getPermissions(LongIdKey longIdKey) throws ServiceException {
        try {
            LoginState loginState = loginService.getLoginState(longIdKey);
            List<Permission> permissions = permissionLookupService.lookupPermissions(loginState.getAccountKey());
            return permissions.stream().map(p -> p.getKey().getStringId()).collect(Collectors.toList());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取权限列表时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public boolean isLogin(LongIdKey longIdKey) throws ServiceException {
        try {
            return loginService.isLogin(longIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("判断指定登录主键是否登录时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void postpone(LongIdKey longIdKey) throws ServiceException {
        try {
            loginService.postpone(longIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("延长指定主键的过期时间时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void logout(LongIdKey longIdKey) throws ServiceException {
        try {
            loginService.logout(longIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("延长指定主键的过期时间时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public Account whoAmI(LongIdKey longIdKey) throws ServiceException {
        try {
            LoginState loginState = loginService.getLoginState(longIdKey);
            return accountResponseService.get(loginState.getAccountKey());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取权限列表时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
