package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示个笔记本。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispNoteBook implements Dto {

    private static final long serialVersionUID = -6041527795986273127L;

    public static JSFixedFastJsonDispNoteBook of(DispNoteBook dispNoteBook) {
        if (Objects.isNull(dispNoteBook)) {
            return null;
        } else {
            return new JSFixedFastJsonDispNoteBook(
                    JSFixedFastJsonLongIdKey.of(dispNoteBook.getKey()),
                    dispNoteBook.getName(),
                    dispNoteBook.getRemark(),
                    dispNoteBook.getCreatedDate(),
                    dispNoteBook.getItemCount(),
                    dispNoteBook.getLastModifiedDate(),
                    dispNoteBook.getLastInspectedDate(),
                    FastJsonDispAccount.of(dispNoteBook.getOwnerAccount()),
                    dispNoteBook.getPermissionLevel(),
                    dispNoteBook.isFavorite()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "created_date", ordinal = 4)
    private Date createdDate;

    @JSONField(name = "item_count", ordinal = 5)
    private int itemCount;

    @JSONField(name = "last_modified_date", ordinal = 6)
    private Date lastModifiedDate;

    @JSONField(name = "last_inspected_date", ordinal = 7)
    private Date lastInspectedDate;

    @JSONField(name = "owner_account", ordinal = 8)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 9)
    private Integer permissionLevel;

    @JSONField(name = "favorite", ordinal = 10)
    private boolean favorite;

    public JSFixedFastJsonDispNoteBook() {
    }

    public JSFixedFastJsonDispNoteBook(
            JSFixedFastJsonLongIdKey key, String name, String remark, Date createdDate, int itemCount,
            Date lastModifiedDate, Date lastInspectedDate, FastJsonDispAccount ownerAccount, Integer permissionLevel,
            boolean favorite
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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

    public FastJsonDispAccount getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(FastJsonDispAccount ownerAccount) {
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
        return "JSFixedFastJsonDispNoteBook{" +
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
