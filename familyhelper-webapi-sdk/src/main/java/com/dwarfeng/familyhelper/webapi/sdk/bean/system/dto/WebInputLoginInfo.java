package com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.LoginInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 动态登录信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputLoginInfo implements Bean {

    private static final long serialVersionUID = 654816706426071895L;

    public static LoginInfo toStackBean(WebInputLoginInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LoginInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()),
                    webInput.getPassword()
            );
        }
    }

    @JSONField(name = "account_key")
    @NotNull
    @Valid
    private WebInputStringIdKey accountKey;

    @JSONField(name = "password")
    @NotNull
    @NotEmpty
    private String password;

    public WebInputLoginInfo() {
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "WebInputLoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                '}';
    }
}
