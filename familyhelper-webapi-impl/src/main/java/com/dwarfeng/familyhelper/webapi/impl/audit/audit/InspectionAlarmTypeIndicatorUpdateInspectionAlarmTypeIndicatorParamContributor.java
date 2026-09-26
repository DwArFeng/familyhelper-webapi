package com.dwarfeng.familyhelper.webapi.impl.audit.audit;

import com.dwarfeng.audit.sdk.bean.entity.WebInputInspectionAlarmTypeIndicator;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新自动审计报警类型指示器参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputInspectionAlarmTypeIndicator} 中提取报警类型标识、标签与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("audit.inspectionAlarmTypeIndicatorUpdateInspectionAlarmTypeIndicatorParamContributor")
public class InspectionAlarmTypeIndicatorUpdateInspectionAlarmTypeIndicatorParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputInspectionAlarmTypeIndicator>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputInspectionAlarmTypeIndicator>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.string_id", data -> {
                    WebInputStringIdKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "label", WebInputInspectionAlarmTypeIndicator::getLabel
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputInspectionAlarmTypeIndicator::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputInspectionAlarmTypeIndicator> contributor;

    public InspectionAlarmTypeIndicatorUpdateInspectionAlarmTypeIndicatorParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
