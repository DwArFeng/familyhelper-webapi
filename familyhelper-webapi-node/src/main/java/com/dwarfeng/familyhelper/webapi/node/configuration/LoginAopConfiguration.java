package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequiredAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequiredAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.login.TokenHandlerLoginRequiredAopManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginAopConfiguration {

    private final TokenHandler tokenHandler;

    public LoginAopConfiguration(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Bean
    public LoginRequiredAdvisor loginRequiredAdvisor() {
        return new LoginRequiredAdvisor();
    }

    @Bean
    public LoginRequiredAopManager loginRequiredAopManager() {
        TokenHandlerLoginRequiredAopManager manager = new TokenHandlerLoginRequiredAopManager();
        manager.setTokenHandler(tokenHandler);
        return manager;
    }
}
