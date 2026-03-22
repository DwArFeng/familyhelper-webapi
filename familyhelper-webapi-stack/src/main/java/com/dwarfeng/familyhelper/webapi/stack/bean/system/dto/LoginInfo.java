package com.dwarfeng.familyhelper.webapi.stack.bean.system.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 动态登录信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LoginInfo implements Dto {

    private static final long serialVersionUID = -1797271757110641991L;

    private StringIdKey accountKey;
    private String password;

    public LoginInfo() {
    }

    public LoginInfo(StringIdKey accountKey, String password) {
        this.accountKey = accountKey;
        this.password = password;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
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
        return "LoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                '}';
    }
}
