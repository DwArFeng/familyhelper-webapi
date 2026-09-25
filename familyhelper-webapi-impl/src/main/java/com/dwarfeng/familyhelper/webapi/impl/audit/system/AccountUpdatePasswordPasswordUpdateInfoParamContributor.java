package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputPasswordUpdateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新账户密码参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputPasswordUpdateInfo} 中提取目标账户标识，不记录旧密码和新密码。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.accountUpdatePasswordPasswordUpdateInfoParamContributor")
public class AccountUpdatePasswordPasswordUpdateInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputPasswordUpdateInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputPasswordUpdateInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "account_key.string_id", passwordUpdateInfo -> {
                    WebInputStringIdKey accountKey = passwordUpdateInfo.getAccountKey();
                    if (Objects.isNull(accountKey)) {
                        return null;
                    }
                    return accountKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputPasswordUpdateInfo> contributor;

    public AccountUpdatePasswordPasswordUpdateInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
