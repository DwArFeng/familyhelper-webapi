package com.dwarfeng.familyhelper.webapi.impl.audit.audit;

import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralFastJsonResponseDataContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 删除自动审计驱动器信息返回值贡献器。
 *
 * <p>
 * 该贡献器仅记录 FastJson 响应外壳，不解析业务数据。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("audit.inspectionDriverInfoDeleteResultContributor")
public class InspectionDriverInfoDeleteResultContributor implements Contributor {

    private static final List<GeneralFastJsonResponseDataContributor.Rule<Object>> CONTRIBUTOR_RULES;

    static {
        CONTRIBUTOR_RULES = Collections.emptyList();
    }

    private final GeneralFastJsonResponseDataContributor<Object> contributor;

    public InspectionDriverInfoDeleteResultContributor() {
        this.contributor = new GeneralFastJsonResponseDataContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
