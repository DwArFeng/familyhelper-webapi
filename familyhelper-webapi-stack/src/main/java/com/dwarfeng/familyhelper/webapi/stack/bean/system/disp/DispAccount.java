package com.dwarfeng.familyhelper.webapi.stack.bean.system.disp;

import com.dwarfeng.familyhelper.webapi.stack.bean.system.vo.Account;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Objects;

/**
 * 可展示账号。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public class DispAccount implements Bean {

    private static final long serialVersionUID = -2807245903120978117L;

    public static DispAccount of(Account account, String displayName) {
        if (Objects.isNull(account)) {
            return null;
        } else {
            return new DispAccount(
                    account.getKey(), account.getName(), account.isEnabled(), account.getRemark(), displayName
            );
        }
    }

    private StringIdKey key;
    private String name;
    private boolean enabled;
    private String remark;
    private String displayName;

    public DispAccount() {
    }

    public DispAccount(StringIdKey key, String name, boolean enabled, String remark, String displayName) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.remark = remark;
        this.displayName = displayName;
    }

    public StringIdKey getKey() {
        return key;
    }

    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "DispAccount{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
