package com.dwarfeng.familyhelper.webapi.impl.audit.logicengine;

import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonStateKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入状态返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在插入成功时从 {@link JSFixedFastJsonStateKey} 中提取部件标识与状态标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("logicengine.stateInsertResultContributor")
public class StateInsertResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<JSFixedFastJsonStateKey>>
            CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<JSFixedFastJsonStateKey>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "section_long_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getSectionLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "state_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getStateId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<JSFixedFastJsonStateKey> contributor;

    public StateInsertResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
