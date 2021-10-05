package com.dwarfeng.familyhelper.webapi.impl.handler.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class TokenHandlerImpl implements TokenHandler {

    private final LoginService loginService;

    @Value("${familyhelper.token_key}")
    private String tokenKey;

    public TokenHandlerImpl(@Qualifier("acckeeperLoginService") LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public long getTokenId(HttpServletRequest httpServletRequest) throws HandlerException {
        try {
            return Long.parseLong(httpServletRequest.getHeader(tokenKey));
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public StringIdKey getAccountKey(HttpServletRequest httpServletRequest) throws HandlerException {
        try {
            long tokenId = Long.parseLong(httpServletRequest.getHeader(tokenKey));
            LoginState loginState = loginService.getLoginState(new LongIdKey(tokenId));
            return loginState.getAccountKey();
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
