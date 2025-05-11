package com.dwarfeng.familyhelper.webapi.stack.bean.note.disp;

import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示个笔记本。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispNoteBook implements Dto {

    private static final long serialVersionUID = 4387926529537490378L;

    public static DispNoteBook of(
            NoteBook noteBook, DispAccount ownerAccount, Integer permissionLevel, boolean favorite) {
        if (Objects.isNull(noteBook)) {
            return null;
        } else {
            return new DispNoteBook(
                    noteBook.getKey(), noteBook.getName(), noteBook.getRemark(), noteBook.getCreatedDate(),
                    noteBook.getItemCount(), noteBook.getLastModifiedDate(), noteBook.getLastInspectedDate(),
                    ownerAccount, permissionLevel, favorite
            );
        }
    }

    private LongIdKey key;
    private String name;
    private String remark;
    private Date createdDate;
    private int itemCount;
    private Date lastModifiedDate;
    private Date lastInspectedDate;
    private DispAccount ownerAccount;
    private Integer permissionLevel;

    /**
     * @since 1.4.0
     */
    private boolean favorite;

    public DispNoteBook() {
    }

    public DispNoteBook(
            LongIdKey key, String name, String remark, Date createdDate, int itemCount, Date lastModifiedDate,
            Date lastInspectedDate, DispAccount ownerAccount, Integer permissionLevel, boolean favorite
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.createdDate = createdDate;
        this.itemCount = itemCount;
        this.lastModifiedDate = lastModifiedDate;
        this.lastInspectedDate = lastInspectedDate;
        this.ownerAccount = ownerAccount;
        this.permissionLevel = permissionLevel;
        this.favorite = favorite;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getLastInspectedDate() {
        return lastInspectedDate;
    }

    public void setLastInspectedDate(Date lastInspectedDate) {
        this.lastInspectedDate = lastInspectedDate;
    }

    public DispAccount getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(DispAccount ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "DispNoteBook{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createdDate=" + createdDate +
                ", itemCount=" + itemCount +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastInspectedDate=" + lastInspectedDate +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                ", favorite=" + favorite +
                '}';
    }
}
