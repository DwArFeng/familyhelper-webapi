package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 移除权限 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPermissionRemoveInfo} 中提取待移除权限标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.permissionRemovePermissionRemoveInfoParamContributor")
public class PermissionRemovePermissionRemoveInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPermissionRemoveInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPermissionRemoveInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.scope_string_id", data -> {
                    WebInputPermissionKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getScopeStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.permission_string_id", data -> {
                    WebInputPermissionKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPermissionStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPermissionRemoveInfo> contributor;

    public PermissionRemovePermissionRemoveInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
