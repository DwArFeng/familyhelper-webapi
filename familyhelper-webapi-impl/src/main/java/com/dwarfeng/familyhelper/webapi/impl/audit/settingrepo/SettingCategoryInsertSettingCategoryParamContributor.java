package com.dwarfeng.familyhelper.webapi.impl.audit.settingrepo;

import com.dwarfeng.settingrepo.sdk.bean.entity.WebInputSettingCategory;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入设置类别 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputSettingCategory} 中提取类别标识、格式化器类型、格式化器参数与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("settingrepo.settingCategoryInsertSettingCategoryParamContributor")
public class SettingCategoryInsertSettingCategoryParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputSettingCategory>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputSettingCategory>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
                "formatter_type", WebInputSettingCategory::getFormatterType
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "formatter_param", WebInputSettingCategory::getFormatterParam
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputSettingCategory::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputSettingCategory> contributor;

    public SettingCategoryInsertSettingCategoryParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
