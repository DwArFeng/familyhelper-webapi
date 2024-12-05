package com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageAuthorization;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.MessageAuthorizationKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示留言授权。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class DispMessageAuthorization implements Dto {

    private static final long serialVersionUID = 7330930225906885851L;

    public static DispMessageAuthorization of(
            MessageAuthorization messageAuthorization, DispAccount receiveAccount, DispAccount authorizedSendAccount
    ) {
        if (Objects.isNull(messageAuthorization)) {
            return null;
        } else {
            return new DispMessageAuthorization(
                    messageAuthorization.getKey(),
                    messageAuthorization.isEnabled(),
                    messageAuthorization.getRemark(),
                    messageAuthorization.getCreatedDate(),
                    receiveAccount,
                    authorizedSendAccount
            );
        }
    }

    private MessageAuthorizationKey key;
    private boolean enabled;
    private String remark;
    private Date createdDate;
    private DispAccount receiveAccount;
    private DispAccount authorizedSendAccount;

    public DispMessageAuthorization() {
    }

    public DispMessageAuthorization(
            MessageAuthorizationKey key, boolean enabled, String remark, Date createdDate, DispAccount receiveAccount,
            DispAccount authorizedSendAccount
    ) {
        this.key = key;
        this.enabled = enabled;
        this.remark = remark;
        this.createdDate = createdDate;
        this.receiveAccount = receiveAccount;
        this.authorizedSendAccount = authorizedSendAccount;
    }

    public MessageAuthorizationKey getKey() {
        return key;
    }

    public void setKey(MessageAuthorizationKey key) {
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

    public DispAccount getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(DispAccount receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public DispAccount getAuthorizedSendAccount() {
        return authorizedSendAccount;
    }

    public void setAuthorizedSendAccount(DispAccount authorizedSendAccount) {
        this.authorizedSendAccount = authorizedSendAccount;
    }

    @Override
    public String toString() {
        return "DispMessageAuthorization{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", createdDate=" + createdDate +
                ", receiveAccount=" + receiveAccount +
                ", authorizedSendAccount=" + authorizedSendAccount +
                '}';
    }
}
