package com.dwarfeng.familyhelper.webapi.impl.audit.notify;

import com.dwarfeng.notify.sdk.bean.entity.WebInputMetaIndicator;
import com.dwarfeng.notify.sdk.bean.key.WebInputMetaIndicatorKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新元数据指示器参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputMetaIndicator} 中提取主题标识、元数据标识、标签、备注与默认值。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("notify.metaIndicatorUpdateMetaIndicatorParamContributor")
public class MetaIndicatorUpdateMetaIndicatorParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputMetaIndicator>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputMetaIndicator>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.topic_id", data -> {
                    WebInputMetaIndicatorKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getTopicId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.meta_id", data -> {
                    WebInputMetaIndicatorKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getMetaId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "label", WebInputMetaIndicator::getLabel
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputMetaIndicator::getRemark
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "default_value", WebInputMetaIndicator::getDefaultValue
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputMetaIndicator> contributor;

    public MetaIndicatorUpdateMetaIndicatorParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
