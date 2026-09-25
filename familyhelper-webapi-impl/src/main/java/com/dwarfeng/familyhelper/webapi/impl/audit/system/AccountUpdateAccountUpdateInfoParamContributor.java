package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputAccountUpdateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新账户参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputAccountUpdateInfo} 中提取账户更新字段。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.accountUpdateAccountUpdateInfoParamContributor")
public class AccountUpdateAccountUpdateInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputAccountUpdateInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputAccountUpdateInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "account_key.string_id", data -> {
                    WebInputStringIdKey accountKey = data.getAccountKey();
                    if (Objects.isNull(accountKey)) {
                        return null;
                    }
                    return accountKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "display_name", WebInputAccountUpdateInfo::getDisplayName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "enabled", WebInputAccountUpdateInfo::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputAccountUpdateInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputAccountUpdateInfo> contributor;

    public AccountUpdateAccountUpdateInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
