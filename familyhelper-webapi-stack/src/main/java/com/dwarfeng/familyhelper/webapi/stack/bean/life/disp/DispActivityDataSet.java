package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示活动数据集合。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivityDataSet implements Dto {

    private static final long serialVersionUID = 4170772272583001703L;

    public static DispActivityDataSet of(
            ActivityDataSet activityDataSet, DispAccount ownerAccount, Integer permissionLevel) {
        if (Objects.isNull(activityDataSet)) {
            return null;
        } else {
            return new DispActivityDataSet(
                    activityDataSet.getKey(), activityDataSet.getName(), activityDataSet.getRemark(),
                    activityDataSet.getItemCount(), activityDataSet.getCreatedDate(), ownerAccount, permissionLevel
            );
        }
    }

    private LongIdKey key;
    private String name;
    private String remark;
    private int itemCount;
    private Date createdDate;
    private DispAccount ownerAccount;
    private Integer permissionLevel;

    public DispActivityDataSet() {
    }

    public DispActivityDataSet(
            LongIdKey key, String name, String remark, int itemCount, Date createdDate, DispAccount ownerAccount,
            Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.itemCount = itemCount;
        this.createdDate = createdDate;
        this.ownerAccount = ownerAccount;
        this.permissionLevel = permissionLevel;
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    @Override
    public String toString() {
        return "DispActivityDataSet{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", itemCount=" + itemCount +
                ", createdDate=" + createdDate +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
