package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputAccountRegisterInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 注册账户参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputAccountRegisterInfo} 中提取非敏感字段，不记录密码。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.accountRegisterAccountRegisterInfoParamContributor")
public class AccountRegisterAccountRegisterInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputAccountRegisterInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputAccountRegisterInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "account_key.string_id", data -> {
                    WebInputStringIdKey accountKey = data.getAccountKey();
                    if (Objects.isNull(accountKey)) {
                        return null;
                    }
                    return accountKey.getStringId();
                })
        );
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "display_name", WebInputAccountRegisterInfo::getDisplayName
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "enabled", WebInputAccountRegisterInfo::isEnabled
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputAccountRegisterInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputAccountRegisterInfo> contributor;

    public AccountRegisterAccountRegisterInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
