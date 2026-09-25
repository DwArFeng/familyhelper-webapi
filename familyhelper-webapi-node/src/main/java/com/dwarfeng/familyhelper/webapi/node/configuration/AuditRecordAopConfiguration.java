package com.dwarfeng.familyhelper.webapi.node.configuration;

import com.dwarfeng.audit.api.integration.subgrade.AuditRecordHandlerImpl;
import com.dwarfeng.audit.stack.service.AuditRecordService;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AnnotationAuditRecordAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordAdvisor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.stack.handler.AuditRecordHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 审计记录 AOP 配置。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Configuration
public class AuditRecordAopConfiguration {

    private final AuditRecordService auditRecordService;

    public AuditRecordAopConfiguration(
            @Qualifier("auditAuditRecordService") AuditRecordService auditRecordService
    ) {
        this.auditRecordService = auditRecordService;
    }

    @Bean
    public AuditRecordAdvisor auditRecordAdvisor() {
        return new AuditRecordAdvisor();
    }

    @Bean
    public AuditRecordHandler auditRecordHandler() {
        return new AuditRecordHandlerImpl(auditRecordService);
    }

    @Bean
    public AuditRecordAopManager auditRecordAopManager(Map<String, Contributor> contributors) {
        AnnotationAuditRecordAopManager manager = new AnnotationAuditRecordAopManager();
        manager.setContributors(contributors);
        return manager;
    }
}
