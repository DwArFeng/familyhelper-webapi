package com.dwarfeng.familyhelper.webapi.stack.bean.system.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 延期信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PostponeInfo implements Dto {

    private static final long serialVersionUID = 3960852843922577092L;

    private StringIdKey loginStateKey;

    public PostponeInfo() {
    }

    public PostponeInfo(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public StringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String toString() {
        return "PostponeInfo{" +
                "loginStateKey=" + loginStateKey +
                '}';
    }
}
