package com.dwarfeng.familyhelper.webapi.impl.audit.audit;

import com.dwarfeng.audit.sdk.bean.entity.WebInputAuditCategory;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新审计类别参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputAuditCategory} 中提取审计类别标识、启用状态、名称与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("audit.auditCategoryUpdateAuditCategoryParamContributor")
public class AuditCategoryUpdateAuditCategoryParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputAuditCategory>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputAuditCategory>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.string_id", data -> {
                    WebInputStringIdKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "enabled", WebInputAuditCategory::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "name", WebInputAuditCategory::getName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputAuditCategory::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputAuditCategory> contributor;

    public AuditCategoryUpdateAuditCategoryParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
