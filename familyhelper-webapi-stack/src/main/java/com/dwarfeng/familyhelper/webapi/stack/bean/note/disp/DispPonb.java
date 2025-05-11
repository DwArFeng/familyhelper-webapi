package com.dwarfeng.familyhelper.webapi.stack.bean.note.disp;

import com.dwarfeng.familyhelper.note.stack.bean.entity.Ponb;
import com.dwarfeng.familyhelper.note.stack.bean.key.PonbKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示笔记本权限。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispPonb implements Dto {

    private static final long serialVersionUID = 8384072319528102630L;

    public static DispPonb of(
            Ponb Ponb, DispNoteBook noteBook, DispAccount account
    ) {
        if (Objects.isNull(noteBook)) {
            return null;
        } else {
            return new DispPonb(
                    Ponb.getKey(), Ponb.getPermissionLevel(), Ponb.getRemark(), noteBook, account
            );
        }
    }

    private PonbKey key;
    private int permissionLevel;
    private String remark;
    private DispNoteBook noteBook;
    private DispAccount account;

    public DispPonb() {
    }

    public DispPonb(PonbKey key, int permissionLevel, String remark, DispNoteBook noteBook, DispAccount account) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.noteBook = noteBook;
        this.account = account;
    }

    public PonbKey getKey() {
        return key;
    }

    public void setKey(PonbKey key) {
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

    public DispNoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(DispNoteBook noteBook) {
        this.noteBook = noteBook;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPonb{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", noteBook=" + noteBook +
                ", account=" + account +
                '}';
    }
}
