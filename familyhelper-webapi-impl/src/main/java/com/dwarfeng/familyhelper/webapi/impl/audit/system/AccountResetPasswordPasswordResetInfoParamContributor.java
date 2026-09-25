package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputPasswordResetInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 重置账户密码参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPasswordResetInfo} 中提取目标账户标识，不记录新密码。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.accountResetPasswordPasswordResetInfoParamContributor")
public class AccountResetPasswordPasswordResetInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPasswordResetInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPasswordResetInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "account_key.string_id", passwordResetInfo -> {
                    WebInputStringIdKey accountKey = passwordResetInfo.getAccountKey();
                    if (Objects.isNull(accountKey)) {
                        return null;
                    }
                    return accountKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPasswordResetInfo> contributor;

    public AccountResetPasswordPasswordResetInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
