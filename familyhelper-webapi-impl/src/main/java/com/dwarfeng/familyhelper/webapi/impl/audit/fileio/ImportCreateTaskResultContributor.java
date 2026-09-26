package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建导入任务返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在创建成功时从 {@link JSFixedFastJsonLongIdKey} 中提取新建标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.importCreateTaskResultContributor")
public class ImportCreateTaskResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<JSFixedFastJsonLongIdKey>> CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<JSFixedFastJsonLongIdKey>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "long_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getLongId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<JSFixedFastJsonLongIdKey> contributor;

    public ImportCreateTaskResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
