package com.dwarfeng.familyhelper.webapi.impl.audit.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicDeriveInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 动态派生 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputDynamicDeriveInfo} 中提取登录态标识与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("acckeeper.deriveDynamicDeriveDynamicDeriveInfoParamContributor")
public class DeriveDynamicDeriveDynamicDeriveInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputDynamicDeriveInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputDynamicDeriveInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
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
                "remark", WebInputDynamicDeriveInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputDynamicDeriveInfo> contributor;

    public DeriveDynamicDeriveDynamicDeriveInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
