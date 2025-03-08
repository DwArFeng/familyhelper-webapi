package com.dwarfeng.familyhelper.webapi.sdk.bean.dto.clannad;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateUpdateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 兼容性证件更新信息。
 *
 * @author DwArFeng
 * @since 1.4.3
 */
public class WebInputCompatibleCertificateUpdateInfo implements Dto {

    private static final long serialVersionUID = -8887284117281888284L;

    public static CertificateUpdateInfo toStackBean(WebInputCompatibleCertificateUpdateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            WebInputLongIdKey certificateKey = webInput.getCertificateKey();
            if (Objects.isNull(certificateKey)) {
                certificateKey = webInput.getCompatibleCertificateKey();
            }
            return new CertificateUpdateInfo(
                    WebInputLongIdKey.toStackBean(certificateKey),
                    webInput.getName(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "certificate_key")
    @Valid
    private WebInputLongIdKey certificateKey;

    @JSONField(name = "account_book_key")
    @Valid
    private WebInputLongIdKey compatibleCertificateKey;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @JSONField(name = "remark")
    private String remark;

    public WebInputCompatibleCertificateUpdateInfo() {
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

    @Override
    public String toString() {
        return "WebInputCompatibleCertificateUpdateInfo{" +
                "certificateKey=" + certificateKey +
                ", compatibleCertificateKey=" + compatibleCertificateKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
