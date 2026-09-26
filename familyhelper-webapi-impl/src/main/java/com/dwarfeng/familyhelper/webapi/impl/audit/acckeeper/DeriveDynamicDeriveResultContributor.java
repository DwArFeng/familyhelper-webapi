package com.dwarfeng.familyhelper.webapi.impl.audit.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.FastJsonDynamicDeriveResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 动态派生返回值贡献器。
 *
 * <p>
 * 该贡献器记录 FastJson 响应外壳，并在派生成功时从 {@link FastJsonDynamicDeriveResult} 中提取业务结果字段。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("acckeeper.deriveDynamicDeriveResultContributor")
public class DeriveDynamicDeriveResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<FastJsonDynamicDeriveResult>> CONTRIBUTOR_RULES;

    static {
        List<GeneralFastJsonResponseDataContributor.Rule<FastJsonDynamicDeriveResult>> CONTRIBUTOR_RULES_DEJA_VU =
                new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "login_state_key.string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    FastJsonStringIdKey loginStateKey = data.getLoginStateKey();
                    if (Objects.isNull(loginStateKey)) {
                        return null;
                    }
                    return loginStateKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "account_key.string_id", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    FastJsonStringIdKey accountKey = data.getAccountKey();
                    if (Objects.isNull(accountKey)) {
                        return null;
                    }
                    return accountKey.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "expire_date", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getExpireDate();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "generated_date", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getGeneratedDate();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "type", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return (long) data.getType();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralFastJsonResponseDataContributor.Rule.of(
                "remark", data -> {
                    if (Objects.isNull(data)) {
                        return null;
                    }
                    return data.getRemark();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralFastJsonResponseDataContributor<FastJsonDynamicDeriveResult> contributor;

    public DeriveDynamicDeriveResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
