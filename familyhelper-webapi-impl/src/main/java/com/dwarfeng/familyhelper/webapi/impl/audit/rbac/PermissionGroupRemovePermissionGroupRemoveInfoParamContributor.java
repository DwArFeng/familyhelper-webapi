package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionGroupKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 移除权限组 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPermissionGroupRemoveInfo} 中提取待移除权限组标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.permissionGroupRemovePermissionGroupRemoveInfoParamContributor")
public class PermissionGroupRemovePermissionGroupRemoveInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPermissionGroupRemoveInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPermissionGroupRemoveInfo>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.scope_string_id", data -> {
                    WebInputPermissionGroupKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getScopeStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.permission_group_string_id", data -> {
                    WebInputPermissionGroupKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPermissionGroupStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPermissionGroupRemoveInfo> contributor;

    public PermissionGroupRemovePermissionGroupRemoveInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
