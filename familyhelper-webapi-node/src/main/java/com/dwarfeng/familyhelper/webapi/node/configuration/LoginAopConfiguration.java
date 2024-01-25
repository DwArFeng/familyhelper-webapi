package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.subgrade.sdk.interceptor.login.HttpLoginRequiredAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequiredAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequiredAopManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginAopConfiguration {

    @Value("${familyhelper.token_key}")
    private String tokenKey;

    @Bean
    public LoginRequiredAdvisor loginRequiredAdvisor() {
        return new LoginRequiredAdvisor();
    }

    @Bean
    public LoginRequiredAopManager loginRequiredAopManager() {
        HttpLoginRequiredAopManager httpLoginRequiredAopManager = new HttpLoginRequiredAopManager();
        httpLoginRequiredAopManager.setTokenKey(tokenKey);
        return httpLoginRequiredAopManager;
    }
}
