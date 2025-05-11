package com.dwarfeng.familyhelper.webapi.sdk.bean.system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.vo.Account;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 账号。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputAccount implements Bean {

    private static final long serialVersionUID = -3881042054418496615L;

    public static Account toStackBean(WebInputAccount webInputAccount) {
        if (Objects.isNull(webInputAccount)) {
            return null;
        } else {
            return new Account(
                    WebInputStringIdKey.toStackBean(webInputAccount.getKey()),
                    webInputAccount.getName(),
                    webInputAccount.isEnabled(),
                    webInputAccount.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputStringIdKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "remark")
    private String remark;

    public WebInputAccount() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
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
        return "WebInputAccount{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}
