package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.clannad.sdk.bean.key.FastJsonMessageAuthorizationKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispMessageAuthorization;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示留言授权。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class FastJsonDispMessageAuthorization implements Dto {

    private static final long serialVersionUID = -6249025539934817090L;

    public static FastJsonDispMessageAuthorization of(DispMessageAuthorization dispMessageAuthorization) {
        if (Objects.isNull(dispMessageAuthorization)) {
            return null;
        } else {
            return new FastJsonDispMessageAuthorization(
                    FastJsonMessageAuthorizationKey.of(dispMessageAuthorization.getKey()),
                    dispMessageAuthorization.isEnabled(),
                    dispMessageAuthorization.getRemark(),
                    dispMessageAuthorization.getCreatedDate(),
                    FastJsonDispAccount.of(dispMessageAuthorization.getReceiveAccount()),
                    FastJsonDispAccount.of(dispMessageAuthorization.getAuthorizedSendAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonMessageAuthorizationKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "created_date", ordinal = 4)
    private Date createdDate;

    @JSONField(name = "receive_account", ordinal = 5)
    private FastJsonDispAccount receiveAccount;

    @JSONField(name = "authorized_send_account", ordinal = 6)
    private FastJsonDispAccount authorizedSendAccount;

    public FastJsonDispMessageAuthorization() {
    }

    public FastJsonDispMessageAuthorization(
            FastJsonMessageAuthorizationKey key, boolean enabled, String remark, Date createdDate,
            FastJsonDispAccount receiveAccount, FastJsonDispAccount authorizedSendAccount
    ) {
        this.key = key;
        this.enabled = enabled;
        this.remark = remark;
        this.createdDate = createdDate;
        this.receiveAccount = receiveAccount;
        this.authorizedSendAccount = authorizedSendAccount;
    }

    public FastJsonMessageAuthorizationKey getKey() {
        return key;
    }

    public void setKey(FastJsonMessageAuthorizationKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public FastJsonDispAccount getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(FastJsonDispAccount receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public FastJsonDispAccount getAuthorizedSendAccount() {
        return authorizedSendAccount;
    }

    public void setAuthorizedSendAccount(FastJsonDispAccount authorizedSendAccount) {
        this.authorizedSendAccount = authorizedSendAccount;
    }

    @Override
    public String toString() {
        return "FastJsonDispMessageAuthorization{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", createdDate=" + createdDate +
                ", receiveAccount=" + receiveAccount +
                ", authorizedSendAccount=" + authorizedSendAccount +
                '}';
    }
}
