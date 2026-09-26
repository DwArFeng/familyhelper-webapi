package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.key.FastJsonRoleUserRelationKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入角色用户关系返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在插入成功时从 {@link FastJsonRoleUserRelationKey} 中提取角色标识与用户标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.roleUserRelationInsertResultContributor")
public class RoleUserRelationInsertResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<FastJsonRoleUserRelationKey>>
            CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<FastJsonRoleUserRelationKey>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "role_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getRoleStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "user_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getUserStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<FastJsonRoleUserRelationKey> contributor;

    public RoleUserRelationInsertResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
