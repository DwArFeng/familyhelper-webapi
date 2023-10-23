package com.dwarfeng.familyhelper.webapi.stack.bean.disp.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityTemplateParticipantKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动模板参与者。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivityTemplateParticipant implements Dto {

    private static final long serialVersionUID = -2897102742789165458L;

    public static DispActivityTemplateParticipant of(
            ActivityTemplateParticipant activityTemplateParticipant, DispActivityTemplate activityTemplate,
            DispAccount userAccount
    ) {
        if (Objects.isNull(activityTemplateParticipant)) {
            return null;
        } else {
            return new DispActivityTemplateParticipant(
                    activityTemplateParticipant.getKey(),
                    activityTemplateParticipant.getRemark(),
                    activityTemplate,
                    userAccount
            );
        }
    }

    private ActivityTemplateParticipantKey key;
    private String remark;
    private DispActivityTemplate activityTemplate;
    private DispAccount account;

    public DispActivityTemplateParticipant() {
    }

    public DispActivityTemplateParticipant(
            ActivityTemplateParticipantKey key, String remark, DispActivityTemplate activityTemplate,
            DispAccount account
    ) {
        this.key = key;
        this.remark = remark;
        this.activityTemplate = activityTemplate;
        this.account = account;
    }

    public ActivityTemplateParticipantKey getKey() {
        return key;
    }

    public void setKey(ActivityTemplateParticipantKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DispActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(DispActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispActivityTemplateParticipant{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                ", activityTemplate=" + activityTemplate +
                ", account=" + account +
                '}';
    }
}
