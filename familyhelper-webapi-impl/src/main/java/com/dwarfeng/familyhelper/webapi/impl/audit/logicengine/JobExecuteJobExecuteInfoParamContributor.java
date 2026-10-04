package com.dwarfeng.familyhelper.webapi.impl.audit.logicengine;

import com.dwarfeng.logicengine.sdk.bean.dto.WebInputJobExecuteInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 执行作业参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputJobExecuteInfo} 中提取任务标识。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("logicengine.jobExecuteJobExecuteInfoParamContributor")
public class JobExecuteJobExecuteInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputJobExecuteInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputJobExecuteInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "task_key.long_id", data -> {
                    WebInputLongIdKey key = data.getTaskKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputJobExecuteInfo> contributor;

    public JobExecuteJobExecuteInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
