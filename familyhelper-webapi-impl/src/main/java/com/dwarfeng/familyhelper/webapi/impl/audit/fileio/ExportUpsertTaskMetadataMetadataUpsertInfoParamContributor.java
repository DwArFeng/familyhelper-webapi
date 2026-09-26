package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.fileio.sdk.bean.dto.WebInputMetadataUpsertInfo;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskItemKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新导出任务元数据 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputMetadataUpsertInfo} 中提取任务项标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.exportUpsertTaskMetadataMetadataUpsertInfoParamContributor")
public class ExportUpsertTaskMetadataMetadataUpsertInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputMetadataUpsertInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputMetadataUpsertInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.task_id", data -> {
                    WebInputTaskItemKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    Long taskId = key.getTaskId();
                    if (Objects.isNull(taskId)) {
                        return null;
                    }
                    return (long) taskId;
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.identifier", data -> {
                    WebInputTaskItemKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getIdentifier();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputMetadataUpsertInfo> contributor;

    public ExportUpsertTaskMetadataMetadataUpsertInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
