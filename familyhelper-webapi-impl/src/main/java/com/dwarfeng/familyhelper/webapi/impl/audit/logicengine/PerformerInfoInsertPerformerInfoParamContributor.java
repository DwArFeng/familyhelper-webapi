package com.dwarfeng.familyhelper.webapi.impl.audit.logicengine;

import com.dwarfeng.logicengine.sdk.bean.entity.WebInputPerformerInfo;
import com.dwarfeng.logicengine.sdk.bean.key.WebInputStateKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入执行器信息参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPerformerInfo} 中提取执行器标识、所属部件标识、锚定状态键、目标状态键、序号、启用状态、类型与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("logicengine.performerInfoInsertPerformerInfoParamContributor")
public class PerformerInfoInsertPerformerInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPerformerInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPerformerInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.long_id", data -> {
                    WebInputLongIdKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "section_key.long_id", data -> {
                    WebInputLongIdKey key = data.getSectionKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "anchor_state_key.section_long_id", data -> {
                    WebInputStateKey key = data.getAnchorStateKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getSectionLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "anchor_state_key.state_id", data -> {
                    WebInputStateKey key = data.getAnchorStateKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getStateId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "target_state_key.section_long_id", data -> {
                    WebInputStateKey key = data.getTargetStateKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getSectionLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "target_state_key.state_id", data -> {
                    WebInputStateKey key = data.getTargetStateKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getStateId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "index", data -> (long) data.getIndex()
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "enabled", WebInputPerformerInfo::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "type", WebInputPerformerInfo::getType
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputPerformerInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPerformerInfo> contributor;

    public PerformerInfoInsertPerformerInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
