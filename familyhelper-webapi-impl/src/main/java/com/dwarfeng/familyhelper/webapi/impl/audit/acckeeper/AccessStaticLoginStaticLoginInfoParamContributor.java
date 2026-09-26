package com.dwarfeng.familyhelper.webapi.impl.audit.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticLoginInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 静态登录 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputStaticLoginInfo} 中提取账户标识、过期时间与备注，不记录密码等敏感字段。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("acckeeper.accessStaticLoginStaticLoginInfoParamContributor")
public class AccessStaticLoginStaticLoginInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputStaticLoginInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputStaticLoginInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
                "expire_date", WebInputStaticLoginInfo::getExpireDate
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputStaticLoginInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputStaticLoginInfo> contributor;

    public AccessStaticLoginStaticLoginInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
