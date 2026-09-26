package com.dwarfeng.familyhelper.webapi.impl.audit.notify;

import com.dwarfeng.notify.sdk.bean.key.FastJsonMetaIndicatorKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入元数据指示器返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在插入成功时从 {@link FastJsonMetaIndicatorKey} 中提取主题标识与元数据标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("notify.metaIndicatorInsertResultContributor")
public class MetaIndicatorInsertResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<FastJsonMetaIndicatorKey>> CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<FastJsonMetaIndicatorKey>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "topic_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getTopicId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "meta_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getMetaId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<FastJsonMetaIndicatorKey> contributor;

    public MetaIndicatorInsertResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
