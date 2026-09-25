package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralMethodContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 方法贡献器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.methodContributor")
public class MethodContributor implements Contributor {

    private static final List<GeneralMethodContributor.Rule> CONTRIBUTOR_RULES;

    static {
        List<GeneralMethodContributor.Rule> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralMethodContributor.Rule.methodName("method"));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralMethodContributor contributor;

    public MethodContributor() {
        this.contributor = new GeneralMethodContributor(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
