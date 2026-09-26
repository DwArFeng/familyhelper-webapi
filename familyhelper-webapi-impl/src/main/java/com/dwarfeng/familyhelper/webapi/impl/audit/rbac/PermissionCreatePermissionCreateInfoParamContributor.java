package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionCreateInfo;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionGroupKey;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建权限 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPermissionCreateInfo} 中提取权限标识、权限组标识、名称、备注与级别。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.permissionCreatePermissionCreateInfoParamContributor")
public class PermissionCreatePermissionCreateInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPermissionCreateInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPermissionCreateInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "group_key.scope_string_id", data -> {
                    WebInputPermissionGroupKey key = data.getGroupKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getScopeStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "group_key.permission_group_string_id", data -> {
                    WebInputPermissionGroupKey key = data.getGroupKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPermissionGroupStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "name", WebInputPermissionCreateInfo::getName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputPermissionCreateInfo::getRemark
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "level", data -> (long) data.getLevel()
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPermissionCreateInfo> contributor;

    public PermissionCreatePermissionCreateInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
