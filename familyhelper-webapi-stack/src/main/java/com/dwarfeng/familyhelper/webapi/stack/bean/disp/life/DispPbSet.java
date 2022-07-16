package com.dwarfeng.familyhelper.webapi.stack.bean.disp.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.PbSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示个人最佳集合。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispPbSet implements Dto {

    private static final long serialVersionUID = 2018555156515416417L;

    public static DispPbSet of(
            PbSet pbSet, DispAccount ownerAccount, Integer permissionLevel) {
        if (Objects.isNull(pbSet)) {
            return null;
        } else {
            return new DispPbSet(
                    pbSet.getKey(), pbSet.getName(), pbSet.getRemark(), pbSet.getCreatedDate(),
                    ownerAccount, permissionLevel
            );
        }
    }

    private LongIdKey key;
    private String name;
    private String remark;
    private Date createdDate;
    private DispAccount ownerAccount;
    private Integer permissionLevel;

    public DispPbSet() {
    }

    public DispPbSet(
            LongIdKey key, String name, String remark, Date createdDate, DispAccount ownerAccount,
            Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
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
        return "DispPbSet{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createdDate=" + createdDate +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
