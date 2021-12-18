package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.finance.sdk.bean.key.JSFixedFastJsonPoabKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispPoab;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示账本权限。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class JSFixedFastJsonDispPoab implements Dto {

    private static final long serialVersionUID = 1395709821024285142L;

    public static JSFixedFastJsonDispPoab of(DispPoab dispPoab) {
        if (Objects.isNull(dispPoab)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPoab(
                    JSFixedFastJsonPoabKey.of(dispPoab.getKey()),
                    dispPoab.getPermissionLevel(),
                    dispPoab.getRemark(),
                    JSFixedFastJsonDispAccountBook.of(dispPoab.getAccountBook()),
                    FastJsonDispAccount.of(dispPoab.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPoabKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "account_book", ordinal = 4)
    private JSFixedFastJsonDispAccountBook accountBook;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPoab() {
    }

    public JSFixedFastJsonDispPoab(
            JSFixedFastJsonPoabKey key, int permissionLevel, String remark, JSFixedFastJsonDispAccountBook accountBook,
            FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.accountBook = accountBook;
        this.account = account;
    }

    public JSFixedFastJsonPoabKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPoabKey key) {
        this.key = key;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonDispAccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(JSFixedFastJsonDispAccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPoab{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", accountBook=" + accountBook +
                ", account=" + account +
                '}';
    }
}
