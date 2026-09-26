package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.fileio.sdk.bean.entity.WebInputExportTaskSetting;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入导出任务设置 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputExportTaskSetting} 中提取任务设置标识、启用状态、名称与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.exportTaskSettingInsertExportTaskSettingParamContributor")
public class ExportTaskSettingInsertExportTaskSettingParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputExportTaskSetting>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputExportTaskSetting>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
                "enabled", WebInputExportTaskSetting::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "name", WebInputExportTaskSetting::getName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputExportTaskSetting::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputExportTaskSetting> contributor;

    public ExportTaskSettingInsertExportTaskSettingParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
