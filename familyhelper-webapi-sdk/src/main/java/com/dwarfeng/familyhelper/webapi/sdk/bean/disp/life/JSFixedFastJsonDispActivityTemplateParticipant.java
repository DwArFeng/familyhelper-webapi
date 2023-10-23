package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonActivityTemplateParticipantKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplateParticipant;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动模板参与者。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispActivityTemplateParticipant implements Dto {

    private static final long serialVersionUID = -7163253618515606114L;

    public static JSFixedFastJsonDispActivityTemplateParticipant of(DispActivityTemplateParticipant disp) {
        if (Objects.isNull(disp)) {
            return null;
        } else {
            return new JSFixedFastJsonDispActivityTemplateParticipant(
                    JSFixedFastJsonActivityTemplateParticipantKey.of(disp.getKey()),
                    disp.getRemark(),
                    JSFixedFastJsonDispActivityTemplate.of(disp.getActivityTemplate()),
                    FastJsonDispAccount.of(disp.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonActivityTemplateParticipantKey key;

    @JSONField(name = "remark", ordinal = 2)
    private String remark;

    @JSONField(name = "activity_template", ordinal = 3)
    private JSFixedFastJsonDispActivityTemplate activityTemplate;

    @JSONField(name = "user_account", ordinal = 4)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispActivityTemplateParticipant() {
    }

    public JSFixedFastJsonDispActivityTemplateParticipant(
            JSFixedFastJsonActivityTemplateParticipantKey key, String remark,
            JSFixedFastJsonDispActivityTemplate activityTemplate, FastJsonDispAccount account
    ) {
        this.key = key;
        this.remark = remark;
        this.activityTemplate = activityTemplate;
        this.account = account;
    }

    public JSFixedFastJsonActivityTemplateParticipantKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonActivityTemplateParticipantKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonDispActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(JSFixedFastJsonDispActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispActivityTemplateParticipant{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                ", activityTemplate=" + activityTemplate +
                ", account=" + account +
                '}';
    }
}
