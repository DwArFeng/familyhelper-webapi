package com.dwarfeng.familyhelper.webapi.impl.audit.audit;

import com.dwarfeng.audit.sdk.bean.entity.WebInputInspectionDriverInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入自动审计驱动器信息参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputInspectionDriverInfo} 中提取驱动器标识、所属自动审计标识、启用状态、类型与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("audit.inspectionDriverInfoInsertInspectionDriverInfoParamContributor")
public class InspectionDriverInfoInsertInspectionDriverInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputInspectionDriverInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputInspectionDriverInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
                "inspection_key.long_id", data -> {
                    WebInputLongIdKey key = data.getInspectionKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "enabled", WebInputInspectionDriverInfo::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "type", WebInputInspectionDriverInfo::getType
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputInspectionDriverInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputInspectionDriverInfo> contributor;

    public InspectionDriverInfoInsertInspectionDriverInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
