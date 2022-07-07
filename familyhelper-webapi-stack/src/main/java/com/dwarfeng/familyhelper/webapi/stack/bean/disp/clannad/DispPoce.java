package com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Poce;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.PoceKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示证件权限。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispPoce implements Dto {

    private static final long serialVersionUID = -2556104971198792223L;

    public static DispPoce of(
            Poce Poce, DispCertificate certificate, DispAccount account
    ) {
        if (Objects.isNull(certificate)) {
            return null;
        } else {
            return new DispPoce(
                    Poce.getKey(), Poce.getPermissionLevel(), Poce.getRemark(), certificate, account
            );
        }
    }

    private PoceKey key;
    private int permissionLevel;
    private String remark;
    private DispCertificate certificate;
    private DispAccount account;

    public DispPoce() {
    }

    public DispPoce(PoceKey key, int permissionLevel, String remark, DispCertificate certificate, DispAccount account) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.certificate = certificate;
        this.account = account;
    }

    public PoceKey getKey() {
        return key;
    }

    public void setKey(PoceKey key) {
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

    public DispCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(DispCertificate certificate) {
        this.certificate = certificate;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPoce{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", certificate=" + certificate +
                ", account=" + account +
                '}';
    }
}
