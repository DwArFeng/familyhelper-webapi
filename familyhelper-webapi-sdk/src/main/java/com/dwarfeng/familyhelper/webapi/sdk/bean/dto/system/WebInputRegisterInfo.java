package com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.RegisterInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 注册信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputRegisterInfo implements Dto {

    private static final long serialVersionUID = 1053485656797933360L;

    public static RegisterInfo toStackBean(WebInputRegisterInfo webInputRegisterInfo) {
        if (Objects.isNull(webInputRegisterInfo)) {
            return null;
        } else {
            return new RegisterInfo(
                    WebInputStringIdKey.toStackBean(webInputRegisterInfo.getKey()),
                    webInputRegisterInfo.isEnabled(),
                    webInputRegisterInfo.getPassword()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputStringIdKey key;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "password")
    @NotNull
    private String password;

    public WebInputRegisterInfo() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
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
        return "WebInputRegisterInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                '}';
    }
}
