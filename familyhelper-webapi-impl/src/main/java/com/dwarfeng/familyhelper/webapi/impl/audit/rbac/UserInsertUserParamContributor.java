package com.dwarfeng.familyhelper.webapi.impl.audit.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.WebInputUser;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 插入用户 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputUser} 中提取用户标识与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("rbac.userInsertUserParamContributor")
public class UserInsertUserParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputUser>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputUser>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.string_id", data -> {
                    WebInputStringIdKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getStringId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputUser::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputUser> contributor;

    public UserInsertUserParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
