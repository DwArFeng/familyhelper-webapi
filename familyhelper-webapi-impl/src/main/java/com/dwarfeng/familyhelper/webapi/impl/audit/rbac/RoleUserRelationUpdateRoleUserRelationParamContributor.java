package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.WebInputRoleUserRelation;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputRoleUserRelationKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新角色用户关系 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputRoleUserRelation} 中提取角色标识、用户标识、启用状态与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.roleUserRelationUpdateRoleUserRelationParamContributor")
public class RoleUserRelationUpdateRoleUserRelationParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputRoleUserRelation>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputRoleUserRelation>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.role_string_id", data -> {
                    WebInputRoleUserRelationKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getRoleStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.user_string_id", data -> {
                    WebInputRoleUserRelationKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getUserStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "enabled", WebInputRoleUserRelation::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputRoleUserRelation::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputRoleUserRelation> contributor;

    public RoleUserRelationUpdateRoleUserRelationParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
