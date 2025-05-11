package com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.note.sdk.bean.key.JSFixedFastJsonPonbKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispPonb;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示笔记本权限。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispPonb implements Dto {

    private static final long serialVersionUID = 1857082076049512698L;

    public static JSFixedFastJsonDispPonb of(DispPonb dispPonb) {
        if (Objects.isNull(dispPonb)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPonb(
                    JSFixedFastJsonPonbKey.of(dispPonb.getKey()),
                    dispPonb.getPermissionLevel(), dispPonb.getRemark(),
                    JSFixedFastJsonDispNoteBook.of(dispPonb.getNoteBook()),
                    FastJsonDispAccount.of(dispPonb.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPonbKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "note_book", ordinal = 4)
    private JSFixedFastJsonDispNoteBook noteBook;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPonb() {
    }

    public JSFixedFastJsonDispPonb(
            JSFixedFastJsonPonbKey key, int permissionLevel, String remark,
            JSFixedFastJsonDispNoteBook noteBook, FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.noteBook = noteBook;
        this.account = account;
    }

    public JSFixedFastJsonPonbKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPonbKey key) {
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

    public JSFixedFastJsonDispNoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(JSFixedFastJsonDispNoteBook noteBook) {
        this.noteBook = noteBook;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPonb{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", noteBook=" + noteBook +
                ", account=" + account +
                '}';
    }
}
