package com.dwarfeng.familyhelper.webapi.impl.handler.system;

import com.dwarfeng.acckeeper.stack.bean.dto.AuthInspectInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.AuthInspectResult;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.AccessService;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Service
public class TokenHandlerImpl implements TokenHandler {

    private final AccessService accessService;

    @Value("${familyhelper.token_key}")
    private String tokenKey;

    public TokenHandlerImpl(@Qualifier("acckeeperAccessService") AccessService accessService) {
        this.accessService = accessService;
    }

    @Override
    public String getUserId(HttpServletRequest httpServletRequest) throws HandlerException {
        try {
            String loginStateId = parseLoginStateId(httpServletRequest);
            StringIdKey loginStateKey = new StringIdKey(loginStateId);
            AuthInspectResult authInspectResult = accessService.authInspect(new AuthInspectInfo(loginStateKey));
            return Optional.of(authInspectResult)
                    .map(AuthInspectResult::getLoginState)
                    .map(LoginState::getAccountKey)
                    .map(StringIdKey::getStringId)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public String getLoginId(HttpServletRequest httpServletRequest) throws HandlerException {
        try {
            return parseLoginStateId(httpServletRequest);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private String parseLoginStateId(HttpServletRequest httpServletRequest) throws Exception {
        String token = httpServletRequest.getHeader(tokenKey);
        if (Objects.isNull(token)) {
            throw new MissedRequestHeaderException(tokenKey);
        }
        return token;
    }

    @Override
    public String toString() {
        return "TokenHandlerImpl{" +
                "accessService=" + accessService +
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
}
