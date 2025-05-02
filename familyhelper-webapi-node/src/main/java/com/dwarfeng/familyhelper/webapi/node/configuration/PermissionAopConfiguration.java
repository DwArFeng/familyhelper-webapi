package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequiredAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequiredAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.permission.TokenHandlerPermissionRequiredAopManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionAopConfiguration {

    private final TokenHandler tokenHandler;

    public PermissionAopConfiguration(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Bean
    public PermissionRequiredAdvisor permissionRequiredAdvisor() {
        return new PermissionRequiredAdvisor();
    }

    @Bean
    public PermissionRequiredAopManager permissionRequiredAopManager() {
        TokenHandlerPermissionRequiredAopManager manager = new TokenHandlerPermissionRequiredAopManager();
        manager.setTokenHandler(tokenHandler);
        return manager;
    }
}
