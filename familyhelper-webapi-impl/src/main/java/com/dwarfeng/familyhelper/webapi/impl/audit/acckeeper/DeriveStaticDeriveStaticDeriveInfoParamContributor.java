package com.dwarfeng.familyhelper.webapi.impl.audit.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticDeriveInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 静态派生 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputStaticDeriveInfo} 中提取登录态标识、过期时间与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("acckeeper.deriveStaticDeriveStaticDeriveInfoParamContributor")
public class DeriveStaticDeriveStaticDeriveInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputStaticDeriveInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputStaticDeriveInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "login_state_key.string_id", deriveInfo -> {
                    WebInputStringIdKey loginStateKey = deriveInfo.getLoginStateKey();
                    if (Objects.isNull(loginStateKey)) {
                        return null;
                    }
                    return loginStateKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "expire_date", WebInputStaticDeriveInfo::getExpireDate
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputStaticDeriveInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputStaticDeriveInfo> contributor;

    public DeriveStaticDeriveStaticDeriveInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
