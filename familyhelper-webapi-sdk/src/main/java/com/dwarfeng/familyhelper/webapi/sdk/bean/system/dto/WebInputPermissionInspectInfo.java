package com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionInspectInfo implements Bean {

    private static final long serialVersionUID = 3643181286856187740L;

    public static PermissionInspectInfo toStackBean(WebInputPermissionInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionInspectInfo(
                    WebInputStringIdKey.toStackBean(webInput.getScopeKey()),
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey())
            );
        }
    }

    @JSONField(name = "scope_key")
    @NotNull
    @Valid
    private WebInputStringIdKey scopeKey;

    @JSONField(name = "account_key")
    @NotNull
    @Valid
    private WebInputStringIdKey accountKey;

    public WebInputPermissionInspectInfo() {
    }

    public WebInputStringIdKey getScopeKey() {
        return scopeKey;
    }

    public void setScopeKey(WebInputStringIdKey scopeKey) {
        this.scopeKey = scopeKey;
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    @Override
    public String toString() {
        return "WebInputPermissionInspectInfo{" +
                "scopeKey=" + scopeKey +
                ", accountKey=" + accountKey +
                '}';
    }
}
