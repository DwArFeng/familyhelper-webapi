package com.dwarfeng.familyhelper.webapi.stack.bean.disp.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.Project;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示资产目录。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class DispProject implements Dto {

    private static final long serialVersionUID = 5419242914845940221L;

    public static DispProject of(
            Project project, DispAccount ownerAccount, Integer permissionLevel) {
        if (Objects.isNull(project)) {
            return null;
        } else {
            return new DispProject(
                    project.getKey(), project.getName(), project.getRemark(), project.getStatus(),
                    project.getCreatedDate(), project.getModifiedDate(), project.getFinishedDate(),
                    ownerAccount, permissionLevel
            );
        }
    }

    private LongIdKey key;
    private String name;
    private String remark;
    private int status;
    private Date createdDate;
    private Date modifiedDate;
    private Date finishedDate;
    private DispAccount ownerAccount;
    private Integer permissionLevel;

    public DispProject() {
    }

    public DispProject(
            LongIdKey key, String name, String remark, int status, Date createdDate, Date modifiedDate,
            Date finishedDate, DispAccount ownerAccount, Integer permissionLevel
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
        return "DispProject{" +
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
