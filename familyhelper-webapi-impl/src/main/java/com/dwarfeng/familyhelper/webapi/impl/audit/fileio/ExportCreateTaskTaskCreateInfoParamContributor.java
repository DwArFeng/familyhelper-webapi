package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.fileio.sdk.bean.dto.WebInputTaskCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建导出任务 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputTaskCreateInfo} 中提取任务设置标识、用户标识与消息。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.exportCreateTaskTaskCreateInfoParamContributor")
public class ExportCreateTaskTaskCreateInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputTaskCreateInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputTaskCreateInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "setting_key.long_id", data -> {
                    WebInputLongIdKey key = data.getSettingKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "user_key.string_id", data -> {
                    WebInputStringIdKey key = data.getUserKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "message", WebInputTaskCreateInfo::getMessage
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputTaskCreateInfo> contributor;

    public ExportCreateTaskTaskCreateInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
