package com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 可展示账号。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public class FastJsonDispAccount implements Bean {

    private static final long serialVersionUID = -5276783438793317464L;

    public static FastJsonDispAccount of(DispAccount dispAccount) {
        if (Objects.isNull(dispAccount)) {
            return null;
        } else {
            return new FastJsonDispAccount(
                    FastJsonStringIdKey.of(dispAccount.getKey()), dispAccount.getName(), dispAccount.isEnabled(),
                    dispAccount.getRemark(), dispAccount.getDisplayName()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "display_name", ordinal = 5)
    private String displayName;

    public FastJsonDispAccount() {
    }

    public FastJsonDispAccount(
            FastJsonStringIdKey key, String name, boolean enabled, String remark, String displayName
    ) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.remark = remark;
        this.displayName = displayName;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
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
        return "FastJsonDispAccount{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
