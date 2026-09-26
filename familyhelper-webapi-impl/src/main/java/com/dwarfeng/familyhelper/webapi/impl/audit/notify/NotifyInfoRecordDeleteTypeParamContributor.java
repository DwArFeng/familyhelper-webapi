package com.dwarfeng.familyhelper.webapi.impl.audit.notify;

import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 删除通知信息记录类型路径参数贡献器。
 *
 * <p>
 * 该贡献器从路径变量 {@link Integer} 中提取待删除通知信息记录的类型，并将其转为 long。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("notify.notifyInfoRecordDeleteTypeParamContributor")
public class NotifyInfoRecordDeleteTypeParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<Integer>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<Integer>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "type", type -> (long) type
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<Integer> contributor;

    public NotifyInfoRecordDeleteTypeParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
