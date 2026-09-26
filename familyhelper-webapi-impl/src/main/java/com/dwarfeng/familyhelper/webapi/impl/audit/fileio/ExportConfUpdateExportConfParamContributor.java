package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.fileio.sdk.bean.entity.WebInputExportConf;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskSettingItemKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新导出配置 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputExportConf} 中提取任务设置项标识与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.exportConfUpdateExportConfParamContributor")
public class ExportConfUpdateExportConfParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputExportConf>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputExportConf>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.task_setting_id", data -> {
                    WebInputTaskSettingItemKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    Long taskSettingId = key.getTaskSettingId();
                    if (Objects.isNull(taskSettingId)) {
                        return null;
                    }
                    return (long) taskSettingId;
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.identifier", data -> {
                    WebInputTaskSettingItemKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getIdentifier();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputExportConf::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputExportConf> contributor;

    public ExportConfUpdateExportConfParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
