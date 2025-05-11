package com.dwarfeng.familyhelper.webapi.sdk.bean.clannad.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispMessage;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * 可展留言。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class JSFixedFastJsonDispMessage implements Dto {

    private static final long serialVersionUID = 2478134376115221870L;

    public static JSFixedFastJsonDispMessage of(DispMessage dispMessage) {
        if (Objects.isNull(dispMessage)) {
            return null;
        } else {
            return new JSFixedFastJsonDispMessage(
                    JSFixedFastJsonLongIdKey.of(dispMessage.getKey()),
                    Optional.ofNullable(dispMessage.getSendAccountKey()).map(FastJsonStringIdKey::of).orElse(null),
                    Optional.ofNullable(dispMessage.getReceiveAccountKey()).map(FastJsonStringIdKey::of).orElse(null),
                    dispMessage.getSubject(),
                    dispMessage.getRemark(),
                    dispMessage.getStatus(),
                    dispMessage.getSentDate(),
                    dispMessage.getReceivedDate(),
                    dispMessage.getAttachmentCount(),
                    dispMessage.getCreatedDate(),
                    dispMessage.isReceiveUserHide(),
                    Optional.ofNullable(dispMessage.getSendAccount()).map(FastJsonDispAccount::of).orElse(null),
                    Optional.ofNullable(dispMessage.getReceiveAccount()).map(FastJsonDispAccount::of).orElse(null)
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "send_user_key", ordinal = 2)
    private FastJsonStringIdKey sendAccountKey;

    @JSONField(name = "receive_user_key", ordinal = 3)
    private FastJsonStringIdKey receiveAccountKey;

    @JSONField(name = "subject", ordinal = 4)
    private String subject;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    @JSONField(name = "status", ordinal = 6)
    private int status;

    @JSONField(name = "sent_date", ordinal = 7)
    private Date sentDate;

    @JSONField(name = "received_date", ordinal = 8)
    private Date receivedDate;

    @JSONField(name = "attachment_count", ordinal = 9)
    private int attachmentCount;

    @JSONField(name = "created_date", ordinal = 10)
    private Date createdDate;

    @JSONField(name = "receive_user_hide", ordinal = 11)
    private boolean receiveUserHide;

    @JSONField(name = "send_account", ordinal = 12)
    private FastJsonDispAccount sendAccount;

    @JSONField(name = "receive_account", ordinal = 13)
    private FastJsonDispAccount receiveAccount;

    public JSFixedFastJsonDispMessage() {
    }

    public JSFixedFastJsonDispMessage(
            JSFixedFastJsonLongIdKey key, FastJsonStringIdKey sendAccountKey, FastJsonStringIdKey receiveAccountKey,
            String subject, String remark, int status, Date sentDate, Date receivedDate, int attachmentCount,
            Date createdDate, boolean receiveUserHide,
            FastJsonDispAccount sendAccount, FastJsonDispAccount receiveAccount
    ) {
        this.key = key;
        this.sendAccountKey = sendAccountKey;
        this.receiveAccountKey = receiveAccountKey;
        this.subject = subject;
        this.remark = remark;
        this.status = status;
        this.sentDate = sentDate;
        this.receivedDate = receivedDate;
        this.attachmentCount = attachmentCount;
        this.createdDate = createdDate;
        this.receiveUserHide = receiveUserHide;
        this.sendAccount = sendAccount;
        this.receiveAccount = receiveAccount;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getSendAccountKey() {
        return sendAccountKey;
    }

    public void setSendAccountKey(FastJsonStringIdKey sendAccountKey) {
        this.sendAccountKey = sendAccountKey;
    }

    public FastJsonStringIdKey getReceiveAccountKey() {
        return receiveAccountKey;
    }

    public void setReceiveAccountKey(FastJsonStringIdKey receiveAccountKey) {
        this.receiveAccountKey = receiveAccountKey;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public int getAttachmentCount() {
        return attachmentCount;
    }

    public void setAttachmentCount(int attachmentCount) {
        this.attachmentCount = attachmentCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isReceiveUserHide() {
        return receiveUserHide;
    }

    public void setReceiveUserHide(boolean receiveUserHide) {
        this.receiveUserHide = receiveUserHide;
    }

    public FastJsonDispAccount getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(FastJsonDispAccount sendAccount) {
        this.sendAccount = sendAccount;
    }

    public FastJsonDispAccount getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(FastJsonDispAccount receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispMessage{" +
                "key=" + key +
                ", sendAccountKey=" + sendAccountKey +
                ", receiveAccountKey=" + receiveAccountKey +
                ", subject='" + subject + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", sentDate=" + sentDate +
                ", receivedDate=" + receivedDate +
                ", attachmentCount=" + attachmentCount +
                ", createdDate=" + createdDate +
                ", receiveUserHide=" + receiveUserHide +
                ", sendAccount=" + sendAccount +
                ", receiveAccount=" + receiveAccount +
                '}';
    }
}
