package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.rbacds.api.integration.subgrade.LegacyPermissionHandler;
import com.dwarfeng.rbacds.stack.service.InspectService;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequiredAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequiredAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.permission.TokenHandlerPermissionRequiredAopManager;
import com.dwarfeng.subgrade.stack.handler.PermissionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionAopConfiguration {

    private static final String SCOPE_ID = "legacy";

    private final TokenHandler tokenHandler;
    private final InspectService inspectService;

    public PermissionAopConfiguration(
            TokenHandler tokenHandler,
            @Qualifier("rbacInspectService") InspectService inspectService
    ) {
        this.tokenHandler = tokenHandler;
        this.inspectService = inspectService;
    }

    @Bean
    public PermissionRequiredAdvisor permissionRequiredAdvisor() {
        return new PermissionRequiredAdvisor();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PermissionHandler permissionHandler() {
        return new LegacyPermissionHandler(inspectService, SCOPE_ID);
    }

    @Bean
    public PermissionRequiredAopManager permissionRequiredAopManager() {
        TokenHandlerPermissionRequiredAopManager manager = new TokenHandlerPermissionRequiredAopManager();
        manager.setTokenHandler(tokenHandler);
        return manager;
    }
}
