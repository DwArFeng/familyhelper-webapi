package com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonActivityParticipantKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityParticipant;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动参与者。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispActivityParticipant implements Dto {

    private static final long serialVersionUID = -7035143557897509170L;

    public static JSFixedFastJsonDispActivityParticipant of(DispActivityParticipant disp) {
        if (Objects.isNull(disp)) {
            return null;
        } else {
            return new JSFixedFastJsonDispActivityParticipant(
                    JSFixedFastJsonActivityParticipantKey.of(disp.getKey()),
                    disp.getRemark(),
                    JSFixedFastJsonDispActivity.of(disp.getActivity()),
                    FastJsonDispAccount.of(disp.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonActivityParticipantKey key;

    @JSONField(name = "remark", ordinal = 2)
    private String remark;

    @JSONField(name = "activity_template", ordinal = 3)
    private JSFixedFastJsonDispActivity activity;

    @JSONField(name = "user_account", ordinal = 4)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispActivityParticipant() {
    }

    public JSFixedFastJsonDispActivityParticipant(
            JSFixedFastJsonActivityParticipantKey key, String remark,
            JSFixedFastJsonDispActivity activity, FastJsonDispAccount account
    ) {
        this.key = key;
        this.remark = remark;
        this.activity = activity;
        this.account = account;
    }

    public JSFixedFastJsonActivityParticipantKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonActivityParticipantKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonDispActivity getActivity() {
        return activity;
    }

    public void setActivity(JSFixedFastJsonDispActivity activity) {
        this.activity = activity;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispActivityParticipant{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                ", activity=" + activity +
                ", account=" + account +
                '}';
    }
}
