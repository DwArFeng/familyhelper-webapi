package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.acckeeper.api.integration.subgrade.LoginHandlerImpl;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequiredAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequiredAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.login.TokenHandlerLoginRequiredAopManager;
import com.dwarfeng.subgrade.stack.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginAopConfiguration {

    private final TokenHandler tokenHandler;
    private final LoginService loginService;
    private final AccountOperateService accountOperateService;

    public LoginAopConfiguration(
            TokenHandler tokenHandler,
            @Qualifier("acckeeperLoginService") LoginService loginService,
            @Qualifier("acckeeperAccountOperateService") AccountOperateService accountOperateService
    ) {
        this.tokenHandler = tokenHandler;
        this.loginService = loginService;
        this.accountOperateService = accountOperateService;
    }

    @Bean
    public LoginRequiredAdvisor loginRequiredAdvisor() {
        return new LoginRequiredAdvisor();
    }

    @Bean
    public LoginHandler loginHandler() {
        return new LoginHandlerImpl(loginService, accountOperateService);
    }

    @Bean
    public LoginRequiredAopManager loginRequiredAopManager() {
        TokenHandlerLoginRequiredAopManager manager = new TokenHandlerLoginRequiredAopManager();
        manager.setTokenHandler(tokenHandler);
        return manager;
    }
}
