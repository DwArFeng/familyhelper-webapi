package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheckAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheckAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.http.DefaultBindingCheckAopManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingCheckAopConfiguration {

    @Bean
    public BindingCheckAdvisor bindingCheckAdvisor() {
        return new BindingCheckAdvisor();
    }

    @Bean
    public BindingCheckAopManager bindingCheckAopManager() {
        return new DefaultBindingCheckAopManager();
    }
}
