package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionGroupKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新权限组 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPermissionGroupUpdateInfo} 中提取权限组标识、父组标识、名称与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.permissionGroupUpdatePermissionGroupUpdateInfoParamContributor")
public class PermissionGroupUpdatePermissionGroupUpdateInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPermissionGroupUpdateInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPermissionGroupUpdateInfo>> CONTRIBUTOR_RULES_DEJA_VU =
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
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "parent_key.scope_string_id", data -> {
                    WebInputPermissionGroupKey key = data.getParentKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getScopeStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "parent_key.permission_group_string_id", data -> {
                    WebInputPermissionGroupKey key = data.getParentKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPermissionGroupStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "name", WebInputPermissionGroupUpdateInfo::getName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputPermissionGroupUpdateInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPermissionGroupUpdateInfo> contributor;

    public PermissionGroupUpdatePermissionGroupUpdateInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
