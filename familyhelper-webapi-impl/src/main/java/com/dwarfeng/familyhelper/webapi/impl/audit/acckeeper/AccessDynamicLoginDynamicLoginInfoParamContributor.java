package com.dwarfeng.familyhelper.webapi.impl.audit.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicLoginInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 动态登录 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputDynamicLoginInfo} 中提取账户标识与备注，不记录密码等敏感字段。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("acckeeper.accessDynamicLoginDynamicLoginInfoParamContributor")
public class AccessDynamicLoginDynamicLoginInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputDynamicLoginInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputDynamicLoginInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "account_key.string_id", loginInfo -> {
                    WebInputStringIdKey accountKey = loginInfo.getAccountKey();
                    if (Objects.isNull(accountKey)) {
                        return null;
                    }
                    return accountKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputDynamicLoginInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputDynamicLoginInfo> contributor;

    public AccessDynamicLoginDynamicLoginInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
