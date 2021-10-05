package com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.PasswordUpdateInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 密码更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputPasswordUpdateInfo implements Dto {

    private static final long serialVersionUID = 4674597824924834885L;

    public static PasswordUpdateInfo toStackBean(WebInputPasswordUpdateInfo webInputPasswordUpdateInfo) {
        if (Objects.isNull(webInputPasswordUpdateInfo)) {
            return null;
        } else {
            return new PasswordUpdateInfo(
                    webInputPasswordUpdateInfo.getOldPassword(),
                    webInputPasswordUpdateInfo.getNewPassword()
            );
        }
    }

    @JSONField(name = "old_password")
    @NotNull
    private String oldPassword;

    @JSONField(name = "new_password")
    @NotNull
    private String newPassword;

    public WebInputPasswordUpdateInfo() {
    }

    public WebInputPasswordUpdateInfo(String oldPassword, String newPassword) {
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
        return "WebInputPasswordUpdateInfo{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
