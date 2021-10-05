package com.dwarfeng.familyhelper.webapi.stack.bean.vo.system;

import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 账号。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Account implements Bean {

    private static final long serialVersionUID = -7457877806844011953L;

    private StringIdKey key;
    private String name;
    private boolean enabled;
    private String remark;

    public Account() {
    }

    public Account(StringIdKey key, String name, boolean enabled, String remark) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.remark = remark;
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

    @Override
    public String toString() {
        return "Account{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}
