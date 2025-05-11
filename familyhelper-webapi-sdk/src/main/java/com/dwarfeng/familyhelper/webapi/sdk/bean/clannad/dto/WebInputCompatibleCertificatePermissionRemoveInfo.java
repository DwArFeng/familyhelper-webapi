package com.dwarfeng.familyhelper.webapi.sdk.bean.clannad.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificatePermissionRemoveInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import java.util.Objects;

/**
 * WebInput 兼容性证件权限删除信息。
 *
 * @author DwArFeng
 * @since 1.4.3
 */
public class WebInputCompatibleCertificatePermissionRemoveInfo implements Dto {

    private static final long serialVersionUID = -7921654849189640457L;

    public static CertificatePermissionRemoveInfo toStackBean(
            WebInputCompatibleCertificatePermissionRemoveInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            WebInputLongIdKey certificateKey = webInput.getCertificateKey();
            if (Objects.isNull(certificateKey)) {
                certificateKey = webInput.getCompatibleCertificateKey();
            }
            return new CertificatePermissionRemoveInfo(
                    WebInputLongIdKey.toStackBean(certificateKey),
                    WebInputStringIdKey.toStackBean(webInput.getUserKey())
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

    public WebInputCompatibleCertificatePermissionRemoveInfo() {
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

    @Override
    public String toString() {
        return "WebInputCompatibleCertificatePermissionRemoveInfo{" +
                "certificateKey=" + certificateKey +
                ", compatibleCertificateKey=" + compatibleCertificateKey +
                ", userKey=" + userKey +
                '}';
    }
}
