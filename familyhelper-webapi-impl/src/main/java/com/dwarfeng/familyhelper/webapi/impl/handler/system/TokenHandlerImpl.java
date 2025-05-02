package com.dwarfeng.familyhelper.webapi.impl.handler.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class TokenHandlerImpl implements TokenHandler {

    private final LoginService loginService;

    @Value("${familyhelper.token_key}")
    private String tokenKey;

    public TokenHandlerImpl(@Qualifier("acckeeperLoginService") LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Long getTokenId(HttpServletRequest httpServletRequest) throws HandlerException {
        try {
            return parseLoginStateId(httpServletRequest);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public LongIdKey getLoginKey(HttpServletRequest httpServletRequest) throws HandlerException {
        try {
            return new LongIdKey(parseLoginStateId(httpServletRequest));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public StringIdKey getUserKey(HttpServletRequest httpServletRequest) throws HandlerException {
        try {
            long loginStateId = parseLoginStateId(httpServletRequest);
            LoginState loginState = loginService.getLoginState(new LongIdKey(loginStateId));
            return loginState.getAccountKey();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private long parseLoginStateId(HttpServletRequest httpServletRequest) throws Exception {
        String token = httpServletRequest.getHeader(tokenKey);
        if (Objects.isNull(token)) {
            throw new MissedRequestHeaderException(tokenKey);
        }
        try {
            return Long.parseLong(token);
        } catch (NumberFormatException e) {
            throw new MalformedRequestHeaderException(tokenKey, token);
        }
    }

    @Override
    public String toString() {
        return "TokenHandlerImpl{" +
                "loginService=" + loginService +
                ", tokenKey='" + tokenKey + '\'' +
                '}';
    }

    /**
     * 请求头缺失异常。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    private static class MissedRequestHeaderException extends Exception {

        private static final long serialVersionUID = 3551210579749308381L;

        private final String missingRequestHeaderName;

        public MissedRequestHeaderException(String missingRequestHeaderName) {
            this.missingRequestHeaderName = missingRequestHeaderName;
        }

        @Override
        public String getMessage() {
            return "缺失的请求头, Header Name: " + missingRequestHeaderName;
        }
    }

    /**
     * 请求头畸形异常。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    private static class MalformedRequestHeaderException extends Exception {

        private static final long serialVersionUID = -4166846932980958037L;

        private final String requestHeaderName;
        private final String requestHeaderValue;

        public MalformedRequestHeaderException(String requestHeaderName, String requestHeaderValue) {
            this.requestHeaderName = requestHeaderName;
            this.requestHeaderValue = requestHeaderValue;
        }

        @Override
        public String getMessage() {
            return "畸形的请求头, Header Name: " + requestHeaderName + ", Header Value: " + requestHeaderValue;
        }
    }
}
