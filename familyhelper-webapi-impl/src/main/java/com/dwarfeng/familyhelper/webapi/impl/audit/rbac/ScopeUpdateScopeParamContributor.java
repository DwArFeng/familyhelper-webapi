package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.WebInputScope;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新作用域 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputScope} 中提取作用域标识、名称与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.scopeUpdateScopeParamContributor")
public class ScopeUpdateScopeParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputScope>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputScope>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
                "name", WebInputScope::getName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputScope::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputScope> contributor;

    public ScopeUpdateScopeParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
