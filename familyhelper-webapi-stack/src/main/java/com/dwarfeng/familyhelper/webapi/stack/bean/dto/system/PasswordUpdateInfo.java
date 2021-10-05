package com.dwarfeng.familyhelper.webapi.stack.bean.dto.system;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 密码更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PasswordUpdateInfo implements Dto {

    private static final long serialVersionUID = -1373294376248190928L;

    private String oldPassword;
    private String newPassword;

    public PasswordUpdateInfo() {
    }

    public PasswordUpdateInfo(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordUpdateInfo{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
