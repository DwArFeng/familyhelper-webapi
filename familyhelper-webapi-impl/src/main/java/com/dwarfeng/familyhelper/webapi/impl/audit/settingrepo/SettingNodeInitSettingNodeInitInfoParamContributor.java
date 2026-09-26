package com.dwarfeng.familyhelper.webapi.impl.audit.settingrepo;

import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputSettingNodeInitInfo;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 初始化设置节点 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputSettingNodeInitInfo} 中提取配置类别、格式化参数、节点类型与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("settingrepo.settingNodeInitSettingNodeInitInfoParamContributor")
public class SettingNodeInitSettingNodeInitInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputSettingNodeInitInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputSettingNodeInitInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "category", WebInputSettingNodeInitInfo::getCategory
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
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "type", data -> (long) data.getType()
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputSettingNodeInitInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputSettingNodeInitInfo> contributor;

    public SettingNodeInitSettingNodeInitInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
