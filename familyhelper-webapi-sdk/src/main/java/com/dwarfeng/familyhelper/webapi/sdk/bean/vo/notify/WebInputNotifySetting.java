package com.dwarfeng.familyhelper.webapi.sdk.bean.vo.notify;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.NotifySetting;
import com.dwarfeng.notify.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 通知设置。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class WebInputNotifySetting implements Bean {

    private static final long serialVersionUID = 261390326301194884L;

    public static NotifySetting toStackBean(WebInputNotifySetting webInputNotifySetting) {
        if (Objects.isNull(webInputNotifySetting)) {
            return null;
        } else {
            return new NotifySetting(
                    WebInputLongIdKey.toStackBean(webInputNotifySetting.getKey()),
                    webInputNotifySetting.getLabel(), webInputNotifySetting.isEnabled(),
                    webInputNotifySetting.getRequiredPermission(), webInputNotifySetting.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "label")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_LABEL)
    private String label;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "required_permission")
    @NotNull
    @NotEmpty
    @Length(max = com.dwarfeng.familyhelper.clannad.sdk.util.Constraints.LENGTH_ID)
    private String requiredPermission;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputNotifySetting() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(String requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputNotifySetting{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", enabled=" + enabled +
                ", requiredPermission='" + requiredPermission + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
