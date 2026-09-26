package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskSettingItemKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入读取器信息返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在插入成功时从 {@link JSFixedFastJsonTaskSettingItemKey} 中提取新建标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.readerInfoInsertResultContributor")
public class ReaderInfoInsertResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<JSFixedFastJsonTaskSettingItemKey>>
            CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<JSFixedFastJsonTaskSettingItemKey>>
                CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "task_setting_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    Long taskSettingId = data.getTaskSettingId();
                    if (Objects.isNull(taskSettingId)) {
                        return null;
                    }
                    return (long) taskSettingId;
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "identifier", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getIdentifier();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<JSFixedFastJsonTaskSettingItemKey> contributor;

    public ReaderInfoInsertResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
