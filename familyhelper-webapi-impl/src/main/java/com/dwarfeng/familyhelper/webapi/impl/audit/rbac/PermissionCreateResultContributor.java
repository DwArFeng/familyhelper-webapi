package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.dto.FastJsonPermissionCreateResult;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建权限返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在创建成功时从 {@link FastJsonPermissionCreateResult} 中提取权限标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.permissionCreateResultContributor")
public class PermissionCreateResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<FastJsonPermissionCreateResult>>
            CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<FastJsonPermissionCreateResult>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "key.scope_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    FastJsonPermissionKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getScopeStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "key.permission_string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    FastJsonPermissionKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPermissionStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<FastJsonPermissionCreateResult> contributor;

    public PermissionCreateResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
