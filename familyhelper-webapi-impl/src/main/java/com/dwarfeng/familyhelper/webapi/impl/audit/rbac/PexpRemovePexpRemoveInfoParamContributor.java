package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPexpRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPexpKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 移除权限表达式 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPexpRemoveInfo} 中提取待移除权限表达式标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.pexpRemovePexpRemoveInfoParamContributor")
public class PexpRemovePexpRemoveInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPexpRemoveInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPexpRemoveInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.scope_string_id", data -> {
                    WebInputPexpKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getScopeStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.role_string_id", data -> {
                    WebInputPexpKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getRoleStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.pexp_string_id", data -> {
                    WebInputPexpKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getPexpStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPexpRemoveInfo> contributor;

    public PexpRemovePexpRemoveInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
