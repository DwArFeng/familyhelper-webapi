package com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Message;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展留言。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class DispMessage implements Dto {

    private static final long serialVersionUID = 9121237601357835653L;

    public static DispMessage of(Message message, DispAccount sendAccount, DispAccount receiveAccount) {
        if (Objects.isNull(message)) {
            return null;
        } else {
            return new DispMessage(
                    message.getKey(),
                    message.getSendUserKey(),
                    message.getReceiveUserKey(),
                    message.getSubject(),
                    message.getRemark(),
                    message.getStatus(),
                    message.getSentDate(),
                    message.getReceivedDate(),
                    message.getAttachmentCount(),
                    message.getCreatedDate(),
                    message.isReceiveUserHide(),
                    sendAccount,
                    receiveAccount);
        }
    }

    private LongIdKey key;
    private StringIdKey sendAccountKey;
    private StringIdKey receiveAccountKey;
    private String subject;
    private String remark;
    private int status;
    private Date sentDate;
    private Date receivedDate;
    private int attachmentCount;
    private Date createdDate;
    private boolean receiveUserHide;
    private DispAccount sendAccount;
    private DispAccount receiveAccount;

    public DispMessage() {
    }

    public DispMessage(
            LongIdKey key, StringIdKey sendAccountKey, StringIdKey receiveAccountKey, String subject, String remark,
            int status, Date sentDate, Date receivedDate, int attachmentCount, Date createdDate,
            boolean receiveUserHide, DispAccount sendAccount, DispAccount receiveAccount
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public StringIdKey getSendAccountKey() {
        return sendAccountKey;
    }

    public void setSendAccountKey(StringIdKey sendAccountKey) {
        this.sendAccountKey = sendAccountKey;
    }

    public StringIdKey getReceiveAccountKey() {
        return receiveAccountKey;
    }

    public void setReceiveAccountKey(StringIdKey receiveAccountKey) {
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

    public DispAccount getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(DispAccount sendAccount) {
        this.sendAccount = sendAccount;
    }

    public DispAccount getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(DispAccount receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    @Override
    public String toString() {
        return "DispMessage{" +
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
