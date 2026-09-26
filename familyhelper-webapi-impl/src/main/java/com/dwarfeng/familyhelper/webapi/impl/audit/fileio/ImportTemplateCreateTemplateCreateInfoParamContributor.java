package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.fileio.sdk.bean.dto.WebInputTemplateCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建导入模板 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputTemplateCreateInfo} 中提取任务设置标识、模板标识与原始名称。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.importTemplateCreateTemplateCreateInfoParamContributor")
public class ImportTemplateCreateTemplateCreateInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputTemplateCreateInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputTemplateCreateInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "task_setting_key.long_id", data -> {
                    WebInputLongIdKey key = data.getTaskSettingKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "identifier", WebInputTemplateCreateInfo::getIdentifier
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "origin_name", WebInputTemplateCreateInfo::getOriginName
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputTemplateCreateInfo> contributor;

    public ImportTemplateCreateTemplateCreateInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
