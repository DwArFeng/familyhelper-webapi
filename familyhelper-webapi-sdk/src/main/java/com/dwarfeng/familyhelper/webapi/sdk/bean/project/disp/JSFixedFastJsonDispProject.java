package com.dwarfeng.familyhelper.webapi.sdk.bean.project.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.project.disp.DispProject;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示资产目录。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class JSFixedFastJsonDispProject implements Dto {

    private static final long serialVersionUID = -7812875704879348769L;

    public static JSFixedFastJsonDispProject of(DispProject dispProject) {
        if (Objects.isNull(dispProject)) {
            return null;
        } else {
            return new JSFixedFastJsonDispProject(
                    JSFixedFastJsonLongIdKey.of(dispProject.getKey()),
                    dispProject.getName(),
                    dispProject.getRemark(),
                    dispProject.getStatus(),
                    dispProject.getCreatedDate(),
                    dispProject.getModifiedDate(),
                    dispProject.getFinishedDate(),
                    FastJsonDispAccount.of(dispProject.getOwnerAccount()),
                    dispProject.getPermissionLevel()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "status", ordinal = 4)
    private int status;

    @JSONField(name = "created_date", ordinal = 5)
    private Date createdDate;

    @JSONField(name = "modified_date", ordinal = 6)
    private Date modifiedDate;

    @JSONField(name = "finished_date", ordinal = 7)
    private Date finishedDate;

    @JSONField(name = "owner_account", ordinal = 8)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 9)
    private Integer permissionLevel;

    public JSFixedFastJsonDispProject() {
    }

    public JSFixedFastJsonDispProject(
            JSFixedFastJsonLongIdKey key, String name, String remark, int status, Date createdDate, Date modifiedDate,
            Date finishedDate, FastJsonDispAccount ownerAccount, Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.finishedDate = finishedDate;
        this.ownerAccount = ownerAccount;
        this.permissionLevel = permissionLevel;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
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

    @Override
    public String toString() {
        return "JSFixedFastJsonDispProject{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", finishedDate=" + finishedDate +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
