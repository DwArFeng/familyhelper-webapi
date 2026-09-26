package com.dwarfeng.familyhelper.webapi.impl.audit.audit;

import com.dwarfeng.audit.sdk.bean.entity.WebInputAuditPropertyIndicator;
import com.dwarfeng.audit.sdk.bean.key.WebInputAuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新审计属性指示器参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputAuditPropertyIndicator} 中提取审计类别标识、属性标识、标签、属性类型与顺序。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("audit.auditPropertyIndicatorUpdateAuditPropertyIndicatorParamContributor")
public class AuditPropertyIndicatorUpdateAuditPropertyIndicatorParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputAuditPropertyIndicator>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputAuditPropertyIndicator>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.audit_category_string_id", data -> {
                    WebInputAuditPropertyIndicatorKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getAuditCategoryStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.property_string_id", data -> {
                    WebInputAuditPropertyIndicatorKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPropertyStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "label", WebInputAuditPropertyIndicator::getLabel
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "property_type", data -> (long) data.getPropertyType()
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "order", data -> (long) data.getOrder()
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputAuditPropertyIndicator> contributor;

    public AuditPropertyIndicatorUpdateAuditPropertyIndicatorParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
