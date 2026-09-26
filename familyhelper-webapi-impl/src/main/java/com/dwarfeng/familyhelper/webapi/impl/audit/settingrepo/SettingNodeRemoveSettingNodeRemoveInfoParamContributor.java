package com.dwarfeng.familyhelper.webapi.impl.audit.settingrepo;

import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputSettingNodeRemoveInfo;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 移除设置节点 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputSettingNodeRemoveInfo} 中提取配置类别与格式化参数。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("settingrepo.settingNodeRemoveSettingNodeRemoveInfoParamContributor")
public class SettingNodeRemoveSettingNodeRemoveInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputSettingNodeRemoveInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputSettingNodeRemoveInfo>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "category", WebInputSettingNodeRemoveInfo::getCategory
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "args", data -> {
                    String[] args = data.getArgs();
                    if (Objects.isNull(args)) {
                        return null;
                    }
                    return Arrays.toString(args);
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputSettingNodeRemoveInfo> contributor;

    public SettingNodeRemoveSettingNodeRemoveInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
