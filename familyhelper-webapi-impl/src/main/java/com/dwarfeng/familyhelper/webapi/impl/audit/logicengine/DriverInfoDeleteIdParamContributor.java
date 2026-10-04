package com.dwarfeng.familyhelper.webapi.impl.audit.logicengine;

import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 删除驱动器信息路径参数贡献器。
 *
 * <p>
 * 该贡献器从路径变量 {@link Long} 中提取待删除驱动器标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("logicengine.driverInfoDeleteIdParamContributor")
public class DriverInfoDeleteIdParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<Long>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<Long>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "id", id -> id
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<Long> contributor;

    public DriverInfoDeleteIdParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
