package com.dwarfeng.familyhelper.webapi.impl.audit.fileio;

import com.dwarfeng.fileio.sdk.bean.dto.WebInputTaskExecuteInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 异步执行导出任务 WebInput 参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputTaskExecuteInfo} 中提取任务标识与消息。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("fileio.exportExecuteTaskAsyncTaskExecuteInfoParamContributor")
public class ExportExecuteTaskAsyncTaskExecuteInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputTaskExecuteInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputTaskExecuteInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "task_key.long_id", data -> {
                    WebInputLongIdKey key = data.getTaskKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getLongId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "message", WebInputTaskExecuteInfo::getMessage
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputTaskExecuteInfo> contributor;

    public ExportExecuteTaskAsyncTaskExecuteInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
