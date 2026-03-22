package com.dwarfeng.familyhelper.webapi.stack.bean.system.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 权限查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionInspectInfo implements Dto {

    private static final long serialVersionUID = 8147071140531479472L;

    private StringIdKey scopeKey;
    private StringIdKey accountKey;

    public PermissionInspectInfo() {
    }

    public PermissionInspectInfo(StringIdKey scopeKey, StringIdKey accountKey) {
        this.scopeKey = scopeKey;
        this.accountKey = accountKey;
    }

    public StringIdKey getScopeKey() {
        return scopeKey;
    }

    public void setScopeKey(StringIdKey scopeKey) {
        this.scopeKey = scopeKey;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    @Override
    public String toString() {
        return "PermissionInspectInfo{" +
                "scopeKey=" + scopeKey +
                ", accountKey=" + accountKey +
                '}';
    }
}
