package com.dwarfeng.familyhelper.webapi.impl.audit.logicengine;

import com.dwarfeng.logicengine.sdk.bean.dto.WebInputManualDispatchInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 手动调度参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputManualDispatchInfo} 中提取部件标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("logicengine.manualDispatchDispatchManualDispatchInfoParamContributor")
public class ManualDispatchDispatchManualDispatchInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputManualDispatchInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputManualDispatchInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "section_key.long_id", data -> {
                    WebInputLongIdKey key = data.getSectionKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputManualDispatchInfo> contributor;

    public ManualDispatchDispatchManualDispatchInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
