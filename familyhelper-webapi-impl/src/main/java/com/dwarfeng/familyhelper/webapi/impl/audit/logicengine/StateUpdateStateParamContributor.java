package com.dwarfeng.familyhelper.webapi.impl.audit.logicengine;

import com.dwarfeng.logicengine.sdk.bean.entity.WebInputState;
import com.dwarfeng.logicengine.sdk.bean.key.WebInputStateKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新状态参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputState} 中提取状态键、名称、类型、首次自旋延迟、自旋间隔与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("logicengine.stateUpdateStateParamContributor")
public class StateUpdateStateParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputState>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputState>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.section_long_id", data -> {
                    WebInputStateKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getSectionLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.state_id", data -> {
                    WebInputStateKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getStateId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "name", WebInputState::getName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "type", data -> (long) data.getType()
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "first_spin_delay", WebInputState::getFirstSpinDelay
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "spin_interval", WebInputState::getSpinInterval
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputState::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputState> contributor;

    public StateUpdateStateParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
