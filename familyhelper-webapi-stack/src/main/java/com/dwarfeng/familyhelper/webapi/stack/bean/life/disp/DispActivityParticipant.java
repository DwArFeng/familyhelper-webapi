package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityParticipantKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动参与者。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivityParticipant implements Dto {

    private static final long serialVersionUID = 7761211272502961900L;

    public static DispActivityParticipant of(
            ActivityParticipant activityParticipant, DispActivity activity, DispAccount userAccount
    ) {
        if (Objects.isNull(activityParticipant)) {
            return null;
        } else {
            return new DispActivityParticipant(
                    activityParticipant.getKey(),
                    activityParticipant.getRemark(),
                    activity,
                    userAccount
            );
        }
    }

    private ActivityParticipantKey key;
    private String remark;
    private DispActivity activity;
    private DispAccount account;

    public DispActivityParticipant() {
    }

    public DispActivityParticipant(
            ActivityParticipantKey key, String remark, DispActivity activity, DispAccount account
    ) {
        this.key = key;
        this.remark = remark;
        this.activity = activity;
        this.account = account;
    }

    public ActivityParticipantKey getKey() {
        return key;
    }

    public void setKey(ActivityParticipantKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DispActivity getActivity() {
        return activity;
    }

    public void setActivity(DispActivity activity) {
        this.activity = activity;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispActivityParticipant{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                ", activity=" + activity +
                ", account=" + account +
                '}';
    }
}
