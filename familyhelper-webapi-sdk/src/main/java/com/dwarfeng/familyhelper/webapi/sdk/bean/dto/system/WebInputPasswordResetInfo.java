package com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.PasswordResetInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 密码重置信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputPasswordResetInfo implements Dto {

    private static final long serialVersionUID = 2778916916551188432L;

    public static PasswordResetInfo toStackBean(WebInputPasswordResetInfo webInputPasswordResetInfo) {
        if (Objects.isNull(webInputPasswordResetInfo)) {
            return null;
        } else {
            return new PasswordResetInfo(
                    WebInputStringIdKey.toStackBean(webInputPasswordResetInfo.getAccountKey()),
                    webInputPasswordResetInfo.getNewPassword()
            );
        }
    }

    @JSONField(name = "account_key")
    @NotNull
    @Valid
    private WebInputStringIdKey accountKey;

    @JSONField(name = "new_password")
    @NotNull
    private String newPassword;

    public WebInputPasswordResetInfo() {
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
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
        return "WebInputPasswordResetInfo{" +
                "accountKey=" + accountKey +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
