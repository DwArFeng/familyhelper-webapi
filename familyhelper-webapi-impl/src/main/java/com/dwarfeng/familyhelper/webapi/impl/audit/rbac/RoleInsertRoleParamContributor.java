package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.WebInputRole;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入角色 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputRole} 中提取角色标识、名称、启用状态与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.roleInsertRoleParamContributor")
public class RoleInsertRoleParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputRole>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputRole>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
                "name", WebInputRole::getName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "enabled", WebInputRole::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputRole::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputRole> contributor;

    public RoleInsertRoleParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
