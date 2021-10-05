package com.dwarfeng.familyhelper.webapi.stack.bean.dto.system;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 注册信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class RegisterInfo implements Dto {

    private static final long serialVersionUID = 8915068738447842531L;

    private StringIdKey key;
    private boolean enabled;
    private String password;

    public RegisterInfo() {
    }

    public RegisterInfo(StringIdKey key, boolean enabled, String password) {
        this.key = key;
        this.enabled = enabled;
        this.password = password;
    }

    public StringIdKey getKey() {
        return key;
    }

    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                '}';
    }
}
