package com.dwarfeng.familyhelper.webapi.impl.audit.notify;

import com.dwarfeng.notify.sdk.bean.entity.WebInputSenderInfo;
import com.dwarfeng.notify.sdk.bean.key.WebInputSenderInfoKey;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralSourceContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新发送器信息参数贡献器。
 *
 * <p>
 * 该贡献器从 {@link WebInputSenderInfo} 中提取发送器标识、标签、类型、参数与备注。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("notify.senderInfoUpdateSenderInfoParamContributor")
public class SenderInfoUpdateSenderInfoParamContributor implements Contributor {

    private static final List<GeneralSourceContributor.Rule<WebInputSenderInfo>> CONTRIBUTOR_RULES;

    static {
        List<GeneralSourceContributor.Rule<WebInputSenderInfo>> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.notify_setting_id", data -> {
                    WebInputSenderInfoKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getNotifySettingId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "key.topic_id", data -> {
                    WebInputSenderInfoKey key = data.getKey();
                    if (Objects.isNull(key)) {
                        return null;
                    }
                    return key.getTopicId();
                }
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "label", WebInputSenderInfo::getLabel
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "type", WebInputSenderInfo::getType
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "param", WebInputSenderInfo::getParam
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralSourceContributor.Rule.of(
                "remark", WebInputSenderInfo::getRemark
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralSourceContributor<WebInputSenderInfo> contributor;

    public SenderInfoUpdateSenderInfoParamContributor() {
        this.contributor = new GeneralSourceContributor<>(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
