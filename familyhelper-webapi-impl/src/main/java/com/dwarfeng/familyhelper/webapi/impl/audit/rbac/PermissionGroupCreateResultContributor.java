package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.dto.FastJsonPermissionGroupCreateResult;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionGroupKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建权限组返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在创建成功时从 {@link FastJsonPermissionGroupCreateResult} 中提取权限组标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.permissionGroupCreateResultContributor")
public class PermissionGroupCreateResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<FastJsonPermissionGroupCreateResult>>
            CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<FastJsonPermissionGroupCreateResult>>
                CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "key.scope_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    FastJsonPermissionGroupKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getScopeStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "key.permission_group_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    FastJsonPermissionGroupKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPermissionGroupStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<FastJsonPermissionGroupCreateResult> contributor;

    public PermissionGroupCreateResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
