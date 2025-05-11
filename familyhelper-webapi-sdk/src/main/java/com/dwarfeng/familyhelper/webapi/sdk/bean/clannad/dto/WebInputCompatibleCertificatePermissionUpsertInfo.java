package com.dwarfeng.familyhelper.webapi.sdk.bean.clannad.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificatePermissionUpsertInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import java.util.Objects;

/**
 * WebInput 兼容性证件权限插入或更新信息。
 *
 * @author DwArFeng
 * @since 1.4.3
 */
public class WebInputCompatibleCertificatePermissionUpsertInfo implements Dto {

    private static final long serialVersionUID = -3305998680311798872L;

    public static CertificatePermissionUpsertInfo toStackBean(
            WebInputCompatibleCertificatePermissionUpsertInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            WebInputLongIdKey certificateKey = webInput.getCertificateKey();
            if (Objects.isNull(certificateKey)) {
                certificateKey = webInput.getCompatibleCertificateKey();
            }
            return new CertificatePermissionUpsertInfo(
                    WebInputLongIdKey.toStackBean(certificateKey),
                    WebInputStringIdKey.toStackBean(webInput.getUserKey()),
                    webInput.getPermissionLevel()
            );
        }
    }

    @JSONField(name = "certificate_key")
    @Valid
    private WebInputLongIdKey certificateKey;

    @JSONField(name = "account_book_key")
    @Valid
    private WebInputLongIdKey compatibleCertificateKey;

    @JSONField(name = "user_key")
    @Valid
    private WebInputStringIdKey userKey;

    @JSONField(name = "permission_level")
    private int permissionLevel;

    public WebInputCompatibleCertificatePermissionUpsertInfo() {
    }

    public WebInputLongIdKey getCertificateKey() {
        return certificateKey;
    }

    public void setCertificateKey(WebInputLongIdKey certificateKey) {
        this.certificateKey = certificateKey;
    }

    public WebInputLongIdKey getCompatibleCertificateKey() {
        return compatibleCertificateKey;
    }

    public void setCompatibleCertificateKey(WebInputLongIdKey compatibleCertificateKey) {
        this.compatibleCertificateKey = compatibleCertificateKey;
    }

    public WebInputStringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(WebInputStringIdKey userKey) {
        this.userKey = userKey;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    @Override
    public String toString() {
        return "WebInputCompatibleCertificatePermissionUpsertInfo{" +
                "certificateKey=" + certificateKey +
                ", compatibleCertificateKey=" + compatibleCertificateKey +
                ", userKey=" + userKey +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
