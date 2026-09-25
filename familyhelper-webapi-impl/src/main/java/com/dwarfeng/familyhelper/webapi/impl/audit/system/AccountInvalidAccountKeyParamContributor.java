package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 禁用账户参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputStringIdKey} 中提取待禁用账户标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.accountInvalidAccountKeyParamContributor")
public class AccountInvalidAccountKeyParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputStringIdKey>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputStringIdKey>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "string_id", WebInputStringIdKey::getStringId
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputStringIdKey> contributor;

    public AccountInvalidAccountKeyParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
