package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto.WebInputLoginInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 登录 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputLoginInfo} 中提取登录账号标识，不记录密码等敏感字段。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.loginLoginParamContributor")
public class LoginLoginParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputLoginInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputLoginInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "account_key.string_id", loginInfo -> {
                    WebInputStringIdKey accountKey = loginInfo.getAccountKey();
                    if (Objects.isNull(accountKey)) {
                        return null;
                    }
                    return accountKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputLoginInfo> contributor;

    public LoginLoginParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
