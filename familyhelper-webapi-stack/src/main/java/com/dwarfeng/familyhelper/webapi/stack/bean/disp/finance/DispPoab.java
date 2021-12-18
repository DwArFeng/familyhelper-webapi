package com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.Poab;
import com.dwarfeng.familyhelper.finance.stack.bean.key.PoabKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示账本权限。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class DispPoab implements Dto {

    private static final long serialVersionUID = -5471564720631172472L;

    public static DispPoab of(
            Poab poab, DispAccountBook accountBook, DispAccount account
    ) {
        if (Objects.isNull(accountBook)) {
            return null;
        } else {
            return new DispPoab(
                    poab.getKey(), poab.getPermissionLevel(), poab.getRemark(), accountBook, account
            );
        }
    }

    private PoabKey key;
    private int permissionLevel;
    private String remark;
    private DispAccountBook accountBook;
    private DispAccount account;

    public DispPoab() {
    }

    public DispPoab(PoabKey key, int permissionLevel, String remark, DispAccountBook accountBook, DispAccount account) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.accountBook = accountBook;
        this.account = account;
    }

    public PoabKey getKey() {
        return key;
    }

    public void setKey(PoabKey key) {
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

    public DispAccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(DispAccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPoab{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", accountBook=" + accountBook +
                ", account=" + account +
                '}';
    }
}
