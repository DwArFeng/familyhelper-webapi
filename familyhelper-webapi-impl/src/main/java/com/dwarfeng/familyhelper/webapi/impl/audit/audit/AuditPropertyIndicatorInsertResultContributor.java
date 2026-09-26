package com.dwarfeng.familyhelper.webapi.impl.audit.audit;

import com.dwarfeng.audit.sdk.bean.key.FastJsonAuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入审计属性指示器返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在插入成功时从 {@link FastJsonAuditPropertyIndicatorKey} 中提取审计类别标识与属性标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("audit.auditPropertyIndicatorInsertResultContributor")
public class AuditPropertyIndicatorInsertResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<FastJsonAuditPropertyIndicatorKey>> CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<FastJsonAuditPropertyIndicatorKey>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "audit_category_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getAuditCategoryStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "property_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getPropertyStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<FastJsonAuditPropertyIndicatorKey> contributor;

    public AuditPropertyIndicatorInsertResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
