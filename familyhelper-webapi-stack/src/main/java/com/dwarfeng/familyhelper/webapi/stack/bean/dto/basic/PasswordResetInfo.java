package com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 密码更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PasswordResetInfo implements Dto {

    private static final long serialVersionUID = -8263533515522228349L;

    private StringIdKey accountKey;
    private String newPassword;

    public PasswordResetInfo() {
    }

    public PasswordResetInfo(StringIdKey accountKey, String newPassword) {
        this.accountKey = accountKey;
        this.newPassword = newPassword;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordResetInfo{" +
                "accountKey=" + accountKey +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
