package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.LoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginResponseService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginResponseServiceImpl implements LoginResponseService {

    private final LoginService loginService;

    private final ServiceExceptionMapper sem;

    public LoginResponseServiceImpl(
            @Qualifier("acckeeperLoginService") LoginService loginService,
            ServiceExceptionMapper sem
    ) {
        this.loginService = loginService;
        this.sem = sem;
    }

    @Override
    public boolean isLogin(LongIdKey longIdKey) throws ServiceException {
        try {
            return loginService.isLogin(longIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断指定登录主键是否登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LoginResponse inspectLoginState(LongIdKey longIdKey) throws ServiceException {
        try {
            LoginState loginState = loginService.getLoginState(longIdKey);
            return new LoginResponse(
                    loginState.getAccountKey().getStringId(),
                    loginState.getKey().getLongId(),
                    new Date(loginState.getExpireDate())
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取个人登录信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LoginResponse login(LoginInfo loginInfo) throws ServiceException {
        try {
            LoginState loginState = loginService.login(loginInfo);
            return new LoginResponse(
                    loginState.getAccountKey().getStringId(),
                    loginState.getKey().getLongId(),
                    new Date(loginState.getExpireDate())
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void logout(LongIdKey longIdKey) throws ServiceException {
        try {
            loginService.logout(longIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("延长指定主键的过期时间时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LoginResponse postpone(LongIdKey longIdKey) throws ServiceException {
        try {
            LoginState loginState = loginService.postpone(longIdKey);
            return new LoginResponse(
                    loginState.getAccountKey().getStringId(),
                    loginState.getKey().getLongId(),
                    new Date(loginState.getExpireDate())
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("延长指定主键的过期时间时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
