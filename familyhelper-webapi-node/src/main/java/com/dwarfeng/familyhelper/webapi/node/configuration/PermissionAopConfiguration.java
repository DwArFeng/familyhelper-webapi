package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.rbacds.api.integration.subgrade.PermissionHandlerImpl;
import com.dwarfeng.rbacds.stack.service.PermissionLookupService;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequiredAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequiredAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.permission.TokenHandlerPermissionRequiredAopManager;
import com.dwarfeng.subgrade.stack.handler.PermissionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionAopConfiguration {

    private final TokenHandler tokenHandler;
    private final PermissionLookupService permissionLookupService;

    public PermissionAopConfiguration(
            TokenHandler tokenHandler,
            @Qualifier("rbacPermissionLookupService") PermissionLookupService permissionLookupService
    ) {
        this.tokenHandler = tokenHandler;
        this.permissionLookupService = permissionLookupService;
    }

    @Bean
    public PermissionRequiredAdvisor permissionRequiredAdvisor() {
        return new PermissionRequiredAdvisor();
    }

    @Bean
    public PermissionHandler permissionHandler() {
        return new PermissionHandlerImpl(permissionLookupService);
    }

    @Bean
    public PermissionRequiredAopManager permissionRequiredAopManager() {
        TokenHandlerPermissionRequiredAopManager manager = new TokenHandlerPermissionRequiredAopManager();
        manager.setTokenHandler(tokenHandler);
        return manager;
    }
}
